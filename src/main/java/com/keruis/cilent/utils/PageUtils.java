package com.keruis.cilent.utils;

import org.junit.Test;

/**
 * Created by Administrator on 2017/8/2.
 */
public class PageUtils {

    public static final Long pageSize = 10L;

    public static Long getpageNum(Integer num) {
        Integer i = 0;
        if (num / pageSize != 0) {
            i = 1;
        }
        return num/pageSize+i;
    }

    @Test
    public void tets(){
        L.w(getpageNum(120)+"");
    }
}
