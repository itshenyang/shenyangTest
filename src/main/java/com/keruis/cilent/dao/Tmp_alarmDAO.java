package com.keruis.cilent.dao;

import com.keruis.cilent.dao.pojos.Tmp_alarm;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/13.
 */
@Repository
public interface Tmp_alarmDAO {

    Integer insert(Tmp_alarm tmp_alarm);

    Integer delete(Tmp_alarm tmp_alarm);

}
