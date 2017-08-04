package com.keruis.cilent.dao.pojos;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

@Alias("sings")
public class Signs extends BasePOJO  implements Comparable<Signs>  {
    private Long id;
    private Long record_id;
    private Long signer;

    private Long device_id;

    private String singer_nickname;//签收人昵称

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private java.sql.Timestamp sign_time;
    private Double sign_lng;
    private Double sign_lat;
    private String sign_address;
    private String sign_road;
    private Long s_id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Long record_id) {
        this.record_id = record_id;
    }

    public Long getSigner() {
        return signer;
    }

    public void setSigner(Long signer) {
        this.signer = signer;
    }

    public java.sql.Timestamp getSign_time() {
        return sign_time;
    }

    public void setSign_time(java.sql.Timestamp sign_time) {
        this.sign_time = sign_time;
    }

    public Double getSign_lng() {
        return sign_lng;
    }

    public void setSign_lng(Double sign_lng) {
        this.sign_lng = sign_lng;
    }

    public Double getSign_lat() {
        return sign_lat;
    }

    public void setSign_lat(Double sign_lat) {
        this.sign_lat = sign_lat;
    }

    public String getSign_address() {
        return sign_address;
    }

    public void setSign_address(String sign_address) {
        this.sign_address = sign_address;
    }

    public String getSign_road() {
        return sign_road;
    }

    public void setSign_road(String sign_road) {
        this.sign_road = sign_road;
    }

    public String getSinger_nickname() {
        return singer_nickname;
    }

    public void setSinger_nickname(String singer_nickname) {
        this.singer_nickname = singer_nickname;
    }

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public Long getS_id() {
        return s_id;
    }

    public void setS_id(Long s_id) {
        this.s_id = s_id;
    }



    @Override
    public int compareTo(Signs o) {
        return (int)o.getSign_time().getTime()-(int)this.getSign_time().getTime();
    }

    @Override
    public String toString() {
        return "Signs{" +
                "id=" + id +
                ", record_id=" + record_id +
                ", signer=" + signer +
                ", device_id=" + device_id +
                ", singer_nickname='" + singer_nickname + '\'' +
                ", sign_time=" + sign_time +
                ", sign_lng=" + sign_lng +
                ", sign_lat=" + sign_lat +
                ", sign_address='" + sign_address + '\'' +
                ", sign_road='" + sign_road + '\'' +
                ", s_id=" + s_id +
                '}';
    }
}
