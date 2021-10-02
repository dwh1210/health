package com.wenhui.health.dao;

import com.github.pagehelper.Page;
import com.wenhui.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/9/29
 */
public interface SetmealDao {
    /**
     * 添加套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐与检查组的关系
     * @param setmealId
     * @param checkGroupId
     */
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkGroupId") Integer checkGroupId);

    /**
     * 条件查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findByCondition(String queryString);

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
     * 更新
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 删除套餐与检查组的关系
     * @param id
     */
    void deleteSetmealCheckGroup(Integer id);

    /**
     * 通过套餐id统计订单个数
     * @param id
     * @return
     */
    int findOrderCountBySetmealId(int id);

    /**
     * 通过id删除套餐
     * @param id
     */
    void deleteById(int id);

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
