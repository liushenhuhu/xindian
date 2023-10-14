package com.ruoyi.xindian.chatECG.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.xindian.chatECG.domain.Chat;
import com.ruoyi.xindian.chatECG.domain.ChatQuiz;
import com.ruoyi.xindian.chatECG.service.ChatQuizService;
import com.ruoyi.xindian.patient_management.vo.DocVO;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lixinlong
 * 2023/7/24 17:30
 */
@RestController
@RequestMapping("/proxyRequest")
public class ChatECGController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);


    @Resource
    private ChatQuizService chatQuizService;


    @PostMapping("/proxyRequest")
    public JSONObject proxyRequest(@RequestBody Chat chat) throws ParseException {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        //定义发送数据
        JSONObject param = new JSONObject();
        param.put("prompt", chat.getText());
        param.put("history", JSON.parseArray(chat.getHistory()));
        //定义接收数据
        JSONObject result = new JSONObject();


        String url = "http://202.102.249.124:6025/";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        //请求参数转JOSN字符串
        StringEntity entity = new StringEntity(JSONObject.toJSONString(param), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        try {
            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = JSON.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("error", "连接错误！");
        }
            //关闭连接
        try {
            client.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        System.out.println(result.get("response"));


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = new Date();
        result.put("responseTime",simpleDateFormat.format(parse));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        JSONObject finalResult = result;
        CompletableFuture.runAsync(() ->{
            System.out.println("异步线程 =====> 保存数据 =====> " + new Date());
            try{
                DocVO docVO = new DocVO();
                docVO.setValue(finalResult.get("response").toString());
                docVO.setLabel(chat.getText());
                String jsonString = JSONObject.toJSONString(docVO);
                ChatQuiz chatQuiz = new ChatQuiz();
                chatQuiz.setUserId(loginUser.getUserId());
                chatQuiz.setCreateTime(chat.getCreateTime());
                chatQuiz.setConversationId(chat.getConversationId());
                chatQuiz.setMessageContent(jsonString);
                chatQuiz.setMessageType("text");
                chatQuiz.setResponseTime(parse);
                if (StringUtils.isNotEmpty(chat.getTitle())){
                    chatQuiz.setTitle(chat.getTitle());
                }
               chatQuizService.insertChatQuiz(chatQuiz);

            }catch (Exception e){
                System.out.println(e);
            }
            System.out.println("异步线程 =====> 结束保存数据 =====> " + new Date());
        },executorService);
        executorService.shutdown(); // 回收线程池
        return result;
    }


    /**
     * 查询用户窗口
     */
    @GetMapping("/getConversation")
    public AjaxResult getConversation(){
        LoginUser loginUser = SecurityUtils.getLoginUser();

        List<ChatQuiz> chatQuizList = chatQuizService.getConversation(loginUser.getUserId());
        return AjaxResult.success(chatQuizList);
    }

    /**
     * 查询用户指定窗口下的所有历史记录
     * @param conversationId
     * @return
     */
    @GetMapping("/getChatQuizList")
    public AjaxResult getChatQuizList(Integer conversationId){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        ChatQuiz chatQuiz = new ChatQuiz();
        chatQuiz.setConversationId(conversationId);
        chatQuiz.setUserId(loginUser.getUserId());

        List<ChatQuiz> chatQuizList = chatQuizService.getChatQuizList(chatQuiz);
        return AjaxResult.success(chatQuizList);
    }

    @DeleteMapping("/deleteConversation")
    public AjaxResult deleteConversation(Integer conversationId){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        ChatQuiz chatQuiz = new ChatQuiz();
        chatQuiz.setConversationId(conversationId);
        chatQuiz.setUserId(loginUser.getUserId());
        int i = chatQuizService.deleteConversation(chatQuiz);
        return AjaxResult.success(i>0);
    }

    @PutMapping("/updateConversation")
    public AjaxResult updateConversation(ChatQuiz chatQuiz){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        chatQuiz.setUserId(loginUser.getUserId());
        return AjaxResult.success(chatQuizService.updateConversation(chatQuiz)>0);

    }


}
