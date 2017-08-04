package com.keruis.cilent.services;

import com.keruis.cilent.dao.AlarmMsgDAO;
import com.keruis.cilent.dao.pojos.AlarmMsg;
import com.keruis.cilent.dao.pojos.Device_data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */
@Service
public class AlarmMsgService extends BaseService {
    @Autowired
    AlarmMsgDAO alarmMsgDAO;

    public Integer alarm_num_ByTime(Device_data device_data) {
        return alarmMsgDAO.query_alarm_all_num(device_data);

    }

    public List<AlarmMsg> query_alarm_ByTime(Device_data device_data) {
        return alarmMsgDAO.query_alarm_ByTime(device_data);
    }

}
