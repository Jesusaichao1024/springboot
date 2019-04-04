package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 保存数据
     * @param comment
     */
    public void save(Comment comment) {
//        comment.set_id(String.valueOf(idWorker.nextId()));
        comment.setPublishdate(new Date());
        commentDao.save(comment);
    }

    /**
     *
     * @param articleId
     * @return
     */
    public List<Comment> findByArticleId(String articleId) {
        List<Comment> list = commentDao.findByArticleid(articleId);
        return list;
    }

    /**
     * 删除数据
     * @param articleId
     */
    public void deleteByArticleId(String articleId) {
        commentDao.deleteByArticleid(articleId);
    }
}
