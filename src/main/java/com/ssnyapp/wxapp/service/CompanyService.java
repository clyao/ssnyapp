package com.ssnyapp.wxapp.service;

import com.ssnyapp.wxapp.domain.CompanyDO;

import java.util.List;
import java.util.Map;

/**
 * 现场招聘会企业
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-09 10:03:34
 */
public interface CompanyService {

	CompanyDO get(Integer id);
	
	List<CompanyDO> list(Map<String,Object> map);

	int count(Map<String,Object> map);

	int save(CompanyDO companyDO);

	int update(CompanyDO companyDO);

	int remove(Long id);
}
