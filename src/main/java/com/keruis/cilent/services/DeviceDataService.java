package com.keruis.cilent.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.keruis.cilent.dao.pojos.*;
import com.keruis.cilent.entities.DeviceDataResult;
import com.keruis.cilent.entities.UserResult;
import com.keruis.cilent.utils.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2017/4/12.
 */
@Service
public class DeviceDataService extends BaseService {
    @Autowired
    RedisDb2Service redisDb2Service;

    public void insert(Device_data device_data) {
        L.w(deviceDataDAO.insertTestData(device_data) + " insert");
    }

    public List<Device_data> queryDeviceDataByDidAndTime(Device_data device_data) {
        device_data.setDevice_timeend(new Timestamp(device_data.getDevice_timeend().getTime() + DeviceDataUtils.dataUpdateTimeInterval));
        List<Device_data> result = new ArrayList<Device_data>();
        if (device_data.getDevice_time().getTime() < timeUtils.get1LastMonth().getTime()) {       //查询的有超过3.1的
            result = deviceDataDAO.queryDeviceDataByDidAndTime(device_data);
            Collections.sort(result);
            return result;
        }
        if (device_data.getDevice_timeend().getTime() >= timeUtils.get1LastMonth().getTime()) {      //查询的没有超过3.1
            List<Timestamp> timeToRedis = new ArrayList<Timestamp>();
            if ((device_data.getDevice_time().getTime() >= timeUtils.get1LastMonth().getTime() && device_data.getDevice_timeend().getTime() < timeUtils.get1CurrentMonth().getTime())
                    || device_data.getDevice_time().getTime() >= timeUtils.get1CurrentMonth().getTime()) { //只含3.1-3.31的 或者 只含4.1-4.13的
                timeToRedis.add(device_data.getDevice_time());
                timeToRedis.add(device_data.getDevice_timeend());
            } else {   //本月和上个月都包含
                timeToRedis.add(device_data.getDevice_time());
                timeToRedis.add(timeUtils.get1CurrentMonth());
                timeToRedis.add(device_data.getDevice_timeend());
            }
            for (int i = 0; i < timeToRedis.size() - 1; i++) {
                List<String> allKeys = new ArrayList<String>();
                Timestamp startTime = timeToRedis.get(i);
                List<String> kers = getTableAllKeys(RedisUtils.getDeviceDataTableName(device_data.getD_id(), timeUtils.getYYMMByTime(startTime)));
                Timestamp overTime = timeToRedis.get(i + 1);
                List<String> allDay = timeUtils.getSameMonthTimeRangeAllYYmmdd(startTime, overTime);
                for (int j = 0; j < allDay.size(); j++) {
                    allKeys.addAll(getQueryKeysByTime(kers, allDay.get(j)));
                }
                result.addAll(StringToDeviceData(getKeysToRedis(allKeys, RedisUtils.getDeviceDataTableName(device_data.getD_id(), timeUtils.getYYMMByTime(startTime)))));
            }

        }

        //==================获取查询的全天的数据===============
        List<Device_data> all = new ArrayList<Device_data>();
        //筛选时间
        for (int i = 0; i < result.size(); i++) {
            Device_data one = result.get(i);
            if (!(one.getDevice_time().getTime() < device_data.getDevice_time().getTime())
                    && !(one.getDevice_time().getTime() > device_data.getDevice_timeend().getTime())
                    && !StringUtils.isEmpty(one.getTemp_1())
                    && !StringUtils.isEmpty(one.getDevice_time())
                    ) {
                all.add(one);
            }
        }
        Collections.sort(all);
        return all;

    }

    public Map<Long, Device_data> gtAllRealTimeDeviceData() {
        List<String> allRealTimeValue = getAllDataByKey(RedisUtils.getRealTimeTable());
        Map<Long, Device_data> map = new HashMap<Long, Device_data>();
        for (int i = 0; i < allRealTimeValue.size(); i++) {
            String oneString = allRealTimeValue.get(i);
            Device_data one = JSONObject.parseObject(oneString, Device_data.class);
            if (StringUtils.isEmpty(one)) {
                continue;
            }
            map.put(one.getD_id(), one);
        }
        return map;
    }

