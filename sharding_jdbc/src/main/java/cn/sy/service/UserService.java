package cn.sy.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sy.domain.User;
import cn.sy.mapper.UserMapper;
import io.shardingjdbc.core.api.HintManager;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public List<User> findAll() {
		List<User> result = userMapper.findAll();
		
		return result;
		
	}
	
	public List<User> findByIds(int user_id1, int user_id2) {
		
		// no effect
//		HintManager hintManager = HintManager.getInstance();
//		hintManager.addDatabaseShardingValue("t_user", "id", 1L);
//		hintManager.addTableShardingValue("t_user", "id", 1L);
		
		List<User> result = userMapper.findByIds(user_id1, user_id2);
		
		return result;
		
	}
	
	public User findById(int user_id) {
		User result = userMapper.findById(user_id);
		
		return result;
		
	}
	
}
