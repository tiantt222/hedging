package com.hedging.user.dao;

import java.util.List;

import com.dz.platform.system.dao.IBaseDao;
import com.hedging.user.entity.SystemInfo;

/**
 * 
 * @author Zhao JinMing
 *
 */
public interface ISystemInfoDao extends IBaseDao {

	String fetchCurrentVersion();

	SystemInfo findBySystemVersion(String systemVersion);

	List<SystemInfo> getAllSort();

}
