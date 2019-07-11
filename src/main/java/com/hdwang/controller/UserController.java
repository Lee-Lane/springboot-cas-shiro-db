package com.hdwang.controller;

import com.alibaba.fastjson.JSONObject;
import com.hdwang.entity.User;
import com.hdwang.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by hdwang on 2017/6/15.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 日志（slf4j->logback）
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 返回text格式数据
     * @param id 主键id
     * @return 用户json字符串
     */
    @RequestMapping("/get/id/{id}")
    @ResponseBody
    public String getUserById(@PathVariable("id")String id){
        logger.info("request /user/get/id/{id}, parameter is "+id);
        User user = userService.getById(Integer.parseInt(id));
        return JSONObject.toJSONString(user);
    }

    /**
     * 返回json格式数据
     * @param number 编号
     * @return 用户
     */
    @RequestMapping("/get/number/{number}")
    @ResponseBody
    public User getUserByNumber(@PathVariable("number")String number){
        User user = userService.getByNumber(number);
        return user;
    }

    @RequestMapping("/add/{number}/{name}")
    @ResponseBody
    public String addUser(@PathVariable("number")String number,@PathVariable("name")String name){
        User user = new User();
        user.setNumber(number);
        user.setName(name);
        return String.valueOf(userService.addUser(user));
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public void getUserById(@PathVariable("id")int id){
        this.userService.deleteUserById(id);
    }

    @RequestMapping("/update/{id}/{number}/{name}")
    @ResponseBody
    public User updateUser(@PathVariable("id")int id, @PathVariable("number")String number, @PathVariable("name")String name,boolean throwEx){
        User user = new User();
        user.setId(id);
        user.setNumber(number);
        user.setName(name);
        User userNew = null;
        try{
            userService.updateUser(user,throwEx);
        }catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        return userNew;
    }
}
