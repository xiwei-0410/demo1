package com.mkl.suaggerDemo.service;

import com.mkl.suaggerDemo.model.User;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUserAll();

    User getUserById(String id);

    int save(User user);

    int update(Map<String,Object> user);

    int delete(String id);

    int addUserAndGrade(User user);

    int ceshi(Map<String,Object> map);
}
