package com.reworkplan.modules;

import com.github.toastshaman.dropwizard.auth.jwt.hmac.HmacSHA512Signer;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.reworkplan.config.ParrotConfiguration;
import io.dropwizard.setup.Environment;

import java.io.UnsupportedEncodingException;

public class ApplicationModule implements Module {
    private final ParrotConfiguration configure;
    private final Environment enviroment;

    public ApplicationModule(ParrotConfiguration configuration, Environment environment) {
        this.configure = configuration;
        this.enviroment = environment;
    }

    @Override
    public void configure(Binder binder) {
    }

    @Provides
    @Singleton
    public HmacSHA512Signer getSigner() throws UnsupportedEncodingException {
        return new HmacSHA512Signer(configure.getJwtTokenSecret());
    }

    public Environment getEnviroment() {
        return this.enviroment;
    }

    public ParrotConfiguration getConfigure() {
        return this.configure;
    }
}
