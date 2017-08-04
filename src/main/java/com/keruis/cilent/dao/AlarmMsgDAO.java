package com.keruis.cilent.dao;

import com.keruis.cilent.dao.pojos.AlarmMsg;
import com.keruis.cilent.dao.pojos.Device_data;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */
@Repository
public interface AlarmMsgDAO {
    Integer query_alarm_all_num(Device_data device_data);

    List<AlarmMsg> query_alarm_ByTime(Device_data device_data);


}
