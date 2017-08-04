package com.keruis.cilent.utils;


import com.keruis.cilent.dao.pojos.Device_data;

/**
 * Created by Administrator on 2017/3/20.
 */

public class RedisUtils {

    public static String getUserTableName(String sid) {
        return "e_" + sid + "_user";
    }

    public static String getUserGroupTableName(String sid) {
        return "e_" + sid + "_u_group";
    }

    public static String getDeviceTableName(String sid) {
        return "e_" + sid + "_device";
    }

    public static String getDeviceGroupTableName(String sid) {
        return "e_" + sid + "_d_group";
    }

    public static String getDeviceDataTableName(Long d_id, String time) {
        return "data_" + d_id + "_" + time;
    }

    public static String getTokenuseridTableName(){
        return "e_tokenuserid";
    }

    public static String getRealTimeTable() {
        return "real_time";
    }

    public static String getRecordsTableName(String did) {
        return "data_" + did + "_records";
    }

    public static String getSignsTableName(String did) {
        return "data_" + did + "_signs";
    }

    public static String getTmp_alarmTableName( ) {
        return "e_tmp_alarm";
    }

    public static String getUSER_ALARM_RATETableName="USER_ALARM_RATE";

    public static String getUSER_ALARM_RATEKeyByUser(Long sid){
        return sid+"_user";
    }
    public static String getUSER_ALARM_RATEKeyByCompany(Long sid){
        return sid+"_company";
    }

}