    public List<Device_data> getDeviceRealTime(User user) {
        List<String> allDeviceString = getKeysToRedis(user.getDidss(), RedisUtils.getRealTimeTable());
        List<Device_data> result = new ArrayList<Device_data>();
        for (int i = 0; i < allDeviceString.size(); i++) {
            String one = allDeviceString.get(i);
            if (!StringUtils.isEmpty(one)) {
                if (one.equals("null")) {
                    continue;
                }
                Device_data device_dataOne = JSONObject.parseObject(one, Device_data.class);
                if (StringUtils.isEmpty(device_dataOne)) {
                    continue;
                }
                device_dataOne = D_idQueryDeviceInfo(device_dataOne);
                result.add(device_dataOne);
            }
        }
        return result;
    }

    public List<Device_data> getReaelPositionDevice(Device_data device_data) {

        Double minLatitude = device_data.getLatitude() - DeviceDataResult.getPOSITION_LATITUDE_RADIUS(device_data.getLatitude());
        Double maxLatitude = device_data.getLatitude() + DeviceDataResult.getPOSITION_LATITUDE_RADIUS(device_data.getLatitude());

        Double minLongitude = device_data.getLongitude() - DeviceDataResult.getPOSITION_LONGITUDE_RADIUS();
        Double maxLongitude = device_data.getLongitude() + DeviceDataResult.getPOSITION_LONGITUDE_RADIUS();
        User Loginuser = getLoginInfo(device_data);
        List<Device_data> all = new ArrayList<Device_data>();
        if (Loginuser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)) {
            Loginuser.setS_id(Loginuser.getU_id());
        }
        List<String> allDevicestrings = getTableAllKeys(RedisUtils.getDeviceTableName(Loginuser.getS_id().toString()));
        if (StringUtils.isEmpty(allDevicestrings) || allDevicestrings.size() == 0) {
            return all;
        }
        List<String> allResultString = getKeysToRedis(allDevicestrings, RedisUtils.getRealTimeTable());
        if (StringUtils.isEmpty(allResultString) || allResultString.size() == 0) {
            return all;
        }
        for (int i = 0; i < allResultString.size(); i++) {
            Device_data one = JSONObject.parseObject(allResultString.get(i), Device_data.class);
            if (StringUtils.isEmpty(one) || StringUtils.isEmpty(one.getLatitude()) || StringUtils.isEmpty(one.getLongitude())) {

                continue;
            }
            if (one.getLatitude() <= maxLatitude &&
                    one.getLatitude() >= minLatitude &&
                    one.getLongitude() <= maxLongitude &&
                    one.getLongitude() >= minLongitude) {
                all.add(one);
            }
        }

