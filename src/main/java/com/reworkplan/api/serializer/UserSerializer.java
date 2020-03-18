package com.reworkplan.api.serializer;

import com.reworkplan.models.User;

public class UserSerializer {
    private String username;
    private String avatar;

    private UserSerializer() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static UserSerializer build(User user) {
        UserSerializer serializer = new UserSerializer();
        serializer.setUsername(user.getUsername());
        serializer.setAvatar(user.getAvatar());
        return serializer;
    }
}
