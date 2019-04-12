package com.tensquare.user.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(StatusCode.OK, "查询成功", userService.findAll(), true);
    }

    /**
     * 根据id值查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(StatusCode.OK, "查询成功", userService.findById(id), true);
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()), true);
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(StatusCode.OK, "查询成功", userService.findSearch(searchMap), true);
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(StatusCode.OK, "增加成功", true);
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(StatusCode.OK, "修改成功", true);
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
       /* //获取头信息
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            return new Result(StatusCode.ACCESSERROR, "权限不足(没有消息头信息)", false);
        }
        if (!authorization.startsWith("Bearer ")) {
            return new Result(StatusCode.ACCESSERROR, "权限不足(消息头对应的值,格式不对)", false);
        }
        //取出token信息
        String token = authorization.split(" ")[1];
        //解析token
        Claims claims = jwtUtil.parseJWT(token);
        if (claims == null) {
            return new Result(StatusCode.ACCESSERROR, "权限不足(没有解析初token信息)", false);
        }
        String roles = (String) claims.get("roles");
        if (!"admin_role".equals(roles)) {
            return new Result(StatusCode.ACCESSERROR, "权限不足(不是管理员)", false);
        }*/
        Claims role = (Claims) request.getAttribute("admin_role");
        if (role == null) {
            return new Result(StatusCode.ACCESSERROR, "权限不足", false);
        }
        userService.deleteById(id);
        return new Result(StatusCode.OK, "删除成功", true);
    }

    /**
     * 发送验证码数据
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile) {
        userService.sendMsg(mobile);
        return new Result(StatusCode.OK, "发送验证码成功", true);
    }

    /**
     * 注册用户
     *
     * @param user
     * @param code
     * @return
     */
    @RequestMapping(value = "/register/{code}", method = RequestMethod.POST)
    public Result register(@RequestBody User user, @PathVariable String code) throws ParseException {
        userService.add(user, code);
        return new Result(StatusCode.OK, "注册成功", true);
    }

    /**
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User user) {
        User userMobile = userService.findByMobile(user);
        if (userMobile != null) {
            String token = jwtUtil.createJwt(userMobile.getId(), userMobile.getNickname(), "user_role");
            Map map = new HashMap<>(16);
            map.put("token", token);
            map.put("name", userMobile.getNickname());
            map.put("avatar", userMobile.getAvatar());
            return new Result(StatusCode.OK, "登录成功",map, true);
        } else {
            return new Result(StatusCode.LOGINERROR, "登录失败 用户名或者密码错误", false);
        }
    }

    /**
     * 更新粉丝数量
     * @param userid
     * @param fans
     * @return
     */
    @RequestMapping(value = "incfans/{userid}/{fans}", method = RequestMethod.PUT)
    public Result updateFansCount(@PathVariable("userid") String userid, @PathVariable("fans") int fans) {
        userService.updateFansCount(userid, fans);
        return new Result(StatusCode.OK, "增加粉丝成功", true);
    }

    /**
     * 更新跟随者数量
     * @param userid
     * @param follow
     * @return
     */
    @RequestMapping(value = "incFollow/{userid}/{follow}", method = RequestMethod.PUT)
    public Result updateFollowFans(@PathVariable("userid") String userid, @PathVariable("follow") int follow) {
        userService.updateFollowFans(userid, follow);
        return new Result(StatusCode.OK, "添加跟随者成功", true);
    }
}
