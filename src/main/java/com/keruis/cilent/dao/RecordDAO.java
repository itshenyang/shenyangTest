package com.keruis.cilent.dao;

import com.keruis.cilent.dao.pojos.Records;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 */
@Repository
public interface RecordDAO extends BaseDAO {
    Integer OnRecord(Records records);

    Integer CloseRecord(Records records);



    List<Records> selectOne(Records records);
}
