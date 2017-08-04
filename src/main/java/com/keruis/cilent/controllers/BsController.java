package com.keruis.cilent.controllers;

import com.keruis.cilent.dao.pojos.Bs460;
import com.keruis.cilent.dao.pojos.User;
import com.keruis.cilent.entities.BaseEntities;
import com.keruis.cilent.services.Bs460Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */
@Controller
@RequestMapping(value = "bs")
public class BsController extends BaseController{
    @Autowired
    Bs460Service bs460Service;

    @RequestMapping(value = "querByAcAndCi", method = RequestMethod.POST)
    @ResponseBody
    public Object reaelTime(@RequestBody Bs460 bs460) {
        BaseEntities baseEntities = new BaseEntities();
        if(StringUtils.isEmpty(bs460.getAc()) || StringUtils.isEmpty(bs460.getCi())){
            return baseEntities;
        }
        List<Bs460> list =  bs460Service.query(bs460);
        baseEntities.setResultCode(BaseEntities.SUCCESS);
        baseEntities.setResultMsg(BaseEntities.SUCCESS_MSG);
        baseEntities.setData(list);
        return baseEntities;
    }
}
