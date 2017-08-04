package com.keruis.cilent.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import com.keruis.cilent.dao.pojos.*;
import com.keruis.cilent.entities.DeviceDataResult;
import com.keruis.cilent.entities.ReportResult;
import com.keruis.cilent.services.DeviceDataService;
import com.keruis.cilent.utils.DeviceDataUtils;
import com.keruis.cilent.utils.L;
import com.keruis.cilent.utils.ReportNameUtils;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletContext;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
@Controller
@RequestMapping(value = "DeviceData")
public class DeviceDataController extends BaseController {


    @RequestMapping(value = "queryDeviceData", method = RequestMethod.POST)
    @ResponseBody
    public Object queryDeviceData(@RequestBody Device_data device_data) {
        L.e((device_data.toString()));
        User LoginUser = baseService.getLoginInfo(device_data);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        if (StringUtils.isEmpty(device_data.getD_id())
                || StringUtils.isEmpty(device_data.getDevice_time())
                || StringUtils.isEmpty(device_data.getDevice_timeend())
                || device_data.getDevice_timeend().getTime() < device_data.getDevice_time().getTime()) {
            deviceDataResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            deviceDataResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return deviceDataResult;
        }
        List<Device_data> list = deviceDataService.queryDeviceDataByDidAndTime(device_data);
        if (StringUtils.isEmpty(list) || list.size() == 0) {
            deviceDataResult.setResultCode(DeviceDataResult.RESULT_ISNULL);
            deviceDataResult.setResultMsg(DeviceDataResult.RESULT_ISNULL_MSG);
            return deviceDataResult;
        }
        list = DeviceDataUtils.updateDataTimeInterval(list, device_data);
        deviceDataResult.setData(list);
        deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
        deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        return deviceDataResult;
    }

    @RequestMapping(value = "queryTemCurve", method = RequestMethod.POST)
    @ResponseBody
    public Object queryTemCurve(@RequestBody Device_data device_data) {
        L.e((device_data.toString()));
        User LoginUser = baseService.getLoginInfo(device_data);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        if (StringUtils.isEmpty(device_data.getD_id())
                || StringUtils.isEmpty(device_data.getDevice_time())
                || StringUtils.isEmpty(device_data.getDevice_timeend())
                || device_data.getDevice_timeend().getTime() < device_data.getDevice_time().getTime()) {
            deviceDataResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            deviceDataResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return deviceDataResult;
        }
        List<Device_data> list = deviceDataService.queryDeviceDataByDidAndTime(device_data);
        if (StringUtils.isEmpty(device_data.getStatus()) || String.valueOf(device_data.getStatus()).equals("0")) {
            list = DeviceDataUtils.updateDataTimeInterval(list, device_data);
        }

        List<Reduce_Device_data> result = new ArrayList<>();
        if (StringUtils.isEmpty(list) || list.size() == 0) {
            deviceDataResult.setResultCode(DeviceDataResult.RESULT_ISNULL);
            deviceDataResult.setResultMsg(DeviceDataResult.RESULT_ISNULL_MSG);
            return deviceDataResult;
        }
        for (int i = 0; i < list.size(); i++) {
            Reduce_Device_data one = new Reduce_Device_data();
            Device_data lod = list.get(i);
            one.setDevice_time(lod.getDevice_time());
            one.setTemp_1(lod.getTemp_1());
            one.setLatitude(lod.getLatitude());
            one.setLongitude(lod.getLongitude());
            one.setDoor(lod.getDoor());
            one.setScheduling(lod.getScheduling());
            one.setVoltage(lod.getVoltage());
            one.setBattery(lod.getBattery());
            one.setRecord_id(lod.getRecord_id());
            result.add(one);
        }
        Device devcieResult = new Device();
        devcieResult.setTokenuserid(device_data.getTokenuserid());
        devcieResult.setD_id(device_data.getD_id());
        Map map = new HashMap();
        map.put("device", deviceDataService.getDeviceById(devcieResult));
        map.put("data", result);
        deviceDataResult.setData(map);
        deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
        deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        return deviceDataResult;
    }

