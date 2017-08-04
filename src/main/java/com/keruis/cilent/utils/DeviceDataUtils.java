package com.keruis.cilent.utils;

import com.keruis.cilent.dao.pojos.Device_data;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */
public class DeviceDataUtils {
    public static final Integer dataUpdateTimeInterval = 5 * 60 * 1000;   //数据之间的间隔

    public static final Integer dataUpdateTimeInterval_tmpOverheating = 1; //温度差基数

    public static final Integer dataTmpDifferenceTime = 30 * 60 * 1000;       //前后温度温差时间

    public static final Double dataMaxTmp = 38.0;             //温度最大为

    public static final Double dataMinTmp = -40.0;             //温度最小为

    public static final Boolean getUpdateDeviceIds(String deviceId) {
        String[] strings = {"1041", "1044", "1045", "1051", "1188", "1189", "1190", "1191"};
        int a = Arrays.binarySearch(strings, deviceId);
        if (a > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static final List<Device_data> updateDataTimeInterval(List<Device_data> list, Device_data device_data) {
        for (int i = 0; i < list.size(); i++) {
            Device_data one1 = list.get(i);
            if (one1.getTemp_1() > dataMaxTmp) {
                list.get(i).setTemp_1(dataMaxTmp);
            }
            if (one1.getTemp_1() < dataMinTmp) {
                list.get(i).setTemp_1(dataMinTmp);
            }
        }
        Collections.sort(list);
        List<Device_data> only = new ArrayList<>();
        int num = 1;
        for (int i = 0; i < list.size(); ) {
            Device_data one1 = list.get(i);
            if (i + num == list.size()) {
                break;
            }
            if (i == 0) {
                only.add(one1);
                i++;
                continue;
            }
            Device_data one2 = list.get(i + num);
            int dataUpdateTmpInt = (int) (one2.getDevice_time().getTime() - one1.getDevice_time().getTime());
            if (one2.getDevice_time().getTime() - one1.getDevice_time().getTime() <= dataTmpDifferenceTime) {
                if (Math.abs(one2.getTemp_1() - one1.getTemp_1()) * 1000 * 60 > dataUpdateTmpInt * dataUpdateTimeInterval_tmpOverheating) {
                    num++;
                    continue;
                }
            }
            i = i + num;
            num = 1;
            only.add(one2);
        }

        Collections.sort(only);
        list = only;
        if (true) {
            List<Device_data> result = new ArrayList<>();
            for (Long i = device_data.getDevice_time().getTime(); i <= device_data.getDevice_timeend().getTime(); i = i + dataUpdateTimeInterval) {
                List<Device_data> listOne = new ArrayList<>();
                Long timestamp1 = i;
                Long timestamp2 = i + dataUpdateTimeInterval;
                for (int j = 0; j < list.size(); j++) {
                    Device_data one = list.get(j);
                    if (one.getDevice_time().getTime() >= timestamp1 && one.getDevice_time().getTime() < timestamp2) {
                        one.setDevice_time(new Timestamp(timestamp1));
                        listOne.add(one);
                    }
                }
                for (int k = 0; k < listOne.size(); k++) {
                    Device_data state = listOne.get(k);
                    if (state.getDoor().toString().equals("1")) {
                        for (int kk = 0; kk < listOne.size(); kk++) {
                            Device_data kkone = listOne.get(kk);
                            kkone.setDoor(1L);
                        }
                        break;
                    }
                }
                if (listOne.size() != 0) {
                    result.add(listOne.get(listOne.size() - 1));
                }
            }
            list = result;
        }
        Collections.sort(list);
        List<Device_data> result = new ArrayList<>();
        for (int k = 0; k < list.size(); k++) {
            Device_data one = list.get(k);
            if (one.getDevice_time().getTime() >= device_data.getDevice_time().getTime() && one.getDevice_time().getTime() <= device_data.getDevice_timeend().getTime() - dataUpdateTimeInterval) {
                result.add(one);
            }
        }
        Collections.sort(list);
        return result;
    }


}
