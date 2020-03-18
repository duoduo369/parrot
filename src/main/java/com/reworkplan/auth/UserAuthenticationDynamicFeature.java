package com.reworkplan.auth;

import com.reworkplan.mappers.UserMapper;
import com.reworkplan.models.User;
import org.glassfish.jersey.server.model.AnnotatedMethod;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class UserAuthenticationDynamicFeature implements DynamicFeature {
    private final CustomJWTAuthFilter<User> authFilter;

    public UserAuthenticationDynamicFeature(byte[] jwtTokenSecret, UserMapper userMapper) {
        authFilter = UserAuthenticationFilter.build(jwtTokenSecret, userMapper);
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        final AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());

        if (!am.isAnnotationPresent(NoAuth.class)) {
            context.register(authFilter);
        }
    }
}
