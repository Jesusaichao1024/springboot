package com.sureal.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: ztq161024zsc
 * @PACKAGE_NAME: com.sureal.pojo
 * @DATE: 2019/3/20
 * @TIME: 17:04
 * @PROJECT_NAME: tensquare_parent
 * @DAY_NAME_SHORT: 星期三
 */
@Entity
@Table(name="tb_friend")
@IdClass(Friend.class)
public class Friend implements Serializable {

    @Id
    private String userid;//当前登陆者id
    @Id
    private String friendid;//当前好友的id

    private String islike;//是否互相关注（0单向。1双向）

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }
}
