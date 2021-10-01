package com.wenhui.health.dao;

import com.wenhui.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 邓文辉
 * @date 2021/10/1
 */
public interface OrderSettingDao {
    /**
     * 通过日期查询预约设置表
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 更新最大预约数
     * @param orderSetting
     */
    void updateNumber(OrderSetting orderSetting);

    /**
     * 添加预约设置
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 通过月份查询预约设置
     * @param month
     * @return
     */
    List<Map<String, Integer>> getDateByMonth(String month);
}
