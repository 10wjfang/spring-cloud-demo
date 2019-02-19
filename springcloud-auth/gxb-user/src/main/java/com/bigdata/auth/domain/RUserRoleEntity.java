package com.bigdata.auth.domain;

import javax.persistence.*;

/**
 * @author fwj
 * @date 2019-02-12 22:28
 **/
@Entity
@Table(name = "r_user_role", schema = "gxb_business", catalog = "")
public class RUserRoleEntity {
    private int urId;
    private int uid;
    private int roleId;

    @Id
    @Column(name = "ur_id")
    public int getUrId() {
        return urId;
    }

    public void setUrId(int urId) {
        this.urId = urId;
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
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RUserRoleEntity that = (RUserRoleEntity) o;

        if (urId != that.urId) return false;
        if (uid != that.uid) return false;
        if (roleId != that.roleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = urId;
        result = 31 * result + uid;
        result = 31 * result + roleId;
        return result;
    }
}