    @RequestMapping(value = "LV3Linkage", method = RequestMethod.POST)
    @ResponseBody
    public Object LV3Linkage(@RequestBody Device_data device_data) {
        L.e((device_data.toString()));
        User LoginUser = baseService.getLoginInfo(device_data);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        deviceDataResult.setData(deviceDataService.LV3Linkage(device_data));
        deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
        deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        return deviceDataResult;
    }

    @RequestMapping(value = "reportExcel")
    public void reportExcel(@RequestParam String did,
                            @RequestParam Timestamp device_time,
                            @RequestParam Timestamp device_timeend,
                            @RequestParam String name
    ) throws Exception {
        Device_data device_data = new Device_data();
        device_data.setD_id(Long.parseLong(did));
        device_data.setDevice_time(device_time);
        device_data.setDevice_timeend(device_timeend);
        device_data.setName(name);
        L.e(device_data.toString());
        String fname = " 数据" + ReportNameUtils.getReportName(device_time, device_timeend, name) + ".xls";// 文件名
        String fileName = URLEncoder.encode(fname, "UTF-8");// 转换编码格式
        // 弹出下载的面板，并且，面板中显示要下载的格式为excel文档格式
        response.setContentType("application/vnd.ms-excel");// 弹出面板
        response.setHeader("Content-disposition", "attchment;fileName=" + fileName);

        /*******************************************************************************/
        OutputStream out = response.getOutputStream();// 产生输出流，用于给客户端输出文件

        WritableWorkbook wb = Workbook.createWorkbook(out);// 产生一个可以用流输出的电子表格文档
        WritableSheet st = wb.createSheet("报表", 0);// 产生一个表单

        /*********************** 调整格式 *********************************************/
        st.getSettings().setDefaultColumnWidth(40);// 设置列宽

        WritableFont ft = new WritableFont(WritableFont.ARIAL, 15,
                WritableFont.NO_BOLD);// 创建字体
        WritableCellFormat wcf = new WritableCellFormat(ft);// 设置单元格的格式
        wcf.setAlignment(Alignment.CENTRE);// 居中对齐
        wcf.setWrap(true);// 自动换行
        wcf.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置边框

        Label title = new Label(0, 0, ReportNameUtils.getReportName(device_time, device_timeend, name), wcf);// 大标题

        st.addCell(title);// 把label添加到表单中
        st.mergeCells(0, 0, 4, 0);// 合并单元格

        Label labName = new Label(0, 1, "方箱名称", wcf);
        Label labTime = new Label(1, 1, "运行时间", wcf);
        Label labWendu = new Label(2, 1, "温度", wcf);
        Label labCity = new Label(3, 1, "位置", wcf);

        st.addCell(labName);
        st.setColumnView(0, 40);
        st.addCell(labTime);
        st.setColumnView(1, 40);
        st.addCell(labWendu);
        st.setColumnView(2, 20);
        st.addCell(labCity);
        st.setColumnView(3, 128);
        // 取得session中的数据，显示在表格中
        List<Device_data> list = deviceDataService.queryDeviceDataByDidAndTime(device_data);
        try {
            list = DeviceDataUtils.updateDataTimeInterval(list, device_data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(list) || list.size() == 0) {
            list = new ArrayList<Device_data>();
        }
        //取得session中的数据，查询的条件

        /**
         * 数据汇总
         */

        for (int i = 0; i < list.size(); i++) {
            Device_data sb = list.get(i);
            Label Lname;
            Label Ltime;
            Label Lwendu;
            Label Lcity;

            Lname = new Label(0, i + 2, device_data.getName(), wcf);

            if (StringUtils.isEmpty(sb.getDevice_time())) {
                Ltime = new Label(1, i + 2, "", wcf);
            } else {
                Ltime = new Label(1, i + 2, sb.getDevice_time().toString().substring(0, sb.getDevice_time().toString().length() - 2), wcf);
            }

            if (StringUtils.isEmpty(sb.getTemp_1())) {
                Lwendu = new Label(2, i + 2, "", wcf);
            } else {
                Lwendu = new Label(2, i + 2, sb.getTemp_1().toString(), wcf);
            }


            if (StringUtils.isEmpty(sb.getAddress())) {
                Lcity = new Label(3, i + 2, "", wcf);
            } else {
                Lcity = new Label(3, i + 2, sb.getAddress().toString(), wcf);
            }

            st.addCell(Lname);
            st.addCell(Ltime);
            st.addCell(Lwendu);
            st.addCell(Lcity);
        }
        st.mergeCells(0, 2 + list.size(), 4, 2 + list.size());//合并

        wb.write();// 输出文档
        wb.close();
        out.flush();
        out.close();
    }

    @RequestMapping(value = "reportSaveImg")
    public void reportSaveImg(
            @RequestParam String did,
            @RequestParam Timestamp device_time,
            @RequestParam Timestamp device_timeend,
            @RequestParam String name,
            @RequestParam String imgbase64,
            @RequestParam String order_number
    ) throws Exception {

        Device_data device_data = new Device_data();
        device_data.setD_id(Long.parseLong(did));
        device_data.setDevice_time(device_time);
        device_data.setDevice_timeend(device_timeend);
        device_data.setName(name);
        device_data.setImgbase64(imgbase64);
        device_data.setOrder_number(order_number);
        L.e(device_data.toString());

        String path = cxt.getRealPath("");//获取服务器路径

        String SongTifont = path + "font" + "/STSONG.TTF";

        String[] ImgString = device_data.getImgbase64().split(",");
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] imgbyte = base64Decoder.decodeBuffer(ImgString[1]);
        String fname = " 数据" + ReportNameUtils.getReportName(device_time, device_timeend, name) + " .pdf";// 文件名
        String fileName = URLEncoder.encode(fname, "UTF-8");// 转换编码格式
        // 弹出下载的面板，并且，面板中显示要下载的格式为excel文档格式
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attchment;fileName=" + fileName);


//        List<shebei> list =(List<shebei>)httpSession.getAttribute("");
//        PDFUtils pdf = new PDFUtils();
//        Document document=pdf.generatePDF(list);
//        document.close();
//        PdfWriter.getInstance(document, res.getOutputStream());
        // 创建一个文档（默认大小A4，边距36, 36, 36, 36）
        Document document = new Document();
        // 设置文档大小
        document.setPageSize(PageSize.A4);
        // 设置边距，单位都是像素，换算大约1厘米=28.33像素
        document.setMargins(50, 50, 50, 50);
        // 设置pdf生成的路径
        OutputStream out = response.getOutputStream();// 产生输出流，用于给客户端输出文件

        //FileOutputStream fileOutputStream= new FileOutputStream("D:/demo.pdf");
        // 创建writer，通过writer将文档写入磁盘
        PdfWriter writer = PdfWriter.getInstance(document, out);
        // demo
        //String title = "报表";
        String content = ReportNameUtils.getReportName(device_time, device_timeend, name);

        // 定义字体
        FontFactoryImp ffi = new FontFactoryImp();
        // 注册全部默认字体目录，windows会自动找fonts文件夹的，返回值为注册到了多少字体
        ffi.registerDirectories();
        // 获取字体，其实不用这么麻烦，后面有简单方法
        Font font = ffi.getFont(SongTifont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.UNDEFINED, null);

        // 打开文档，只有打开后才能往里面加东西
        document.open();


        // 增加一个段落
        String Temperatuepath = path + ("/img/keruis/TemperatueReport.png");//温度报表图片PDF头部
        Image TemperatupImage = Image.getInstance(Temperatuepath);
        TemperatupImage.setAlignment(Image.ALIGN_CENTER);
        TemperatupImage.scaleAbsolute(550, 27);
        document.add(TemperatupImage);//添加温度报表图片PDF头部

        document.add(new Paragraph("\n\r", font));
        String mindraypath = path + ("/img//qiyeLogin/mindray.png");//企业Log图片路径

        Image mindrayImage = Image.getInstance(mindraypath);
        mindrayImage.setAlignment(Image.ALIGN_CENTER);
        mindrayImage.scalePercent(50);
        document.add(mindrayImage);//添加一个企业log

        document.add(new Paragraph("    公司名称：" + ReportResult.QIYE_NAME_MINDREAY, font)); //公司名称

        document.add(new Paragraph("    产品类型：移动方舱Ⅲ代", font)); //公司名称


        document.add(new Paragraph("    设备编号：" + device_data.getName(), font)); //设备名称
        int i = 0;

        do {
            if (i == 0) {
                if (device_data.getOrder_number().length() <= 45) {
                    document.add(new Paragraph("    运货单号：" + device_data.getOrder_number(), font)); //运货单号
                } else {
                    document.add(new Paragraph("    运货单号：" + device_data.getOrder_number().substring(i, i + 45), font)); //运货单号
                }
            }

            if (i != 0) {
                if (device_data.getOrder_number().length() > i + 45) {
                    L.w("" + device_data.getOrder_number().substring(i, i + 45));
                    document.add(new Paragraph("                        " + device_data.getOrder_number().substring(i, i + 45), font)); //运货单号
                } else {
                    document.add(new Paragraph("                        " + device_data.getOrder_number().substring(i, device_data.getOrder_number().length()), font)); //运货单号
                }


            }
            i = i + 45;
        } while (i < device_data.getOrder_number().length());

        document.add(new Paragraph("统计时间间隔：5分钟", font)); //运货单号
        document.add(new Paragraph("行程起止时间：" + device_data.getDevice_time().toString().substring(0, device_data.getDevice_time().toString().length() - 2) + " 到 " + device_data.getDevice_timeend().toString().substring(0, device_data.getDevice_timeend().toString().toString().length() - 2), font)); //运货单号

        document.add(new Paragraph("\n\r", font));


        Image img = Image.getInstance(imgbyte);
        img.setAlignment(Image.ALIGN_CENTER);
        img.scaleAbsolute(670, 280);
        document.add(img);//添加一个温度曲线图
        document.add(new Paragraph("\n\r", font));
        document.add(new Paragraph("\n\r", font));


        String keruisCopyrightpath = path + ("/img//keruis/Copyright.png");//获取Copyrigh图片

        Image keruisCopyright = Image.getInstance(keruisCopyrightpath);
        keruisCopyright.setAlignment(Image.ALIGN_CENTER);
        keruisCopyright.scalePercent(50);
        document.add(keruisCopyright);

        //关闭文档，才能输出
        document.close();
        writer.close();
        out.flush();
        out.close();

    }

