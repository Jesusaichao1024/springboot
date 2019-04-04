package com.sureal.web;

import com.sureal.pojo.Label;
import com.sureal.service.LabelService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import entity.StatusCode;

import java.util.List;

/**
 * @Author Jesusaichao
 * @Date 2019/3/11
 * @Time 11:35
 * @PackageName com.sureal.web
 * @Project_Name tensquare_parent
 * @Description 标签的控制层
 */
@RestController
@RequestMapping("/label")
@CrossOrigin//跨域访问
public class LabelController {
    @Autowired
    private LabelService labelService;

    /**
     * 查询所有的数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        //调取业务层的数据
        List<Label> list = labelService.findAll();
        //创建实体类返回数据
        return new Result(StatusCode.OK, "查询所有成功", list, true);
    }

    /**
     * 根据id值查询
     *
     * @param labelId
     * @return
     */
    @RequestMapping(value = "{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId) {
        //调取service中业务
        System.out.println("labelId = " + labelId);
        Label label = labelService.findById(labelId);
        return new Result(StatusCode.OK, "查询成功", label, true);
    }

    /**
     * 保存数据
     *
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(StatusCode.OK, "保存成功", true);
    }

    /**
     * 更新数据
     *
     * @param labelId
     * @param label
     * @return
     */
    @RequestMapping(value = "{labelId}", method = RequestMethod.PUT)
    public Result update(@PathVariable("labelId") String labelId, @RequestBody Label label) {
        label.setId(labelId);
        //调取业务层的数据
        labelService.update(label);
        return new Result(StatusCode.OK, "更新成功", true);
    }

    /**
     * 删除数据
     *
     * @param labelId
     * @return
     */
    @RequestMapping(value = "{labelId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("labelId") String labelId) {
        labelService.delete(labelId);
        return new Result(StatusCode.OK, "删除成功", true);
    }

    /**
     * 条件查询
     * @param label
     * @return
     */

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Result searchCondition(@RequestBody Label label) {
        //调取业务层数据
        List<Label> condition = labelService.findCondition(label);
        return new Result(StatusCode.OK, "条件查询成功", condition, true);
    }

    /**
     * 分页查询数据 包含模糊查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "search/{page}/{size}", method = RequestMethod.POST)
    public Result search(@RequestBody Label label, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Label> labelPage = labelService.findPage(label, page, size);
        //创建自己分页的数据
        PageResult<Label> pageResult = new PageResult<>(labelPage.getTotalElements(), labelPage.getContent());
        return new Result(StatusCode.OK, "分页查询成功", pageResult, true);
    }

}
