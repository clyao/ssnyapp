package com.ssnyapp.wxapp.dao;

import com.ssnyapp.wxapp.domain.CompanyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 现场招聘会企业
 * @author clyao
 * @email clyao@163.com
 * @date 2017-11-02 16:17:48
 */
@Mapper
public interface CompanyDao {

	CompanyDO get(Integer id);
	
	List<CompanyDO> list(Map<String,Object> map);

	int count(Map<String,Object> map);

	int save(CompanyDO companyDO);

	int update(CompanyDO companyDO);

	int remove(Long id);

}
