package com.reworkplan.models;

import javax.security.auth.Subject;
import java.security.Principal;

public class User implements Principal {
    private Integer id;
    private String username;
    private String avatar;
    private String password;
    private Boolean isSuperUser;
    private Boolean isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getSuperUser() {
        return isSuperUser;
    }

    public void setSuperUser(Boolean superUser) {
        isSuperUser = superUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    public boolean hasPermission(String permission) {
        if (isSuperUser) {
            return true;
        }
        if (isActive) {
            return true;
        }
        return false;
    }
}
