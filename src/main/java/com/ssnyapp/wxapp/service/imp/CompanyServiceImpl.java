package com.ssnyapp.wxapp.service.imp;

import com.ssnyapp.wxapp.dao.CompanyDao;
import com.ssnyapp.wxapp.domain.CompanyDO;
import com.ssnyapp.wxapp.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyDao companyMapper;

	@Override
	public CompanyDO get(Integer id) {
		return companyMapper.get(id);
	}


	@Override
	public List<CompanyDO> list(Map<String,Object> map){
		return companyMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return companyMapper.count(map);
	}

	@Override
	public int save(CompanyDO companyDO) {
		return companyMapper.save(companyDO);
	}

	@Override
	public int update(CompanyDO companyDO) {
		return companyMapper.update(companyDO);
	}

	@Override
	public int remove(Long id) {
		return companyMapper.remove(id);
	}

}
