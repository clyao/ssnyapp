package com.ssnyapp.wxapp.service.imp;

import com.ssnyapp.wxapp.dao.JobFairDao;
import com.ssnyapp.wxapp.domain.JobFairDO;
import com.ssnyapp.wxapp.service.JobFairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobFairServiceImpl implements JobFairService {
	@Autowired
	private JobFairDao jobFairMapper;

	@Override
	public JobFairDO get(Integer id) {
		return jobFairMapper.get(id);
	}

	@Override
	public List<JobFairDO> list(Integer limit){
		return jobFairMapper.list(limit);
	}

	@Override
	public List<JobFairDO> listForWeek(Map<String, Object> map) {
		return jobFairMapper.listForWeek(map);
	}


}
