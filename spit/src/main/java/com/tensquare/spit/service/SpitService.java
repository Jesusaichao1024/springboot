package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * @author ztq161024zsc
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询所有的数据
     * @return
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据id值查询数据
     * @param id
     * @return
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     * 保存数据
     * @param spit
     */
    public void save(Spit spit){
        spit.set_id(String.valueOf(idWorker.nextId()));
        //完善字段值
        //发布日期
        spit.setPublishtime(new Date());
       /* spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数*/
        //状态
        spit.setState("1");
        //分析回复数和父节点的关系，A在B下面做了回复，B的id就是A的父节点，B里面的回复数加一。
        if(!StringUtils.isEmpty(spit.getParentid())){
            //有父节点，让父节点的回复数加1
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }

    /**
     * 更新数据
     * @param spit
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    /**
     * 分页查询数据
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentId(String parentid, int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return spitDao.findByParentid(parentid, pageable);
    }


    /**
     * 如果要使用如下原生mongo中的方法需要使用MongoTemplate
     * db.spit.update({_id:"2"},{$inc:{thumbup:NumberInt(1)}} )
     */
    public void addthumbup(String id){
        //使用spring data mongo的方式来自增
//        Spit spit = spitDao.findById(id).get();
//        spit.setThumbup(spit.getThumbup()+1);
//        spitDao.save(spit);

        //封装查询条件，也就是原始的api中第一个参数{_id:"2"}
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        //封装自增规则，也就是原始的api中第二个参数{$inc:{thumbup:NumberInt(1)}}
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }






}
