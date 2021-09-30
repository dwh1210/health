package com.wenhui.health.service;

import com.wenhui.health.entity.PageResult;
import com.wenhui.health.entity.QueryPageBean;
import com.wenhui.health.exception.MyException;
import com.wenhui.health.pojo.CheckGroup;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/9/27
 */
public interface CheckGroupService {
    /**
     * 添加检查组
     * @param checkGroup
     * @param checkItemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过id查询选中的检查组id集合
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsCheckGroupId(int id);

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkItemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkItemIds);

    /**
     * 根据id删除检查组
     * @param id
     */
    void deleteById(int id) throws MyException;

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