        return all;
    }

    public List<Object> LV3Linkage(Device_data device_data) {
        User Loginuser = getLoginInfo(device_data);
        List<Object> result = new ArrayList<Object>();
        Map<Long, Device_data> AllRealTime = gtAllRealTimeDeviceData();
        if (Loginuser.getU_type().equals(UserResult.ROOT_CODE_TYPE)) {
            List<String> allQiye = getAllDataByKey(RedisUtils.getUserTableName(Loginuser.getU_id().toString()));
            for (int i = 0; i < allQiye.size(); i++) {
                User QiyeOne = JSONObject.parseObject(allQiye.get(i), User.class);
                Map<String, Object> QiyeOneMap = new HashMap<String, Object>();
                QiyeOneMap.put("name", QiyeOne.getU_nickname());
                QiyeOneMap.put("id", QiyeOne.getU_id());
                List<Object> allFenzu = new ArrayList<Object>();
                List<String> allFenzuString = getAllDataByKey(RedisUtils.getDeviceGroupTableName(QiyeOne.getU_id().toString()));
                List<String> alldeviceString = getAllDataByKey(RedisUtils.getDeviceTableName(QiyeOne.getU_id().toString()));
                Map<Long, Device> allDevice = new HashMap<Long, Device>();
                for (int k = 0; k < alldeviceString.size(); k++) {
                    try {
                        Device DeviceOne = JSONObject.parseObject(alldeviceString.get(k), Device.class);
                        allDevice.put(DeviceOne.getD_id(), DeviceOne);
                    } catch (Exception e) {

                    }

                }
                for (int j = 0; j < allFenzuString.size(); j++) {
                    Device_group deviceGroupOne = JSONObject.parseObject(allFenzuString.get(j), Device_group.class);
                    Map<String, Object> deviceGroupOneMap = new HashMap<String, Object>();
                    deviceGroupOneMap.put("name", deviceGroupOne.getD_g_name());
                    deviceGroupOneMap.put("id", deviceGroupOne.getD_g_id());
                    List<Object> allDeviceByGroup = new ArrayList<Object>();
                    Map groupone = new HashMap();
                    for (Long d_id : allDevice.keySet()) {
                        Device DeviceOne = allDevice.get(d_id);
                        L.w(" deviceone" + DeviceOne.toString());
                        Device_data one = new Device_data();
                        if (deviceGroupOne.getD_g_id().equals(DeviceOne.getD_g_id())) {
                            Device_data Device_dataOne = AllRealTime.get(DeviceOne.getD_id());
                            if (!StringUtils.isEmpty(Device_dataOne)) {
                                one = Device_dataOne;
                                one.setName(DeviceOne.getD_name());
                            } else {
                                one.setName(DeviceOne.getD_name());
                                one.setD_id(DeviceOne.getD_id());
                            }
                            allDeviceByGroup.add(one);
                        }

                    }
                    groupone.put("id", deviceGroupOne.getD_g_id());
                    groupone.put("name", deviceGroupOne.getD_g_name());
                    deviceGroupOneMap.put("device", allDeviceByGroup);
                    allFenzu.add(deviceGroupOneMap);
                }

                QiyeOneMap.put("fenzu", allFenzu);
                result.add(QiyeOneMap);
            }
        } else {
            if (Loginuser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)) {
                Loginuser.setS_id(Loginuser.getU_id());
            }
            User QiyeOne = Loginuser;
            Map<String, Object> QiyeOneMap = new HashMap<String, Object>();
            QiyeOneMap.put("name", QiyeOne.getU_nickname());
            QiyeOneMap.put("id", QiyeOne.getS_id());
            List<Object> allFenzu = new ArrayList<Object>();
            List<String> allFenzuString = getAllDataByKey(RedisUtils.getDeviceGroupTableName(QiyeOne.getS_id().toString()));
            List<String> alldeviceString = getAllDataByKey(RedisUtils.getDeviceTableName(QiyeOne.getS_id().toString()));
            Map<Long, Device> allDevice = new HashMap<Long, Device>();
            for (int k = 0; k < alldeviceString.size(); k++) {
                Device DeviceOne = JSONObject.parseObject(alldeviceString.get(k), Device.class);
                allDevice.put(DeviceOne.getD_id(), DeviceOne);
            }
            for (int j = 0; j < allFenzuString.size(); j++) {
                Device_group deviceGroupOne = JSONObject.parseObject(allFenzuString.get(j), Device_group.class);
                Map<String, Object> deviceGroupOneMap = new HashMap<String, Object>();
                deviceGroupOneMap.put("name", deviceGroupOne.getD_g_name());
                deviceGroupOneMap.put("id", deviceGroupOne.getD_g_id());
                List<Object> allDeviceByGroup = new ArrayList<Object>();
                Map groupone = new HashMap();
                for (Long d_id : allDevice.keySet()) {
                    Device DeviceOne = allDevice.get(d_id);
                    L.w(" deviceone" + DeviceOne.toString());
                    Device_data one = new Device_data();
                    if (deviceGroupOne.getD_g_id().equals(DeviceOne.getD_g_id())) {
                        Device_data Device_dataOne = AllRealTime.get(DeviceOne.getD_id());
                        if (!StringUtils.isEmpty(Device_dataOne)) {
                            one = Device_dataOne;
                            one.setName(DeviceOne.getD_name());
                        } else {
                            one.setName(DeviceOne.getD_name());
                            one.setD_id(DeviceOne.getD_id());
                        }
                        allDeviceByGroup.add(one);
                    }

                }
                groupone.put("id", deviceGroupOne.getD_g_id());
                groupone.put("name", deviceGroupOne.getD_g_name());
                deviceGroupOneMap.put("device", allDeviceByGroup);
                allFenzu.add(deviceGroupOneMap);
            }

            QiyeOneMap.put("fenzu", allFenzu);
            result.add(QiyeOneMap);
        }
        return result;

    }


    public List<Device_data> StringToDeviceData(List<String> all) {
        List<Device_data> list = new ArrayList<Device_data>();
        for (int i = 0; i < all.size(); i++) {
            String one = all.get(i);
            if (!StringUtils.isEmpty(one)) {
                list.add(JSON.parseObject(one, Device_data.class));
            }
        }
        return list;
    }

    public Device_data D_idQueryDeviceInfo(Device_data device_data) {
        Device_data result = device_data;
        String tableName = RedisUtils.getDeviceTableName("*");
        List<String> strings = likeQueryAllTableName(tableName);
        for (int i = 0; i < strings.size(); i++) {
            String one = getDataByKey(strings.get(i), device_data.getD_id().toString());
            if (!StringUtils.isEmpty(one)) {
                Device deviceOne = JSONObject.parseObject(one, Device.class);

                result.setD_id(deviceOne.getD_id());
                result.setD_oct_id(deviceOne.getD_oct_id());
                result.setD_factory_id(deviceOne.getD_factory_id());
                result.setD_name(deviceOne.getD_name());
                result.setName(deviceOne.getD_name());
                result.setD_type(deviceOne.getD_type());

                result.setD_status(deviceOne.getD_status());
                result.setD_mark(deviceOne.getD_mark());

                result.setD_temp_limit_top(deviceOne.getD_temp_limit_top());
                result.setD_temp_limit_floor(deviceOne.getD_temp_limit_floor());
                result.setD_work_temp_1(deviceOne.getD_work_temp_1());
                result.setD_work_temp_2(deviceOne.getD_work_temp_2());
                result.setFrequency_1(deviceOne.getFreqency_1());
                result.setFrequency_2(deviceOne.getFreqency_2());
                result.setBluetooth(deviceOne.getBlueteeth());
                result.setSM_code(deviceOne.getSM_code());
            }
        }
        return result;
    }

    public List<Device_data> query(Device_data device_data) {
        return deviceDataDAO.queryDeviceDataByDidAndTime(device_data);
    }

    public Device getDeviceById(Device device) {
        try {
            List<String> list = likeQueryAllTableName(RedisUtils.getDeviceTableName("*"));
            L.w("  getDeviceById list :" + list.toString());
            Device all = null;
            for (int i = 0; i < list.size(); i++) {
                String allString = getDataByKey(list.get(i), device.getD_id().toString());
                if (!StringUtils.isEmpty(allString)) {
                    all = JSON.parseObject(allString, Device.class);
                    return all;
                }
            }
        } catch (Exception e) {
            L.f("getDeviceById 方法异常：" + e.toString());
            return null;
        }
        return null;
    }

    public void DeviceDataToRedis(List<Device_data> list) {
        Collections.sort(list);
        if (!StringUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                Device_data one = list.get(i);
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    L.w("sleep 异常");
                }
                if (!StringUtils.isEmpty(one)) {
                    setDataToRedis(
                            RedisUtils.getDeviceDataTableName(one.getD_id(), timeUtils.getYYMMByTime(one.getDevice_time()))
                            , timeUtils.getYYMMddhhmmssByTime(one.getDevice_time())
                            , one
                    );
                }

            }
        }

    }

    public Boolean updaeOneTmp(Device_data device_data) {
        try {
            User LoginUser = getLoginInfo(device_data);
            Long sid = LoginUser.getS_id();
            if (LoginUser.getU_type().equals("2")) {
                sid = LoginUser.getU_id();
            }
            Device_data one = deviceDataDAO.queryOneTmp(device_data);
            List<String> time = timeUtils.getLastAndNext5Min(device_data.getDevice_time());
            one.setTemp_1(device_data.getTemp_1());
            one.setGps_time(device_data.getDevice_time());
            one.setServer_time(device_data.getDevice_time());
            one.setDevice_time(device_data.getDevice_time());

            if (sid.intValue() == 3094L) {
                List<String> allList = redisDb2Service.getKeysToRedis(time, RedisUtils.getDeviceDataTableName(device_data.getD_id(), timeUtils.getYYMMByTime(device_data.getDevice_time())));
                if(!StringUtils.isEmpty(one)){
                    redisDb2Service.setDataToRedis(RedisUtils.getDeviceDataTableName(device_data.getD_id(), timeUtils.getYYMMByTime(one.getDevice_time())), timeUtils.getYYMMddhhmmssByTime(one.getDevice_time()), one);

                }
                 for (int i = 0; i < allList.size(); i++) {
                    try {
                        String listOne = allList.get(i);
                        if (!StringUtils.isEmpty(listOne)) {
                            one = (Device_data) JSONUtils.parse(listOne, Device_data.class);
                            one.setTemp_1(device_data.getTemp_1());
                            one.setGps_time(device_data.getDevice_time());
                            one.setServer_time(device_data.getDevice_time());
                            L.w("" + one.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    redisDb2Service.setDataToRedis(RedisUtils.getDeviceDataTableName(device_data.getD_id(), timeUtils.getYYMMByTime(one.getDevice_time())), timeUtils.getYYMMddhhmmssByTime(one.getDevice_time()), one);
                }
            }
//            else {
//                List<Device_data> alldata = new ArrayList<>();
//                for (int n = 0; n < time.size(); n++) {
//                    String stringONe = redisDb2Service.getDataByKey(RedisUtils.getDeviceDataTableName(device_data.getD_id(), timeUtils.getYYMMByTime(device_data.getDevice_time())), time.get(n));
//                    try {
//                        Device_data oneDevice_data = (Device_data) JSONUtils.parse(stringONe, Device_data.class);
//                        if (StringUtils.isEmpty(oneDevice_data)) {
//                            continue;
//                        }
//                        alldata.add(oneDevice_data);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (alldata.size() == 0) {
//                    setDataToRedis(RedisUtils.getDeviceDataTableName(one.getD_id(), timeUtils.getYYMMByTime(one.getDevice_time())), timeUtils.getYYMMddhhmmssByTime(one.getDevice_time()), one);
//                } else {
//                    for (int i = alldata.size() - 1; i >= 0; i--) {
//                        try {
//                            Device_data dataone = alldata.get(i);
//                            try {
//                                if (!StringUtils.isEmpty(dataone)) {
//                                    dataone.setTemp_1(device_data.getTemp_1());
//                                    dataone.setGps_time(device_data.getDevice_time());
//                                    dataone.setServer_time(device_data.getDevice_time());
//                                    dataone.setDevice_time(device_data.getDevice_time());
//                                    setDataToRedis(RedisUtils.getDeviceDataTableName(one.getD_id(), timeUtils.getYYMMByTime(one.getDevice_time())), timeUtils.getYYMMddhhmmssByTime(one.getDevice_time()), dataone);
//                                    break;
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                continue;
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            continue;
//                        }
//
//                    }
//                }
//
//
//            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
