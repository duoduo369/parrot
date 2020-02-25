package com.reworkplan.modules.provider;

import com.google.inject.name.Named;
import org.apache.ibatis.session.SqlSessionManager;

import javax.inject.Inject;
import javax.inject.Provider;

public class MysqlMapperProvider <T> implements Provider<T> {

    private final Class<T> mapperType;

    @Inject
    @Named("mysql")
    private SqlSessionManager sqlSessionManager;

    public MysqlMapperProvider(Class<T> mapperType) {
        this.mapperType = mapperType;
    }

    public void setSqlSessionManager(SqlSessionManager sqlSessionManager) {
        this.sqlSessionManager = sqlSessionManager;
    }

    @Override
    public T get() {
        return this.sqlSessionManager.getMapper(mapperType);

    }
}
