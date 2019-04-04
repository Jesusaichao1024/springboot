package com.jesusaichao.pipeline;

import com.jesusaichao.dao.UserDao;
import com.jesusaichao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.DownloadUtil;
import util.IdWorker;

import java.io.IOException;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/28
 * @TIME: 9:35
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.jesusaichao.pipeline
 * @Decription: 入库类
 */
@Component
public class UserPipeline implements Pipeline {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private UserDao userDao;
    @Override
    public void process(ResultItems resultItems, Task task) {
        String nickname = resultItems.get("nickname");
        String image = resultItems.get("image");
        User user = new User();
        user.setId(String.valueOf(idWorker.nextId()));
        user.setNickname(nickname);
        String images = image.substring(image.lastIndexOf("/" + 1));
        user.setAvatar(images);
        userDao.save(user);
        //下载图片
        try {
            DownloadUtil.download(image, images, "E:\\AI\\photo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
