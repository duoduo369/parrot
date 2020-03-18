package com.reworkplan;

import com.google.inject.Injector;
import com.reworkplan.bundles.AuthBundle;
import com.reworkplan.bundles.CorsBundle;
import com.reworkplan.bundles.GuiceBundle;
import com.reworkplan.bundles.MysqlBundle;
import com.reworkplan.config.ParrotConfiguration;
import com.reworkplan.mappers.ArticleMapper;
import com.reworkplan.modules.InjectorFactory;
import com.reworkplan.resources.ArticlesResource;
import com.reworkplan.resources.AuthenticationResource;
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import tw.hyl.common.jersey.jodatime.JodaTimeParamConverterProvider;

public class ParrotApplication extends Application<ParrotConfiguration> {
    private final InjectorFactory injectorFactory;

    public ParrotApplication() {
        this.injectorFactory = new InjectorFactory();
    }

    public ParrotApplication(InjectorFactory injectorFactory) {
        this.injectorFactory = injectorFactory;
    }
    @Override
    public void initialize(Bootstrap<ParrotConfiguration> bootstrap) {
        MysqlBundle mysqlBundle = new MysqlBundle();

        bootstrap.addBundle(new TemplateConfigBundle());
        bootstrap.addBundle(new Java8Bundle());
        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.addBundle(mysqlBundle);
        bootstrap.addBundle(new GuiceBundle(injectorFactory, mysqlBundle));
        bootstrap.addBundle(new CorsBundle());
        bootstrap.addBundle(new AuthBundle());
        super.initialize(bootstrap);
    }

    @Override
    public void run(ParrotConfiguration configuration, Environment environment) throws Exception {

        Injector injector = configuration.getInjector();
        environment.jersey().register(JodaTimeParamConverterProvider.class);
        environment.jersey().register(injector.getInstance(ArticlesResource.class));
        environment.jersey().register(injector.getInstance(AuthenticationResource.class));
    }
    public static void main(String[] args) throws Exception {
        new ParrotApplication(new InjectorFactory()).run(args);
    }
}
