package com.group.dao;

import com.group.pojo.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User queryPersonByPersonName(String username);

    List<User> selectByPage(User user);

    User selectByUsername(String username);

}