package com.tensquare.article.dao;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentDao extends MongoRepository<Comment, String> {
    /**
     * 根据文章id值查询品论列表
     * @param articleid
     * @return
     */
    List<Comment> findByArticleid(String articleid);

    /**
     * 删除数据
     * @param articleId
     */
    void deleteByArticleid(String articleId);
}
