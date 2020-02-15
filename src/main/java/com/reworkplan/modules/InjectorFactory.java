package com.reworkplan.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.reworkplan.config.ParrotConfiguration;
import io.dropwizard.setup.Environment;

public class InjectorFactory {
    public Injector get(ParrotConfiguration configuration, Environment environment) {
        Injector intjector = Guice.createInjector(
                new ApplicationModule(configuration, environment)
        );
        return intjector ;
    }
}
