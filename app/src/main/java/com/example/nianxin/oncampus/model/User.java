package com.example.nianxin.oncampus.model;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * package:com.example.nianxin.oncampus.model
 * author: nianxin
 * time:2018/4/6 21:50
 * desc: User实体
 */

public class User extends BmobUser implements Serializable{
    public String sex;//性别

    public String nickname;//昵称

    public Integer usertype;//用户类型 1 普通用户 2 管理员

    public BmobFile iconAddr;//头像

    public BmobDate birthday;//生日

    public String motto;//签名

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setIconAddr(BmobFile iconAddr) {
        this.iconAddr = iconAddr;
    }

    public BmobFile getIconAddr() {
        return iconAddr;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public BmobDate getBirthday() {
        return birthday;
    }

    public void setBirthday(BmobDate birthday) {
        this.birthday = birthday;
    }
}
