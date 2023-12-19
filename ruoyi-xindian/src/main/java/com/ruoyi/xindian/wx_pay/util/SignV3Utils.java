package com.ruoyi.xindian.wx_pay.util;
 
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Random;
 
@Component
public class SignV3Utils {
 
        //V3主商户ID
        private static String merchantId;
        //微信商户平台APIv3证书序列号
        private static String certificateSerialNo;
        //私钥（不要把私钥文件暴露在公共场合，如上传到Github，写在客户端代码等。）
        private static String privateKey;
 
        //配置文件配置好主商户号
//        @Value("${wx-gzh.mchid}")
        public void setMerchantId(String merchantId) {
            SignV3Utils.merchantId = merchantId;
        }
        //配置文件配置好序列号
//        @Value("${wx-gzh.certificateSerialNo}")
        public void setCertificateSerialNo(String certificateSerialNo) {
            SignV3Utils.certificateSerialNo = certificateSerialNo;
        }
        //配置文件配置好私钥
//        @Value("${wx-gzh.privateKey}")
        public void setPrivateKey(String privateKey) {
            SignV3Utils.privateKey = privateKey;
        }
        /**
         * 使用方法
         * @param method 请求方法
         * @param url 请求url
         * @param body 请求内容
         * @return
         */
        public static HashMap<String, String> getSignMap(String method, String url, String body) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, SignatureException, FileNotFoundException {
            String authorization = getSign(method, url, body);
 
            HashMap<String, String> headsMap = new HashMap<>();
            headsMap.put("Authorization", authorization);
            headsMap.put("Content-Type", "application/json");
            headsMap.put("Accept", "application/json");
 
            return headsMap;
        }
 
        public static String getSign(String method, String url, String body) throws NoSuchAlgorithmException, SignatureException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, FileNotFoundException {
            return "WECHATPAY2-SHA256-RSA2048 " + getToken(method, HttpUrl.parse(url), body);
        }
 
        public static String getToken(String method, HttpUrl url, String body) throws UnsupportedEncodingException, SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, FileNotFoundException {
            String nonceStr = nonceString();
            long timestamp = System.currentTimeMillis() / 1000;
            String message = buildMessage(method, url, timestamp, nonceStr, body);
            String signature = sign(message.getBytes("utf-8"));
            return "mchid=\"" + merchantId + "\","
                    + "nonce_str=\"" + nonceStr + "\","
                    + "timestamp=\"" + timestamp + "\","
                    + "serial_no=\"" + certificateSerialNo + "\","
                    + "signature=\"" + signature + "\"";
        }
 
        public static String sign(byte[] message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, FileNotFoundException {
            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new FileInputStream(ResourceUtils.getFile(privateKey)));
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(merchantPrivateKey);
            sign.update(message);
 
            return Base64.getEncoder().encodeToString(sign.sign());
        }
 
        public static String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
            String canonicalUrl = url.encodedPath();
            if (url.encodedQuery() != null) {
                canonicalUrl += "?" + url.encodedQuery();
            }
 
            return method + "\n"
                    + canonicalUrl + "\n"
                    + timestamp + "\n"
                    + nonceStr + "\n"
                    + body + "\n";
        }
 
 
        private static PrivateKey getPKCS8PrivateKey(String strPk) throws NoSuchAlgorithmException, InvalidKeySpecException {
            String realPK = strPk.replaceAll("-----END PRIVATE KEY-----", "")
                    .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("\n", "");
 
            byte[] b1 = Base64.getDecoder().decode(realPK);
 
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
 
            KeyFactory kf = KeyFactory.getInstance("RSA");
 
            return kf.generatePrivate(spec);
        }
 
        public static String nonceString() {
 
            String currTime = String.format("%d", (long) System.currentTimeMillis() / 1000);
 
            String strTime = currTime.substring(8, currTime.length());
 
            Random random = new Random();
            int num = (int) (random.nextDouble() * (1000000 - 100000) + 100000);
            String code = String.format("%06d", num);
 
            String nonce_str = currTime.substring(2) + code;
            return nonce_str;
 
        }
 
}