package com.hedging.user.dao;

import java.util.List;

import com.dz.platform.system.dao.IBaseDao;
import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.Function;
import com.hedging.user.entity.User;

/**
 * @author tianwenlong
 *
 */
public interface IUserDao extends IBaseDao {

  /**
   * 根据用户名查询
   * 
   * @param name
   * @return
   */
  public User findByName(String name);

  /**
   * 根据userId获取用户功能
   * 
   * @param userId
   * @return
   */
  public List<Function> listFunctionsByUserId(Long userId);

  /**
   * 条件分页查询
   * 
   * @param pager
   * @param user
   * @return
   */
  public Pager<User> search(Pager<User> pager, User user);

  /**
   * 根据用户名和类型查询
   * 
   * @param name
   * @param type
   * @return
   */
  public User findByNameAndType(String name, String type);
}
