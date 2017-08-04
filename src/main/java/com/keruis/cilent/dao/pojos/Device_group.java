package com.keruis.cilent.dao.pojos;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("device_group")
public class Device_group extends BasePOJO implements Serializable, Comparable<Device_group> {
    private Long d_g_id;
    private String d_g_name;
    private Long d_g_owner;

    private String d_g_owner_nickname;//所属人昵称

    private String d_g_status;
    private Long d_g_creator;

    private String d_g_creator_nickname;//创建人昵称
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")

    private java.sql.Timestamp d_g_create_time;
    private Long d_g_modifier;

    private String d_g_modifier_nickname;//修改昵称
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")

    private java.sql.Timestamp d_g_modify_time;
    private String d_g_mark;
    private String d_g_default;

    public Long getD_g_id() {
        return d_g_id;
    }

    public void setD_g_id(Long d_g_id) {
        this.d_g_id = d_g_id;
    }

    public String getD_g_name() {
        return d_g_name;
    }

    public void setD_g_name(String d_g_name) {
        this.d_g_name = d_g_name;
    }

    public Long getD_g_owner() {
        return d_g_owner;
    }

    public void setD_g_owner(Long d_g_owner) {
        this.d_g_owner = d_g_owner;
    }

    public String getD_g_status() {
        return d_g_status;
    }

    public void setD_g_status(String d_g_status) {
        this.d_g_status = d_g_status;
    }

    public Long getD_g_creator() {
        return d_g_creator;
    }

    public void setD_g_creator(Long d_g_creator) {
        this.d_g_creator = d_g_creator;
    }

    public java.sql.Timestamp getD_g_create_time() {
        return d_g_create_time;
    }

    public void setD_g_create_time(java.sql.Timestamp d_g_create_time) {
        this.d_g_create_time = d_g_create_time;
    }

    public Long getD_g_modifier() {
        return d_g_modifier;
    }

    public void setD_g_modifier(Long d_g_modifier) {
        this.d_g_modifier = d_g_modifier;
    }

    public java.sql.Timestamp getD_g_modify_time() {
        return d_g_modify_time;
    }

    public void setD_g_modify_time(java.sql.Timestamp d_g_modify_time) {
        this.d_g_modify_time = d_g_modify_time;
    }

    public String getD_g_mark() {
        return d_g_mark;
    }

    public void setD_g_mark(String d_g_mark) {
        this.d_g_mark = d_g_mark;
    }

    public String getD_g_default() {
        return d_g_default;
    }

    public void setD_g_default(String d_g_default) {
        this.d_g_default = d_g_default;
    }

    public String getD_g_owner_nickname() {
        return d_g_owner_nickname;
    }

    public void setD_g_owner_nickname(String d_g_owner_nickname) {
        this.d_g_owner_nickname = d_g_owner_nickname;
    }

    public String getD_g_creator_nickname() {
        return d_g_creator_nickname;
    }

    public void setD_g_creator_nickname(String d_g_creator_nickname) {
        this.d_g_creator_nickname = d_g_creator_nickname;
    }

    public String getD_g_modifier_nickname() {
        return d_g_modifier_nickname;
    }

    public void setD_g_modifier_nickname(String d_g_modifier_nickname) {
        this.d_g_modifier_nickname = d_g_modifier_nickname;
    }

    public int compareTo(Device_group o) {

        return this.getD_g_id().compareTo(o.getD_g_id());

    }

    @Override
    public String toString() {
        return "Device_group{" +
                "d_g_id=" + d_g_id +
                ", d_g_name='" + d_g_name + '\'' +
                ", d_g_owner=" + d_g_owner +
                ", d_g_owner_nickname='" + d_g_owner_nickname + '\'' +
                ", d_g_status='" + d_g_status + '\'' +
                ", d_g_creator=" + d_g_creator +
                ", d_g_creator_nickname='" + d_g_creator_nickname + '\'' +
                ", d_g_create_time=" + d_g_create_time +
                ", d_g_modifier=" + d_g_modifier +
                ", d_g_modifier_nickname='" + d_g_modifier_nickname + '\'' +
                ", d_g_modify_time=" + d_g_modify_time +
                ", d_g_mark='" + d_g_mark + '\'' +
                ", d_g_default='" + d_g_default + '\'' +
                '}';
    }
}
