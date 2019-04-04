package com.jesusaichao.processor;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/28
 * @TIME: 9:19
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.jesusaichao.processor
 */
@Component
public class UserProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        //获取用户名称 //*[@id="feedlist_id"]/li[1]/div/div[1]/h2/a
        String nickname = page.getHtml().xpath("//*[@id=\"uid\"]/text()").toString();
        //获取头像
        String image = page.getHtml().xpath("//*[@id=\"asideProfile\"]/div[1]/div[1]/a/img/@src").toString();
        if (nickname != null && image != null) {
            page.putField("nickname", nickname);
            page.putField("image", image);
        } else {
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setSleepTime(1000).setRetryTimes(5);
    }

}
