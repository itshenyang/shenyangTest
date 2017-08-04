package com.keruis.cilent.services;

import com.keruis.cilent.utils.JSONUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/23.
 */
@Service
public class RedisDb2Service {



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
        JedisPool pool = new JedisPool(config, host, 6379, Protocol.DEFAULT_TIMEOUT, pwd, 2);
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
            if (jedis != null) {
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
            if (jedis != null) {
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
            if (jedis != null) {
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
            if (jedis != null) {
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
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public void setJedisExpiration(String KeyId, int ExpirationTime) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(KeyId, ExpirationTime);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void delDataToRedis(String KeyId) {
        Jedis jedis = getJedis();
        try {
            jedis.del(KeyId);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void delDataToRedis(String TableName, String KeyId) {
        Jedis jedis = getJedis();
        try {
            jedis.hdel(TableName, KeyId);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> getTableAllKeys(String Table) {
        Jedis jedis = getJedis();
        try {
            Set<String> staffsSet = jedis.hkeys(Table);
            List<String> result = new ArrayList<String>(staffsSet);
            return result;
        } finally {
            if (jedis != null) {
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
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> likeQueryAllTableName(String tablename) {
        List<String> all = new ArrayList<String>();
        Jedis jedis = getJedis();
        try {
            Set<String> strings = jedis.keys(tablename);
            for (String str : strings) {
                all.add(str);
            }
            return all;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
