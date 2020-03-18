package com.reworkplan.bundles;

import com.google.inject.Injector;
import com.reworkplan.auth.UserAuthenticationDynamicFeature;
import com.reworkplan.config.ParrotConfiguration;
import com.reworkplan.mappers.UserMapper;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AuthBundle implements ConfiguredBundle<ParrotConfiguration> {
    @Override
    public void run(ParrotConfiguration configuration, Environment environment) throws Exception {
        Injector injector = configuration.getInjector();
        environment.jersey().register(new UserAuthenticationDynamicFeature(
                configuration.getJwtTokenSecret(),
                injector.getInstance(UserMapper.class)
        ));

    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }
}
