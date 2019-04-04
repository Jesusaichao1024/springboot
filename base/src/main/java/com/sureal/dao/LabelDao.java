package com.sureal.dao;

import com.sureal.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author Jesusaichao
 * @Date 2019/3/11
 * @Time 10:47
 * @PackageName com.sureal.dao
 * @Project_Name tensquare_parent
 * @Description
 */
public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {

}
