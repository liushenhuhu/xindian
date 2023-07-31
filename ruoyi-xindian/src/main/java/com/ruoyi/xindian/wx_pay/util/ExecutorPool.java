package com.ruoyi.xindian.wx_pay.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * @author lixinlong
 * 2023/7/28 18:17
 */
@Component
public class ExecutorPool {

    //线程池最大线程数量
    private final static int MAXTHREAD = 24;

    //初始化线程数量
    private final static int THREADSZIE = 20;

    //固定长度线程池
    private final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(THREADSZIE, MAXTHREAD, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    //重复发送次数
    private final static int sentNum = 5;


    @Resource
    private  WXPublicRequest wxPublicRequest;

    /**
     * 推送消息的主方法
     * @param msgList
     */
    public  void send(LinkedList<String> msgList) {

        if (msgList == null) {
            throw new RuntimeException("无消息可推送");
        }

        int pushNum=THREADSZIE>msgList.size()?msgList.size():THREADSZIE;
        CountDownLatch countDownLatch= new CountDownLatch(pushNum);

        for (int i=0;i<pushNum;i++){
            String msg = msgList.removeFirst();
            //消息推送
            poolSend(msg,countDownLatch);
        }

        try {
            //阻塞主线程，等待所有子线程执行完
            countDownLatch.await();
            TimeUnit.SECONDS.sleep(1);

        }catch (InterruptedException e){
            throw new RuntimeException("线程中断"+e);
        }

    }

    /**
     * 多线程推送消息处理
     * @param msg
     * @param
     * @param
     * @param countDownLatch
     */
    private void poolSend(String msg,CountDownLatch countDownLatch) {
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //boolean result = sendMessage(msg, successList);
                    sendMessage(msg);
                    //消息推送失败处理
                    /*if (!result) {
                        repeatSent(result,msg, successList);
                    }*/

                } catch (Exception e) {
                    throw new RuntimeException("多线程推送消息异常"+e);
                }
                countDownLatch.countDown();
            }
        });
    }

    /**
     * 推送消息,这里假设了消息推送失败的场景
     *
     * @param msg
     * @param
     * @return
     */
    private  boolean sendMessage(String msg) {
        boolean isFlag = false;
        if (!isFlag) {
            wxPublicRequest.sendOrderMsg("你好，有一条新的问诊订单",msg, "诊断订单","患者提交了一个心电订单");
            isFlag = true;
        }
        return isFlag;
    }



}
