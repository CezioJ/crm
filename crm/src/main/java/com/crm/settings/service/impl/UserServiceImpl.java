package com.crm.settings.service.impl;

import com.crm.model.User;
import com.crm.settings.mapper.UserMapper;
import com.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(Map<String, Object> map) {
        User user = userMapper.selectUserByLogin(map);
        return user;
    }

    @Override
    public List<User> queryAllUsers() {
        return userMapper.selectAllUsers();
    }
}
