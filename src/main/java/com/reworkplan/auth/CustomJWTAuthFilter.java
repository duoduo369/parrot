package com.reworkplan.auth;

import com.github.toastshaman.dropwizard.auth.jwt.JsonWebTokenParser;
import com.github.toastshaman.dropwizard.auth.jwt.JsonWebTokenVerifier;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebToken;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;

import javax.annotation.Priority;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.Priorities.AUTHENTICATION;

@Priority(AUTHENTICATION)
public final class CustomJWTAuthFilter<P extends Principal> extends AuthFilter<JsonWebToken, P> {
    private static final String SESSION_TOKEN_QUERY_PARAM = "sessionToken";
    private final JsonWebTokenVerifier tokenVerifier;
    private final JsonWebTokenParser tokenParser;
    private final String cookieName;

    public CustomJWTAuthFilter(JsonWebTokenParser tokenParser, JsonWebTokenVerifier tokenVerifier, String cookieName) {
        this.tokenParser = tokenParser;
        this.tokenVerifier = tokenVerifier;
        this.cookieName = cookieName;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final Optional<String> optionalToken = getTokenFromCookieOrHeaderOrQueryParam(requestContext);

        if ("OPTIONS".equals(requestContext.getMethod())) {
            return;
        }
        if (optionalToken.isPresent()) {
            try {
                final JsonWebToken token = verifyToken(optionalToken.get());
                final Optional<P> principal = authenticator.authenticate(token);

                if (principal.isPresent()) {
                    boolean isSecure = requestContext.getSecurityContext().isSecure();
                    ParrotSecurityContext<P> securityContext = new ParrotSecurityContext<>(authorizer,
                            principal.get(),
                            isSecure);
                    requestContext.setSecurityContext(securityContext);
                    return;
                }
            } catch (AuthenticationException ex) {
                throw new InternalServerErrorException();
            }
        }

        throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
    }

    private JsonWebToken verifyToken(String rawToken) {
        final JsonWebToken token = tokenParser.parse(rawToken);
        tokenVerifier.verifySignature(token);
        return token;
    }

    private Optional<String> getTokenFromHeader(MultivaluedMap<String, String> headers) {
        final String header = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            int space = header.indexOf(' ');
            if (space > 0) {
                final String method = header.substring(0, space);
                if (prefix.equalsIgnoreCase(method)) {
                    final String rawToken = header.substring(space + 1);
                    return Optional.of(rawToken);
                }
            }
        }

        return Optional.absent();
    }

    public Optional<String> getTokenFromCookie(ContainerRequestContext requestContext) {
        final Map<String, Cookie> cookies = requestContext.getCookies();

        if (cookieName != null && cookies.containsKey(cookieName)) {
            final Cookie tokenCookie = cookies.get(cookieName);
            final String rawToken = tokenCookie.getValue();
            return Optional.of(rawToken);
        }

        return Optional.absent();
    }

    private Optional<String> getTokenFromQueryParam(ContainerRequestContext requestContext) {
        MultivaluedMap<String, String> queryParameters = requestContext.getUriInfo().getQueryParameters();
        for (String s : queryParameters.keySet()) {
            if (SESSION_TOKEN_QUERY_PARAM.equals(s)) {
                List<String> queryParam = queryParameters.get(s);
                if (!queryParam.isEmpty()) {
                    return Optional.of(queryParam.get(0));
                }
            }
        }
        return Optional.absent();
    }

    public Optional<String> getTokenFromCookieOrHeaderOrQueryParam(ContainerRequestContext requestContext) {
        final Optional<String> headerToken = getTokenFromHeader(requestContext.getHeaders());

        if (headerToken.isPresent()) {
            return headerToken;
        }

        final Optional<String> cookieToken = getTokenFromCookie(requestContext);

        if (cookieToken.isPresent()) {
            return cookieToken;
        }

        final Optional<String> queryParamToken = getTokenFromQueryParam(requestContext);
        return queryParamToken.isPresent() ? queryParamToken : Optional.<String>absent();
    }

    public static class Builder<P extends Principal> extends
            AuthFilterBuilder<JsonWebToken, P, CustomJWTAuthFilter<P>> {
        private JsonWebTokenParser tokenParser;
        private JsonWebTokenVerifier tokenVerifier;
        private String cookieName;

        public Builder<P> setTokenParser(JsonWebTokenParser tokenParser) {
            this.tokenParser = tokenParser;
            return this;
        }

        public Builder<P> setTokenVerifier(JsonWebTokenVerifier tokenVerifier) {
            this.tokenVerifier = tokenVerifier;
            return this;
        }

        public Builder<P> setCookieName(String cookieName) {
            this.cookieName = cookieName;
            return this;
        }

        @Override
        protected CustomJWTAuthFilter<P> newInstance() {
            Preconditions.checkArgument(tokenParser != null, "JsonWebTokenParser is not set");
            Preconditions.checkArgument(tokenVerifier != null, "JsonWebTokenVerifier is not set");
            return new CustomJWTAuthFilter<>(tokenParser, tokenVerifier, cookieName);
        }
    }
}
