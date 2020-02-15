package com.reworkplan.resources;

import com.codahale.metrics.annotation.Timed;
import com.reworkplan.common.Constants;
import com.reworkplan.common.response.MetaListResponse;
import com.reworkplan.common.response.MetaMapperResponse;
import com.reworkplan.mappers.ArticleMapper;
import com.reworkplan.models.Article;
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
    private final SqlSessionFactory sessionFactory;

    public ArticlesResource(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @GET
    @Timed
    public MetaListResponse get(@DefaultValue(Constants.DEFAULT_PARAM_OFFSET) @QueryParam("offset") Integer offset,
                                @DefaultValue(Constants.DEFAULT_PARAM_LIMIT) @QueryParam("limit") Integer limit){
        MetaListResponse response = new MetaListResponse();
        response.putMeta("count", 0);
        try (SqlSession session = sessionFactory.openSession()) {
            ArticleMapper articleMapper = session.getMapper(ArticleMapper.class);
            Boolean isActive = true;
            Integer count = articleMapper.countAll(isActive);
            List<Article> articles = articleMapper.selectAll(isActive, offset, limit);
            response.putMeta("count", count);
            response.setData(articles);
        }
        return response;
    }

    @Path("/{id}")
    @GET
    @Timed
    public MetaMapperResponse get(@NotNull @PathParam("id") Integer articleId) {
        MetaMapperResponse response = new MetaMapperResponse();
        try (SqlSession session = sessionFactory.openSession()) {
            ArticleMapper articleMapper = session.getMapper(ArticleMapper.class);
            Boolean isActive = true;
            Article article = articleMapper.select(articleId, isActive);
            System.out.println("==============");
            System.out.println(articleId);
            System.out.println(article);
            response.setData(article);
        }
        return response;
    }
}
