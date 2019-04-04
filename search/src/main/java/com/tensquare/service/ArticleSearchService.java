package com.tensquare.service;

import com.tensquare.dao.ArticleSearchDao;
import com.tensquare.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/15
 * @TIME: 16:17
 * @DAY: 15
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.tensquare.service
 */
@Service
public class ArticleSearchService {
    @Autowired(required = false)
    private ArticleSearchDao articleSearchDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存数据
     * @param article
     */
    public void save(Article article) {
//        article.setId(String.valueOf(idWorker.nextId()));
        articleSearchDao.save(article);
    }

    /**
     * 查询所有的数据
     * @return
     */
    public List<Article> findAll() {
        return (List<Article>) articleSearchDao.findAll();
    }
    /**
     * 根据id值查询
     * @param id
     * @return
     */
    public Article findById(String id) {
        Article article = articleSearchDao.findById(id).get();
        return article;
    }

    /**
     *模糊分页查询
     * @param keyWords
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByTileLike(String keyWords, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Article> articlePage = articleSearchDao.findByTitleOrContentLike(keyWords, keyWords, pageRequest);
        return articlePage;
    }
}
