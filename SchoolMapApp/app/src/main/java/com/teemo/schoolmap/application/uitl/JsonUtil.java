package com.teemo.schoolmap.application.uitl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/20 22:43
 * @description
 */
@Deprecated
public class JsonUtil {

    /**
     * 解析 json 并且将 json 填充到实体类中
     *
     * @param response response
     * @param clazz    clazz
     * @param <T>      T
     * @return Lis<T>
     */
    public static <T> List<T> buildObject(String response, Class<T> clazz) {
        List<T> dataList = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.getBoolean("success")) {
                JSONArray jsonArrayData = jsonResponse.getJSONArray("data");
                for (int i = 0; i < jsonResponse.getInt("total"); i++) {
                    JSONObject data = jsonArrayData.getJSONObject(i);
                    dataList.add(jsonToObject(data, clazz));
                }
                return dataList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> T jsonToObject(JSONObject data, Class<T> clazz) {
        T object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        List<Field> fieldList = new ArrayList<>();
        for (Class superClazz = clazz; !superClazz.equals(clazz); superClazz = clazz.getSuperclass()) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        for (Field field : fieldList) {
            try {
                if (field.getType().equals(Class.class)) {
                    field.set(object, jsonToObject(data.getJSONObject(field.getName()), field.getClass()));
                } else {
                    field.set(object, field.get(field.getName()));
                }
            } catch (IllegalAccessException | JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
