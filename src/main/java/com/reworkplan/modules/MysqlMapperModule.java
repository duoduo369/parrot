package com.reworkplan.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.reworkplan.config.ParrotConfiguration;
import com.reworkplan.mappers.ArticleMapper;
import com.reworkplan.mappers.UserMapper;
import com.reworkplan.modules.provider.MysqlMapperProvider;
import io.dropwizard.setup.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;

import static com.google.inject.util.Providers.guicify;

public class MysqlMapperModule extends AbstractModule {
    private final ParrotConfiguration configure;
    private final Environment enviroment;

    public MysqlMapperModule(ParrotConfiguration configuration, Environment environment) {
        this.configure = configuration;
        this.enviroment = environment;
    }

    @Override
    protected void configure() {
        addMapper(ArticleMapper.class);
        addMapper(UserMapper.class);
    }

    @Provides
    @Singleton
    @Named("mysql")
    public SqlSessionManager getMysqlSqlSessionManager(@Named("mysql") SqlSessionFactory sqlSessionFactory) {
        return SqlSessionManager.newInstance(sqlSessionFactory);
    }

    @Provides
    @Singleton
    @Named("mysql")
    public SqlSessionFactory getMysqlSqlSessionFactory() {
        return this.configure.getMysqlSqlSessionFactory();
    }

    private <T> void addMapper(Class<T> mapperType) {
        bind(mapperType).toProvider(guicify(new MysqlMapperProvider<>(mapperType))).in(Scopes.SINGLETON);
    }
}
