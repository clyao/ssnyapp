package com.ssnyapp.common.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssnyapp.common.domain.TaskDO;
import com.ssnyapp.common.service.JobService;
import com.ssnyapp.common.utils.PageUtils;
import com.ssnyapp.common.utils.Query;
import com.ssnyapp.common.utils.R;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-26 20:53:48
 */
@Controller
@RequestMapping("/common/taskScheduleJob")
public class JobController extends BaseController{
	@Autowired
	private JobService taskScheduleJobService;

	@GetMapping()
	String TaskScheduleJob() {
		return "common/taskScheduleJob/taskScheduleJob";
	}

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<TaskDO> taskScheduleJobList = taskScheduleJobService.list(query);
		int total = taskScheduleJobService.count(query);
		PageUtils pageUtils = new PageUtils(taskScheduleJobList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	String add() {
		return "common/taskScheduleJob/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Long id, Model model) {
		TaskDO taskScheduleJob = taskScheduleJobService.get(id);
		model.addAttribute("TaskScheduleJob", taskScheduleJob);
		return "common/taskScheduleJob/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		TaskDO taskScheduleJob = taskScheduleJobService.get(id);
		return R.ok().put("taskScheduleJob", taskScheduleJob);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save(TaskDO taskScheduleJob) {
		if (taskScheduleJobService.save(taskScheduleJob) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody TaskDO taskScheduleJob) {
		taskScheduleJobService.update(taskScheduleJob);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	public R remove(Long id) {
		if (taskScheduleJobService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") Long[] ids) {
		taskScheduleJobService.batchRemove(ids);

		return R.ok();
	}

	@PostMapping(value = "/changeJobStatus")
	@ResponseBody
	public R changeJobStatus(Long id,String cmd ) {
		String label = "停止";
		if (cmd.equals("start")) {
			label = "启动";
		} else {
			label = "停止";
		}
		try {
			taskScheduleJobService.changeStatus(id, cmd);
			return R.ok("任务" + label + "成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok("任务" + label + "失败");
	}

}