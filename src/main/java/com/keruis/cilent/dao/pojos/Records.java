package com.keruis.cilent.dao.pojos;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

@Alias("records")
public class Records extends BasePOJO implements Comparable<Records> {
    private Long id;
    private Long device_id;
    private Long creator;

    private String creator_nickname;//行程开启人昵称

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private java.sql.Timestamp start_time;
    private Double start_lng;
    private Double start_lat;
    private String start_address;
    private String start_road;
    private Long closer;

    private String closer_nickname;//行程关闭人昵称

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private java.sql.Timestamp close_time;
    private Double close_lng;
    private Double close_lat;
    private String close_address;
    private String close_road;



    private Long s_id;
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

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public java.sql.Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(java.sql.Timestamp start_time) {
        this.start_time = start_time;
    }

    public Double getStart_lng() {
        return start_lng;
    }

    public void setStart_lng(Double start_lng) {
        this.start_lng = start_lng;
    }

    public Double getStart_lat() {
        return start_lat;
    }

    public void setStart_lat(Double start_lat) {
        this.start_lat = start_lat;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getStart_road() {
        return start_road;
    }

    public void setStart_road(String start_road) {
        this.start_road = start_road;
    }

    public Long getCloser() {
        return closer;
    }

    public void setCloser(Long closer) {
        this.closer = closer;
    }

    public java.sql.Timestamp getClose_time() {
        return close_time;
    }

    public void setClose_time(java.sql.Timestamp close_time) {
        this.close_time = close_time;
    }

    public Double getClose_lng() {
        return close_lng;
    }

    public void setClose_lng(Double close_lng) {
        this.close_lng = close_lng;
    }

    public Double getClose_lat() {
        return close_lat;
    }

    public void setClose_lat(Double close_lat) {
        this.close_lat = close_lat;
    }

    public String getClose_address() {
        return close_address;
    }

    public void setClose_address(String close_address) {
        this.close_address = close_address;
    }

    public String getClose_road() {
        return close_road;
    }

    public void setClose_road(String close_road) {
        this.close_road = close_road;
    }


    public String getCreator_nickname() {
        return creator_nickname;
    }

    public void setCreator_nickname(String creator_nickname) {
        this.creator_nickname = creator_nickname;
    }

    public String getCloser_nickname() {
        return closer_nickname;
    }

    public void setCloser_nickname(String closer_nickname) {
        this.closer_nickname = closer_nickname;
    }

    public Long getS_id() {
        return s_id;
    }

    public void setS_id(Long s_id) {
        this.s_id = s_id;
    }

    @Override
    public int compareTo(Records o) {
        return (int)o.getStart_time().getTime()-(int)this.getStart_time().getTime();
    }

    @Override
    public String toString() {
        return "Records{" +
                "id=" + id +
                ", device_id=" + device_id +
                ", creator=" + creator +
                ", creator_nickname='" + creator_nickname + '\'' +
                ", start_time=" + start_time +
                ", start_lng=" + start_lng +
                ", start_lat=" + start_lat +
                ", start_address='" + start_address + '\'' +
                ", start_road='" + start_road + '\'' +
                ", closer=" + closer +
                ", closer_nickname='" + closer_nickname + '\'' +
                ", close_time=" + close_time +
                ", close_lng=" + close_lng +
                ", close_lat=" + close_lat +
                ", close_address='" + close_address + '\'' +
                ", close_road='" + close_road + '\'' +
                ", s_id=" + s_id +
                '}';
    }
}
