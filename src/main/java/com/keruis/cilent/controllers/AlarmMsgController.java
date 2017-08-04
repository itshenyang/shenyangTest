package com.keruis.cilent.controllers;

import com.keruis.cilent.dao.AlarmMsgDAO;
import com.keruis.cilent.dao.pojos.AlarmMsg;
import com.keruis.cilent.dao.pojos.Device_data;
import com.keruis.cilent.dao.pojos.User;
import com.keruis.cilent.entities.DeviceDataResult;
import com.keruis.cilent.services.AlarmMsgService;
import com.keruis.cilent.utils.DeviceDataUtils;
import com.keruis.cilent.utils.L;
import com.keruis.cilent.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */
@Controller
@RequestMapping(value = "alarmmsg")
public class AlarmMsgController extends BaseController {
    @Autowired
    AlarmMsgService alarmMsgService;


    @RequestMapping(value = "query_alarm_ByTime", method = RequestMethod.POST)
    @ResponseBody
    public Object query_alarm_ByTime(@RequestBody Device_data device_data) {
        User LoginUser = baseService.getLoginInfo(device_data);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        if (StringUtils.isEmpty(device_data.getDevice_time()) ||
                StringUtils.isEmpty(device_data.getDevice_timeend())||
                StringUtils.isEmpty(device_data.getBluetooth())) {
            deviceDataResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            deviceDataResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return deviceDataResult;
        }
        device_data.setDelayed(PageUtils.pageSize); //一页条数
        if (StringUtils.isEmpty(device_data.getBluetooth())) {
            device_data.setBluetooth(1L);
        }
        device_data.setBluetooth(device_data.getBluetooth() * PageUtils.pageSize-PageUtils.pageSize);   //该页起点
        Integer AllNum = alarmMsgService.alarm_num_ByTime(device_data);
        if(AllNum.intValue()==0){
            deviceDataResult.setResultCode(DeviceDataResult.RESULT_ISNULL);
            deviceDataResult.setResultMsg(DeviceDataResult.RESULT_ISNULL_MSG);
            return deviceDataResult;
        }
        L.w(" AllNum  " + AllNum);
        List<AlarmMsg> list = alarmMsgService.query_alarm_ByTime(device_data);
        Map map = new HashMap();
        map.put("pageNum", PageUtils.getpageNum(AllNum));
        map.put("data", list);

        deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
        deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        deviceDataResult.setData(map);
        return deviceDataResult;
    }

}
