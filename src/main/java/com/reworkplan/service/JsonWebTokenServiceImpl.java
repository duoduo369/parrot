package com.reworkplan.service;

import com.github.toastshaman.dropwizard.auth.jwt.hmac.HmacSHA512Signer;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebToken;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenClaim;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenHeader;
import com.google.inject.Inject;
import com.reworkplan.models.User;
import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

public class JsonWebTokenServiceImpl implements JsonWebTokenService {
    private static final int DEFAULT_SESSION_EXPIRATION_HOURS = 24 * 7;
    private final HmacSHA512Signer signer;

    @Inject
    public JsonWebTokenServiceImpl(HmacSHA512Signer signer) {
        this.signer = signer;
    }

    @Override
    public String tokenizeAndSign(User user) {
        return sign(tokenize(user));
    }

    @Override
    public JsonWebToken tokenize(User user) {
        return JsonWebToken.builder()
                .header(JsonWebTokenHeader.HS512())
                .claim(JsonWebTokenClaim.builder()
                        .subject(user.getUsername())
                        .issuedAt(DateTime.now())
                        .expiration(DateTime.now().plusHours(DEFAULT_SESSION_EXPIRATION_HOURS))
                        .build())
                .build();
    }

    @Override
    public String sign(JsonWebToken jsonWebToken) {
        return signer.sign(jsonWebToken);
    }

    @Override
    public Long getExpiryInMinutes() {
        return TimeUnit.MINUTES.convert(DEFAULT_SESSION_EXPIRATION_HOURS, TimeUnit.HOURS);
    }
}
