package com.wxw.suaggerDemo.controller;

import com.wxw.suaggerDemo.model.User;
import com.wxw.suaggerDemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;


@Api(description = "用户操作接口")
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService service;

    @ApiOperation(value = "获取用户", notes="获取用户信息")
    @RequestMapping(value = "getUserAll", method= RequestMethod.GET)
    @ResponseBody
    public List<User> getUserAll() {
        return service.getUserAll();
    }

    @ApiOperation(value = "获取用户单条数据")
    @ApiImplicitParam(name = "id", value = "主键id", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value = "getUserById", method= RequestMethod.GET)
    @ResponseBody
    public User getUserById(@RequestParam(value = "id") String id) {
        return service.getUserById(id);
    }

    @ApiOperation(value = "添加用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "imgUrl", value = "图片地址", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "saveUser", method= RequestMethod.POST)
    @ResponseBody
    public int saveUser(User user) {
        return service.save(user);
    }


    @ApiOperation(value = "修改用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "phone", value = "电话", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", paramType = "query", required = true, dataType = "int"),
    })
    @RequestMapping(value = "updateUser", method= RequestMethod.PUT)
    @ResponseBody
    public int updateUser(int id, String phone,String name,String sex,int age) {
        Map<String,Object> user = new HashMap<>();
        user.put("id",id);
        user.put("phone",phone);
        user.put("name",name);
        user.put("sex",sex);
        user.put("age",age);
        return service.update(user);
    }


    @ApiOperation(value = "删除用户数据")
    @ApiImplicitParam(name = "id", value = "主键id", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value = "deleteUser", method= RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> deleteUser(String  id) {
        Map<String,Object> map = new HashMap<>();
        if(service.delete(id)>0){
            map.put("state","200");
            map.put("msg","删除成功");
        }else {
            map.put("state","300");
            map.put("msg","删除失败");
        }
        return map;
    }


    /*********************************springboot事务管理***********************************/

    @RequestMapping("ceshiAop")
    @ResponseBody
    public Object ceshiAop(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("phone","1888888888");
        map.put("name","张无忌");
        return service.ceshi(map);
    }


}

