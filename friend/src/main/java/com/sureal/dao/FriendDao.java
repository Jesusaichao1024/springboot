package com.sureal.dao;

import com.sureal.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: ztq161024zsc
 * @PACKAGE_NAME: com.sureal.dao
 * @DATE: 2019/3/20
 * @TIME: 17:07
 * @PROJECT_NAME: tensquare_parent
 * @DAY_NAME_SHORT: 星期三
 */
public interface FriendDao extends JpaRepository<Friend, String> {
    /**
     * 根据用户id值与被关注用户的id值查询记录个数
     * @param userId
     * @param friendId
     * @return
     */
    @Query("select count(f) from Friend f where f.userid=?1 and f.friendid=?2")
    Integer selectCount(String userId, String friendId);

    /**
     * 根据用户id值和好友的id值查询好友列表
     * @param userid
     * @param friendid
     * @return
     */
    Friend findByUseridAndFriendid(String userid, String friendid);

    /**
     * 更新isLike的信息
     * @param userId
     * @param friendId
     * @param isLike
     */
    @Query("update Friend  set islike =?3 where userid=?1 and friendid=?2")
    @Modifying
    void updateIsLike(String userId, String friendId, String isLike);
}
