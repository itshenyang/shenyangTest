package com.keruis.cilent.dao.pojos;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/6/13.
 */
@Alias("tmp_alarm")
public class Tmp_alarm extends BasePOJO {

    private Long id;

    private Long device_id;


    private Long user_id;

    private Timestamp create_time;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Tmp_alarm{" +
                "id=" + id +
                ", device_id=" + device_id +
                ", user_id=" + user_id +
                ", create_time=" + create_time +
                ", state='" + state + '\'' +
                '}';
    }
}
