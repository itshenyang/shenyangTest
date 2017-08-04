package com.keruis.cilent.entities;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/4/7.
 */
public class DeviceDataResult extends BaseEntities {

    public static final Integer POSITION_RADIUS = 1000;                     //一百米半径的箱子

    public static final Double getPOSITION_LONGITUDE_RADIUS() {
        BigDecimal bg = new BigDecimal((1.0 / 111 / 1000) * DeviceDataResult.POSITION_RADIUS);
        return bg.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

    }            //一百米的经度

    public static Double getPOSITION_LATITUDE_RADIUS(Double LONGITUDE) {
        BigDecimal bg = new BigDecimal((1.0 / 111 * Math.cos(LONGITUDE)) / 1000 * DeviceDataResult.POSITION_RADIUS);
        return bg.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
