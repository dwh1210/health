package com.wenhui.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wenhui.health.dao.CheckItemDao;
import com.wenhui.health.entity.PageResult;
import com.wenhui.health.entity.QueryPageBean;
import com.wenhui.health.entity.Result;
import com.wenhui.health.exception.MyException;
import com.wenhui.health.pojo.CheckItem;
import com.wenhui.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/9/24
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 查询分页
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //判断是否有查询条件
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //调用dao查询
        Page<CheckItem> checkItemPage = checkItemDao.findByCondition(queryPageBean.getQueryString());
        long total = checkItemPage.getTotal();//总记录数
        List<CheckItem> rows = checkItemPage.getResult();//分页结果集
        return new PageResult<CheckItem>(total, rows);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    /**
     * 更新
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public void deleteById(int id) {
        //判断是否被检查组使用
        int count = checkItemDao.findCountByCheckId(id);
        //count>0 被使用了，报错
        if(count > 0){
            throw  new MyException("该检查项被检查组使用了，不能删除");
        }
        // count=0 ,可以删除
            checkItemDao.deleteById(id);
    }


}
