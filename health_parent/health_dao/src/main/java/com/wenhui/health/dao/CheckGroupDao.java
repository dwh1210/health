package com.wenhui.health.dao;

import com.github.pagehelper.Page;
import com.wenhui.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/9/27
 */
public interface CheckGroupDao {
    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组与检查项的关系
     * @param checkGroupId
     * @param checkItemId
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemId") Integer checkItemId);

    /**
     * 条件查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     *
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsCheckGroupId(int id);

    void update(CheckGroup checkGroup);

    void deleteCheckGroupCheckItem(Integer id);

    /**
     * 通过检查组id，查询使用了这个检查组的套餐个数
     * @param id
     * @return
     */
    int findCountByCheckGroupId(int id);

    /**
     * 通过id删除检查组
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