    @RequestMapping(value = "reportPDF")
    public void reportPDF(
            @RequestParam String did,
            @RequestParam Timestamp device_time,
            @RequestParam Timestamp device_timeend,
            @RequestParam String name,
            @RequestParam String imgbase64,
            @RequestParam String order_number
    ) throws Exception {
        String path = cxt.getRealPath("");//获取服务器路径
        String SongTifont = path + "font" + "/STSONG.TTF";

        Device_data device_data = new Device_data();
        device_data.setD_id(Long.parseLong(did));
        device_data.setDevice_time(device_time);
        device_data.setDevice_timeend(device_timeend);
        device_data.setName(name);
        device_data.setImgbase64(imgbase64);
        device_data.setOrder_number(order_number);
        L.e(device_data.toString());


        String[] ImgString = device_data.getImgbase64().split(",");
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] imgbyte = base64Decoder.decodeBuffer(ImgString[1]);
        String fname = " 数据曲线" + ReportNameUtils.getReportName(device_time, device_timeend, name) + " .pdf";// 文件名
        String fileName = URLEncoder.encode(fname, "UTF-8");// 转换编码格式
        // 弹出下载的面板，并且，面板中显示要下载的格式为excel文档格式
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attchment;fileName=" + fileName);


//        List<shebei> list =(List<shebei>)httpSession.getAttribute("");
//        PDFUtils pdf = new PDFUtils();
//        Document document=pdf.generatePDF(list);
//        document.close();
//        PdfWriter.getInstance(document, res.getOutputStream());
        // 创建一个文档（默认大小A4，边距36, 36, 36, 36）
        Document document = new Document();
        // 设置文档大小
        document.setPageSize(PageSize.A4);
        // 设置边距，单位都是像素，换算大约1厘米=28.33像素
        document.setMargins(50, 50, 50, 50);
        // 设置pdf生成的路径
        OutputStream out = response.getOutputStream();// 产生输出流，用于给客户端输出文件

