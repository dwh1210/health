package com.wenhui.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wenhui.health.constant.MessageConstant;
import com.wenhui.health.entity.PageResult;
import com.wenhui.health.entity.QueryPageBean;
import com.wenhui.health.entity.Result;
import com.wenhui.health.pojo.Setmeal;
import com.wenhui.health.service.SetmealService;
import com.wenhui.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 邓文辉
 * @date 2021/9/29
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    private  static final Logger logger = LoggerFactory.getLogger(SetmealController.class);

    /**
     * 套餐图片上传
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        //获取文件名，获取它的后缀名
        String originalFilename = imgFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //产生唯一的名称，拼接后缀名，图片名
        String imgName = UUID.randomUUID().toString() + suffix;
        try {
            //调用QiNiuUtils上传
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),imgName);
            //成功后返回图片名和域名
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("imgName",imgName);
            resultMap.put("domain",QiNiuUtils.DOMAIN);
            return new Result(true,MessageConstant.UPLOAD_SUCCESS,resultMap);
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error("上传文件失败",e);
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }

    }

    /**
     * 添加套餐
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {
        //调用服务来添加
        setmealService.add(setmeal, checkGroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEAL_FAIL,pageResult);
    }

    /**
     * 通过id查询套餐信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Setmeal setmeal = setmealService.findById(id);
        Map<String, Object> resultMap = new HashMap<String, Object>(2);
        resultMap.put("setmeal",setmeal);
        resultMap.put("domain",QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,resultMap);
    }

    /**
     * 通过id查询选中的检查组id集合
     */
    @GetMapping("findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(int id){
        List<Integer> ids = setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,ids);
    }

    /**
     * 更新套餐
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkGroupIds){
        //调用服务更新
        setmealService.update(setmeal, checkGroupIds);
        return new Result(true,"更新套餐成功");
    }

    /**
     * 通过id删除
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        setmealService.deleteById(id);
        return new Result(true,"删除套餐成功");
    }
}
