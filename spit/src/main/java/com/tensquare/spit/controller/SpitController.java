package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ztq161024zsc
 * @description: 吐槽微服务
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HttpServletRequest request;

    /**
     * 查询所有的数据
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Spit> list = spitService.findAll();
        return new Result( StatusCode.OK, "查询成功", list,true);
    }

    /**
     * 根据id值查询数据
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        Spit spit = spitService.findById(spitId);
        return new Result(StatusCode.OK, "查询成功", spit,true);
    }

    /**
     * 保存数据
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        Claims claims = (Claims) request.getAttribute("user_role");
        if (claims == null) {
            return new Result(StatusCode.ACCESSERROR, "权限不足", false);
        }
        spit.set_id(claims.getId());
        spitService.save(spit);
        return new Result(StatusCode.OK, "保存成功",true);
    }

    /**
     * 根据id值修改数据
     * @param spitId
     * @param spit
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId, @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(StatusCode.OK, "修改成功",true);
    }

    /**
     * 删除数据
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result( StatusCode.OK, "删除成功",true);
    }

    /**
     * 根据上级id查询数据分页吐槽数据
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findByParentAndPage(@PathVariable String parentid, @PathVariable int page, @PathVariable int size){
        //分页数据传入参数
        Page<Spit> pageData = spitService.findByParentId(parentid, page, size);
        //获取返回的内容
        PageResult<Spit> result = new PageResult<>(pageData.getTotalElements(), pageData.getContent());
        return new Result(StatusCode.OK, "查询成功", result,true);
    }

    /**
     * 点赞数据
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result addthumbup(@PathVariable String spitId){
        //模拟一个用户id,后面完善。
        String userid = "6666";
        //从redis缓存中拿当前用户是否点赞的标识
        if(redisTemplate.opsForValue().get("thumbup_"+userid+"_"+spitId)!=null){
            return new Result( StatusCode.REPERROR, "不能重复点赞",true);
        };
        spitService.addthumbup(spitId);
        //向redis缓存中存入一个已经点赞的标识
        redisTemplate.opsForValue().set("thumbup_"+userid+"_"+spitId, "1");
        return new Result(StatusCode.OK, "点赞成功",true);
    }

}
