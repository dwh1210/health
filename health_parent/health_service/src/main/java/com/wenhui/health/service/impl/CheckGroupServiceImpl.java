package com.wenhui.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wenhui.health.dao.CheckGroupDao;
import com.wenhui.health.entity.PageResult;
import com.wenhui.health.entity.QueryPageBean;
import com.wenhui.health.exception.MyException;
import com.wenhui.health.pojo.CheckGroup;
import com.wenhui.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 邓文辉
 * @date 2021/9/27
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkItemIds
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        //先添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组的id
        Integer checkGroupId = checkGroup.getId();
        //遍历选中的检查项的ids数组
        if (checkItemIds != null) {
            //添加检查组与检查项的关系
            for (Integer checkItemId : checkItemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkItemId);
            }
        }
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<CheckGroup> checkGroupPage = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(checkGroupPage.getTotal(),checkGroupPage.getResult());
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 通过id查询选中的检查组id集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsCheckGroupId(id);
    }

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkItemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkItemIds) {
        //更新检查组
        checkGroupDao.update(checkGroup);
        //删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        //遍历添加新关系
        if (checkItemIds != null) {
            for (Integer checkItemId : checkItemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkItemId);
            }
        }
    }

    /**
     * 根据id删除检查组
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) throws MyException {
        //判断检查组是否被使用
        int count = checkGroupDao.findCountByCheckGroupId(id);
        //被使用了，就报错
        if (count > 0) {
            throw new MyException("该检查组被套餐被使用了，不能删除");
        }
        //没使用
        //先删除关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //再删除检查组
        checkGroupDao.deleteById(id);

    }

    /**
     * 查询所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }


}
