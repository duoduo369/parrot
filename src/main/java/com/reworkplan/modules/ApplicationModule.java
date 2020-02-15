package com.reworkplan.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.reworkplan.config.ParrotConfiguration;
import io.dropwizard.setup.Environment;

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

    public Environment getEnviroment() {
        return this.enviroment;
    }

    public ParrotConfiguration getConfigure() {
        return this.configure;
    }
}
