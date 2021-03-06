package com.reworkplan.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Injector;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ParrotConfiguration extends Configuration {
    @Valid
    @NotNull
    private DataSourceFactory mysql = new DataSourceFactory();
    private SqlSessionFactory mysqlSqlSessionFactory;
    private Injector injector;
    @NotEmpty
    private final String jwtTokenSecret = "6b0d178ee3e54d5487fe18cde6c4e871";

    public byte[] getJwtTokenSecret() {
        return jwtTokenSecret.getBytes();
    }

    public Injector getInjector() {
        return injector;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    public SqlSessionFactory getMysqlSqlSessionFactory() {
        return mysqlSqlSessionFactory;
    }

    public void setMysqlSqlSessionFactory(SqlSessionFactory mysqlSqlSessionFactory) {
        this.mysqlSqlSessionFactory = mysqlSqlSessionFactory;
    }

    @JsonProperty("mysql")
    public void setMysql(DataSourceFactory mysql) {
        this.mysql = mysql;
    }

    @JsonProperty("mysql")
    public DataSourceFactory getMysql() {
        return mysql;
    }
}