        //FileOutputStream fileOutputStream= new FileOutputStream("D:/demo.pdf");
        // 创建writer，通过writer将文档写入磁盘
        PdfWriter writer = PdfWriter.getInstance(document, out);
        // demo
        //String title = "报表";
        String content = ReportNameUtils.getReportName(device_time, device_timeend, name);

        // 定义字体
        FontFactoryImp ffi = new FontFactoryImp();
        // 注册全部默认字体目录，windows会自动找fonts文件夹的，返回值为注册到了多少字体
        ffi.registerDirectories();
        // 获取字体，其实不用这么麻烦，后面有简单方法
        Font font = ffi.getFont(SongTifont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.UNDEFINED, null);

        // 打开文档，只有打开后才能往里面加东西
        document.open();


        // 增加一个段落
        String Temperatuepath = path + ("/img/keruis/TemperatueReport.png");

        Image TemperatupImage = Image.getInstance(Temperatuepath);
        TemperatupImage.setAlignment(Image.ALIGN_CENTER);
        TemperatupImage.scaleAbsolute(550, 27);
        document.add(TemperatupImage);//添加温度报表图片PDF头部

        document.add(new Paragraph("\n\r", font));


        String mindraypath = path + ("/img/keruis/keruis.png");//企业Log图片路径
        Device device = null;
        try {
            device = deviceDataService.getDeviceById(device_data);
            if (String.valueOf(device.getS_id()).equals("3094")) {
                mindraypath = path + ("/img/qiyeLogin/mindray.png");//温度报表图片PDF头部;
                Image mindrayImage = Image.getInstance(mindraypath);
                mindrayImage.setAlignment(Image.ALIGN_CENTER);
                mindrayImage.scalePercent(50);
                document.add(mindrayImage);//添加一个企业log
            }else {
                Image mindrayImage = Image.getInstance(mindraypath);
                mindrayImage.setAlignment(Image.ALIGN_CENTER);
                mindrayImage.scalePercent(100);
                document.add(mindrayImage);//添加一个企业log
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (String.valueOf(device.getS_id()).equals("3094")) {
                document.add(new Paragraph("    公司名称：" + ReportResult.QIYE_NAME_MINDREAY, font)); //公司名称
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        document.add(new Paragraph("    产品类型：移动方舱Ⅲ代", font)); //公司名称


        document.add(new Paragraph("    设备编号：" + device_data.getName(), font)); //设备名称
        int i = 0;

        do {
            if (i == 0) {
                if (device_data.getOrder_number().length() <= 45) {
                    document.add(new Paragraph("    运货单号：" + device_data.getOrder_number(), font)); //运货单号
                } else {
                    document.add(new Paragraph("    运货单号：" + device_data.getOrder_number().substring(i, i + 45), font)); //运货单号
                }
            }

            if (i != 0) {
                if (device_data.getOrder_number().length() > i + 45) {
                    L.w("" + device_data.getOrder_number().substring(i, i + 45));
                    document.add(new Paragraph("                        " + device_data.getOrder_number().substring(i, i + 45), font)); //运货单号
                } else {
                    document.add(new Paragraph("                        " + device_data.getOrder_number().substring(i, device_data.getOrder_number().length()), font)); //运货单号
                }


            }
            i = i + 45;
        } while (i < device_data.getOrder_number().length());

        document.add(new Paragraph("统计时间间隔：5分钟", font)); //运货单号
        document.add(new Paragraph("行程起止时间：" + device_data.getDevice_time().toString().substring(0, device_data.getDevice_time().toString().length() - 2) + " 到 " + device_data.getDevice_timeend().toString().substring(0, device_data.getDevice_timeend().toString().length() - 2), font)); //运货单号

        document.add(new Paragraph("\n\r", font));


        Image img = Image.getInstance(imgbyte);
        img.setAlignment(Image.ALIGN_CENTER);
        img.scaleAbsolute(670, 280);
        document.add(img);//添加一个温度曲线图
        document.add(new Paragraph("\n\r", font));
        document.add(new Paragraph("\n\r", font));

        Font fontData = ffi.getFont(SongTifont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8, Font.UNDEFINED, null);
        // 创建表格，5列的表格
        PdfPTable table = new PdfPTable(4);
        table.setTotalWidth(PageSize.A4.getWidth() - 60);
        table.setLockedWidth(true);
        // 创建头
        PdfPHeaderCell header = new PdfPHeaderCell();


        List<Device_data> list = deviceDataService.queryDeviceDataByDidAndTime(device_data);
        try {
            list = DeviceDataUtils.updateDataTimeInterval(list, device_data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(list) || list.size() == 0) {
            list = new ArrayList<Device_data>();
        }

        Integer num = list.size();
        PdfPCell Pdfcell1 = new PdfPCell();
        for (int num1 = 0; num1 < num / 4; num1++) {

            Device_data sb = list.get(num1);
            String one = "";
            if (StringUtils.isEmpty(sb.getDevice_time())) {
                one += "  ";
            } else {
                one += sb.getDevice_time().toString().substring(0, sb.getDevice_time().toString().length() - 2);
            }
            if (StringUtils.isEmpty(sb.getTemp_1())) {
                one += "  ";
            } else {
                one += "  " + sb.getTemp_1().toString();
            }
            Paragraph cell3 = new Paragraph(one, fontData);
            cell3.setAlignment(Element.ALIGN_CENTER);
            Pdfcell1.addElement(cell3);
        }
        table.addCell(Pdfcell1);
        PdfPCell Pdfcell2 = new PdfPCell();
        for (int num1 = num / 4; num1 < num / 4 * 2; num1++) {

            Device_data sb = list.get(num1);
            String one = "";
            if (StringUtils.isEmpty(sb.getDevice_time())) {
                one += "  ";
            } else {
                one += sb.getDevice_time().toString().substring(0, sb.getDevice_time().toString().length() - 2);
            }

            if (StringUtils.isEmpty(sb.getTemp_1())) {
                one += "  ";
            } else {
                one += "  " + sb.getTemp_1().toString();
            }
            Paragraph cell3 = new Paragraph(one, fontData);
            cell3.setAlignment(Element.ALIGN_CENTER);
            Pdfcell2.addElement(cell3);
        }
        table.addCell(Pdfcell2);
        PdfPCell Pdfcell3 = new PdfPCell();

        for (int num1 = num / 4 * 2; num1 < num / 4 * 3; num1++) {

            Device_data sb = list.get(num1);
            String one = "";
            if (StringUtils.isEmpty(sb.getDevice_time())) {
                one += "  ";
            } else {
                one += sb.getDevice_time().toString().substring(0, sb.getDevice_time().toString().length() - 2);
            }

            if (StringUtils.isEmpty(sb.getTemp_1())) {
                one += "  ";
            } else {
                one += "  " + sb.getTemp_1().toString();
            }
            Paragraph cell3 = new Paragraph(one, fontData);
            cell3.setAlignment(Element.ALIGN_CENTER);
            Pdfcell3.addElement(cell3);
        }
        table.addCell(Pdfcell3);
        PdfPCell Pdfcell4 = new PdfPCell();
        for (int num1 = num / 4 * 3; num1 < num; num1++) {

            Device_data sb = list.get(num1);
            String one = "";
            if (StringUtils.isEmpty(sb.getDevice_time())) {
                one += "  ";
            } else {
                one += sb.getDevice_time().toString().substring(0, sb.getDevice_time().toString().length() - 2);
            }

            if (StringUtils.isEmpty(sb.getTemp_1())) {
                one += "  ";
            } else {
                one += "  " + sb.getTemp_1().toString();
            }
            Paragraph cell3 = new Paragraph(one, fontData);
            cell3.setAlignment(Element.ALIGN_CENTER);
            Pdfcell4.addElement(cell3);
        }
        table.addCell(Pdfcell4);
        document.add(table);
        String keruisCopyrightpath = path + ("/img/keruis/Copyright.png");//获取Copyrigh图片

        Image keruisCopyright = Image.getInstance(keruisCopyrightpath);
        keruisCopyright.setAlignment(Image.ALIGN_CENTER);
        keruisCopyright.scalePercent(50);
        document.add(keruisCopyright);

        //关闭文档，才能输出
        document.close();
        writer.close();
        out.flush();
        out.close();
    }

    @RequestMapping(value = "reaelTime", method = RequestMethod.POST)
    @ResponseBody
    public Object reaelTime(@RequestBody User user) {
        User LoginUser = baseService.getLoginInfo(user);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        if (StringUtils.isEmpty(user.getDidss())
                || user.getDidss().size() == 0) {
            deviceDataResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            deviceDataResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return deviceDataResult;
        }
        List<Device_data> result = deviceDataService.getDeviceRealTime(user);
        Tmp_alarm tmp_alarm = new Tmp_alarm();
        tmp_alarm.setUser_id(LoginUser.getU_id());
        Map<Long, String> allTmp = tmp_alarmService.getAllTmpAalarmByUser(tmp_alarm);

        for (int i = 0; i < result.size(); i++) {
            Device_data one = result.get(i);
            if (!StringUtils.isEmpty(one.getRecord_id())) {
                Records one1 = recordService.getRecords(one.getD_id(), Long.parseLong(one.getRecord_id().toString()));
                if (!StringUtils.isEmpty(one1)) {
                    result.get(i).setRecord_id(one1);
                }
            }
            result.get(i).setTmp_alarm_state(0L);
            String state = allTmp.get(one.getD_id());
            if (!StringUtils.isEmpty(state)) {
                result.get(i).setTmp_alarm_state(Long.parseLong(state));
                break;
            }
        }
        deviceDataResult.setData(result);
        deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
        deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        return deviceDataResult;
    }

    @RequestMapping(value = "reaelPositionDevice", method = RequestMethod.POST)
    @ResponseBody
    public Object reaelPosition(@RequestBody Device_data device_data) {
        User LoginUser = baseService.getLoginInfo(device_data);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        if (StringUtils.isEmpty(device_data.getLongitude()) ||
                StringUtils.isEmpty(device_data.getLatitude())
                ) {
            deviceDataResult.setResultCode(DeviceDataResult.DATA_ILLEGAL);
            deviceDataResult.setResultMsg(DeviceDataResult.DATA_ILLEGAL_MSG);
            return deviceDataResult;
        }
        deviceDataResult.setData(deviceDataService.getReaelPositionDevice(device_data));
        deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
        deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
        return deviceDataResult;
    }

    @RequestMapping(value = "updateOneTmpData", method = RequestMethod.POST)
    @ResponseBody
    public Object updateOneTmpData(@RequestBody Device_data device_data) {
        User LoginUser = baseService.getLoginInfo(device_data);
        DeviceDataResult deviceDataResult = new DeviceDataResult();
        if (StringUtils.isEmpty(LoginUser)) {
            deviceDataResult.setResultCode(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY);
            deviceDataResult.setResultMsg(DeviceDataResult.USER_NOLOGIN_OR_NOEFFICACY_MSG);
            return deviceDataResult;
        }
        if(deviceDataService.updaeOneTmp(device_data)){
            deviceDataResult.setResultCode(DeviceDataResult.SUCCESS);
            deviceDataResult.setResultMsg(DeviceDataResult.SUCCESS_MSG);
            return deviceDataResult;
        }
        deviceDataResult.setResultCode(DeviceDataResult.FAILURE);
        deviceDataResult.setResultMsg(DeviceDataResult.FAILURE_MSG);
        return deviceDataResult;
    }




    @RequestMapping(value = "test", method = RequestMethod.POST)
    @ResponseBody
    public Object test() {
        /**
         * `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
         `d_id` INT(11) NOT NULL COMMENT '设备ID',
         `name` VARCHAR(128) DEFAULT NULL COMMENT '设备名称',
         `server_time` DATETIME DEFAULT NULL COMMENT '服务器时间',
         `gps_time` DATETIME DEFAULT NULL COMMENT 'GPS时间',
         `device_time` DATETIME DEFAULT NULL COMMENT '设备时间',
         `frequency_1` INT(11) DEFAULT NULL COMMENT '回传频率1',
         `frequency_2` INT(11) DEFAULT NULL COMMENT '回传频率2',
         `bluetooth` TINYINT(4) DEFAULT NULL COMMENT '蓝牙状态',
         `status` TINYINT(4) DEFAULT NULL COMMENT '设备状态',
         `work_status` TINYINT(4) DEFAULT NULL COMMENT '设备工作状态',
         `delayed` TINYINT(4) DEFAULT NULL COMMENT '是否补传',
         `locate_status` TINYINT(4) DEFAULT NULL COMMENT '定位状态',
         `speed` FLOAT DEFAULT NULL COMMENT '速度',
         `humidity` FLOAT DEFAULT NULL COMMENT '湿度',
         `temp_1` FLOAT DEFAULT NULL COMMENT '温度1',
         `temp_2` FLOAT DEFAULT NULL COMMENT '温度2',
         `working_temp_1` FLOAT DEFAULT NULL COMMENT '工作温度1',
         `working_temp_2` FLOAT DEFAULT NULL COMMENT '工作温度2',
         `env_temp_1` FLOAT DEFAULT NULL COMMENT '环境温度1',
         `env_temp_2` FLOAT DEFAULT NULL COMMENT '环境温度2',
         `longitude` DOUBLE DEFAULT NULL COMMENT '经度',
         `latitude` DOUBLE DEFAULT NULL COMMENT '纬度',
         `address` VARCHAR(128) DEFAULT NULL COMMENT '位置',
         `door` TINYINT(4) DEFAULT NULL COMMENT '门状态',
         `error_code` TINYINT(4) DEFAULT NULL COMMENT '异常状态码',
         `voltage` FLOAT DEFAULT NULL COMMENT '电压值',
         `battery` FLOAT DEFAULT NULL COMMENT '电量值',
         `gsm_signal` FLOAT DEFAULT NULL COMMENT 'GSM信号值',
         `door_by_hand` TINYINT(4) DEFAULT NULL COMMENT '手动开门',
         `scheduling` TINYINT(4) DEFAULT NULL COMMENT '是否在行程中',
         `mark` VARCHAR(128) DEFAULT NULL COMMENT '设备备注信息',
         */
        Random random = new Random();
        Device_data one = new Device_data();
        one.setD_id(10001L);
        one.setName("方箱11号");
        one.setFrequency_1(10L);
        one.setFrequency_2(20L);
        one.setBluetooth(1L);
        one.setStatus(1L);
        one.setWork_status(1L);
        one.setSpeed(100.0);
        one.setHumidity(10.0);

        one.setTemp_1(3.0);
        one.setTemp_2(5.0);
        one.setWorking_temp_1(4.0);
        one.setWorking_temp_2(5.0);
        one.setLongitude(120.0);
        one.setLatitude(40.0);
        one.setAddress("北京");
        one.setDoor(1L);
        one.setError_code(1L);
        one.setVoltage(99.5);
        one.setBattery(24.5);
        one.setGsm_signal(41.0);
        one.setDoor_by_hand(1L);
        one.setScheduling(5L);
        for (int i = 1; i < 288 * 20; i++) {
            one.setServer_time(new Timestamp(1490445000000L + i * 300000)); //
            one.setGps_time(new Timestamp(1490445000000L + i * 300000));
            one.setDevice_time(new Timestamp(1490445000000L + i * 300000));
            one.setMark("这是第" + i + "条测试数据");
            //random.nextInt(3);
            deviceDataService.insert(one);
        }
        return null;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody Device_data device_data) {

        deviceDataService.DeviceDataToRedis(deviceDataService.query(device_data));
        return null;
    }
}
