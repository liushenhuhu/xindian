package com.ruoyi.xindian.wx_pay.util;


import org.apache.http.client.HttpClient;

/**
 * 常量
 */
public class WXPayConstants {

    /*
        自定义内容     */
    /*支付*/
    public static final String APP_ID = "wx50da6a0dfc5c45c0";//appid  填写你们自己的 小程序
    public static final String WX_PUBLIC_ID ="wx219c20026db0351e";//appid  填写你们自己的 公众号
    public static final String MCH_ID = "1645541073";//商家ID   填写你们自己的
    public static final String SECRET = "e74ff167d287ba25aefc87d9afe5fcf5";//微信支付
    public static final String WX_PUBLIC_SECRET = "c55d24177dc7f5cff61d8bebfdaa7d51";//微信公众号
    public static final String WX_SECRET = "4a10ac992f6bd399982aa1ec5e67a241";//微信小程序
    public static final String OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String NOTIFY_URL = "";//回调ip
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//统一下单接口
//    public static final String CALLBACK_URL = "http://localhost:83/dev-api/api/v1/callBack";//微信服务器调用支付结果通知路径
//    public static final String CALLBACK_URL = "http://8m7bff.natappfree.cc/api/v1/callBack";//微信服务器调用支付结果通知路径
    public static final String CALLBACK_URL = "https://ecg.mindyard.cn:84/prod-api/api/v1/callBack";//微信服务器调用支付结果通知路径
//    public static final String CALLBACK_URL = "https://develop.mindyard.cn:84/prod-api/api/v1/callBack";//微信服务器调用支付结果通知路径
    public static final String PATERNER_KEY = "ADFHJWhduenfks17shGDYE6278jfww12";//商户key秘钥   填写你们自己的
    /*退款*/
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";//申请退款路径接口

    public static final String WE_CHAT_CALL_BACK_DOMAIN_URL = " http://6nxme4.natappfree.cc";//获取用户

    
//    下边这些没啥用：
    public static enum SignType {
        MD5
    }

    public static final String DOMAIN_API = "api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "apius.mch.weixin.qq.com";
    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";
    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";
    public static final String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";
    public static final String USER_AGENT = WXPAYSDK_VERSION +
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();
    public static final String MICROPAY_URL_SUFFIX     = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX   = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX      = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX   = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX       = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX  = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX       = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX     = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";
    // sandbox
    public static final String SANDBOX_MICROPAY_URL_SUFFIX     = "/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX   = "/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL_SUFFIX      = "/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX   = "/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL_SUFFIX       = "/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX  = "/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL_SUFFIX       = "/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL_SUFFIX     = "/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";
}
