package com.keruis.cilent.dao.pojos;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Alias("user")
public class User extends BasePOJO implements Serializable {
    private Long u_id;
    private String u_name;
    private String u_pwd;
    private String u_nickname;
    private String u_type;
    private Long u_g_id;

    private String u_g_name;//用户分组名称

    private String u_status;
    private String avatar;
    private String sex;
    private String email;
    private String telephone;
    private String address;
    private Long u_creator;

    private String u_creator_nickname;//创建人昵称

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp u_create_time;
    private Long u_modifier;

    private String u_modifier_nickname;//修改昵称

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp u_modify_time;
    private String u_mark;
    private Long s_id;
    private String s_id_nickname; //企业昵称


    private String openId;
    private String nickName;
    private String gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;


    private List<String> didss;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp tokenuserid_time;



    private String wx_push_status;
    private String sms_push_status;
    private String mail_push_status;



    public Long getU_id() {
        return u_id;
    }

    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_pwd() {
        return u_pwd;
    }

    public void setU_pwd(String u_pwd) {
        this.u_pwd = u_pwd;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public String getU_type() {
        return u_type;
    }

    public void setU_type(String u_type) {
        this.u_type = u_type;
    }

    public Long getU_g_id() {
        return u_g_id;
    }

    public void setU_g_id(Long u_g_id) {
        this.u_g_id = u_g_id;
    }

    public String getU_status() {
        return u_status;
    }

    public void setU_status(String u_status) {
        this.u_status = u_status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getU_creator() {
        return u_creator;
    }

    public void setU_creator(Long u_creator) {
        this.u_creator = u_creator;
    }

    public Timestamp getU_create_time() {
        return u_create_time;
    }

    public void setU_create_time(Timestamp u_create_time) {
        this.u_create_time = u_create_time;
    }

    public Long getU_modifier() {
        return u_modifier;
    }

    public void setU_modifier(Long u_modifier) {
        this.u_modifier = u_modifier;
    }

    public Timestamp getU_modify_time() {
        return u_modify_time;
    }

    public void setU_modify_time(Timestamp u_modify_time) {
        this.u_modify_time = u_modify_time;
    }

    public String getU_mark() {
        return u_mark;
    }

    public void setU_mark(String u_mark) {
        this.u_mark = u_mark;
    }

    public Long getS_id() {
        return s_id;
    }

    public void setS_id(Long s_id) {
        this.s_id = s_id;
    }

    public String getU_g_name() {
        return u_g_name;
    }

    public void setU_g_name(String u_g_name) {
        this.u_g_name = u_g_name;
    }

    public String getU_creator_nickname() {
        return u_creator_nickname;
    }

    public void setU_creator_nickname(String u_creator_nickname) {
        this.u_creator_nickname = u_creator_nickname;
    }

    public String getU_modifier_nickname() {
        return u_modifier_nickname;
    }

    public void setU_modifier_nickname(String u_modifier_nickname) {
        this.u_modifier_nickname = u_modifier_nickname;
    }

    public List<String> getDidss() {
        return didss;
    }

    public String getS_id_nickname() {
        return s_id_nickname;
    }

    public void setS_id_nickname(String s_id_nickname) {
        this.s_id_nickname = s_id_nickname;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public void setDidss(List<String> didss) {
        this.didss = didss;
    }

    public Timestamp getTokenuserid_time() {
        return tokenuserid_time;
    }

    public void setTokenuserid_time(Timestamp tokenuserid_time) {
        this.tokenuserid_time = tokenuserid_time;
    }

    public String getWx_push_status() {
        return wx_push_status;
    }

    public void setWx_push_status(String wx_push_status) {
        this.wx_push_status = wx_push_status;
    }

    public String getSms_push_status() {
        return sms_push_status;
    }

    public void setSms_push_status(String sms_push_status) {
        this.sms_push_status = sms_push_status;
    }

    public String getMail_push_status() {
        return mail_push_status;
    }

    public void setMail_push_status(String mail_push_status) {
        this.mail_push_status = mail_push_status;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_id=" + u_id +
                ", u_name='" + u_name + '\'' +
                ", u_pwd='" + u_pwd + '\'' +
                ", u_nickname='" + u_nickname + '\'' +
                ", u_type='" + u_type + '\'' +
                ", u_g_id=" + u_g_id +
                ", u_g_name='" + u_g_name + '\'' +
                ", u_status='" + u_status + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", u_creator=" + u_creator +
                ", u_creator_nickname='" + u_creator_nickname + '\'' +
                ", u_create_time=" + u_create_time +
                ", u_modifier=" + u_modifier +
                ", u_modifier_nickname='" + u_modifier_nickname + '\'' +
                ", u_modify_time=" + u_modify_time +
                ", u_mark='" + u_mark + '\'' +
                ", s_id=" + s_id +
                ", s_id_nickname='" + s_id_nickname + '\'' +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", language='" + language + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", unionId='" + unionId + '\'' +
                ", didss=" + didss +
                ", tokenuserid_time=" + tokenuserid_time +
                ", wx_push_status='" + wx_push_status + '\'' +
                ", sms_push_status='" + sms_push_status + '\'' +
                ", mail_push_status='" + mail_push_status + '\'' +
                '}';
    }
}
