package com.reworkplan.auth;

import com.github.toastshaman.dropwizard.auth.jwt.JsonWebTokenValidator;
import com.github.toastshaman.dropwizard.auth.jwt.exceptions.TokenExpiredException;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebToken;
import com.github.toastshaman.dropwizard.auth.jwt.validator.ExpiryValidator;
import com.google.common.base.Optional;
import com.reworkplan.mappers.UserMapper;
import com.reworkplan.models.User;
import io.dropwizard.auth.Authenticator;

public class UserAuthenticator implements Authenticator<JsonWebToken, User> {
    private final UserMapper userMapper;

    public UserAuthenticator(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    protected JsonWebTokenValidator getValidator() {
        return new ExpiryValidator();
    }

    @Override
    public Optional<User> authenticate(JsonWebToken token) {
        final JsonWebTokenValidator expiryValidator = getValidator();
        try {
            expiryValidator.validate(token);
        } catch (TokenExpiredException e) {
            throw e;
        }
        User user = userMapper.selectByUsername(token.claim().subject());

        return Optional.fromNullable(user);
    }
}
