package com.keruis.cilent.controllers;

import com.keruis.cilent.services.BaseService;
import com.keruis.cilent.services.DeviceDataService;
import com.keruis.cilent.services.RecordService;
import com.keruis.cilent.services.Tmp_alarmService;
import com.keruis.cilent.test.RecordTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/11/22.
 */
@Controller
public class BaseController {
    @Autowired
    BaseService baseService;
    @Autowired
    DeviceDataService deviceDataService;
    @Autowired
    RecordService recordService;
    @Autowired
    Tmp_alarmService tmp_alarmService;
    @Autowired
    ServletContext cxt;
    @Autowired
    RecordTest recordTest;


    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession httpSession;


    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.httpSession = request.getSession();
    }

}
