package com.keruis.cilent.entities;

/**
 * Created by Administrator on 2017/3/20.
 */
public class UserResult extends BaseEntities {

    public final static String ROOT_CODE_TYPE = "1";            //系统管理员
    public final static String ENTERPRISE_CODE_TYPE = "2";  //企业用户
    public final static String GENERAL_MANAGER_CODE_TYPE = "3"; //一般管理员
    public final static String GENERAL_USER_CODE_TYPE = "4";     //一般客户

    public final static String DEVICErR_USER_CODE_TYPE = "5";//司机

    public final static String ROOT_USER_ID = "1000";     //超级管理员id

    public final static String ACCOUNT_DISABLED_STATE = "1";  //账号被禁用状态码

    public final static int PWD_ERROR = 401;
    public final static String PWD_ERROR_MSG = "登录失败,用户名或者密码错误！";


    public final static int USER_NAME_ALREADY_EXISTS = 402;
    public final static String USER_NAME_ALREADY_EXISTS_MSG = "该用户名已存在！";

    public final static int USER_INCREASES_DELETION_OPERATION = 403;
    public final static String USER_INCREASES_DELETION_OPERATIONMSG = "抱歉！你的用户权限不足！";


    public final static int USER_IS_DISABLED = 404;
    public final static String USER_IS_DISABLED_MSG = "抱歉！你的账号已经被停用！请联系管理员。";


}
