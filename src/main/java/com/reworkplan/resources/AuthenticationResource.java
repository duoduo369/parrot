package com.reworkplan.resources;

import com.google.inject.Inject;
import com.reworkplan.api.request.LoginRequest;
import com.reworkplan.api.response.MetaMapperResponse;
import com.reworkplan.api.serializer.UserSerializer;
import com.reworkplan.auth.NoAuth;
import com.reworkplan.models.User;
import com.reworkplan.service.JsonWebTokenService;
import com.reworkplan.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path(BasePath.AUTHORIZATION)
@Produces(APPLICATION_JSON)
public final class AuthenticationResource {
    private final UserService userService;
    private final JsonWebTokenService jsonWebTokenService;

    @Inject
    public AuthenticationResource(UserService userService, JsonWebTokenService jsonWebTokenService) {
        this.userService = userService;
        this.jsonWebTokenService = jsonWebTokenService;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @NoAuth
    @Path("/login")
    public MetaMapperResponse login(LoginRequest loginRequest, @Context HttpServletRequest request) throws
            InvalidKeySpecException, NoSuchAlgorithmException {
        Optional<User> optionalUser = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (!optionalUser.isPresent()){
            throw new NotAuthorizedException("Wrong username or password");
        }
        User user = optionalUser.get();
        String token = jsonWebTokenService.tokenizeAndSign(user);
        UserSerializer serializer = UserSerializer.build(user);
        MetaMapperResponse response = new MetaMapperResponse();
        response.putMeta("token", token);
        response.setData(serializer);
        return response;
    }
}
