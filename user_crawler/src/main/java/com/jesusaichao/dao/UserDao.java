package com.jesusaichao.dao;

import com.jesusaichao.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/28
 * @TIME: 9:16
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.jesusaichao.dao
 */
public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
}
