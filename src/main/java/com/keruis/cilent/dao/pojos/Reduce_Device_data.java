package com.keruis.cilent.dao.pojos;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/13.
 */
@Alias("reduce_device_data")
public class Reduce_Device_data implements Serializable {


    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp device_time;

    private Double temp_1;

    private Double longitude;
    private Double latitude;

    private Long door;

    private Long scheduling;

    private Double voltage;

    private Double battery;

    private Object record_id;


    public Timestamp getDevice_time() {
        return device_time;
    }

    public void setDevice_time(Timestamp device_time) {
        this.device_time = device_time;
    }

    public Double getTemp_1() {
        return temp_1;
    }

    public void setTemp_1(Double temp_1) {
        this.temp_1 = temp_1;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getDoor() {
        return door;
    }

    public void setDoor(Long door) {
        this.door = door;
    }

    public Long getScheduling() {
        return scheduling;
    }

    public void setScheduling(Long scheduling) {
        this.scheduling = scheduling;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Double getBattery() {
        return battery;
    }

    public void setBattery(Double battery) {
        this.battery = battery;
    }

    public Object getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Object record_id) {
        this.record_id = record_id;
    }

    @Override
    public String toString() {
        return "Reduce_Device_data{" +
                "device_time=" + device_time +
                ", temp_1=" + temp_1 +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", door=" + door +
                ", scheduling=" + scheduling +
                ", voltage=" + voltage +
                ", battery=" + battery +
                ", record_id=" + record_id +
                '}';
    }
}
