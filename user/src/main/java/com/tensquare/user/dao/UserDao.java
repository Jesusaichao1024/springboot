package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
    /**
     * 根据电话号码查询用户信息
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 更新粉丝数量
     * @param userid
     * @param fans
     */
    @Modifying
    @Query("update User set fanscount = fanscount + ?2 where id=?1 ")
    void updateFansCount(String userid, int fans);

    /**
     * 跟新跟随者数量
     * @param userId
     * @param fans
     */
    @Modifying
    @Query("update User  set followcount = followcount +?2 where id=?1")
    void updateFollowFans(String userId, int fans);
}
