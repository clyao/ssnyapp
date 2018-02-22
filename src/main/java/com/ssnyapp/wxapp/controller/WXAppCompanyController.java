package com.ssnyapp.wxapp.controller;

import com.ssnyapp.common.controller.BaseController;
import com.ssnyapp.common.utils.PageUtils;
import com.ssnyapp.common.utils.Query;
import com.ssnyapp.common.utils.R;
import com.ssnyapp.wxapp.domain.CompanyDO;
import com.ssnyapp.wxapp.service.CompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by clyao on 2017/10/24.
 */
@RequestMapping("/wxappCompany/company")
@Controller
public class WXAppCompanyController extends BaseController {

    @Autowired
    CompanyService companyService;

    @GetMapping()
    @RequiresPermissions("wxapp:companyList:companyList")
    public String company() {
        return "wxapp/company/list";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("wxapp:companyList:companyList")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<CompanyDO> companyDOList = companyService.list(query);
        int total = companyService.count(query);
        PageUtils pageUtils = new PageUtils(companyDOList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("wxapp:companyList:add")
    String add() {
        return "wxapp/company/add";
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequiresPermissions("wxapp:companyList:add")
    @PostMapping("/save")
    public R save(CompanyDO companyDO) {
        return R.error();
    }

    /**
     * 删除
     */
    @RequiresPermissions("wxapp:companyList:remove")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (companyService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 审核
     */
    @RequiresPermissions("wxapp:companyList:audit")
    @PostMapping("/audit")
    @ResponseBody
    public R audit(Long id) {
        CompanyDO companyDO = companyService.get(Integer.parseInt(String.valueOf(id)));
        companyDO.setState(1);
        companyService.update(companyDO);
        return R.ok();
    }

}
