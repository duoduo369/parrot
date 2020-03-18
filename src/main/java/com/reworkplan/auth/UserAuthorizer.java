package com.reworkplan.auth;

import com.reworkplan.models.User;
import io.dropwizard.auth.Authorizer;

public class UserAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String permission) {
        return user.hasPermission(permission);
    }
}
