package com.ruoyi.xindian.chatECG.controller;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private RestTemplate restTemplate;

    @Resource
    private ChatQuizService chatQuizService;


    @PostMapping("/proxyRequest")
    public HashMap<String,Object> proxyRequest(@RequestBody Chat chat) throws ParseException {
        LoginUser loginUser = SecurityUtils.getLoginUser();
//        //定义发送数据
//        HttpHeaders headers = new HttpHeaders(); //构建请求头
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        Map<String, Object> paramsMap = new HashMap<>();
//        paramsMap.put("query", chat.getText());
//        paramsMap.put("stream", false);
//        paramsMap.put("knowledge_base_name", "samples");
//        paramsMap.put("top_k", 5);
//        paramsMap.put("score_threshold", 0.5);
//        paramsMap.put("model_name","Qwen-14B-Chat");
//        paramsMap.put("temperature",0.8);
//        paramsMap.put("max_tokens",0);
//        paramsMap.put("prompt_name","default");
//        JSONArray objects = JSON.parseArray(chat.getHistory());
//        if (objects!=null){
//            paramsMap.put("history", objects);
//        }else {
//            paramsMap.put("history", new String[]{});
//        }
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
////        String url = "http://219.155.7.235:6025/";
//        String url = "https://ecg.mindyard.cn/chat/knowledge_base_chat";
        HashMap<String,Object> sendMessageVo=new HashMap<>();
//        try {
//            sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
//        }catch (Exception e){
//            sendMessageVo=new HashMap<>();
//            sendMessageVo.put("response","对不起，网络出小差了");
//            System.out.println(e);
//        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = new Date();
//        sendMessageVo.put("responseTime",simpleDateFormat.format(parse));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        HashMap<String,Object> finalResult = sendMessageVo;
        CompletableFuture.runAsync(() ->{
            System.out.println("异步线程 =====> 保存数据 =====> " + new Date());
            try{
                DocVO docVO = new DocVO();
                docVO.setValue(chat.getHistory());
                docVO.setLabel(chat.getText());
                String jsonString = JSONObject.toJSONString(docVO);
                ChatQuiz chatQuiz = new ChatQuiz();
                chatQuiz.setUserId(loginUser.getUserId());
                chatQuiz.setCreateTime(new Date());
                chatQuiz.setConversationId(chat.getConversationId());
                chatQuiz.setMessageContent(jsonString);
                chatQuiz.setMessageType("text");
                chatQuiz.setResponseTime(parse);
                if (StringUtils.isNotEmpty(chat.getTitle())){
                    chatQuiz.setTitle(chat.getTitle());
                }
                List<ChatQuiz> chatQuizListIsNotTet = chatQuizService.getChatQuizListIsNotTet(chatQuiz);
                if (chatQuizListIsNotTet==null|| chatQuizListIsNotTet.isEmpty()){
                    chatQuizService.insertChatQuiz(chatQuiz);
                }else {
                    chatQuiz.setTitle(null);
                    chatQuizService.insertChatQuiz(chatQuiz);
                }
            }catch (Exception e){
                System.out.println(e);
            }
            System.out.println("异步线程 =====> 结束保存数据 =====> " + new Date());
        },executorService);
        executorService.shutdown(); // 回收线程池
        return sendMessageVo;
    }

    @PostMapping("/appProxyRequest")
    public Map<String, Object> appProxyRequest(@RequestBody Chat chat) throws ParseException {
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("prompt", chat.getText());
        JSONArray objects = JSON.parseArray(chat.getHistory());
        if (objects!=null){
            paramsMap.put("history", objects);
        }else {
            paramsMap.put("history", new String[]{});
        }

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
//        String url = "http://219.155.7.235:6025/";
        String url = "http://202.102.249.124:6025/";
        HashMap<String,Object> sendMessageVo=new HashMap<>();
        try {
            sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
        }catch (Exception e){
            sendMessageVo=new HashMap<>();
            sendMessageVo.put("response","对不起，网络出小差了");
            System.out.println(e);
        }
        System.out.println(sendMessageVo.get("response"));

        return sendMessageVo;
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
    public AjaxResult updateConversation(@RequestBody ChatQuiz chatQuiz){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        chatQuiz.setUserId(loginUser.getUserId());
        return AjaxResult.success(chatQuizService.updateConversation(chatQuiz)>0);

    }


}
