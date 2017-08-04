package com.keruis.cilent.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/5/24.
 */
public class JSONUtils {





        private static SerializeConfig config;
        static {
            config = new SerializeConfig();
            config.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        }

        private static final SerializerFeature[] features = {
//            SerializerFeature.DisableCircularReferenceDetect,//打开循环引用检测，JSONField(serialize = false)不循环
//            SerializerFeature.WriteDateUseDateFormat,//默认使用系统默认 格式日期格式化
                SerializerFeature.WriteMapNullValue, //输出空置字段
                SerializerFeature.WriteNullListAsEmpty,//list字段如果为null，输出为[]，而不是null
                SerializerFeature.WriteNullNumberAsZero,// 数值字段如果为null，输出为0，而不是null
                SerializerFeature.WriteNullBooleanAsFalse,//Boolean字段如果为null，输出为false，而不是null
                SerializerFeature.WriteNullStringAsEmpty//字符类型字段如果为null，输出为""，而不是null
        };

        public static String toString(Object obj) {
            return JSON.toJSONString(obj, config, features);
        }

        public static Object parse(String json , Class tClass){
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            Object obj = null;
            try {
                obj = JSON.parseObject(json , tClass);
            }catch (Exception e){
                e.printStackTrace();
            }
            return obj;
        }



}
