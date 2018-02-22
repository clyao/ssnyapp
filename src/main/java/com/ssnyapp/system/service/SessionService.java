package com.ssnyapp.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssnyapp.system.domain.UserOnline;

@Service
public interface SessionService {
	List<UserOnline> list();
}
