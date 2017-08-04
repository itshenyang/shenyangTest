package com.keruis.cilent.controllers;

import com.alibaba.fastjson.JSONObject;
import com.keruis.cilent.dao.pojos.Device_data;
import com.keruis.cilent.dao.pojos.Records;
import com.keruis.cilent.dao.pojos.Signs;
import com.keruis.cilent.dao.pojos.User;
import com.keruis.cilent.entities.DeviceDataResult;
import com.keruis.cilent.entities.RecordResult;
import com.keruis.cilent.entities.UserResult;
import com.keruis.cilent.services.RedisDb2Service;
import com.keruis.cilent.utils.L;
import com.keruis.cilent.utils.PositionUtils;
import com.keruis.cilent.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/14.
 */
@Controller
@RequestMapping(value = "record")
public class RecordController extends BaseController {

    @Autowired
    RedisDb2Service redisDb2Service;

    @RequestMapping(value = "openRecord", method = RequestMethod.POST)
    @ResponseBody
    public Object openRecord(@RequestBody Records records) {
        User LoginUser = baseService.getLoginInfo(records);
        RecordResult recordResult = new RecordResult();
        if (StringUtils.isEmpty(LoginUser)) {
            recordResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            recordResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return recordResult;
        }
        if (StringUtils.isEmpty(records.getDevice_id()) ||
                StringUtils.isEmpty(records.getStart_lng()) ||
                StringUtils.isEmpty(records.getStart_lat())) {
            recordResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            recordResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return recordResult;
        }
        if (!LoginUser.getU_type().equals(UserResult.ROOT_CODE_TYPE) &&
                !LoginUser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE) && !LoginUser.getU_type().equals(UserResult.GENERAL_MANAGER_CODE_TYPE)) {
            recordResult.setResultCode(DeviceDataResult.USER_NO_JURISDICTION);
            recordResult.setResultMsg(DeviceDataResult.USER_NO_JURISDICTION_MSG);
            return recordResult;
        }
        Device_data realtime = new Device_data();
        String realtimeString = baseService.getDataByKey(RedisUtils.getRealTimeTable(), records.getDevice_id().toString());
        String realtimeString2 = redisDb2Service.getDataByKey(RedisUtils.getRealTimeTable(), records.getDevice_id().toString());
        if (!StringUtils.isEmpty(realtimeString)) {
            realtime = JSONObject.parseObject(realtimeString2, Device_data.class);
            if (!StringUtils.isEmpty(realtime.getRecord_id())) {
                if (Integer.parseInt(realtime.getRecord_id().toString()) != 0) {
                    recordResult.setResultCode(RecordResult.RECORD_IS_OPEN);
                    recordResult.setResultMsg(RecordResult.RECORD_IS_OPEN_MSG);
                    return recordResult;
                }
            }
        }
        records.setCreator(LoginUser.getU_id());
        Map map = PositionUtils.getPosition(records.getStart_lng().toString(), records.getStart_lat().toString());

