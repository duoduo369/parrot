package com.reworkplan.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ParrotConfiguration extends Configuration {
    @Valid
    @NotNull
    private DataSourceFactory mysql = new DataSourceFactory();

    @JsonProperty("mysql")
    public void setMysql(DataSourceFactory mysql) {
        this.mysql = mysql;
    }

    @JsonProperty("mysql")
    public DataSourceFactory getMysql() {
        return mysql;
    }
}
