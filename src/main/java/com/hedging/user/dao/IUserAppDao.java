package com.hedging.user.dao;

import com.dz.platform.system.dao.IBaseDao;
import com.hedging.user.entity.UserApp;

/**
 * @author tianwenlong
 *
 */
public interface IUserAppDao extends IBaseDao {

	/**
	 * 根据appId查找
	 * 
	 * @param appId
	 * @return
	 */
	public UserApp findByAppId(String appId);

}
