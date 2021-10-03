package com.wenhui.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wenhui.health.pojo.Setmeal;
import com.wenhui.health.service.SetmealService;
import com.wenhui.health.utils.QiNiuUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.aspectj.weaver.ast.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 邓文辉
 * @date 2021/10/3
 */
@Component
public class GenerateHtmlJob {

    private final Logger logger = LoggerFactory.getLogger(GenerateHtmlJob.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Reference
    private SetmealService setmealService;

    /**
     * 初始化方法
     */
    @PostConstruct
    public void init() {
        //设置模板路径
        freemarkerConfiguration.setClassForTemplateLoading(GenerateHtmlJob.class, "/ftl");
        //设置编码
        freemarkerConfiguration.setDefaultEncoding("utf-8");
    }

    @Value("${out_put_path}")
    private String out_put_path;//生成静态页面的路径

    @Scheduled(initialDelay = 3000,fixedDelay = 1800000)
    public void generateHtmlJob(){
        logger.info("任务启动了。。。。");
        Jedis jedis = jedisPool.getResource();
        String key = "setmeal:static:html";
        Set<String> jds = jedis.smembers(key);
        logger.debug("要处理的套餐id个数:{}",jds.size());
        jds.forEach(id ->{
           String[] idInfo = id.split("\\|");
           Integer setmealId= Integer.valueOf(idInfo[0]);//套餐id
            //操作类型
            String operationType = idInfo[1];
            String filename = out_put_path+File.separator+"setmeal_"+setmealId+".html";
            if ("1".equals(operationType)) {
                generateDetailHtml(setmealId,filename);
            }else if ("0".equals(operationType)) {
                logger.debug("删除静态页面{}",setmealId);
                new File(filename).delete();
            }
            //删除redis中对应的id值
            jedis.srem(key, id);
        });
        jedis.close();
    }

    private void generateSetmealList(){
        logger.debug("生成列表页面");
        String templateName = "mobile_setmeal.ftl";
        //构建详情数据
        List<Setmeal> list = setmealService.findAll();
        //拼接图片路径
        list.forEach(setmeal -> setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg()));
        Map<String, Object> dataMap = new HashMap<String,Object>();
        dataMap.put("setmealList",list);
        //存放的目录
        String filename = out_put_path+ File.separator + "mobile_setmealhtml";
        generateStaticHtml(filename, templateName, dataMap);
        logger.info("生成套餐列表页面成功");
    }

    /**
     * 生成详情页面
     * @param setmealId
     * @param filename
     */
    private void generateDetailHtml(Integer setmealId,String filename){
        logger.debug("生成详情静态页面{}",setmealId);
        String templateName = "mobile_setmeal_detail.ftl";
        //构建详情数据
        Setmeal setmeal = setmealService.findDetailById(setmealId);
        //拼接图片路径
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        Map<String, Object> dataMap = new HashMap<String,Object>();
        dataMap.put("setmeal",setmeal);
        generateStaticHtml(filename, templateName, dataMap);
        logger.info("生成详情静态页面成功");
    }
    /**
     * 生成页面
     * @param fileName
     * @param templateName
     * @param dataMap
     */
    public void generateStaticHtml(String fileName,String templateName,Map<String, Object> dataMap){
        try {
            //获取模板对象
            Template template = freemarkerConfiguration.getTemplate(templateName);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"));
            //给模板填充数据
            template.process(dataMap,writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("生成静态页面失败{}",fileName,e);
        }
    }
}
