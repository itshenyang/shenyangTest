package com.keruis.cilent.utils;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/4/17.
 */
public class ReportNameUtils {
    public static String getReportName(Timestamp server_time, Timestamp server_timeend, String d_id) {
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateDay = new SimpleDateFormat("dd");
        DateFormat dateHH = new SimpleDateFormat("HH");
        DateFormat dateMM = new SimpleDateFormat("mm");
        DateFormat datess = new SimpleDateFormat("ss");//数据方箱1号2017_02_28%2500_00_00_00----2017_02_28%2500_00_00_00
        return d_id + "" + dateYear.format(server_time) + "_" + dateMonth.format(server_time) + "_" + dateDay.format(server_time) + "-" + dateHH.format(server_time) + "_" + dateMM.format(server_time) + "_" + datess.format(server_time) +
                "----" + dateYear.format(server_timeend) + "_" + dateMonth.format(server_timeend) + "_" + dateDay.format(server_timeend) + "-" + dateHH.format(server_timeend) + "_" + dateMM.format(server_timeend) + "_" + datess.format(server_timeend);
    }

    @Test
    public void main() {
        L.w(getReportName(new Timestamp(1483407895000L), new Timestamp(1486212900000L), "方箱1号"));
    }
}
