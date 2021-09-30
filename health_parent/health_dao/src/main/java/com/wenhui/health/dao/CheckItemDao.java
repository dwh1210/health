package com.wenhui.health.dao;

import com.github.pagehelper.Page;
import com.wenhui.health.entity.PageResult;
import com.wenhui.health.entity.QueryPageBean;
import com.wenhui.health.pojo.CheckItem;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/9/24
 */
public interface CheckItemDao {
    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 条件查询
     * @param queryPageBean
     * @return
     */
    Page<CheckItem> findByCondition(String queeryString);

    CheckItem findById(int id);

    /**
     * 更新
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 根据检查项id查询是否被检查组使用
     * @param id
     * @return
     */
    int findCountByCheckId(int id);

    /**
     * 根据检查项id删除
     * @param id
     */
    void deleteById(int id);
}
