package com.sureal.web;

import com.sureal.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ztq161024zsc
 * @PACKAGE_NAME: com.sureal.web
 * @DATE: 2019/3/20
 * @TIME: 18:40
 * @PROJECT_NAME: tensquare_parent
 * @DAY_NAME_SHORT: 星期三
 */
@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 添加好友
     * @param friendId 好友id值
     * @param type 1表示好友 添加关注 2 表示非好友 拉黑
     * @return
     */
    @RequestMapping(value = "/likes/{friendId}/{type}", method = RequestMethod.PUT)
    public Result addFriends(@PathVariable("friendId") String friendId, @PathVariable("type") String type) {
        Claims claims = (Claims) request.getAttribute("user_role");
        if (claims == null) {
            return new Result(StatusCode.ACCESSERROR, "权限不足", false);
        }
        //添加好友的数据
        if ("1".equals(type)) {
            int i = friendService.addFriends(claims.getId(), friendId);
            if (i == 0) {
                return new Result(StatusCode.REMOTEERROR, "已经添加此好友", false);
            }
            //拉黑数据
        } else if ("2".equals(type)) {
            friendService.addNoFriend(claims.getId(), friendId);
        }
        return new Result(StatusCode.OK, "添加成功", true);
    }

    /**
     * 添加好友
     * @param friendId
     * @param type
     * @return
     */
    @RequestMapping(value = "/like/{friendId}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable("friendId") String friendId, @PathVariable("type") String type) {
        Claims claims = (Claims) request.getAttribute("user_role");
        if (claims == null) {
            return new Result(StatusCode.ACCESSERROR, "权限不足", false);
        }
        //添加好友的数据
        if ("1".equals(type)) {
            friendService.addFriend(claims.getId(), friendId);
            //拉黑数据
        } else if ("2".equals(type)) {
            friendService.addNoFriend(claims.getId(), friendId);
        }
        return new Result(StatusCode.OK, "添加成功", true);
    }

    /**
     * 删除好友
     * @param friendId
     * @return
     */
    @RequestMapping(value = "{friendId}", method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable("friendId") String friendId) {
        //获取请求中的Claims
        Claims claims = (Claims) request.getAttribute("user_role");
        //判断是否有权限
        if (claims == null) {
            return new Result(StatusCode.ACCESSERROR, "权限不足", false);
        }
        //取出claims的id值
        String userId = claims.getId();
        //删除好友
        friendService.deleteFriend(userId, friendId);
        //创建返回对象
        return new Result(StatusCode.OK, "删除成功", true);
    }
}
