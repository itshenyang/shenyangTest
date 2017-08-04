package com.keruis.cilent.dao;

import com.keruis.cilent.dao.pojos.Signs;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/5/14.
 */
@Repository
public interface SignsDAO extends BaseDAO {
        Integer insert(Signs signs);
}
