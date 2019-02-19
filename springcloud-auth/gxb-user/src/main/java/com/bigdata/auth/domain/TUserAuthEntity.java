package com.bigdata.auth.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author fwj
 * @date 2019-02-12 16:15
 **/
@Entity
@Table(name = "t_user_auth", schema = "gxb_business", catalog = "")
public class TUserAuthEntity implements Serializable {
    private int authId;
    private int uid;
    private byte identiyType;
    private String identifier;
    private String credential;
    private Timestamp lastLogin;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

    @Id
    @Column(name = "auth_id")
    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    @Basic
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "identiy_type")
    public byte getIdentiyType() {
        return identiyType;
    }

    public void setIdentiyType(byte identiyType) {
        this.identiyType = identiyType;
    }

    @Basic
    @Column(name = "identifier")
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Basic
    @Column(name = "credential")
    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    @Basic
    @Column(name = "last_login")
    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Basic
    @Column(name = "gmt_create")
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_modified")
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TUserAuthEntity that = (TUserAuthEntity) o;

        if (authId != that.authId) return false;
        if (uid != that.uid) return false;
        if (identiyType != that.identiyType) return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;
        if (credential != null ? !credential.equals(that.credential) : that.credential != null) return false;
        if (lastLogin != null ? !lastLogin.equals(that.lastLogin) : that.lastLogin != null) return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) return false;
        if (gmtModified != null ? !gmtModified.equals(that.gmtModified) : that.gmtModified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = authId;
        result = 31 * result + uid;
        result = 31 * result + (int) identiyType;
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (credential != null ? credential.hashCode() : 0);
        result = 31 * result + (lastLogin != null ? lastLogin.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtModified != null ? gmtModified.hashCode() : 0);
        return result;
    }
}
