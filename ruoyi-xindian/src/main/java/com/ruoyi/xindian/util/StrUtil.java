package com.ruoyi.xindian.util;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class StrUtil {

    public static  String[] Lead={"I", "II", "III", "aVR", "aVL", "aVF", "V1", "V2", "V3", "V4", "V5", "V6"};
    /**
     *二维数组字符串转二维数组
     * @param str
     * @return
     */
    public static int[][] StrToList(String str){
        str = str.replaceAll("\\[\\[", "").replaceAll("\\]\\]", ""); // 去掉两端的括号
        String[] rows = str.split("\\],\\["); // 按照行分割
        int rowCount = rows.length;
        int colCount = rows[0].split(",").length;

        int[][] arr = new int[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            String[] cols = rows[i].split(",");
            for (int j = 0; j < colCount; j++) {
                arr[i][j] = Integer.parseInt(cols[j]);
            }
        }
        return arr;
    }

    /**
     * 判断12导数据
     * @param flag
     * @param threshold
     * @throws JSONException
     */
    public static boolean isSameDir(String str1,String str2,String flag,double threshold) throws JSONException {
        for (String s : Lead) {
            JSONObject jsonObject1 = new JSONObject(str1);
            JSONObject jsonObject2 = new JSONObject(str2);
            JSONObject a = jsonObject1.getJSONObject(s);
            JSONObject b = jsonObject2.getJSONObject(s);
            if(!oneLead(a,b,flag,threshold)) return false;
        }
        return true;
    }
    public static boolean isScope(int[] a,int[] b, double threshold){
        double intersection = Math.max(0, Math.min(a[1], b[1]) - Math.max(a[0], b[0]));
        double union = Math.max(a[1], b[1]) - Math.min(a[0], b[0]);
        return (intersection/union)>=threshold;
    }

    /**
     * 比较两个字符串字典是否相同
     * @param flag 标签
     * @param threshold 阈值
     * @return
     */
    public static boolean oneLead(JSONObject str1,JSONObject str2,String flag,double threshold) throws JSONException {
        //获取二维数组
        String list1 = str1.get(flag).toString();
        String list2 = str2.get(flag).toString();
        if("[]".equals(list1) && "[]".equals(list2)){
            return true;
        } else if("[]".equals(list1) || "[]".equals(list2)){
            return false;
        }
        int[][] intL1 = StrToList(list1);
        int[][] intL2 = StrToList(list2);
        if(intL1.length!=intL2.length) return false;
        for(int i=0;i<intL1.length;i++){
            if(!isScope(intL1[i],intL2[i],threshold)) return false;
        }
        return true;
    }

    public static int randomInt(int n){
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(n);
    }
}
