package com.sureal.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: ztq161024zsc
 * @PACKAGE_NAME: com.sureal.client
 * @DATE: 2019/3/21
 * @TIME: 15:21
 * @PROJECT_NAME: tensquare_parent
 * @DAY_NAME_SHORT: 星期四
 */
@FeignClient("tensquare-user")
@Component
public interface UserClient {
    /**
     * 更新粉丝数
     * @param userid
     * @param fans
     */
    @RequestMapping(value = "/user/incfans/{userid}/{fans}", method = RequestMethod.PUT)
    void updateFansCount(@PathVariable("userid") String userid, @PathVariable("fans") int fans);

    /**
     * 更新跟随者数量
     * @param userid
     * @param follow
     */
    @RequestMapping(value = "/user/incFollow/{userid}/{follow}", method = RequestMethod.PUT)
    void updateFollowCount(@PathVariable("userid") String userid, @PathVariable("follow") int follow);
}
