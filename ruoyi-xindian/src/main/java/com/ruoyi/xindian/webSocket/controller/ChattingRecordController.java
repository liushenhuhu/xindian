package com.ruoyi.xindian.webSocket.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.xindian.webSocket.domain.ChattingRecord;
import com.ruoyi.xindian.webSocket.service.ChattingRecordService;
import com.ruoyi.xindian.webSocket.vo.SnVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/chatting")
public class ChattingRecordController extends BaseController {




    @Resource
    private ChattingRecordService chattingRecordService;


    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 修改聊天状态
     * @param chattingRecord
     * @return
     */
    @PutMapping("/putChattingStatus")
    public int putChattingStatus(@RequestBody ChattingRecord chattingRecord){
        return chattingRecordService.putChattingStatus(chattingRecord);
    }


    /**
     * 查询聊天记录（做分页）
     */
    @GetMapping("/getChattingList")
    public TableDataInfo getChattingList(ChattingRecord chattingRecord){
        startPage();
        return getDataTable(chattingRecordService.getChattingList(chattingRecord));
    }


    /**
     * 查询未读消息，最后一条消息
     * @param chattingRecord
     * @return
     */
    @GetMapping("/getChattingByUserList")
    public AjaxResult getChattingByUserList(ChattingRecord chattingRecord){

        return AjaxResult.success(chattingRecordService.getChattingByUserList(chattingRecord));
    }

    @GetMapping("/noToken/getUserSn")
    public AjaxResult getUserSn(String phone){
        if (Boolean.TRUE.equals(redisTemplate.hasKey("login_user_dev_sn:"+phone))){
            return AjaxResult.success("操作成功",redisTemplate.opsForValue().get("login_user_dev_sn:"+phone));
        }
        return AjaxResult.success();
    }

    @PostMapping("/noToken/addUserSn")
    public AjaxResult addUserSn(@RequestBody SnVo snVo){
        redisTemplate.opsForValue().set("login_user_dev_sn:"+snVo.getPhone(),snVo.getSn());
        return AjaxResult.success();
    }
}