        if (!StringUtils.isEmpty(map)) {
            records.setStart_lat(Double.parseDouble(map.get("lat").toString()));
            records.setStart_lng(Double.parseDouble(map.get("log").toString()));
            records.setStart_address(map.get("regeocode").toString());
            records.setStart_road(map.get("roads").toString());
        }
        if (recordService.OnRecordSucceed(records)) {

            ///////////////////////////////
            L.w(realtime.toString());
            realtime.setRecord_id(records.getId());

            baseService.setDataToRedis(RedisUtils.getRealTimeTable(), realtime.getD_id().toString(), realtime);

            redisDb2Service.setDataToRedis(RedisUtils.getRealTimeTable(), realtime.getD_id().toString(), realtime);

            ///////////////////////////////

            recordResult.setResultCode(DeviceDataResult.SUCCESS);
            recordResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
            return recordResult;
        }
        recordResult.setResultCode(DeviceDataResult.FAILURE);
        recordResult.setResultMsg(DeviceDataResult.FAILURE_MSG);
        return recordResult;
    }

    @RequestMapping(value = "signOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object signOrder(@RequestBody Signs signs) {

        User LoginUser = baseService.getLoginInfo(signs);
        RecordResult recordResult = new RecordResult();
        if (StringUtils.isEmpty(LoginUser)) {
            recordResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            recordResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return recordResult;
        }
        if (StringUtils.isEmpty(StringUtils.isEmpty(signs.getDevice_id()))) {
            recordResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            recordResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return recordResult;
        }

        if (!LoginUser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE) &&
                !LoginUser.getU_type().equals(UserResult.GENERAL_MANAGER_CODE_TYPE) &&
                !LoginUser.getU_type().equals(UserResult.GENERAL_USER_CODE_TYPE)) {

            recordResult.setResultCode(DeviceDataResult.USER_NO_JURISDICTION);
            recordResult.setResultMsg(DeviceDataResult.USER_NO_JURISDICTION_MSG);
            return recordResult;
        }

        if (!StringUtils.isEmpty(signs.getSign_lng()) && !StringUtils.isEmpty(signs.getSign_lat())) {
            Map map = PositionUtils.getPosition(signs.getSign_lng().toString(), signs.getSign_lat().toString());
            signs.setSign_lat(Double.parseDouble(map.get("lat").toString()));
            signs.setSign_lng(Double.parseDouble(map.get("log").toString()));
            signs.setSign_address(map.get("regeocode").toString());
            signs.setSign_road(map.get("roads").toString());
        }
        signs.setSigner(LoginUser.getU_id());
        Device_data realtime = new Device_data();
        String realtimeString = redisDb2Service.getDataByKey(RedisUtils.getRealTimeTable(), signs.getDevice_id().toString());

        if (!StringUtils.isEmpty(realtimeString)) {
            realtime = JSONObject.parseObject(realtimeString, Device_data.class);
            if (StringUtils.isEmpty(realtime.getRecord_id())) {
                recordResult.setResultCode(RecordResult.SIGN_IS_CLOSE);
                recordResult.setResultMsg(RecordResult.SIGN_IS_CLOSE_MSG);
                return recordResult;
            }
            if (!StringUtils.isEmpty(realtime.getSignser())) {
                for (int i = 0; i < realtime.getSignser().size(); i++) {
                    Long one = realtime.getSignser().get(i);
                    if (LoginUser.getU_id().equals(one)) {
                        recordResult.setResultCode(RecordResult.SIGN_IS_YET);
                        recordResult.setResultMsg(RecordResult.SIGN_IS_YET_MSG);
                        return recordResult;
                    }
                }

            }
        }

        signs.setRecord_id(Long.parseLong(realtime.getRecord_id().toString()));
        if (recordService.signsOrder(Long.parseLong(signs.getDevice_id().toString()), signs)) {
            ///////////////////////////////
            if (StringUtils.isEmpty(realtime.getSignser())) {
                realtime = JSONObject.parseObject(realtimeString, Device_data.class);
                List<Long> list = new ArrayList<>();
                list.add(LoginUser.getU_id());
                realtime.setSignser(list);
                baseService.setDataToRedis(RedisUtils.getRealTimeTable(), realtime.getD_id().toString(), realtime);
                redisDb2Service.setDataToRedis(RedisUtils.getRealTimeTable(), realtime.getD_id().toString(), realtime);
            } else {
                realtime.getSignser().add(LoginUser.getU_id());
                baseService.setDataToRedis(RedisUtils.getRealTimeTable(), realtime.getD_id().toString(), realtime);
                redisDb2Service.setDataToRedis(RedisUtils.getRealTimeTable(), realtime.getD_id().toString(), realtime);
            }
            ///////////////////////////////
            recordResult.setResultCode(DeviceDataResult.SUCCESS);
            recordResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
            return recordResult;
        }
        recordResult.setResultCode(DeviceDataResult.FAILURE);
        recordResult.setResultMsg(DeviceDataResult.FAILURE_MSG);
        return recordResult;

    }


    @RequestMapping(value = "closeRecord", method = RequestMethod.POST)
    @ResponseBody
    public Object closeRecord(@RequestBody Records records) {
        User LoginUser = baseService.getLoginInfo(records);
        RecordResult recordResult = new RecordResult();
        if (StringUtils.isEmpty(LoginUser)) {
            recordResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            recordResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return recordResult;
        }
        if (StringUtils.isEmpty(records.getClose_lat()) ||
                StringUtils.isEmpty(records.getClose_lng())) {
            recordResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            recordResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return recordResult;
        }
        if (!LoginUser.getU_type().equals(UserResult.ROOT_CODE_TYPE) &&
                !LoginUser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE) &&
                !LoginUser.getU_type().equals(UserResult.GENERAL_MANAGER_CODE_TYPE)
                ) {
            recordResult.setResultCode(DeviceDataResult.USER_NO_JURISDICTION);
            recordResult.setResultMsg(DeviceDataResult.USER_NO_JURISDICTION_MSG);
            return recordResult;
        }
        Device_data realtime = new Device_data();
        String realtimeString = redisDb2Service.getDataByKey(RedisUtils.getRealTimeTable(), records.getDevice_id().toString());
        if (!StringUtils.isEmpty(realtimeString)) {
            realtime = JSONObject.parseObject(realtimeString, Device_data.class);
            if (!StringUtils.isEmpty(realtime.getRecord_id())) {
                if ((int) realtime.getRecord_id() == 0) {
                    recordResult.setResultCode(RecordResult.RECORD_IS_CLOSE);
                    recordResult.setResultMsg(RecordResult.RECORD_IS_CLOSE_MSG);
                    return recordResult;
                }
            } else {
                recordResult.setResultCode(RecordResult.RECORD_IS_CLOSE);
                recordResult.setResultMsg(RecordResult.RECORD_IS_CLOSE_MSG);
                return recordResult;
            }
            records.setId(Long.parseLong(realtime.getRecord_id().toString()));
        }
        records.setCloser(LoginUser.getU_id());
        Map map = PositionUtils.getPosition(records.getClose_lng().toString(), records.getClose_lat().toString());
        records.setId(Long.parseLong(realtime.getRecord_id().toString()));
        if (!StringUtils.isEmpty(map)) {
            records.setClose_lat(Double.parseDouble(map.get("lat").toString()));
            records.setStart_lng(Double.parseDouble(map.get("log").toString()));
            records.setClose_address(map.get("regeocode").toString());
            records.setClose_road(map.get("roads").toString());
        }
        if (recordService.CloseRecordSucceed(records)) {
            ///////////////////////////////
            realtime.setRecord_id(null);
            realtime.setSignser(null);
            baseService.setDataToRedis(RedisUtils.getRealTimeTable(), realtime.getD_id().toString(), realtime);
            redisDb2Service.setDataToRedis(RedisUtils.getRealTimeTable(), realtime.getD_id().toString(), realtime);
            ///////////////////////////////
            recordResult.setResultCode(DeviceDataResult.SUCCESS);
            recordResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
            return recordResult;
        }
        recordResult.setResultCode(DeviceDataResult.FAILURE);
        recordResult.setResultMsg(DeviceDataResult.FAILURE_MSG);
        return recordResult;
    }

    @RequestMapping(value = "queryAllRecordByDid", method = RequestMethod.POST)
    @ResponseBody
    public Object queryAllRecordByDid(@RequestBody Records records) {
        User LoginUser = baseService.getLoginInfo(records);
        RecordResult recordResult = new RecordResult();
        if (StringUtils.isEmpty(LoginUser)) {
            recordResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            recordResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return recordResult;
        }
        if (StringUtils.isEmpty(records.getDevice_id())) {
            recordResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            recordResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return recordResult;
        }
        List<Records> list = recordService.getAllRecords(records.getDevice_id());
        if (StringUtils.isEmpty(list) || list.size() == 0) {
            recordResult.setResultCode(DeviceDataResult.RESULT_ISNULL);
            recordResult.setResultMsg(DeviceDataResult.RESULT_ISNULL_MSG);
            return recordResult;
        }
        if (StringUtils.isEmpty(records.getS_id())) {
            if (LoginUser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)) {
                records.setS_id(LoginUser.getU_id());
            } else {
                records.setS_id(LoginUser.getS_id());
            }
        }
        list = recordService.setRescordsAssociatedUser(list, LoginUser);
        Collections.sort(list);
        recordResult.setData(list);
        recordResult.setResultCode(DeviceDataResult.SUCCESS);
        recordResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        return recordResult;
    }

    @RequestMapping(value = "queryOneRecordByRid", method = RequestMethod.POST)
    @ResponseBody
    public Object queryOneRecordByRid(@RequestBody Records records) {
        User LoginUser = baseService.getLoginInfo(records);
        RecordResult recordResult = new RecordResult();
        if (StringUtils.isEmpty(LoginUser)) {
            recordResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            recordResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return recordResult;
        }
        if (StringUtils.isEmpty(records.getDevice_id())
                || StringUtils.isEmpty(records.getId())) {
            recordResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            recordResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return recordResult;
        }
        Records list = recordService.getRecords(records.getDevice_id(), records.getId());
        if (StringUtils.isEmpty(list)) {
            recordResult.setResultCode(DeviceDataResult.RESULT_ISNULL);
            recordResult.setResultMsg(DeviceDataResult.RESULT_ISNULL_MSG);
            return recordResult;
        }
        if (StringUtils.isEmpty(records.getS_id())) {
            if (LoginUser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)) {
                records.setS_id(LoginUser.getU_id());
            } else {
                records.setS_id(LoginUser.getS_id());
            }
        }
        List<Records> result = new ArrayList<>();
        result.add(list);
        result = recordService.setRescordsAssociatedUser(result, LoginUser);
        recordResult.setData(result.get(0));
        recordResult.setResultCode(DeviceDataResult.SUCCESS);
        recordResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        return recordResult;
    }

    @RequestMapping(value = "queryAllSignsByRid", method = RequestMethod.POST)
    @ResponseBody
    public Object queryAllSignsByRid(@RequestBody Records records) {
        User LoginUser = baseService.getLoginInfo(records);
        RecordResult recordResult = new RecordResult();
        if (StringUtils.isEmpty(LoginUser)) {
            recordResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            recordResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return recordResult;
        }
        if (StringUtils.isEmpty(records.getDevice_id())
                || StringUtils.isEmpty(records.getId())) {
            recordResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            recordResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return recordResult;
        }
        List<Signs> list = recordService.getAllSignsByRecordsId(records.getDevice_id(), records.getId());
        if (StringUtils.isEmpty(list) || list.size() == 0) {
            recordResult.setResultCode(DeviceDataResult.RESULT_ISNULL);
            recordResult.setResultMsg(DeviceDataResult.RESULT_ISNULL_MSG);
            return recordResult;
        }
        if (StringUtils.isEmpty(records.getS_id())) {
            if (LoginUser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)) {
                records.setS_id(LoginUser.getU_id());
            } else {
                records.setS_id(LoginUser.getS_id());
            }
        }
        list = recordService.setSignsAssociatedUser(list, LoginUser);
        Collections.sort(list);
        recordResult.setData(list);
        recordResult.setResultCode(DeviceDataResult.SUCCESS);
        recordResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        return recordResult;
    }
    @RequestMapping(value = "RecordTest", method = RequestMethod.POST)
    public void RecordTest() {
        recordTest.updateDB2RecordId();
    }
}
