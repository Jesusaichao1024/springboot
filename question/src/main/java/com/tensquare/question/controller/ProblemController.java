package com.tensquare.question.controller;
import java.util.List;
import java.util.Map;

import com.tensquare.question.client.LabelClient;
import com.tensquare.question.client.impl.LabelClientImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.question.pojo.Problem;
import com.tensquare.question.service.ProblemService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	@Autowired
	private LabelClient labelClient;

	/**
	 * 根据标签id值查询数据
	 * @param labelId
	 * @return
	 */
	@RequestMapping("label/{labelId}")
	public Result findByLabelId(@PathVariable("labelId") String labelId) {

		return labelClient.findById(labelId);
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(StatusCode.OK,"查询成功",problemService.findAll(),true);
	}
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(StatusCode.OK,"查询成功",problemService.findById(id),true);
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) ,true);
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(StatusCode.OK,"查询成功",problemService.findSearch(searchMap),true);
    }

	/**
	 * 注入request
	 */
	@Autowired
	private HttpServletRequest request;

	/**
	 * 增加
	 *
	 * @param problem
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody Problem problem) {
		//判断是否登录
		Claims claims = (Claims) request.getAttribute("user_role");
		if (claims == null) {
			return new Result(StatusCode.ACCESSERROR, "权限不足", false);
		}
		problem.setId(claims.getId());
		problemService.add(problem);
		return new Result(StatusCode.OK, "增加成功", true);
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(StatusCode.OK,"修改成功",true);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(StatusCode.OK,"删除成功",true);
	}

	/**
	 * 最新的数据展示
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/newList/{labelid}/{page}/{size}", method = RequestMethod.GET)
	public Result newList(@PathVariable String labelid, @PathVariable Integer page, @PathVariable Integer size) {
		Page<Problem> list = problemService.newList(labelid, page, size);
		PageResult<Problem> result = new PageResult<>(list.getTotalElements(), list.getContent());
		return new Result(StatusCode.OK, "查询成功", result, true);
	}

	/**
	 * 最热问题的展示
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/hotList/{labelid}/{page}/{size}", method = RequestMethod.GET)
	public Result hotList(@PathVariable String labelid, @PathVariable Integer page, @PathVariable Integer size) {
		Page<Problem> list = problemService.hotList(labelid, page, size);
		PageResult<Problem> result = new PageResult<>(list.getTotalElements(), list.getContent());
		return new Result(StatusCode.OK, "查询成功", result, true);
	}

	/**
	 * 等待回答问题
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/waitList/{labelid}/{page}/{size}", method = RequestMethod.GET)
	public Result waitList(@PathVariable String labelid, @PathVariable Integer page, @PathVariable Integer size) {
		Page<Problem> list = problemService.waitList(labelid, page, size);
		PageResult<Problem> result = new PageResult<>(list.getTotalElements(), list.getContent());
		return new Result(StatusCode.OK, "查询成功", result, true);
	}
}
