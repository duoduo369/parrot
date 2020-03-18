package com.reworkplan.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.reworkplan.auth.NoAuth;
import com.reworkplan.common.Constants;
import com.reworkplan.api.response.MetaListResponse;
import com.reworkplan.api.response.MetaMapperResponse;
import com.reworkplan.models.Article;
import com.reworkplan.models.User;
import com.reworkplan.service.ArticleService;
import io.dropwizard.auth.Auth;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path(BasePath.ARTICLE)
@Produces(APPLICATION_JSON)
public class ArticlesResource {
    private final ArticleService articleService;

    @Inject
    public ArticlesResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @NoAuth
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

    @NoAuth
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

    @Path("/{id}/secret")
    @GET
    @Timed
    public MetaMapperResponse getSecretArticle(@Auth User user, @NotNull @PathParam("id") Integer articleId) {
        MetaMapperResponse response = new MetaMapperResponse();
        Boolean isActive = true;
        Article article = articleService.get(articleId, isActive);
        response.setData(article);
        return response;
    }
}
