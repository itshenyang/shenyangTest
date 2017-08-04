package com.keruis.cilent.dao;

import com.keruis.cilent.dao.pojos.Device_data;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
@Repository
public interface DeviceDataDAO {
    List<Device_data> queryDeviceDataByDidAndTime(Device_data device_data);

    int insertTestData(Device_data device_data);

    Device_data queryOneTmp(Device_data device_data);
}
