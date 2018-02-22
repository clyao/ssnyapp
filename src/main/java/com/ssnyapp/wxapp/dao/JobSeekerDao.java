package com.ssnyapp.wxapp.dao;

import com.ssnyapp.wxapp.domain.JobSeekerDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 现场招聘会求职者
 * @author clyao
 * @email clyao@163.com
 * @date 2017-11-02 16:17:48
 */
@Mapper
public interface JobSeekerDao {

	JobSeekerDO get(Integer id);
	
	List<JobSeekerDO> list(Map<String,Object> map);

	int count(Map<String,Object> map);

	int save(JobSeekerDO jobSeekerDO);

	int update(JobSeekerDO jobSeekerDO);

	int remove(Long id);

}
