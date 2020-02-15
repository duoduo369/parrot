package com.reworkplan.mappers;

import com.reworkplan.models.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    Article select(@Param("id") Integer id, @Param("isActive") Boolean isActive);
    List<Article> selectAll(@Param("isActive") Boolean isActive,
                            @Param("offset") Integer offset,
                            @Param("limit") Integer limit);
    Integer countAll (@Param("isActive") Boolean isActive);
}
