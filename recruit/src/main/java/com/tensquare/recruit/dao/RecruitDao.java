package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    /**
     * 查询最新职位列表(按创建爱你日期的降序排序) 状态为2
     * @param status
     * @return
     */
    List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String status);

    /**
     * 查询状态不为0并以创建时间降序排序的钱12条数据
     * @param status
     * @return
     */
    List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String status);
}
