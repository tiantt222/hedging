package com.hedging.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hedging.system.exception.BusinessException;
import com.hedging.user.dao.ISystemInfoDao;
import com.hedging.user.entity.SystemInfo;
import com.hedging.user.service.ISystemInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Zhao JinMing
 *
 */
@Slf4j
@Service
public class SystemInfoService implements ISystemInfoService {

	@Resource
	private ISystemInfoDao systemInfoDao;

	@Override
	public void deleteSystemInfo(Long id) {
		systemInfoDao.delete(SystemInfo.class, id);
	}

	@Override
	public void addSystemInfo(SystemInfo systemInfo) {
		SystemInfo find = findBySystemVersion(systemInfo.getSystemVersion());
		if (find != null) {
			throw new BusinessException("版本号已存在");
		}
		String currentVersion = fetchCurrentVersion();
		if (!(systemInfo.getSystemVersion().compareTo(currentVersion) >= 1)) {
			throw new BusinessException("版本号小于当前版本");
		}
		String htmlEncode = htmlEncode(systemInfo.getUpdateLog());
		systemInfo.setUpdateLog(htmlEncode);
		systemInfo.setCreateTime(new Date());
		systemInfo.setUpdateTime(new Date());
		systemInfoDao.add(systemInfo);

	}

	private SystemInfo findBySystemVersion(String systemVersion) {
		return systemInfoDao.findBySystemVersion(systemVersion);
	}

	@Override
	public void updateSystemInfo(SystemInfo systemInfo) {
		systemInfo.setUpdateTime(new Date());
		String htmlEncode = htmlEncode(systemInfo.getUpdateLog());
		systemInfo.setUpdateLog(htmlEncode);
		systemInfoDao.update(systemInfo);

	}

	@Override
	public List<SystemInfo> searchAll() {
		return systemInfoDao.list(SystemInfo.class);
	}

	@Override
	public SystemInfo findById(Long id) {
		return systemInfoDao.find(SystemInfo.class, id);
	}

	@Override
	public String fetchCurrentVersion() {
		return systemInfoDao.fetchCurrentVersion();
	}

	@Override
	public List<SystemInfo> getAllSort() {
		List<SystemInfo> systemInfos = systemInfoDao.getAllSort();
		for (SystemInfo systemInfo : systemInfos) {
			String updateLog = systemInfo.getUpdateLog();
			updateLog = updateLog.replace("\n", "");
			systemInfo.setUpdateLog(updateLog);
		}
		return systemInfos;
	}

	@Override
	public String fetchCurrentLog() {
		SystemInfo systemVersion = null;
		try {
			String currentVersion = systemInfoDao.fetchCurrentVersion();
			systemVersion = systemInfoDao.findBySystemVersion(currentVersion);
			// 当点击页面右下角版本号显示版本信息时，偶尔会发生没有内容的情况，后发现是页面的打开时间当大于sql查询时间时，接口调用返回后，但页面还没打开
			Thread.sleep(10);
		} catch (InterruptedException e) {
			log.error("获取当前办法错误", e);
		}
		return systemVersion.getUpdateLog().replace("\n", "</br>");
	}

	public String htmlEncode(String string) {
		if (null == string || "".equals(string))
			return null;
		else {
			String result = string;
			result = result.replaceAll("&", "＆");
			result = result.replaceAll("<", "＜");
			result = result.replaceAll(">", "＞");
			result = result.replaceAll("\"", "＂");
			return (result.toString());
		}
	}
}