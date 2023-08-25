package com.mkl.suaggerDemo.mapper;

import com.mkl.suaggerDemo.model.GradeInfo;
import com.mkl.suaggerDemo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    List<User> getUserAll();

    User getUserById(String id);

    int save(User user);

    int update(Map<String,Object> user);

    int delete(String id);

    int addGrade(GradeInfo gradeInfo);


    int ceshi(Map<String,Object> map);
}
