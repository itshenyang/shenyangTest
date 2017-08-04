package com.keruis.cilent.entities;

/**
 * Created by Huan on 2017/2/8.
 */
public class BaseEntities {

    public final static int SUCCESS = 200;
    public final static String SUCCESS_MSG = "操作成功";

    public final static int FAILURE = 100;
    public final static String FAILURE_MSG = "操作失败";

    public final static int DATA_ILLEGAL = 300;
    public final static String DATA_ILLEGAL_MSG = "传输数据不合法";

    public final static int RESULT_ISNULL = 400;
    public final static String RESULT_ISNULL_MSG = "结果集为空";

    public final static int USER_NOLOGIN_OR_NOEFFICACY = 500;
    public final static String USER_NOLOGIN_OR_NOEFFICACY_MSG = "尚未登录或者登录已经失效!";

    public final static int USER_NO_JURISDICTION =600;
    public final static String USER_NO_JURISDICTION_MSG = "抱歉该功能你暂时没有权限！";


    private int resultCode = FAILURE;
    private String resultMsg = FAILURE_MSG;
    private Object data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
