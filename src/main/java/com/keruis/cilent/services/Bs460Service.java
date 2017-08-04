package com.keruis.cilent.services;

import com.keruis.cilent.dao.Bs460DAO;
import com.keruis.cilent.dao.pojos.Bs460;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */
@Service
public class Bs460Service extends BaseService{
    @Autowired
    Bs460DAO bs460DAO;


    public List<Bs460> query(Bs460 bs460){
       return bs460DAO.queryBsByACAndCI(bs460);
    }
}
