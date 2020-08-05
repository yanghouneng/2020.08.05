package com.group.service;

import com.group.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public interface UserService {
    String checkLogin(User user, String remember, HttpServletResponse response, HttpServletRequest request);

    User queryCookie(HttpServletRequest request);

    //查询所有用户
    HashMap<String,Object> queryAllUser(User user);

    User selectByUsername(String username);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);

    int delete(int userid);
}
