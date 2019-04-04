package com.tensquare.question.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.question.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**
     * hibernate支持hql语句
     */
    /**
     * 查询最新的数据根据更新时间的降序排序(最新回答列表)
     * @param labelid
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem  where id in (select problemid from  tb_pl where labelid = ?) order by updatetime desc", nativeQuery = true)
    Page<Problem> newList(String labelid, Pageable pageable);

    /**
     *热门回答列表
     * @param labelid
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem where id in (select problemid from tb_pl where labelid = ?) order by reply desc", nativeQuery = true)
    Page<Problem> hotList(String labelid, Pageable pageable);

    /**
     *等待回答列表
     * @param labelid
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem where id in (select problemid from tb_pl where labelid = ?) and reply = 0  order by createtime desc", nativeQuery = true)
    Page<Problem> waitList(String labelid, Pageable pageable);
}
