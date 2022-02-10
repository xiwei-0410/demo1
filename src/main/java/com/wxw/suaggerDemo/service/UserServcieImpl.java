package com.wxw.suaggerDemo.service;

import com.wxw.suaggerDemo.mapper.UserMapper;
import com.wxw.suaggerDemo.model.GradeInfo;
import com.wxw.suaggerDemo.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServcieImpl implements UserService{
    @Autowired
    private UserMapper mapper;


    @Override
    @Cacheable(value = "users")
    public List<User> getUserAll() {
        return mapper.getUserAll();
    }

    @Override
    @Cacheable(value = "users",key = "#id")
    public User getUserById(String id) {
        return mapper.getUserById(id);
    }

    @Override
    public int save(User user) {
        return mapper.save(user);
    }

    @Override
    public int update(Map<String,Object> user) {
        return mapper.update(user);
    }

    @Override
    public int delete(String id) {
        return mapper.delete(id);
    }

    @Override
    @Transactional(rollbackFor =Exception.class)//未检查异常
    public int addUserAndGrade(User user) {
        int num = 0;
        try{
            GradeInfo gradeInfo = new GradeInfo();
            gradeInfo.setGrade("一年级");
            mapper.addGrade(gradeInfo);
            System.out.println(gradeInfo.getId());
            user.setGradeId(gradeInfo.getId());
            num =  mapper.save(user);
        }catch (Exception e){
            num = 2;
            System.out.println("不操作======");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return num;
    }

    @Override
    public int ceshi(Map<String, Object> map) {
        return mapper.ceshi(map);
    }
}
