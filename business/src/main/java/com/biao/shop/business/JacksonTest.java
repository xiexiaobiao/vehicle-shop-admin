package com.biao.shop.business;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class JacksonTest {
   /* public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MyClass myClass = new MyClass();
        myClass.setName("daidai");
        myClass.setAge(123);
        myClass.setTime(LocalDateTime.now());
        File newFile = new File("D:/my-class.json");
        System.out.println(mapper.canSerialize(MyClass.class));
        // json序列化
        // 序列化到文件
        mapper.writeValue(newFile, myClass);
        // 序列化成字符串
        String json = mapper.writeValueAsString(myClass);
        System.out.println(json);
        // json反序列化
        // 从文件反序列化
        MyClass older = mapper.readValue(new File("D:/my-class.json"), MyClass.class);
        System.out.println(older);
        // 从json串反序列化
        MyClass older2 = mapper.readValue(json, MyClass.class);
        System.out.println(older2);
        // json Tree解析
        JsonNode root = mapper.readTree(newFile);
        System.out.println(root.at("/name"));
        System.out.println(root.at("/age"));
        System.out.println(root.at("/time"));
    }
    static class MyClass{
        String name;
        Integer age;
        *//**因Jackson默认LocalDateTime序列化为："time":{"nano":538064800,"year":2020,"monthValue":1,"dayOfMonth":18,"hour":15,"minute":11,"second":9,
         * "month":"JANUARY","dayOfWeek":"SATURDAY","dayOfYear":18,"chronology":{"id":"ISO","calendarType":"iso8601"}}
         * 导致无法反序列化为LocalDateTime，可以先将LocalDateTime转为instant，再转回来。*//*
        @JsonSerialize(using = CustomDateSerializer.class)
        @JsonDeserialize(using = CustomDateDeserializer.class)
        LocalDateTime time;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "MyClass{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", time=" + time +
                    '}';
        }
    }

    static class CustomDateSerializer extends JsonSerializer<LocalDateTime>{

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeNumber(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        }
    }

    static class  CustomDateDeserializer extends JsonDeserializer<LocalDateTime>{

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Long timeStamp = p.getLongValue();
            return LocalDateTime.ofEpochSecond(timeStamp / 1000, 0, ZoneOffset.ofHours(8));
        }
    }*/
}
