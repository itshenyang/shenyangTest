package com.keruis.cilent.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONPObject;
import com.keruis.cilent.dao.Tmp_alarmDAO;
import com.keruis.cilent.dao.pojos.Device;
import com.keruis.cilent.dao.pojos.Tmp_alarm;
import com.keruis.cilent.utils.L;
import com.keruis.cilent.utils.RedisUtils;
import com.keruis.cilent.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/13.
 */
@Service
public class Tmp_alarmService extends BaseService {
    @Autowired
    Tmp_alarmDAO tmp_alarmDAO;

    public Boolean insertSucceed(Tmp_alarm tmp_alarm) {
        Timestamp newTime = timeUtils.getTimeNow();
        tmp_alarm.setCreate_time(newTime);
        try {
            tmp_alarmDAO.delete(tmp_alarm);
            if (tmp_alarmDAO.insert(tmp_alarm) == 1) {
                L.w("Tmp_alarm:" + " " + tmp_alarm.toString());
                setDataToRedis(RedisUtils.getTmp_alarmTableName(), tmp_alarm.getUser_id().toString() + "_" + tmp_alarm.getDevice_id().toString(), tmp_alarm);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteSucceed(Tmp_alarm tmp_alarm) {
        try {
            if (tmp_alarmDAO.delete(tmp_alarm) > 0) {
                delDataToRedis(RedisUtils.getTmp_alarmTableName(), tmp_alarm.getUser_id().toString() + "_" + tmp_alarm.getDevice_id().toString());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public Map<Long, String> getAllTmpAalarmByUser(Tmp_alarm tmp_alarm) {
        List<String> allAlarm = getTableAllKeys(RedisUtils.getTmp_alarmTableName());
        allAlarm = getQueryKeysByTime(allAlarm, tmp_alarm.getUser_id().toString() + "_");
        List<Tmp_alarm> list = new ArrayList<>();
        for (int i = 0; i < allAlarm.size(); i++) {
            String oneString = getDataByKey(RedisUtils.getTmp_alarmTableName(), allAlarm.get(i));
            list.add(JSON.parseObject(oneString, Tmp_alarm.class));
        }
        Map<Long, String> allId = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            allId.put(list.get(i).getDevice_id(), list.get(i).getState());
        }
        return allId;
    }

    public void updateUSER_ALARM_RATE(Long sid, Long user_rate, Long company_rate) {
        if (!StringUtils.isEmpty(user_rate)) {
            setDataToRedis(RedisUtils.getUSER_ALARM_RATETableName, RedisUtils.getUSER_ALARM_RATEKeyByUser(sid), user_rate.toString());
        }
        if (!StringUtils.isEmpty(company_rate)) {
            setDataToRedis(RedisUtils.getUSER_ALARM_RATETableName, RedisUtils.getUSER_ALARM_RATEKeyByCompany(sid), company_rate.toString());
        }
    }

    public Device queryUSER_ALARM_RATE(Long sid) {
        Long moren = 30 * 60 * 1000L;
        Device device = new Device();
        String one1 = getDataByKey(RedisUtils.getUSER_ALARM_RATETableName, RedisUtils.getUSER_ALARM_RATEKeyByUser(sid));
        String one2 = getDataByKey(RedisUtils.getUSER_ALARM_RATETableName, RedisUtils.getUSER_ALARM_RATEKeyByCompany(sid));
        if (!StringUtils.isEmpty(one1)) {
            device.setFreqency_1(Long.parseLong(one1));
        } else {
            device.setFreqency_1(moren);
        }
        if (!StringUtils.isEmpty(one2)) {
            device.setFreqency_2(Long.parseLong(one2));
        } else {
            device.setFreqency_2(moren);
        }
        return  device;
    }
}
