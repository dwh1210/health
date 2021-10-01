package com.wenhui.health.service;

import com.wenhui.health.exception.MyException;
import com.wenhui.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author 邓文辉
 * @date 2021/10/1
 */
public interface OrderSettingService {
    /**
     * 批量导入预约设置
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList) throws MyException;

    /**
     * 通过月份查询预约设置
     * @param month
     * @return
     */
    List<Map<String, Integer>> getDateByMonth(String month);

    /**基于日历的预约设置
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
