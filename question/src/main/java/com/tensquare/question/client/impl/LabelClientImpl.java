package com.tensquare.question.client.impl;

import com.tensquare.question.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @Author: ztq161024zsc
 * @PACKAGE_NAME: com.tensquare.question.client.impl
 * @DATE: 2019/3/21
 * @TIME: 18:23
 * @PROJECT_NAME: tensquare_parent
 * @DAY_NAME_SHORT: 星期四
 */
@Component
public class LabelClientImpl implements LabelClient {

    @Override
    public Result findById(String id) {
        return new Result(StatusCode.ERROR, "熔断器断啦", false);
    }
}
