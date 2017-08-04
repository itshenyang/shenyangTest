package com.keruis.cilent.entities;

import com.keruis.cilent.dao.pojos.BasePOJO;

/**
 * Created by Administrator on 2017/5/14.
 */
public class RecordResult extends BaseEntities {
    public static final String GuideMapKey = "8b802b4a9e6e71c810594062c683fae3";

    public static final int RECORD_IS_OPEN=201;
    public static  final  String RECORD_IS_OPEN_MSG ="行程已开启,无法再次开启起行程!";

    public static final int RECORD_IS_CLOSE=202;
    public static  final  String RECORD_IS_CLOSE_MSG ="行程已关闭,无法再次关闭行程!";

    public static final int SIGN_IS_YET  =301;
    public static  final  String SIGN_IS_YET_MSG ="你已经签收过,无法再次签收!";

    public static final int SIGN_IS_CLOSE  =302;
    public static  final  String SIGN_IS_CLOSE_MSG ="行程已关闭,无法签收!";
}
