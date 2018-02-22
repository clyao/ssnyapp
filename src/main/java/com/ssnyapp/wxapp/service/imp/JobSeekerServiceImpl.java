package com.ssnyapp.wxapp.service.imp;

import com.ssnyapp.wxapp.dao.JobSeekerDao;
import com.ssnyapp.wxapp.domain.JobSeekerDO;
import com.ssnyapp.wxapp.service.JobSeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {
	@Autowired
	private JobSeekerDao jobSeekerMapper;

	@Override
	public JobSeekerDO get(Integer id) {
		return jobSeekerMapper.get(id);
	}

	@Override
	public List<JobSeekerDO> list(Map<String,Object> map){
		return jobSeekerMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return jobSeekerMapper.count(map);
	}

	@Override
	public int save(JobSeekerDO jobSeekerDO) {
		return jobSeekerMapper.save(jobSeekerDO);
	}

	@Override
	public int update(JobSeekerDO jobSeekerDO) {
		return jobSeekerMapper.update(jobSeekerDO);
	}

	@Override
	public int remove(Long id) {
		return jobSeekerMapper.remove(id);
	}

}
