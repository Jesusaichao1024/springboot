package com.tensquare.recruit.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import util.IdWorker;

import com.tensquare.recruit.dao.EnterpriseDao;
import com.tensquare.recruit.pojo.Enterprise;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Enterprise> findAll() {
        return enterpriseDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param enterprise
     * @param page
     * @param size
     * @return
     */
    public Page<Enterprise> findSearch(Enterprise enterprise, int page, int size) {
        Specification<Enterprise> specification = createSpecification(enterprise);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return enterpriseDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param enterprise
     * @return
     */
    public List<Enterprise> findSearch(Enterprise enterprise) {
        Specification<Enterprise> specification = createSpecification(enterprise);
        return enterpriseDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Enterprise findById(String id) {
        return enterpriseDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    public void add(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId() + "");
        enterpriseDao.save(enterprise);
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    public void update(Enterprise enterprise) {
        enterpriseDao.save(enterprise);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        enterpriseDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param enterprise
     * @return
     */
    private Specification<Enterprise> createSpecification(Enterprise enterprise) {

        return new Specification<Enterprise>() {

            @Override
            public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (!StringUtils.isEmpty(enterprise.getId())) {
                    Predicate predicateId = cb.like(root.get("id"), "%" + enterprise.getId() + "%");
                    predicateList.add(predicateId);
                }
                // 企业名称
                if (!StringUtils.isEmpty(enterprise.getName())) {
                    Predicate predicateName = cb.like(root.get("name"), "%" + enterprise.getName() + "%");
                    predicateList.add(predicateName);
                }
                // 企业简介
                if (!StringUtils.isEmpty(enterprise.getSummary())) {
                    Predicate predicateSummary = cb.like(root.get("summary"), "%" + enterprise.getSummary() + "%");
                    predicateList.add(predicateSummary);
                }
                // 企业地址
                if (!StringUtils.isEmpty(enterprise.getAddress())) {
                    Predicate predicateAddress = cb.like(root.get("address"), "%" + enterprise.getAddress() + "%");
                    predicateList.add(predicateAddress);
                }
                // 标签列表
                if (!StringUtils.isEmpty(enterprise.getLabels())) {
                    Predicate predicateLabels = cb.like(root.get("labels"), "%" + enterprise.getLabels() + "%");
                    predicateList.add(predicateLabels);
                }
                // 坐标
                if (!StringUtils.isEmpty(enterprise.getCoordinate())) {
                    Predicate predicateCoordinate = cb.like(root.get("coordinate"), "%" + enterprise.getCoordinate() + "%");
                    predicateList.add(predicateCoordinate);
                }
                // 是否热门
                if (!StringUtils.isEmpty(enterprise.getIshot())) {
                    Predicate predicateIsHot = cb.like(root.get("ishot"), "%" + enterprise.getIshot() + "%");
                    predicateList.add(predicateIsHot);
                }
                // LOGO
                if (!StringUtils.isEmpty(enterprise.getLogo())) {
                    Predicate predicateLogo = cb.like(root.get("logo"), "%" + enterprise.getLogo() + "%");
                    predicateList.add(predicateLogo);
                }
                // URL
                if (!StringUtils.isEmpty(enterprise.getUrl())) {
                    Predicate predicateUrl = cb.like(root.get("url"), "%" + enterprise.getUrl() + "%");
                    predicateList.add(predicateUrl);
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    /**
     * 查询当前的热度为1的公司
     * @return
     */
    public List<Enterprise> findByHot() {
        return  enterpriseDao.findByIshot("1");

    }

}
