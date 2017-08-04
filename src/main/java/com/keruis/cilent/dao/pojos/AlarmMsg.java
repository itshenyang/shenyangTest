package com.keruis.cilent.dao.pojos;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author
 */
@Alias("alarmmsg")
public class AlarmMsg implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 设备id
     */
    private Integer dId;

    /**
     * 设备名称
     */
    private String dName;

    /**
     * 发生时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp occurTime;

    /**
     * 微信消息内容
     */
    private String alarmInfo;

    /**
     * 报警状态
     */
    private Integer alarmState;

    /**
     * 创建日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    /**
     * 备注
     */
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public Timestamp getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Timestamp occurTime) {
        this.occurTime = occurTime;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public Integer getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(Integer alarmState) {
        this.alarmState = alarmState;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AlarmMsg{" +
                "id=" + id +
                ", dId=" + dId +
                ", dName='" + dName + '\'' +
                ", occurTime=" + occurTime +
                ", alarmInfo='" + alarmInfo + '\'' +
                ", alarmState=" + alarmState +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}