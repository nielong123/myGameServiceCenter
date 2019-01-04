package com.org.myGameServiceCenter.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;


/** 
 * @ClassName GsonUtils  
 * @Description TODO 
 * @author gaozhx 
 * @date 2017年4月11日  
 *   
 */
public class GsonUtils {
    public static Gson gson = new GsonBuilder().disableHtmlEscaping()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    
    private GsonUtils(){}
    /**
     * 
     * @Title: toJson 
     * @Description: 将对象转换为JSON字符串
     * @param obj
     * @return 参数说明
     * @return String    返回类型
     * @throws
     */
    public static String getJsonFromObject(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * @Title: getJson 
     * @Description: 将JSON字符串转换为对象 
     * @param json
     * @param classOfT
     * @return 参数说明
     * @return T    返回类型
     * @throws
     */
    public static <T> T getObjectFromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * @Title: getJson 
     * @Description: 将JSON字符串转换为集合
     * @param json
     */
    public static <T> List<T> getListFromJson(String json, Type type) {
        // 用法
        // List<Bean> beans = GsonUtils.getJson(json,new TypeToken<List<Bean>>() {}.getType());
        return gson.fromJson(json, type);
    }
}
