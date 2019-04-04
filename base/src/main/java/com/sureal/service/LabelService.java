package com.sureal.service;

import com.sureal.dao.LabelDao;
import com.sureal.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jesusaichao
 * @Date 2019/3/11
 * @Time 11:18
 * @PackageName com.sureal.service
 * @Project_Name tensquare_parent
 * @Description
 */
@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有的标签数据
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据id值查询标签数据
     * @param id
     * @return
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 保存数据
     * @param label
     */
    public void save(Label label) {
        //使用雪花算法给id值赋值
        label.setId(String.valueOf(idWorker.nextId()));
        labelDao.save(label);
    }

    /**
     * 更新数据
     * @param label
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除数据
     * @param id
     */
    public void delete(String id){
        labelDao.deleteById(id);
    }

    /**
     * 创建查询条件对象
     * @param label
     * @return
     */
    private Specification<Label> generateCondition(Label label) {
        Specification<Label> specification = new Specification<Label>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/**
             * 拼接条件的地方
             * @param root  泛型是啥, root就是啥
             * @param query 查询的对象
             * @param criteriaBuilder  条件的创建对象
             * @return Predicate
             * 三个条件 labelName status recommend
             *         标签的名字 状态是否可用 推荐
             *         模糊查询         精确查询
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //创建集合保存条件
                ArrayList<Predicate> predicates = new ArrayList<>();
                //判断标签名字是否为空
                if (!StringUtils.isEmpty(label.getLabelname())) {
                    Predicate predicateName = criteriaBuilder.like(root.get("labelname"), "%" + label.getLabelname() + "%");
                    predicates.add(predicateName);
                }
                //判断是否是有效地
                if (!StringUtils.isEmpty(label.getState())) {
                    Predicate predicateStatus = criteriaBuilder.equal(root.get("state"), label.getState());
                    predicates.add(predicateStatus);
                }
                //判断是否推荐
                if (!StringUtils.isEmpty(label.getRecommend())) {
                    Predicate predicateRecommend = criteriaBuilder.equal(root.get("recommend"), label.getRecommend());
                    predicates.add(predicateRecommend);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return specification;
    }

    /**
     * 条件查询
     * @param label
     * @return
     */
    public List<Label> findCondition(Label label) {
        //创造查询条件
        Specification<Label> condition = generateCondition(label);
        //返回查询的结果
        return labelDao.findAll(condition);
    }

    /**
     * 分页查询条件
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> findPage(Label label, Integer page, Integer size) {
        Specification<Label> condition = generateCondition(label);
        PageRequest pageAble = PageRequest.of(page - 1, size);
        return labelDao.findAll(condition, pageAble);
    }
}
