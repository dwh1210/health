package com.wenhui.health.service;

import com.wenhui.health.entity.PageResult;
import com.wenhui.health.entity.QueryPageBean;
import com.wenhui.health.exception.MyException;
import com.wenhui.health.pojo.Setmeal;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/9/29
 */
public interface SetmealService {
    /**
     * 添加套餐
     * @param setmeal
     * @param checkGroupIds
     */
    void add(Setmeal setmeal, Integer[] checkGroupIds);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id查询套餐信息
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 通过id查询选中的检查组id集合
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     * 更新套餐
     * @param setmeal
     * @param checkGroupIds
     */
    void update(Setmeal setmeal, Integer[] checkGroupIds);

    /**
     * 根据id删除套餐
     * @param id
     */
    void deleteById(int id) throws MyException;

    /**
     * 查询数据库中套餐的所有图片
     * @return
     */
    List<String> findImgs();

    /**
     * 查询所有套餐
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 查询详情套餐
     * @param id
     * @return
     */
    Setmeal findDetailById(int id);
}
