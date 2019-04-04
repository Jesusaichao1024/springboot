package com.tensquare.recruit.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.recruit.pojo.Recruit;
import com.tensquare.recruit.service.RecruitService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * 控制器层
 * @author Administrator
 *招聘信息
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {

	@Autowired
	private RecruitService recruitService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(StatusCode.OK,"查询成功",recruitService.findAll(),true);
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(StatusCode.OK,"查询成功",recruitService.findById(id),true);
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
		Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
		return  new Result(StatusCode.OK,"分页查询成功",  new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()),true );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(StatusCode.OK,"条件查询成功",recruitService.findSearch(searchMap),true);
    }
	
	/**
	 * 增加
	 * @param recruit
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Recruit recruit  ){
		recruitService.add(recruit);
		return new Result(StatusCode.OK,"增加成功",true);
	}
	
	/**
	 * 修改
	 * @param recruit
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Recruit recruit, @PathVariable String id ){
		recruit.setId(id);
		recruitService.update(recruit);		
		return new Result(StatusCode.OK,"修改成功",true);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		recruitService.deleteById(id);
		return new Result(StatusCode.OK,"删除成功",true);
	}

	/**
	 * 查询前四个数据
	 * @return
	 */
	@RequestMapping(value = "/search/recommend", method = RequestMethod.GET)
	public Result findTop4ByStateOrderByCreatetimeDesc() {
		List<Recruit> list = recruitService.findTop4ByStateOrOrderByCreatetimeDesc("2");
		return new Result(StatusCode.OK, "查询前4个数据成功并排序", list, true);
	}

	/**
	 * 查询状态不为0并以创建时间降序排序的钱12条数据
	 *
	 * @return
	 */
	@RequestMapping(value = "/search/newList", method = RequestMethod.GET)
	public Result newList() {
		List<Recruit> list = recruitService.findTop12ByStateNotOrderByCreatetimeDesc("0");
		return new Result(StatusCode.OK, "查询状不为0的数据并降序排序前12条数据", list, true);
	}

}
