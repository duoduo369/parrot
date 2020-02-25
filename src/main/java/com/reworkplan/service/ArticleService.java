package com.reworkplan.service;

import com.reworkplan.models.Article;

import java.util.List;

public interface ArticleService {
    Article get(Integer id, Boolean isActive);
    List<Article> getList(Boolean isActive, Integer offset, Integer limit);
    Integer count (Boolean isActive);
}
