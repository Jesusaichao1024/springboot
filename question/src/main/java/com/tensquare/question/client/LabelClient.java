package com.tensquare.question.client;

import com.tensquare.question.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: ztq161024zsc
 * @PACKAGE_NAME: com.tensquare.question.client
 * @DATE: 2019/3/20
 * @TIME: 15:24
 * @PROJECT_NAME: tensquare_parent
 * @DAY_NAME_SHORT: 星期三
 * @MINUTE: 24
 */

/**
 * 用于指定从哪个服务调用功能 不能包含下划线
 */
@FeignClient(value = "tensquare-base",fallback = LabelClientImpl.class)
public interface LabelClient {
    /**
     * 地址是被调用的映射地址 pathVariable必须置顶参数名称
     * @param labelId
     * @return
     */
    @RequestMapping(value = "label/{labelId}", method = RequestMethod.GET)
     Result findById(@PathVariable("labelId") String labelId);
}
