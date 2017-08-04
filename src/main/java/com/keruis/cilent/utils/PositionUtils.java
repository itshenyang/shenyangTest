package com.keruis.cilent.utils;

import com.alibaba.fastjson.JSONObject;
import com.keruis.cilent.entities.RecordResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/14.
 */
public class PositionUtils {

    public static String Coordinatetransformation(String log, String lat) {
        String res = "";
        String path = "http://restapi.amap.com/v3/assistant/coordinate/convert?locations=" + log + "," + lat + "&coordsys=gps&output=JSON&key=" + RecordResult.GuideMapKey;
        //参数直接加载url后面
        try {
            res = PositionUtils.okGet(path);
        } catch (Exception e) {
            L.w("位置信息转换失败");
            return res;
        }
        Map map = JSONObject.parseObject(res, Map.class);
        return map.get("locations").toString();
    }

    public static Map getPosition(String log, String lat) {
        String locations = Coordinatetransformation(log, lat);
        if (StringUtils.isEmpty(locations)) {
            return null;
        }

        String res = "";
        String path = "http://restapi.amap.com/v3/geocode/regeo?output=JSON&location=" + locations + "&key=" + RecordResult.GuideMapKey + "&radius=1000&extensions=all";
        //参数直接加载url后面
        try {
            res = PositionUtils.okGet(path);
        } catch (Exception e) {
            L.w("位置信息转换失败");
            return null;
        }
        try {
            Map<String, String> map = new HashMap<>();
            map.put("log", locations.split(",")[0]);
            map.put("lat", locations.split(",")[1]);
            String all = res;
            if (StringUtils.isEmpty(all)) {
                return null;
            }
            Map allMap = JSONObject.parseObject(all, Map.class);
            Map regeocode = (Map) allMap.get("regeocode");
            map.put("regeocode", regeocode.get("formatted_address").toString());
            List roads = (List) regeocode.get("roads");
            if (StringUtils.isEmpty(roads) || roads.size() == 0) {
                map.put("roads", "");
            } else {
                Map one = (Map) roads.get(0);
                map.put("roads", one.get("name") + "" + one.get("direction") + "" + one.get("distance"));

            }
            L.e("map:" + map.toString());
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    public void tests() throws Exception {//121.455199,31.147672  31.141241 121.448642

        Map map = getPosition("121.448642", "31.141241");
        if (StringUtils.isEmpty(map)) {
            L.w("nulll");
        } else {
            L.w(map.toString());
        }

    }

    public static void test() {
        String res = "";
        String path = "http://apis.map.qq.com/ws/coord/v1/translate?locations=31.1433,121.4441&type=1&key=TG3BZ-NDA3P-L6ID7-LI3OT-KNMOZ-FTBPP&output=json";
        //参数直接加载url后面
        try {
        } catch (Exception e) {
            L.w("位置信息转换失败");
        }

    }

    public static String okGet(String url) throws Exception {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();


    }
}
