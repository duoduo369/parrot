package com.reworkplan.auth;

import com.github.toastshaman.dropwizard.auth.jwt.JsonWebTokenParser;
import com.github.toastshaman.dropwizard.auth.jwt.hmac.HmacSHA512Verifier;
import com.github.toastshaman.dropwizard.auth.jwt.parser.DefaultJsonWebTokenParser;
import com.reworkplan.mappers.UserMapper;
import com.reworkplan.models.User;

public class UserAuthenticationFilter {
    private static final String REALM = "Parrot";
    private static final String BEARER = "Bearer";

    private UserAuthenticationFilter() {
    }

    public static CustomJWTAuthFilter<User> build(byte[] jwtTokenSecret, UserMapper userMapper) {

        final JsonWebTokenParser tokenParser = new DefaultJsonWebTokenParser();
        final HmacSHA512Verifier tokenVerifier = new HmacSHA512Verifier(jwtTokenSecret);

        return new CustomJWTAuthFilter.Builder<User>()
                .setTokenParser(tokenParser)
                .setTokenVerifier(tokenVerifier)
                .setRealm(REALM)
                .setPrefix(BEARER)
                .setAuthenticator(new UserAuthenticator(userMapper))
                .setAuthorizer(new UserAuthorizer())
                .buildAuthFilter();
    }
}
