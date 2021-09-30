package com.wenhui.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wenhui.health.constant.MessageConstant;
import com.wenhui.health.entity.PageResult;
import com.wenhui.health.entity.QueryPageBean;
import com.wenhui.health.entity.Result;
import com.wenhui.health.pojo.CheckGroup;
import com.wenhui.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 邓文辉
 * @date 2021/9/27
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkItemIds){
        //调用服务添加检查组
        checkGroupService.add(checkGroup, checkItemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用服务查询
        PageResult<CheckGroup> PageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,PageResult);
    }

    /**
     * 通过id查询
     */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkGroup);
    }

    /**
     * 通过id查询选中的检查组id集合
     */
    @GetMapping("/findCheckItemIdsCheckGroupId")
    public Result findCheckItemIdsCheckGroupId(int id){
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsCheckGroupId(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
    }

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkItemIds){
        //调用服务更新检查组
        checkGroupService.update(checkGroup, checkItemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 根据id删除检查组
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        checkGroupService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 查询所有检查组
     */
    @GetMapping("findAll")
    public Result findAll(){
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
    }
}
