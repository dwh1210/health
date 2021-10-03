package com.wenhui.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wenhui.health.dao.SetmealDao;
import com.wenhui.health.entity.PageResult;
import com.wenhui.health.entity.QueryPageBean;
import com.wenhui.health.exception.MyException;
import com.wenhui.health.pojo.Setmeal;
import com.wenhui.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/9/29
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    /**
     * 添加套餐
     * @param setmeal
     * @param checkGroupIds
     * @return
     */
    @Override
    @Transactional
    public Integer add(Setmeal setmeal, Integer[] checkGroupIds) {
        //添加套餐
        setmealDao.add(setmeal);
        //获取套餐的id
        Integer setmealId = setmeal.getId();
        //遍历检查组的id，
        if (checkGroupIds != null) {
            for (Integer checkGroupId : checkGroupIds) {
                //添加套餐与检查组的关系
                setmealDao.addSetmealCheckGroup(setmealId,checkGroupId);
            }
        }
        //添加事务控制
        return setmealId;
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<Setmeal>(page.getTotal(), page.getResult());
    }

    /**
     * 通过id查询套餐信息
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    /**
     * 通过id查询选中的检查组id集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    /**
     * 更新套餐
     * @param setmeal
     * @param checkGroupIds
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkGroupIds) {
        //更新
        setmealDao.update(setmeal);
        //删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        //添加新关系
        if (checkGroupIds != null) {
            for (Integer checkGroupId : checkGroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(), checkGroupId);
            }
        }
    }

    /**
     * 根据id删除套餐
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) throws MyException {

        int count = setmealDao.findOrderCountBySetmealId(id);
        if (count > 0) {
            //订单被使用了
            throw new MyException("该订单被使用了，不能被删除");
        }
        //先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        //再删除套餐
        setmealDao.deleteById(id);
    }

    /**
     * 查询数据库中套餐的所有图片
     * @return
     */
    @Override
    public List<String> findImgs() {
        return setmealDao.findImgs();
    }

    /**
     * 查询所有套餐
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    /**
     * 查询详情套餐
     * @param id
     * @return
     */
    @Override
    public Setmeal findDetailById(int id) {
        return setmealDao.findDetailById(id);
    }
}
