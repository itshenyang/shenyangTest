package com.keruis.cilent.dao;

import com.keruis.cilent.dao.pojos.Bs460;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */
@Repository

public interface Bs460DAO {
      List<Bs460> queryBsByACAndCI(Bs460 bs460);

}
