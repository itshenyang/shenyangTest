package com.keruis.cilent.dao.pojos;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

@Alias("device")
public class Device extends BasePOJO implements Serializable {
    private Long d_id;
    private String d_oct_id;
    private String d_factory_id;
    private String d_name;
    private String d_type;
    private Long d_g_id;

    private String d_g_name;//分组名称

    private String d_status;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")

    private java.sql.Timestamp d_deadline;
    private Long d_creator;

    private String u_creator_nickname;//创建人昵称
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")

    private java.sql.Timestamp d_create_time;
    private Long d_modifier;

    private String u_modifier_nickname;//修改昵称
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")


    private java.sql.Timestamp d_modify_time;
    private String d_mark;
    private Long s_id;
    private String s_id_nickname;

    private Double d_temp_limit_top;
    private Double d_temp_limit_floor;
    private Double d_work_temp_1;
    private Double d_work_temp_2;
    private Long freqency_1;
    private Long freqency_2;
    private Long blueteeth;
    private Long SM_code;

    private Long tmp_alarm_state;

    private Double max_temp;
    private Double min_temp;
    private Long last_record_id;

    public Double getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(Double max_temp) {
        this.max_temp = max_temp;
    }

    public Double getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(Double min_temp) {
        this.min_temp = min_temp;
    }

    public Long getLast_record_id() {
        return last_record_id;
    }

    public void setLast_record_id(Long last_record_id) {
        this.last_record_id = last_record_id;
    }

    public Long getD_id() {
        return d_id;
    }

    public void setD_id(Long d_id) {
        this.d_id = d_id;
    }

    public String getD_oct_id() {
        return d_oct_id;
    }

    public void setD_oct_id(String d_oct_id) {
        this.d_oct_id = d_oct_id;
    }

    public String getD_factory_id() {
        return d_factory_id;
    }

