package com.ruoyi.xindian.order.listenner;

import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.alert_log.service.IAlertLogService;
import com.ruoyi.xindian.ecgCount.controller.EcgCountController;
import com.ruoyi.xindian.ecgCount.service.EcgCountService;
import com.ruoyi.xindian.equipment.service.EquipmentHeadingCodeService;
import com.ruoyi.xindian.report.config.WxMsgRunConfig;
import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import com.ruoyi.xindian.wx_pay.controller.WXPayController;
import com.ruoyi.xindian.wx_pay.service.OrderInfoService;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * redis失效key监听
 * 
 * @author 郑文
 *
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private WXPublicRequest wxPublicRequest;


    @Resource
    private IAlertLogService alertLogService;

    @Resource
    private WxMsgRunConfig wxMsgRunConfig;

    @Resource
    private WXPayController wxPayController;

    @Resource
    private EquipmentHeadingCodeService equipmentHeadingCodeService;


    @Resource
    private EcgCountService ecgCountService;

    @Resource
    private EcgCountController ecgCountController;

    private final Lock lock = new ReentrantLock();

    /**
     * redis失效key事件处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // message.toString()可以获取失效的key



        lock.lock();
        try {
            String expiredKey = message.toString();
            String[] split = expiredKey.split(":");
            String[] split1 = expiredKey.split("!");
            if (split[0].equals("order")){
                System.out.println("订单创建15分钟，开始判断订单是否支付并进行数据删除-------------------");
                orderInfoService.redisOrderKey(split[1]);
                return;
            }
            if (split[0].equals("orderQuery")){
                wxPayController.orderQuery(split[1]);
            }
            if (split[0].equals("invoice")){
                try {
                    wxPublicRequest.getInvoiceState(split[1]);
                } catch (Exception e) {
                    System.out.println(e);
                }
                return;
            }
            if (split1[0].equals("getEquipmentCodeTwo")){

                try {
                    equipmentHeadingCodeService.selectCodeState(split1[1]);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            if (split1[0].equals("getEquipmentCodeSF")){
                try {
                    equipmentHeadingCodeService.getCodeStatus(split1[1]);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            if (split1[0].equals("getEquipmentCodeT15")){
                try {
                    equipmentHeadingCodeService.selectCodeState15(split1[1]);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            if (split1[0].equals("getEquipmentCodeAgainTwo")){

                try {
                    equipmentHeadingCodeService.selectCodeStateAgain(split1[1]);
                }catch (Exception e){
                    System.out.println(e);
                }
            }

            //用于提交心电，加入抢单，十分钟过期，判断有没有诊断
            if (split[0].equals("reportPT")){
                try {
                   wxMsgRunConfig.redisTOrAdd(split[1]);
                } catch (Exception e) {
                    System.out.println(e);
                }
                return;
            }

            //用于医生诊断，判断诊断30分钟内是否
            if (split[0].equals("reportDT")){
                try {
                    wxMsgRunConfig.redisDTTime(split[1]);
                } catch (Exception e) {
                    System.out.println(e);
                }
                return;
            }
            if (expiredKey.equals("ecgCountType")){
                ecgCountController.getTypeList();
            }
            if (expiredKey.equals("ecgCountTypeAge")){
                ecgCountController.ageList(new AgeStatistics());
            }
            if (expiredKey.equals("ecgAgeYoung")){
                ecgCountService.getAgeYoung();
            }
            if (expiredKey.equals("earlyLogTest01")){
                AlertLog alertLog = new AlertLog();
                alertLog.setEcgType("J12");
                alertLog.setRedisKey("earlyLogTest01");
                alertLogService.redisEarly(alertLog);
                return;
            }
            if (expiredKey.equals("earlyLogTest02")){
                AlertLog alertLog = new AlertLog();
                alertLog.setEcgType("Jsingle");
                alertLog.setRedisKey("earlyLogTest02");
                alertLogService.redisEarly(alertLog);
                return;
            }
            if (expiredKey.equals("earlyLogTest03")){
                AlertLog alertLog = new AlertLog();
                alertLog.setEcgType("D12");
                alertLog.setRedisKey("earlyLogTest03");
                alertLogService.redisEarly(alertLog);
                return;
            }
            if (expiredKey.equals("earlyLogTest04")){
                AlertLog alertLog = new AlertLog();
                alertLog.setEcgType("Dsingle");
                alertLog.setRedisKey("earlyLogTest04");
                alertLogService.redisEarly(alertLog);
            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            lock.unlock();
        }


    }
}
