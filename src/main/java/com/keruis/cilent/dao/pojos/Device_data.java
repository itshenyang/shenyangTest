package com.keruis.cilent.dao.pojos;


import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.List;

@Alias("device_data")
public class Device_data extends Device implements Comparable<Device_data> {
    private Long id;
    private String name;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp server_time;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp gps_time;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp device_time;
    private Long frequency_1;
    private Long frequency_2;
    private Long bluetooth;
    private Long status;
    private Long work_status;
    private Long delayed;
    private Long locate_status;
    private Double speed;
    private Double humidity;
    private Double temp_1;
    private Double temp_2;
    private Double working_temp_1;
    private Double working_temp_2;
    private Double env_temp_1;
    private Double env_temp_2;
    private Double longitude;
    private Double latitude;
    private String address;
    private Long door;
    private Long error_code;
    private Double voltage;
    private Double battery;
    private Double gsm_signal;
    private Long door_by_hand;
    private Long scheduling;
    private String mark;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp device_timeend;
    private String order_number;

    private String Imgbase64;


    private Object record_id;

    private List<Long> signser;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getServer_time() {
        return server_time;
    }

    public void setServer_time(Timestamp server_time) {
        this.server_time = server_time;
    }

    public Timestamp getGps_time() {
        return gps_time;
    }

    public void setGps_time(Timestamp gps_time) {
        this.gps_time = gps_time;
    }

    public Timestamp getDevice_time() {
        return device_time;
    }

    public void setDevice_time(Timestamp device_time) {
        this.device_time = device_time;
    }

    public Long getFrequency_1() {
        return frequency_1;
    }

    public void setFrequency_1(Long frequency_1) {
        this.frequency_1 = frequency_1;
    }

    public Long getFrequency_2() {
        return frequency_2;
    }

    public void setFrequency_2(Long frequency_2) {
        this.frequency_2 = frequency_2;
    }

    public Long getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(Long bluetooth) {
        this.bluetooth = bluetooth;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getWork_status() {
        return work_status;
    }

    public void setWork_status(Long work_status) {
        this.work_status = work_status;
    }

    public Long getDelayed() {
        return delayed;
    }

    public void setDelayed(Long delayed) {
        this.delayed = delayed;
    }

    public Long getLocate_status() {
        return locate_status;
    }

    public void setLocate_status(Long locate_status) {
        this.locate_status = locate_status;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTemp_1() {
        return temp_1;
    }

    public void setTemp_1(Double temp_1) {
        this.temp_1 = temp_1;
    }

    public Double getTemp_2() {
        return temp_2;
    }

    public void setTemp_2(Double temp_2) {
        this.temp_2 = temp_2;
    }

    public Double getWorking_temp_1() {
        return working_temp_1;
    }

    public void setWorking_temp_1(Double working_temp_1) {
        this.working_temp_1 = working_temp_1;
    }

    public Double getWorking_temp_2() {
        return working_temp_2;
    }

    public void setWorking_temp_2(Double working_temp_2) {
        this.working_temp_2 = working_temp_2;
    }

    public Double getEnv_temp_1() {
        return env_temp_1;
    }

    public void setEnv_temp_1(Double env_temp_1) {
        this.env_temp_1 = env_temp_1;
    }

    public Double getEnv_temp_2() {
        return env_temp_2;
    }

    public void setEnv_temp_2(Double env_temp_2) {
        this.env_temp_2 = env_temp_2;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getDoor() {
        return door;
    }

    public void setDoor(Long door) {
        this.door = door;
    }

    public Long getError_code() {
        return error_code;
    }

    public void setError_code(Long error_code) {
        this.error_code = error_code;
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

    public Double getGsm_signal() {
        return gsm_signal;
    }

    public void setGsm_signal(Double gsm_signal) {
        this.gsm_signal = gsm_signal;
    }

    public Long getDoor_by_hand() {
        return door_by_hand;
    }

    public void setDoor_by_hand(Long door_by_hand) {
        this.door_by_hand = door_by_hand;
    }

    public Long getScheduling() {
        return scheduling;
    }

    public void setScheduling(Long scheduling) {
        this.scheduling = scheduling;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Timestamp getDevice_timeend() {
        return device_timeend;
    }

    public void setDevice_timeend(Timestamp device_timeend) {
        this.device_timeend = device_timeend;
    }

    public int compareTo(Device_data o) {

        return (int) this.getDevice_time().getTime() - (int) o.getDevice_time().getTime();

    }

    public String getImgbase64() {
        return Imgbase64;
    }

    public void setImgbase64(String imgbase64) {
        Imgbase64 = imgbase64;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public Object getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Object record_id) {
        this.record_id = record_id;
    }

    public List<Long> getSignser() {
        return signser;
    }

    public void setSignser(List<Long> signser) {
        this.signser = signser;
    }

    @Override
    public String toString() {
        return "Device_data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", server_time=" + server_time +
                ", gps_time=" + gps_time +
                ", device_time=" + device_time +
                ", frequency_1=" + frequency_1 +
                ", frequency_2=" + frequency_2 +
                ", bluetooth=" + bluetooth +
                ", status=" + status +
                ", work_status=" + work_status +
                ", delayed=" + delayed +
                ", locate_status=" + locate_status +
                ", speed=" + speed +
                ", humidity=" + humidity +
                ", temp_1=" + temp_1 +
                ", temp_2=" + temp_2 +
                ", working_temp_1=" + working_temp_1 +
                ", working_temp_2=" + working_temp_2 +
                ", env_temp_1=" + env_temp_1 +
                ", env_temp_2=" + env_temp_2 +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", address='" + address + '\'' +
                ", door=" + door +
                ", error_code=" + error_code +
                ", voltage=" + voltage +
                ", battery=" + battery +
                ", gsm_signal=" + gsm_signal +
                ", door_by_hand=" + door_by_hand +
                ", scheduling=" + scheduling +
                ", mark='" + mark + '\'' +
                ", device_timeend=" + device_timeend +
                ", order_number='" + order_number + '\'' +
                ", Imgbase64='" + Imgbase64 + '\'' +
                ", record_id=" + record_id +
                ", signser=" + signser +
                ", max_temp=" + max_temp +
                ", min_temp=" + min_temp +
                ", last_record_id=" + last_record_id +
                '}';
    }
}
