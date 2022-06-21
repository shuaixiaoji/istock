package com.github.istock.utils;

import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author shuaixiaoji
 * @Description 数据处理工具类
 * @date 2022/6/17 10:38
 */
public class DataUtils {

    /**
     * JSONArray 按quota组合json对象
     *
     * @param originData
     * @param combineData
     * @param quota
     * @return
     */
    public static JSONArray combineArray(JSONArray originData,JSONArray combineData,String quota){
        originData.forEach(origin -> combineData.forEach(combine -> {
            // 如果是相同的条件，合并jsonObject
            if (isSameQuota(origin,combine,quota)) {
                ((HashMap)origin).putAll((HashMap)combine);
            } else{
                // 不是相同条件的，添加至jsonArray
                originData.add(combine);
            }
        }));
        return originData;
    }

    private static Object getQuota(Object o,String quota){
        return ((HashMap)o).get(quota);
    }

    private static boolean isSameQuota(Object h, Object o,String quota){
        return Objects.equals(getQuota(h,quota),getQuota(o,quota));
    }
}
