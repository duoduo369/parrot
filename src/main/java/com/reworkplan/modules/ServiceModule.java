package com.reworkplan.modules;

import com.github.toastshaman.dropwizard.auth.jwt.JsonWebTokenSigner;
import com.github.toastshaman.dropwizard.auth.jwt.hmac.HmacSHA512Signer;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.reworkplan.auth.hasher.PBKDF2PasswordHasher;
import com.reworkplan.auth.hasher.PasswordHasher;
import com.reworkplan.service.*;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bindService(ArticleService.class, ArticleServiceImpl.class);
        bindService(UserService.class, UserServiceImpl.class);
        bindService(JsonWebTokenService.class, JsonWebTokenServiceImpl.class);
        bindService(PasswordHasher.class, PBKDF2PasswordHasher.class);
    }

    private <T> void bindService(Class<T> interfaceClass, Class<? extends T> implClass) {
        bind(interfaceClass).to(implClass).in(Scopes.SINGLETON);
    }
}
