package com.wenhui.health.service;

import com.wenhui.health.entity.PageResult;
import com.wenhui.health.entity.QueryPageBean;
import com.wenhui.health.entity.Result;
import com.wenhui.health.exception.MyException;
import com.wenhui.health.pojo.CheckItem;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/9/24
 */
public interface CheckItemService {
    /**
     * 查询所有的检查项
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 更新
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    void deleteById(int id) throws MyException;
}
