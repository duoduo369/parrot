package com.reworkplan.service;

import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebToken;
import com.reworkplan.models.User;

public interface JsonWebTokenService {

    String tokenizeAndSign(User user);

    JsonWebToken tokenize(User user);

    String sign(JsonWebToken jsonWebToken);

    Long getExpiryInMinutes();
}
