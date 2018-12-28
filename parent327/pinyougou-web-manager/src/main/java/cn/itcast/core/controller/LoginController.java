package cn.itcast.core.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆管理
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    //获取当前登陆人
    @RequestMapping("/showName")
    public Map<String,Object> showName(){

        //使用安全框架获取当前登陆人(Session)
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,Object> map = new HashMap<>();
        map.put("username",name);
        //map.put("curTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        map.put("curTime",new Date());
        //new Date() : 当前时间 日期格式(英国:格林威志)    字符串 页面显示
        return map;

    }
}