    public void setD_factory_id(String d_factory_id) {
        this.d_factory_id = d_factory_id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_type() {
        return d_type;
    }

    public void setD_type(String d_type) {
        this.d_type = d_type;
    }

    public Long getD_g_id() {
        return d_g_id;
    }

    public void setD_g_id(Long d_g_id) {
        this.d_g_id = d_g_id;
    }

    public String getD_status() {
        return d_status;
    }

    public void setD_status(String d_status) {
        this.d_status = d_status;
    }

    public java.sql.Timestamp getD_deadline() {
        return d_deadline;
    }

    public void setD_deadline(java.sql.Timestamp d_deadline) {
        this.d_deadline = d_deadline;
    }

    public Long getD_creator() {
        return d_creator;
    }

    public void setD_creator(Long d_creator) {
        this.d_creator = d_creator;
    }

    public java.sql.Timestamp getD_create_time() {
        return d_create_time;
    }

    public void setD_create_time(java.sql.Timestamp d_create_time) {
        this.d_create_time = d_create_time;
    }

    public Long getD_modifier() {
        return d_modifier;
    }

    public void setD_modifier(Long d_modifier) {
        this.d_modifier = d_modifier;
    }

    public java.sql.Timestamp getD_modify_time() {
        return d_modify_time;
    }

    public void setD_modify_time(java.sql.Timestamp d_modify_time) {
        this.d_modify_time = d_modify_time;
    }

    public String getD_mark() {
        return d_mark;
    }

    public void setD_mark(String d_mark) {
        this.d_mark = d_mark;
    }


    public Long getS_id() {
        return s_id;
    }

    public void setS_id(Long s_id) {
        this.s_id = s_id;
    }


    public String getU_creator_nickname() {
        return u_creator_nickname;
    }

    public void setU_creator_nickname(String u_creator_nickname) {
        this.u_creator_nickname = u_creator_nickname;
    }

    public String getU_modifier_nickname() {
        return u_modifier_nickname;
    }

    public void setU_modifier_nickname(String u_modifier_nickname) {
        this.u_modifier_nickname = u_modifier_nickname;
    }

    public String getD_g_name() {
        return d_g_name;
    }

    public void setD_g_name(String d_g_name) {
        this.d_g_name = d_g_name;
    }

    public int compareTo(Device o) {
        int i = this.getD_name().length() - o.getD_name().length();
        if (i == 0) {
            return this.getD_name().compareTo(o.getD_name());
        }
        return i;
    }

    public String getS_id_nickname() {
        return s_id_nickname;
    }

    public void setS_id_nickname(String s_id_nickname) {
        this.s_id_nickname = s_id_nickname;
    }

    public Double getD_temp_limit_top() {
        return d_temp_limit_top;
    }

    public void setD_temp_limit_top(Double d_temp_limit_top) {
        this.d_temp_limit_top = d_temp_limit_top;
    }

    public Double getD_temp_limit_floor() {
        return d_temp_limit_floor;
    }

    public void setD_temp_limit_floor(Double d_temp_limit_floor) {
        this.d_temp_limit_floor = d_temp_limit_floor;
    }

    public Double getD_work_temp_1() {
        return d_work_temp_1;
    }

    public void setD_work_temp_1(Double d_work_temp_1) {
        this.d_work_temp_1 = d_work_temp_1;
    }

    public Double getD_work_temp_2() {
        return d_work_temp_2;
    }

    public void setD_work_temp_2(Double d_work_temp_2) {
        this.d_work_temp_2 = d_work_temp_2;
    }

    public Long getFreqency_1() {
        return freqency_1;
    }

    public void setFreqency_1(Long freqency_1) {
        this.freqency_1 = freqency_1;
    }

    public Long getFreqency_2() {
        return freqency_2;
    }

    public void setFreqency_2(Long freqency_2) {
        this.freqency_2 = freqency_2;
    }

    public Long getBlueteeth() {
        return blueteeth;
    }

    public void setBlueteeth(Long blueteeth) {
        this.blueteeth = blueteeth;
    }

    public Long getSM_code() {
        return SM_code;
    }

    public void setSM_code(Long SM_code) {
        this.SM_code = SM_code;
    }

    public Long getTmp_alarm_state() {
        return tmp_alarm_state;
    }

    public void setTmp_alarm_state(Long tmp_alarm_state) {
        this.tmp_alarm_state = tmp_alarm_state;
    }

    @Override
    public String toString() {
        return "Device{" +
                "d_id=" + d_id +
                ", d_oct_id='" + d_oct_id + '\'' +
                ", d_factory_id='" + d_factory_id + '\'' +
                ", d_name='" + d_name + '\'' +
                ", d_type='" + d_type + '\'' +
                ", d_g_id=" + d_g_id +
                ", d_g_name='" + d_g_name + '\'' +
                ", d_status='" + d_status + '\'' +
                ", d_deadline=" + d_deadline +
                ", d_creator=" + d_creator +
                ", u_creator_nickname='" + u_creator_nickname + '\'' +
                ", d_create_time=" + d_create_time +
                ", d_modifier=" + d_modifier +
                ", u_modifier_nickname='" + u_modifier_nickname + '\'' +
                ", d_modify_time=" + d_modify_time +
                ", d_mark='" + d_mark + '\'' +
                ", s_id=" + s_id +
                ", s_id_nickname='" + s_id_nickname + '\'' +
                ", d_temp_limit_top=" + d_temp_limit_top +
                ", d_temp_limit_floor=" + d_temp_limit_floor +
                ", d_work_temp_1=" + d_work_temp_1 +
                ", d_work_temp_2=" + d_work_temp_2 +
                ", freqency_1=" + freqency_1 +
                ", freqency_2=" + freqency_2 +
                ", blueteeth=" + blueteeth +
                ", SM_code=" + SM_code +
                ", tmp_alarm_state=" + tmp_alarm_state +
                ", max_temp=" + max_temp +
                ", min_temp=" + min_temp +
                ", last_record_id=" + last_record_id +
                '}';
    }
}
