package com.keruis.cilent.test;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Image;
import com.keruis.cilent.entities.DeviceDataResult;
import com.keruis.cilent.utils.L;
import com.keruis.cilent.utils.TimeUtils;
import com.mysql.cj.core.util.Base64Decoder;
import org.junit.Test;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.sql.Timestamp;
import java.text.Collator;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2017/3/15.
 */
public class RedisTest {

    @Test
    public void test() {
        Jedis jedis = new Jedis("hb201j.keruis.com", 6379);
        jedis.auth("Keruis@123");
        String img = jedis.hmget("e_img", "3094").get(0);
        String[] ImgString = img.split(",");
        img = ImgString[1];
        byte[] bytes;
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            bytes = base64Decoder.decodeBuffer(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Image.getInstance(bytes);
        L.w(img + "");

    }

    @Test
    public void test1() {
        List<User> list = new ArrayList<User>();
//        list.add(new User("刘媛媛",1,1));
//        list.add(new User("王硕",1,1));
//        list.add(new User("李明",1,1));
//        list.add(new User("刘迪",1,1));
//        list.add(new User("刘布",1,1));
//        list.add(new User("德玛",1,1));

        list.add(new User("方箱3号", 4, 1, new Timestamp(1000000000)));  //10
        list.add(new User("方箱4号", 3, 1, new Timestamp(1200000000))); //12
        list.add(new User("方箱10号", 1, 1, new Timestamp(1100000000))); //14
        list.add(new User("方箱11号", 1, 1, new Timestamp(1500000000)));//8
        list.add(new User("方箱100号", 1, 1, new Timestamp(1600000000)));
        list.add(new User("方箱110号", 1, 1, new Timestamp(1300000000)));
        list.add(new User("方箱101号", 1, 1, new Timestamp(1200000000)));

        list.add(new User("方箱2号", 2, 1, new Timestamp(1700000000)));
        list.add(new User("方箱1号", 1, 1, new Timestamp(1900000000)));


        // Collections.sort(list.getClass().getName(), Collator.getInstance(java.util.Locale.CHINA));
        Collections.sort(list);
        for (User user : list) {
            L.w(user.getName() + "," + user.getScore() + "," + user.getAge());
        }
    }

    @Test
    public void sfd() {
        List<User> users = new ArrayList<User>();
        users.add(new User("方箱1号", 78, 26));
        users.add(new User("方箱2号", 67, 23));
        users.add(new User("方箱10号", 34, 56));
        users.add(new User("方箱11号", 67, 23));
        Collections.reverse(users);//降序
        for (User user : users) {
            L.w(user.getName() + "," + user.getScore() + "," + user.getAge());
        }

    }

    @Test
    public void mian1() throws Exception {
        BASE64Decoder base64Decoder = new BASE64Decoder();

        BASE64Encoder encoder = new BASE64Encoder();
        L.w(encoder.encode(base64Decoder.decodeBuffer("d://mindray.png")));

    }

    @Test
    public void tets11() {
        Jedis jedis = new Jedis("hb201j.keruis.com", 6379);
        jedis.auth("Keruis@123");
        //List<String> all = new ArrayList<>();

//            Set<String> strings = jedis.keys("121212121");
//            for (String str : strings) {
//                all.add(str);
//            }
        L.w(jedis.hget("e_1000_device", "2122121"));

    }

    @Test
    public void main11() {
        L.w(DeviceDataResult.getPOSITION_LONGITUDE_RADIUS() + "");
        L.w(DeviceDataResult.getPOSITION_LATITUDE_RADIUS(31.230416) + "");
        //String strImg = GetImageStr();
        //System.out.println(strImg);
    }

    //图片转化成base64字符串
    public String GetImageStr() {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = "d://mindray.png";//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    //base64字符串转化成图片
    public static boolean GenerateImage(String imgStr) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "d://222.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Test
public void tst11(){
    Jedis jedis = new Jedis("hb201j.keruis.com", 6379);
    jedis.auth("Keruis@123");
    //L.w("" + jedis.keys("*8*").toString());
    //L.w(jedis.h);
    List<String> list = jedis.hvals("e_tokenuserid");
    for(int i=0;i<list.size();i++){
        String oneString = list.get(i);
        if(!StringUtils.isEmpty(oneString)){
            com.keruis.cilent.dao.pojos.User one =  JSONObject.parseObject(oneString, com.keruis.cilent.dao.pojos.User.class);
            Timestamp newTime = new Timestamp(System.currentTimeMillis());

            if(newTime.getTime()<one.getTokenuserid_time().getTime()+ 3600000){
                L.w(newTime.getTime()+"   "+one.getTokenuserid_time().getTime());
                L.w(""+one.toString());
            }
        }
    }
    jedis.close();
}

}
