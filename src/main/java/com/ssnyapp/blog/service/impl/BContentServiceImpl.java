package com.ssnyapp.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.ssnyapp.blog.dao.ContentDao;
import com.ssnyapp.blog.domain.ContentDO;
import com.ssnyapp.blog.service.BContentService;



@Service
public class BContentServiceImpl implements BContentService {
	@Autowired
	private ContentDao bContentMapper;
	
	@Override
	public ContentDO get(Long cid){
		return bContentMapper.get(cid);
	}
	
	@Override
	public List<ContentDO> list(Map<String, Object> map){
		return bContentMapper.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return bContentMapper.count(map);
	}
	
	@Override
	public int save(ContentDO bContent){
		return bContentMapper.save(bContent);
	}
	
	@Override
	public int update(ContentDO bContent){
		return bContentMapper.update(bContent);
	}
	
	@Override
	public int remove(Long cid){
		return bContentMapper.remove(cid);
	}
	
	@Override
	public int batchRemove(Long[] cids){
		return bContentMapper.batchRemove(cids);
	}
	
}
