package cn.sy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.domain.User;
import cn.sy.service.UserService;


@RestController
public class UserController {

	@Autowired
	UserService userService;

    @ResponseBody
    @RequestMapping("/users")
    public List<User> users() {
		List<User> result = userService.findAll();
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/userOrUser")
    public List<User> userOrUser(int user1, int user2) {
		List<User> result = userService.findByIds(user1, user2);
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/user")
    public User users(int user_id) {
		User result = userService.findById(user_id);
		
		return result;
		
	}
    

}
