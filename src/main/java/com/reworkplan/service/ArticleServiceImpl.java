package com.reworkplan.service;

import com.google.inject.Inject;
import com.reworkplan.mappers.ArticleMapper;
import com.reworkplan.models.Article;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;

    @Inject
    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public List<Article> getList(Boolean isActive, Integer offset, Integer limit) {
        List<Article> articles = articleMapper.selectAll(isActive, offset, limit);
        return articles;
    }

    @Override
    public Article get(Integer id, Boolean isActive) {
        return articleMapper.select(id, isActive);
    }

    @Override
    public Integer count(Boolean isActive) {
        return articleMapper.countAll(isActive);
    }
}
