package com.keruis.cilent.controllers;

import com.keruis.cilent.dao.pojos.*;
import com.keruis.cilent.entities.DeviceDataResult;
import com.keruis.cilent.entities.UserResult;
import com.keruis.cilent.services.Tmp_alarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */
@Controller
@RequestMapping(value = "TmpAlarm")
public class TmpAlarmController extends BaseController {
    @Autowired
    Tmp_alarmService tmp_alarmService;

    @RequestMapping(value = "CloseAlarm", method = RequestMethod.POST)
    @ResponseBody
    public Object CloseAlarm(@RequestBody Tmp_alarm  tmp_alarm) {
        User LoginUser = baseService.getLoginInfo(tmp_alarm);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        if(StringUtils.isEmpty(tmp_alarm.getDevice_id()) ){
            deviceDataResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            deviceDataResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return deviceDataResult;
        }
        tmp_alarm.setUser_id(LoginUser.getU_id());
        tmp_alarm.setState("1");
        if(tmp_alarmService.insertSucceed(tmp_alarm)){
            deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
            deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
            return deviceDataResult;
        }
        deviceDataResult.setResultCode(DeviceDataResult.FAILURE);
        deviceDataResult.setResultMsg(DeviceDataResult.FAILURE_MSG);
         return deviceDataResult;
    }


    @RequestMapping(value = "OpenAlarm", method = RequestMethod.POST)
    @ResponseBody
    public Object OpenAlarm(@RequestBody Tmp_alarm  tmp_alarm) {
        User LoginUser = baseService.getLoginInfo(tmp_alarm);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        if(StringUtils.isEmpty(tmp_alarm.getDevice_id()) ){
            deviceDataResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            deviceDataResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return deviceDataResult;
        }
        tmp_alarm.setUser_id(LoginUser.getU_id());
        if(tmp_alarmService.deleteSucceed(tmp_alarm)){
            deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
            deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
            return deviceDataResult;
        }
        deviceDataResult.setResultCode(DeviceDataResult.FAILURE);
        deviceDataResult.setResultMsg(DeviceDataResult.FAILURE_MSG);
        return deviceDataResult;
    }



    @RequestMapping(value = "update_ALARM_RATE", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUSER_ALARM_RATE(@RequestBody Device device) {
        User LoginUser = baseService.getLoginInfo(device);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        Long sid = LoginUser.getS_id();
         if(LoginUser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)){
             sid=LoginUser.getU_id();
         }
         tmp_alarmService.updateUSER_ALARM_RATE(sid,device.getFreqency_1(),device.getFreqency_2());
        deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
        deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        return deviceDataResult;
    }

    @RequestMapping(value = "query_ALARM_RATE", method = RequestMethod.POST)
    @ResponseBody
    public Object query_ALARM_RATE(@RequestBody Device device) {
        User LoginUser = baseService.getLoginInfo(device);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        Long sid = LoginUser.getS_id();
        if(LoginUser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)){
            sid=LoginUser.getU_id();
        }
        Device device1 = tmp_alarmService.queryUSER_ALARM_RATE(sid);
        deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
        deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        deviceDataResult.setData(device1);
        return deviceDataResult;
    }




}
