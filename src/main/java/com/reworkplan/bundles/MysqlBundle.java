package com.reworkplan.bundles;

import com.loginbox.dropwizard.mybatis.EnhancedMybatisBundle;
import com.reworkplan.config.ParrotConfiguration;
import com.reworkplan.mappers.handlers.DateTimeTypeHandler;
import com.reworkplan.models.Article;
import io.dropwizard.db.PooledDataSourceFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.joda.time.DateTime;

import java.util.Map;

public class MysqlBundle extends EnhancedMybatisBundle<ParrotConfiguration> {
    public MysqlBundle(){
        super("com.reworkplan.mappers");
    }
    @Override
    public PooledDataSourceFactory getDataSourceFactory(ParrotConfiguration configuration) {
        return configuration.getMysql();
    }

    @Override
    protected void registerCustomTypeHandlers(TypeHandlerRegistry typeHandlerRegistry) {
        typeHandlerRegistry.register(DateTime.class, DateTimeTypeHandler.class);
    }

    @Override
    protected void registerAliases(TypeAliasRegistry typeAliasRegistry) {
        typeAliasRegistry.registerAlias(Article.class);
    }
}
