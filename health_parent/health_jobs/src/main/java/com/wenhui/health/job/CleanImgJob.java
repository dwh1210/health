package com.wenhui.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wenhui.health.service.SetmealService;
import com.wenhui.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/9/30
 */
@Component
public class CleanImgJob {

    private static final Logger logger = LoggerFactory.getLogger(CleanImgJob.class);

    @Reference
    private SetmealService setmealService;

    @Scheduled(initialDelay = 3000,fixedDelay = 1800000)
    public void cleanImg(){
       logger.info("任务执行了");
        List<String> img7Niu = QiNiuUtils.listFile();
        logger.debug("七牛云上有{}张图片",null==img7Niu?0:img7Niu.size());
        List<String> imgInDB = setmealService.findImgs();
        logger.debug("数据库上有{}张图片",null==imgInDB?0:imgInDB.size());
        img7Niu.removeAll(imgInDB);
        logger.debug("要删除的图片有{}张",img7Niu.size());
        QiNiuUtils.removeFiles(img7Niu.toArray(new String[]{}));
        logger.debug("删除{}张图片成功",img7Niu.size());
    }
}
