package com.ssnyapp.wxapp.service;

import com.ssnyapp.wxapp.domain.JobFairDO;

import java.util.List;
import java.util.Map;

/**
 * 现场招聘会
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-09 10:03:34
 */
public interface JobFairService {

	JobFairDO get(Integer id);

	List<JobFairDO> list(Integer limit);

	List<JobFairDO> listForWeek(Map<String,Object> map);
}
