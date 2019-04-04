package com.sureal.dao;

import com.sureal.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: ztq161024zsc
 * @PACKAGE_NAME: com.sureal.dao
 * @DATE: 2019/3/20
 * @TIME: 17:19
 * @PROJECT_NAME: tensquare_parent
 * @DAY_NAME_SHORT: 星期三
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String> {
    /**
     * 根据用户得id值和好友的id值查询好友列表
     * @param userId
     * @param friendId
     * @return
     */
    NoFriend findByUseridAndFriendid(String userId, String friendId);
}
