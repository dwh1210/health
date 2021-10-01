package com.wenhui.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wenhui.health.constant.MessageConstant;
import com.wenhui.health.entity.Result;
import com.wenhui.health.pojo.OrderSetting;
import com.wenhui.health.service.OrderSettingService;
import com.wenhui.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 邓文辉
 * @date 2021/10/1
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    public static final Logger logger = LoggerFactory.getLogger(OrderSettingController.class);

    /**
     * 批量导入预约设置
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){
        //接受上传的文件，MultipartFile
        //调用POIUtils解析excel，list<String[]>
        try {
            List<String[]> orderInfoStringArrList = POIUtils.readExcel(excelFile);
            //将List<String[]>转换为，List<orderString>
            final SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> orderSettingList = orderInfoStringArrList.stream().map(orderInfoStringArr -> {
                OrderSetting os = new OrderSetting();
                String orderDateStr = orderInfoStringArr[0];//日期字符串
                try {
                    os.setOrderDate(sdf.parse(orderDateStr));
                } catch (ParseException e) {}
                os.setNumber(Integer.valueOf(orderInfoStringArr[1]));//最大预约数
                return os;
            }).collect(Collectors.toList());

            //调用服务导入
            orderSettingService.add(orderSettingList);
        } catch (IOException e) {
            logger.error("导入预约设置失败");
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }
    /**
     * 日历展示
     */
    @GetMapping("/getDateByMonth")
    public Result getDateByMonth (String month){
        //调用服务查询
        List<Map<String, Integer>> monthDate = orderSettingService.getDateByMonth(month);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,monthDate);
    }

    /**
     * 基于日历的预约设置
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        //调用服务来更新
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS );
    }
}
