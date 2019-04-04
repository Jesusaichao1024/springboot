package com.tensquare.dao;

import com.tensquare.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author:ztq161024zsc
 * @DATE:2019/3/15
 * @TIME:16:16
 * @DAY:15
 * @PROJECT_NAME:tensquare_parent
 * @PACKAGE_NAME:com.tensquare.dao
 */
public interface ArticleSearchDao extends ElasticsearchRepository<Article, String> {
    /**
     * 模糊查询数据
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);

}
