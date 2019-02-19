package com.bigdata.auth.view;

import java.sql.Timestamp;

/**
 * @author fwj
 * @date 2019-02-12 16:15
 **/
public class LoginInfoVo {
    private int authId;
    private int uid;
    private byte identiyType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
    private String identifier;
    private Timestamp lastLogin;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public byte getIdentiyType() {
        return identiyType;
    }

    public void setIdentiyType(byte identiyType) {
        this.identiyType = identiyType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }
}
