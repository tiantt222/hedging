package com.hedging.user.service;

import java.util.List;

import com.hedging.user.entity.SystemInfo;

/**
 * 系统信息
 * 
 * @author Zhao JinMing
 *
 */
public interface ISystemInfoService {

	void deleteSystemInfo(Long id);

	void addSystemInfo(SystemInfo systemInfo);

	void updateSystemInfo(SystemInfo systemInfo);

	List<SystemInfo> searchAll();

	SystemInfo findById(Long id);

	String fetchCurrentVersion();

	List<SystemInfo> getAllSort();

	String fetchCurrentLog();

}
