package com.reworkplan.auth;

import io.dropwizard.auth.Authorizer;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class ParrotSecurityContext <P extends Principal> implements SecurityContext {
    private final Authorizer<P> authorizer;
    private final P principal;
    private final boolean isSecure;

    public ParrotSecurityContext(Authorizer<P> authorizer, P principal, boolean isSecure) {
        this.authorizer = authorizer;
        this.principal = principal;
        this.isSecure = isSecure;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return authorizer.authorize(principal, role);
    }

    @Override
    public boolean isSecure() {
        return isSecure;
    }

    @Override
    public String getAuthenticationScheme() {
        return BASIC_AUTH;
    }

}
