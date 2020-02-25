package com.reworkplan.bundles;

import com.google.inject.Injector;
import com.reworkplan.config.ParrotConfiguration;
import com.reworkplan.modules.InjectorFactory;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class GuiceBundle implements ConfiguredBundle<ParrotConfiguration> {
    private final InjectorFactory injectorFactory;
    private final MysqlBundle mysqlBundle;

    public GuiceBundle(InjectorFactory injectorFactory, MysqlBundle mysqlBundle) {
        this.injectorFactory = injectorFactory;
        this.mysqlBundle = mysqlBundle;
    }

    @Override
    public void run(ParrotConfiguration configuration, Environment environment) throws Exception {
        configuration.setMysqlSqlSessionFactory(mysqlBundle.getSqlSessionFactory());
        Injector injector = injectorFactory.get(configuration, environment);
        configuration.setInjector(injector);
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }
}
