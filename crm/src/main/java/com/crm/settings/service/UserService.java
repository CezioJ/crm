package com.crm.settings.service;

import com.crm.model.User;

import java.util.Map;

public interface UserService {
    User login(Map<String,Object> map);
}