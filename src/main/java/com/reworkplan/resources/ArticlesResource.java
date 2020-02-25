package com.reworkplan.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.reworkplan.common.Constants;
import com.reworkplan.common.response.MetaListResponse;
import com.reworkplan.common.response.MetaMapperResponse;
import com.reworkplan.mappers.ArticleMapper;
import com.reworkplan.models.Article;
import com.reworkplan.service.ArticleService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path(BasePath.ARTICLE_API)
@Produces(APPLICATION_JSON)
public class ArticlesResource {
    private final ArticleService articleService;

    @Inject
    public ArticlesResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GET
    @Timed
    public MetaListResponse get(@DefaultValue(Constants.DEFAULT_PARAM_OFFSET) @QueryParam("offset") Integer offset,
                                @DefaultValue(Constants.DEFAULT_PARAM_LIMIT) @QueryParam("limit") Integer limit){
        MetaListResponse response = new MetaListResponse();
        response.putMeta("count", 0);
        Boolean isActive = true;
        Integer count = articleService.count(isActive);
        List<Article> articles = articleService.getList(isActive, offset, limit);
        response.putMeta("count", count);
        response.setData(articles);
        return response;
    }

    @Path("/{id}")
    @GET
    @Timed
    public MetaMapperResponse get(@NotNull @PathParam("id") Integer articleId) {
        MetaMapperResponse response = new MetaMapperResponse();
        Boolean isActive = true;
        Article article = articleService.get(articleId, isActive);
        response.setData(article);
        return response;
    }
}
