package com.hedging.user.service;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dz.platform.system.util.Pager;
import com.hedging.user.entity.User;
import com.hedging.user.vo.ResetPasswordVo;
import com.hedging.user.vo.UserLoginVo;

/**
 * @author tianwenlong
 *
 */
public interface IUserService {

  /**
   * 用户登录
   */
  public User login(UserLoginVo userLoginVo);

  /**
   * 用户登录后处理
   */
  public Set<String> processLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response,
      User user);

  /**
   * 获取用户权限功能集
   */
  public Set<String> listFunctions(User user);

  /**
   * 新增
   */
  public User add(User userViewVo);

  /**
   * 修改
   */
  public User update(User userInfo);

  /**
   * 删除
   */
  public void delete(Long id);

  /**
   * 根据ID查找用户
   */
  public User find(Long userId);

  /**
   * 查询用户
   */
  public Pager<User> search(Pager<User> pager, User user);

  /**
   * 重置用户密码
   */
  public void modifyPassword(ResetPasswordVo resetPasswordVo);

  /**
   * 改变用户状态
   * 
   * <br>
   * 禁用 | 启用
   */
  public void changeStatus(Long userId, String status);

  /**
   * 更新自己的用户信息
   * 
   * @param userInfo
   * @return
   */
  public User updateMyInfo(User userInfo);

  /**
   * 根据用户名查找用户
   * 
   * @param username
   * @return
   */
  public User findByUsername(String username);

}
