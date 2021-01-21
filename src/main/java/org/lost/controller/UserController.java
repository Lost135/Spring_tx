package org.lost.controller;

import org.lost.domain.Role;
import org.lost.domain.User;
import org.lost.service.RoleService;
import org.lost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "list")
    public ModelAndView list(){
        ModelAndView modelAndView = new ModelAndView();
        List<User> userList = userService.list();
        modelAndView.addObject("userList",userList);
        modelAndView.setViewName("user-list");
        return modelAndView;
    }

    @RequestMapping(value = "saveUI")
    public ModelAndView saveUI(){
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roleList = roleService.list();
        modelAndView.addObject("roleList",roleList);
        modelAndView.setViewName("user-add");
        return modelAndView;
    }

    @RequestMapping(value = "save")
    public String save(User user, Long[] roleIds ){
        userService.save(user,roleIds);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "del/{userId}")
    public String del(@PathVariable("userId") Long userId){
        userService.del(userId);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "login")
    public String login(String username, String password, HttpSession session){
        User user = userService.login(username,password);
        if(user != null){
            session.setAttribute("user",user);
            return "redirect:/index.jsp";
        }else {
            return "redirect:/login.jsp";
        }

    }


}
