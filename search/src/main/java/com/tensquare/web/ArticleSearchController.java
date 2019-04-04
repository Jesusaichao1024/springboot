package com.tensquare.web;

import com.tensquare.pojo.Article;
import com.tensquare.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/15
 * @TIME: 16:47
 * @DAY: 15
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.tensquare.web
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {
    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * 查询所有的数据
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Article> list = articleSearchService.findAll();
        return new Result(StatusCode.OK, "查询所有的数据成功", list, true);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        Article article = articleSearchService.findById(id);
        return new Result(StatusCode.OK, "查询数据成功", article, true);
    }
    /**
     * 保存数据
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article) {
        articleSearchService.save(article);
        return new Result(StatusCode.OK, "保存成功", true);
    }

    /**
     * 模糊查询分页数据
     * @param keyWords
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{keyWords}/{page}/{size}",method = RequestMethod.GET)
    public Result findByTitle(@PathVariable String keyWords,@PathVariable Integer page,@PathVariable Integer size) {
        Page<Article> articlePage = articleSearchService.findByTileLike(keyWords, page, size);
        PageResult<Article> result = new PageResult<>(articlePage.getTotalElements(), articlePage.getContent());
        return new Result(StatusCode.OK, "查询分页成功", result, true);
    }
}
