package com.tensquare.user.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import util.IdWorker;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private IdWorker idWorker;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 根据用户的手机验证密码是否在正确
	 * @param user
	 * @return
	 */
	public User findByMobile(User user) {
		User userMobile = userDao.findByMobile(user.getMobile());
		if (StringUtils.isEmpty(user.getMobile())) {
			throw new NullPointerException("请输入手机号");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			throw new NullPointerException("请输入密码");
		}
		if (userMobile == null) {
			throw new IllegalArgumentException("手机号或者密码错误,请重新输入");
		}
		if (!passwordEncoder.matches(user.getPassword(), userMobile.getPassword())) {
			throw new IllegalArgumentException("手机号或者密码错误,请重新输入");
		}

			return userMobile;
	}

	/**
	 * 发送手机验证码
	 *
	 * @param mobile
	 */
	public void sendMsg(String mobile) {
		//生成6位短信验证码
		Random random = new Random();
		//最大数
		int max = 999999;
		//最小数
		int min = 100000;
		//随机生成数据
		int code = random.nextInt(max);
		if (code < min) {
			code = code + min;
		}
		System.out.println(mobile + "收到的验证码是:" + code);
		//存入缓存中
		redisTemplate.opsForValue().set("smscode_" + mobile, String.valueOf(code), 10, TimeUnit.MINUTES);
		//将验证码和手机号发送到rabbitMq中
		Map<String, String> map = new HashMap<>(16);
		map.put("mobile", mobile);
		map.put("code", String.valueOf(code));
		rabbitTemplate.convertAndSend("sms", map);
	}

	/**
	 * 注册用户
	 * @param user
	 * @param code
	 */
	public void add(User user, String code) throws ParseException {
		//判断验证码数据是否正确
		String sysCode = (String) redisTemplate.opsForValue().get("smscode_" + user.getMobile());
		//提取验证码数据
		if (sysCode == null) {
			throw new RuntimeException("请点击获取验证码数据");
		}
		if (!sysCode.equals(code)) {
			throw new RuntimeException("验证码输入不正确");
		}
		user.setId(String.valueOf(idWorker.nextId()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String registerDate = format.format(new Date());
		Date nowDate = format.parse(registerDate);
		//注册时间
		user.setRegdate(nowDate);
		//更新时间
		String updateDate = format.format(new Date());
		Date dateUpdate = format.parse(updateDate);
		user.setUpdatedate(dateUpdate);
		//最后登录时间
		String lastDate = format.format(new Date());
		Date dateLast = format.parse(lastDate);
		user.setLastdate(dateLast);
		//密码加密
		String encode = passwordEncoder.encode(user.getPassword());
		user.setPassword(encode);
		//保存数据
		userDao.save(user);
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
		//密码加密
		String encode = passwordEncoder.encode(user.getPassword());
		//保存数据库
		user.setPassword(encode);
		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	/**
	 * 增加粉丝数量
	 *
	 * @param userid
	 * @param fans
	 */
	@Transactional
	public void updateFansCount(String userid, int fans) {
		userDao.updateFansCount(userid, fans);
	}

	/**
	 * 更新跟随者数量
	 * @param userId
	 * @param fans
	 */
	@Transactional
	public void updateFollowFans(String userId, int fans) {
		userDao.updateFollowFans(userId, fans);
	}
}
