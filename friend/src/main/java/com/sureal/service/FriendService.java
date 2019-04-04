package com.sureal.service;

import com.sureal.client.UserClient;
import com.sureal.dao.FriendDao;
import com.sureal.dao.NoFriendDao;
import com.sureal.pojo.Friend;
import com.sureal.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: ztq161024zsc
 * @PACKAGE_NAME: com.sureal.service
 * @DATE: 2019/3/20
 * @TIME: 17:21
 * @PROJECT_NAME: tensquare_parent
 * @DAY_NAME_SHORT: 星期三
 */
@Service
public class FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;
    @Autowired
    private UserClient userClient;

    /**
     * 添加好友
     *
     * @param userId
     * @param friendId
     */
    @Transactional
    public int addFriends(String userId, String friendId) {
        Integer count = friendDao.selectCount(userId, friendId);
        //如果已经是好友,就直接返回数据
        if (count > 0) {
            return 0;
        }
        //表中添加数据
        Friend friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");
        friendDao.save(friend);
        userClient.updateFansCount(userId, 1);
        userClient.updateFollowCount(friendId, 1);
        //对方喜欢 将喜欢设置1
        if (friendDao.selectCount(friendId, userId) == 0) {
            friendDao.updateIsLike(userId, friendId, "1");
            friendDao.updateIsLike(friendId, userId, "1");
        }
        return 1;
    }
    @Transactional
    public void addFriend(String userId, String friendId) {
        //判断当前用户是否为好友
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendId);
        if (friend != null) {
            throw new RuntimeException("不能重复添加");
        }
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");
        //保存数据
        friendDao.save(friend);
        //更新粉丝数和关注数
        userClient.updateFansCount(userId, 1);
        userClient.updateFollowCount(friendId, 1);
        Friend friends = friendDao.findByUseridAndFriendid(friendId, userId);
        if (friends != null) {
            friendDao.updateIsLike(userId, friendId, "1");
            friendDao.updateIsLike(friendId, userId, "1");
        }
    }

    /**
     * 删除好友
     * @param userId
     * @param friendId
     */
    @Transactional
    public void deleteFriend(String userId, String friendId) {
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendId);
        if (friend != null) {
            friendDao.delete(friend);
            //更新用户的粉丝数和好友的跟随者
            userClient.updateFollowCount(userId, -1);
            userClient.updateFansCount(friendId, -1);
            //把好友id值作为用户id值 把用户id值作为好友id值再次查询
            Friend friends = friendDao.findByUseridAndFriendid(friendId, userId);
            if (friends != null) {
                //解除好友的关系
                friendDao.updateIsLike(friendId, userId, "0");
            }
        }
    }

    /**
     * 拉黑好友
     * @param userId
     * @param friendId
     */
    public void addNoFriend(String userId, String friendId) {
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        //判断是否有数据
        if (noFriend != null) {
            throw new RuntimeException("不能重复拉黑");
        }
        //添加数据
        noFriend = new NoFriend();
        noFriend.setFriendid(friendId);
        noFriend.setUserid(userId);
        noFriendDao.save(noFriend);
    }
}
