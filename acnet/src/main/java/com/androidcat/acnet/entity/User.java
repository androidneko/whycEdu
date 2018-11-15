package com.androidcat.acnet.entity;

import java.io.Serializable;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-21 17:43:37
 * add function description here...
 */
public class User implements Serializable{

    public String userId;
    public String userName;
    public String phonenumber;
    public String token;
    public String loginName;
    public String sex;
    public String email = "";
    public String deptId;
    public String avatar;

    public User() {
    }

}
