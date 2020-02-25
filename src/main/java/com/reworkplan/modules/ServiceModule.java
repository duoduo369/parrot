package com.reworkplan.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.reworkplan.service.ArticleService;
import com.reworkplan.service.ArticleServiceImpl;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bindService(ArticleService.class, ArticleServiceImpl.class);
    }

    private <T> void bindService(Class<T> interfaceClass, Class<? extends T> implClass) {
        bind(interfaceClass).to(implClass).in(Scopes.SINGLETON);
    }
}
