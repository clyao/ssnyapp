package com.ssnyapp.wxapp.service;

import com.ssnyapp.wxapp.domain.JobSeekerDO;

import java.util.List;
import java.util.Map;

/**
 * 现场招聘求职者
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-09 10:03:34
 */
public interface JobSeekerService {

	JobSeekerDO get(Integer id);
	
	List<JobSeekerDO> list(Map<String,Object> map);

	int count(Map<String,Object> map);

	int save(JobSeekerDO jobSeekerDO);

	int update(JobSeekerDO jobSeekerDO);

	int remove(Long id);
}
