package com.androidcat.acnet.entity.request;


public class LoginRequest extends BaseRequest {
    public String loginName = null;
    public String password = null;

    public String getPasswd() {
        return password;
    }

    public void setPasswd(String passwd) {
        this.password = passwd;
    }
}