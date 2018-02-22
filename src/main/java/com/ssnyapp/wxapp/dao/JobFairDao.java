package com.ssnyapp.wxapp.dao;

import com.ssnyapp.wxapp.domain.JobFairDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 现场招聘会
 * @author clyao
 * @email clyao@163.com
 * @date 2017-11-02 16:17:48
 */
@Mapper
public interface JobFairDao {

	JobFairDO get(Integer id);

	List<JobFairDO> list(Integer limit);

	List<JobFairDO> listForWeek(Map<String,Object> map);

}
