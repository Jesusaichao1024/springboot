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
 * @TIME: 17:15
 * @PROJECT_NAME: tensquare_parent
 * @DAY_NAME_SHORT: 星期三
 */
@Entity
@Table(name = "tb_nofriend")
@IdClass(NoFriend.class)
public class NoFriend implements Serializable {
    @Id
    private String userid;
    @Id
    private String friendid;

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
}
