package com.keruis.cilent.services;

import com.alibaba.fastjson.JSON;
import com.keruis.cilent.dao.DeviceDataDAO;
import com.keruis.cilent.dao.pojos.BasePOJO;
import com.keruis.cilent.dao.pojos.Device;
import com.keruis.cilent.dao.pojos.Device_group;
import com.keruis.cilent.dao.pojos.User;
import com.keruis.cilent.entities.UserResult;
import com.keruis.cilent.utils.JSONUtils;
import com.keruis.cilent.utils.L;
import com.keruis.cilent.utils.RedisUtils;
import com.keruis.cilent.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2017/3/28.
 */
@Service
public class BaseService {

    @Autowired
    protected DeviceDataDAO deviceDataDAO;


    protected TimeUtils timeUtils = new TimeUtils();

    protected final int FailureTime = 1 * 60 * 60;//用户登录信息过期时间


    private JedisPool jedisPool = getJedisPool();
    private final String host = "hb201j.keruis.com";
    private final String pwd = "Keruis@123";


    public JedisPool getJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
        config.setMaxIdle(20);
        config.setMinIdle(10);
        config.setMaxTotal(300);
        config.setMinEvictableIdleTimeMillis(1800000);
        config.setSoftMinEvictableIdleTimeMillis(1800000);
        config.setTestOnBorrow(true);
        JedisPool pool = new JedisPool(config, host, 6379, Protocol.DEFAULT_TIMEOUT, pwd);
        return pool;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }




    public String getDataByKey(String TableName, String KeyId) {
        Jedis jedis = getJedis();
        try {
            String userString = jedis.hget(TableName, KeyId);
            return userString;
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }

    }

    public String getDataByKey(String KeyId) {
        Jedis jedis = getJedis();
        try {
            String userString = jedis.get(KeyId);
            return userString;
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public List<String> getAllDataByKey(String TableName) {
        Jedis jedis = getJedis();
        try {
            List<String> userString = jedis.hvals(TableName);
            return userString;
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public void setDataToRedis(String TableName, String KeyId, Object pojo) {
        Jedis jedis = getJedis();
        try {
            String data = JSONUtils.toString(pojo);
            jedis.hset(TableName, KeyId, data);
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }
    public void setDataToRedis(String TableName, String KeyId, String pojo) {
        Jedis jedis = getJedis();
        try {
            jedis.hset(TableName, KeyId, pojo);
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public void setDataToRedis(String TableName, Object pojo) {
        Jedis jedis = getJedis();
        try {
            String data = JSONUtils.toString(pojo);
            jedis.set(TableName, data);
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }


    public void setJedisExpiration(String KeyId, int ExpirationTime) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(KeyId, ExpirationTime);
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public void delDataToRedis(String KeyId) {
        Jedis jedis = getJedis();
        try {
            jedis.del(KeyId);
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public void delDataToRedis(String TableName, String KeyId) {
        Jedis jedis = getJedis();
        try {
            jedis.hdel(TableName, KeyId);
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }


    public User getUser(User user) {
        User userObj = JSON.parseObject(getDataByKey(RedisUtils.getUserTableName(user.getS_id().toString()), user.getU_id().toString()), User.class);
        if (StringUtils.isEmpty(userObj)) {
            return null;
        }
        L.w(userObj.toString());
        return userObj;
    }

    public User getLoginInfo(BasePOJO basePOJO) {
        Timestamp newTime =timeUtils.getTimeNow();
        if (StringUtils.isEmpty(basePOJO)) {
            return null;
        }
        if (StringUtils.isEmpty(basePOJO.getTokenuserid())) {
            return null;
        }
        String IDString = getDataByKey(RedisUtils.getTokenuseridTableName(),basePOJO.getTokenuserid());
        if (StringUtils.isEmpty(IDString)) {
            return null;
        }
        User userObj = JSON.parseObject(IDString, User.class);
        if( newTime.getTime() > userObj.getTokenuserid_time().getTime() + FailureTime * 1000){
            delDataToRedis(RedisUtils.getTokenuseridTableName(),basePOJO.getTokenuserid());
            return null;
        }
        String userString = getDataByKey(RedisUtils.getUserTableName(userObj.getS_id().toString()), userObj.getU_id().toString());
        if (StringUtils.isEmpty(userString)) {
            return null;
        }
        User Loginuser = JSON.parseObject(userString, User.class);

        if (StringUtils.isEmpty(Loginuser)) {
            return null;
        }
        Loginuser.setTokenuserid(basePOJO.getTokenuserid());
        if (Loginuser.getU_status().equals(UserResult.ACCOUNT_DISABLED_STATE)) {
            return null;
        }
        Loginuser.setTokenuserid_time(newTime);
        setDataToRedis(RedisUtils.getTokenuseridTableName(),basePOJO.getTokenuserid(),Loginuser);
        return Loginuser;
    }

    public List<String> getTableAllKeys(String Table) {
        Jedis jedis = getJedis();
        try {
            Set<String> staffsSet = jedis.hkeys(Table);
            List<String> result = new ArrayList<String>(staffsSet);
            return result;
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public List<String> getQueryKeysByTime(List<String> list, String time) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            String one = list.get(i);
            if (one.indexOf(time) != -1) {
                result.add(one);
            }
        }
        return result;
    }

    public List<String> getKeysToRedis(List<String> keys, String table) {
        Jedis jedis = getJedis();
        try {
            String[] kyesString = keys.toArray(new String[keys.size()]);
            if (kyesString.length == 0) {
                return new ArrayList<String>();
            }
            return jedis.hmget(table, kyesString);
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public List<String> likeQueryAllTableName(String tablename) {
        List<String> all = new ArrayList<>();
        Jedis jedis = getJedis();
        try {
            Set<String> strings = jedis.keys(tablename);
            for (String str : strings) {
                all.add(str);
            }
            return all;
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public List<User> getRootUser() {
        List<String> string = getAllDataByKey(RedisUtils.getUserTableName(UserResult.ROOT_USER_ID));
        List<User> list = new ArrayList<User>();
        if (StringUtils.isEmpty(string) || string.size() == 0) {
            return null;
        }
        for (int i = 0; i < string.size(); i++) {
            list.add(JSON.parseObject(string.get(i), User.class));
        }
        return list;
    }

    public List<Device> getAllDevice(Device device) {
        User LoginUser = getLoginInfo(device);
        List<Device> all = new ArrayList<Device>();
        if (StringUtils.isEmpty(device.getS_id())) {
            if (LoginUser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)) {
                device.setS_id(LoginUser.getU_id());
            } else {
                device.setS_id(LoginUser.getS_id());
            }
        }

        List<String> allString = getAllDataByKey(RedisUtils.getDeviceTableName(device.getS_id().toString()));
        if (allString.size() == 0) {

            return all;
        }
        for (int i = 0; i < allString.size(); i++) {
            Device one = JSON.parseObject(allString.get(i), Device.class);
            all.add(one);
        }
        return all;
    }
    public List<User> getAlluser(User user) {
        User Loginuser = getLoginInfo(user);
        List<User> all = new ArrayList<User>();
        List<String> allString;
        if (Loginuser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)) {
            allString = getAllDataByKey(RedisUtils.getUserTableName(Loginuser.getU_id().toString()));
        } else {
            allString = getAllDataByKey(RedisUtils.getUserTableName(Loginuser.getS_id().toString()));
        }
        for (int i = 0; i < allString.size(); i++) {
            User one = JSON.parseObject(allString.get(i), User.class);
            if (one.getU_type().equals(UserResult.ROOT_CODE_TYPE)) {
                continue;
            }
            if (Loginuser.getU_type().equals(UserResult.ENTERPRISE_CODE_TYPE)) {
                if (Loginuser.getU_id().equals(one.getU_id())) {
                    continue;
                }
            }
            all.add(one);
        }
        return all;
    }
    public Map<Long, User> getAllUserMap(User user) {
        List<User> list = getAlluser(user);
        Map<Long, User> result = new HashMap<Long, User>();
        for (int i = 0; i < list.size(); i++) {
            User one = list.get(i);
            result.put(one.getU_id(), one);
        }
        List<User> root = getRootUser();
        for (int i = 0; i < root.size(); i++) {
            result.put(root.get(i).getU_id(), root.get(i));
        }
        return result;
    }

}
