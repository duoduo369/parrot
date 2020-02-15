package com.reworkplan;

import com.reworkplan.bundles.CorsBundle;
import com.reworkplan.bundles.MysqlBundle;
import com.reworkplan.config.ParrotConfiguration;
import com.reworkplan.mappers.ArticleMapper;
import com.reworkplan.modules.InjectorFactory;
import com.reworkplan.resources.ArticlesResource;
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import tw.hyl.common.jersey.jodatime.JodaTimeParamConverterProvider;

public class ParrotApplication extends Application<ParrotConfiguration> {
    private final InjectorFactory injectorFactory;
    private final MysqlBundle mysqlBundle;

    public ParrotApplication() {
        this.injectorFactory = new InjectorFactory();
        this.mysqlBundle = new MysqlBundle();
    }

    public ParrotApplication(InjectorFactory injectorFactory) {
        this.injectorFactory = injectorFactory;
        this.mysqlBundle = new MysqlBundle();
    }
    @Override
    public void initialize(Bootstrap<ParrotConfiguration> bootstrap) {
        bootstrap.addBundle(new TemplateConfigBundle());
        bootstrap.addBundle(new Java8Bundle());
        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.addBundle(new CorsBundle());
        bootstrap.addBundle(mysqlBundle);
        super.initialize(bootstrap);
    }

    @Override
    public void run(ParrotConfiguration configuration, Environment environment) throws Exception {
        SqlSessionFactory sessionFactory = mysqlBundle.getSqlSessionFactory();
        sessionFactory.getConfiguration().addMapper(ArticleMapper.class);

        environment.jersey().register(JodaTimeParamConverterProvider.class);
        environment.jersey().register(new ArticlesResource(sessionFactory));
    }
    public static void main(String[] args) throws Exception {
        new ParrotApplication(new InjectorFactory()).run(args);
    }
}
