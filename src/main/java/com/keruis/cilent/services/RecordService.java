package com.keruis.cilent.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.keruis.cilent.dao.RecordDAO;
import com.keruis.cilent.dao.SignsDAO;
import com.keruis.cilent.dao.pojos.Records;
import com.keruis.cilent.dao.pojos.Signs;
import com.keruis.cilent.dao.pojos.User;
import com.keruis.cilent.entities.UserResult;
import com.keruis.cilent.utils.L;
import com.keruis.cilent.utils.RedisUtils;
import com.keruis.cilent.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/14.
 */
@Service
public class RecordService extends BaseService {

    @Autowired
    RecordDAO recordDAO;
    @Autowired
    SignsDAO signsDAO;
    @Autowired
    RedisDb2Service redisDb2Service;

    public boolean OnRecordSucceed(Records records) {
        Timestamp newTime = timeUtils.getTimeNow();
        records.setStart_time(newTime);
        records.setTokenuserid(null);
        if (recordDAO.OnRecord(records) == 1) {
            setDataToRedis(RedisUtils.getRecordsTableName(records.getDevice_id().toString()),records.getId().toString(),records);
            return true;
        }
        return false;
    }
    public Boolean signsOrder(Long device_id,Signs signs){
        Timestamp newTime = timeUtils.getTimeNow();
        signs.setSign_time(newTime);
        signs.setTokenuserid(null);
        if(signsDAO.insert(signs)==1){
            List<Signs> list = new ArrayList<>();
            String allSignsString =  getDataByKey(RedisUtils.getSignsTableName(device_id.toString()),signs.getRecord_id().toString());
            if(!StringUtils.isEmpty(allSignsString)){
                List<Signs> all = JSON.parseArray(allSignsString,Signs.class);
                list=all;
            }
            list.add(signs);
            setDataToRedis(RedisUtils.getSignsTableName(device_id.toString()),signs.getRecord_id().toString(),list);
            return true;
        }
        return false;
    }
    public boolean CloseRecordSucceed(Records records) {
        User Loginuser = getLoginInfo(records);
        if(Loginuser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)){
            Loginuser.setS_id(Loginuser.getU_id());
        }
        Timestamp newTime = timeUtils.getTimeNow();
        records.setClose_time(newTime);
        records.setTokenuserid("");
        if (recordDAO.CloseRecord(records) == 1) {
            String lodRecordsString = getDataByKey(RedisUtils.getRecordsTableName(records.getDevice_id().toString()),records.getId().toString());
            Records lodRecords = JSONObject.parseObject(lodRecordsString,Records.class);
            lodRecords.setCloser(records.getCloser());
            lodRecords.setClose_time(records.getClose_time());
            lodRecords.setClose_lng(records.getClose_lng());
            lodRecords.setClose_lat(records.getClose_lat());
            lodRecords.setClose_address(records.getClose_address());
            lodRecords.setClose_road(records.getClose_road());
            setDataToRedis(RedisUtils.getRecordsTableName(records.getDevice_id().toString()),records.getId().toString(),lodRecords);
            return true;
        }
        return false;
    }

    public Records getRecords(Long device_id,Long records_id ){
        if(StringUtils.isEmpty(records_id)){
            return null;
        }
        String recordsString =  getDataByKey(RedisUtils.getRecordsTableName(device_id.toString()),records_id.toString());
        if(StringUtils.isEmpty(recordsString)){
            return null;
        }
        Records result =  JSONObject.parseObject(recordsString, Records.class);
         if(StringUtils.isEmpty(result) ){
             return null;
         }
         return result;
    }
    public List<Records> getAllRecords(Long device_id ){
        List<String> recordsString =  getAllDataByKey(RedisUtils.getRecordsTableName(device_id.toString()) );
        if(StringUtils.isEmpty(recordsString) || recordsString.size()==0){
            return null;
        }
        List<Records> result = new ArrayList<>();
        for(int i =0;i<recordsString.size();i++){
            Records one =JSONObject.parseObject(recordsString.get(i), Records.class);
            result.add(one);
        }
        return result;
    }


    public List<Signs> getAllSignsByRecordsId (Long device_id,Long records_id ){
        if(StringUtils.isEmpty(records_id)){
            return null;
        }
        String signsString =  getDataByKey(RedisUtils.getSignsTableName(device_id.toString()),records_id.toString());
        if( StringUtils.isEmpty(signsString)){
            return null;
        }
        List<Signs> all = JSON.parseArray(signsString,Signs.class);
        return all;
    }

    public List<Records> setRescordsAssociatedUser(List<Records> list, User user) {
        if (StringUtils.isEmpty(list) || list.size() == 0) {
            return list;
        }
        Map<Long, User> allUserMap = getAllUserMap(user);
        for (int i = 0; i < list.size(); i++) {
            Records one = list.get(i);
            L.w(" one : " + one.toString());
            User U_creator_nickname = allUserMap.get(one.getCreator());
            User U_modifier_nickname = allUserMap.get(one.getCloser());
            if (!StringUtils.isEmpty(U_creator_nickname)) {
                one.setCreator_nickname(U_creator_nickname.getU_nickname());
            }
            if (!StringUtils.isEmpty(U_modifier_nickname)) {
                one.setCloser_nickname(U_modifier_nickname.getU_nickname());
            }
        }
        return list;
    }

    public List<Signs> setSignsAssociatedUser(List<Signs> list, User user) {
        if (StringUtils.isEmpty(list) || list.size() == 0) {
            return list;
        }
        Map<Long, User> allUserMap = getAllUserMap(user);
        for (int i = 0; i < list.size(); i++) {
            Signs one = list.get(i);
            User U_creator_nickname = allUserMap.get(one.getSigner());
            if (!StringUtils.isEmpty(U_creator_nickname)) {
                one.setSinger_nickname(U_creator_nickname.getU_nickname());
            }
        }
        return list;
    }

}
