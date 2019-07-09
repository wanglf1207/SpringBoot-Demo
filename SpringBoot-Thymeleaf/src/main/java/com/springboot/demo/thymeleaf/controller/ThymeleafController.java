package com.springboot.demo.thymeleaf.controller;

import com.springboot.demo.thymeleaf.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ThymeleafController {

    @GetMapping("/index")
    public ModelAndView index() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("title","这是我的第一个thymleaf页面");
        mv.addObject("desc","坚持各种学习");

        Student student = new Student();
        student.setName("王利峰");
        student.setAge(35);
        student.setEmail("wanglf1207@163.com");

        mv.addObject("student",student);
        return mv;
    }
}
