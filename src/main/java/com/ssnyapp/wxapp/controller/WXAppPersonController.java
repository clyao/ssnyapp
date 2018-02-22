package com.ssnyapp.wxapp.controller;

import com.ssnyapp.common.controller.BaseController;
import com.ssnyapp.common.utils.PageUtils;
import com.ssnyapp.common.utils.Query;
import com.ssnyapp.common.utils.R;
import com.ssnyapp.wxapp.domain.JobSeekerDO;
import com.ssnyapp.wxapp.service.JobSeekerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by clyao on 2017/10/24.
 */
@RequestMapping("/wxappPerson/person")
@Controller
public class WXAppPersonController extends BaseController {

    @Autowired
    JobSeekerService jobSeekerService;

    @GetMapping()
    @RequiresPermissions("wxapp:personList:personList")
    public String person() {
        return "wxapp/person/list";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("wxapp:personList:personList")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<JobSeekerDO> jobSeekerDOList = jobSeekerService.list(query);
        int total = jobSeekerService.count(query);
        PageUtils pageUtils = new PageUtils(jobSeekerDOList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("wxapp:personList:add")
    String add() {
        return "wxapp/person/add";
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequiresPermissions("wxapp:companyList:add")
    @PostMapping("/save")
    public R save(JobSeekerDO jobSeekerDO) {
        return R.error();
    }

    /**
     * 删除
     */
    @RequiresPermissions("wxapp:personList:remove")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (jobSeekerService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 审核
     */
    @RequiresPermissions("wxapp:personList:audit")
    @PostMapping("/audit")
    @ResponseBody
    public R audit(Long id) {
        JobSeekerDO jobSeekerDO = jobSeekerService.get(Integer.parseInt(String.valueOf(id)));
        jobSeekerDO.setState(1);
        jobSeekerService.update(jobSeekerDO);
        return R.ok();
    }

}
