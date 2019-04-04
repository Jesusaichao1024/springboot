package com.jesusaichao.task;

import com.jesusaichao.pipeline.UserPipeline;
import com.jesusaichao.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/28
 * @TIME: 9:42
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.jesusaichao.task
 */
@Component
public class UserTask {
    //缓存机制
    @Autowired
    private RedisScheduler redisScheduler;
    //入库类
    @Autowired
    private UserPipeline userPipeline;
    //爬取类
    @Autowired
    private UserProcessor userProcessor;

    @Scheduled(cron = "15 13 10 * * ?")
    public void userTask() {
        System.out.println("爬取用户");
        Spider spider = Spider.create(userProcessor);
        spider.addUrl("https://blog.csdn.net/nav/ai");
        spider.addPipeline(userPipeline);
        spider.setScheduler(redisScheduler);
        spider.run();
    }
}
