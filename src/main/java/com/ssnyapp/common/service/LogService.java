package com.ssnyapp.common.service;

import org.springframework.stereotype.Service;

import com.ssnyapp.common.domain.LogDO;
import com.ssnyapp.common.domain.PageDO;
import com.ssnyapp.common.utils.Query;
@Service
public interface LogService {
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
