package com.keruis.cilent.test;

import com.keruis.cilent.dao.RecordDAO;
import com.keruis.cilent.dao.pojos.Device_data;
import com.keruis.cilent.dao.pojos.Records;
import com.keruis.cilent.services.BaseService;
import com.keruis.cilent.services.RedisDb2Service;
import com.keruis.cilent.utils.JSONUtils;
import com.keruis.cilent.utils.L;
import com.keruis.cilent.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by sy on 2017/7/28.
 */
@Service
public class RecordTest {
    @Autowired
    RedisDb2Service redisDb2Service;
    @Autowired
    BaseService baseService;
    @Autowired
    RecordDAO recordDAO;

    public void updateDB2RecordId() {
        List<String> all = baseService.getAllDataByKey(RedisUtils.getRealTimeTable());
        for (int i = 0; i < all.size(); i++) {
            try {
                String oneone = all.get(i);
                Device_data oneDeivce = (Device_data) JSONUtils.parse(oneone, Device_data.class);
                Long recordsId = 0L;
                Records recordsOne = new Records();
                recordsOne.setDevice_id(oneDeivce.getD_id());
                List<Records> recordsesAll = recordDAO.selectOne(recordsOne);
                try {
                    recordsId = recordsesAll.get(0).getId();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    oneDeivce.setRecord_id(recordsId);
                    baseService.setDataToRedis(RedisUtils.getRealTimeTable(), oneDeivce.getD_id().toString(), oneDeivce);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    String oneone2 = redisDb2Service.getDataByKey(RedisUtils.getRealTimeTable(), oneDeivce.getD_id().toString());
                    Device_data one2Device = (Device_data) JSONUtils.parse(oneone2, Device_data.class);
                    one2Device.setRecord_id(recordsId);
                    redisDb2Service.setDataToRedis(RedisUtils.getRealTimeTable(), oneDeivce.getD_id().toString(), one2Device);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

        }
    }
}
