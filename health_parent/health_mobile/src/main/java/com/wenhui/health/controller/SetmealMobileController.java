package com.wenhui.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wenhui.health.constant.MessageConstant;
import com.wenhui.health.entity.Result;
import com.wenhui.health.pojo.Setmeal;
import com.wenhui.health.service.SetmealService;
import com.wenhui.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/10/2
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;
    @GetMapping("/getSetmeal")
    public Result getSetmeal(){
        //查询所有套餐
        List<Setmeal> list = setmealService.findAll();
        //页面要显示图片，拼接图片的完整信息
        list.forEach(s -> {
            s.setImg(QiNiuUtils.DOMAIN+s.getImg());
        });
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,list);
    }

    /**
     * 查询详情套餐
     */

    @GetMapping("/findDetailById")
    public Result findDetailById(int id){
        Setmeal setmeal = setmealService.findDetailById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
