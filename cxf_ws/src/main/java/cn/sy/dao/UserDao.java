package cn.sy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.sy.domain.User;
import cn.sy.mapper.UserMapper;

@Repository
public class UserDao {

	@Autowired
	private UserMapper userMapper;
	
	public User findByName(String name) {
		return userMapper.findByName(name);
	}
	
	public int count() {
		return userMapper.count();
	}
	
	public int insert(User user) {
		return userMapper.insert(user);
	}
}
