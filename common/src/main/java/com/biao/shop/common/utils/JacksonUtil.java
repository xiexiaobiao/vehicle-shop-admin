package com.biao.shop.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;

/**
 * @ClassName Json
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/12
 * @Version V1.0
 **/
public class JacksonUtil<T> {

    public static <T> String convertToJson(T object) throws JsonProcessingException {
        //转换为json, 但要注意时间的序列化
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new CustomDateSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(timeModule);
        String JsonStr = objectMapper.writeValueAsString(object);
        return JsonStr;
    }

    public static <T> T convertJsonToObject(String jsonStr, Class<T> clazz) throws JsonProcessingException {
        //json反转化, 但要注意时间的序列化
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new CustomDateDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(timeModule);
        T object = objectMapper.readValue(jsonStr,clazz);
        return object;
    }
}
