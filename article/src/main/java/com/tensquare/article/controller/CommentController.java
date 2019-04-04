package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 保存数据
     * @param comment
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment) {
        commentService.save(comment);
        return new Result(StatusCode.OK, "保存成功", true);
    }

    /**
     * 根据文章id值查询评论数
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.POST)
    public Result findByArticleId(@PathVariable String articleId) {
        List<Comment> list = commentService.findByArticleId(articleId);
        return new Result(StatusCode.OK, "查询成功", list, true);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @RequestMapping(value = "article/{id}", method = RequestMethod.DELETE)
    public Result deleteByArticleId(@PathVariable String id) {
        commentService.deleteByArticleId(id);
        return new Result(StatusCode.OK, "删除成功", true);
    }

}
