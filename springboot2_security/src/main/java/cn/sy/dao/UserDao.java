package cn.sy.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import cn.sy.domain.MyUserDetails;
import cn.sy.domain.User;
import cn.sy.mapper.UserMapper;

@Repository
public class UserDao implements UserDetailsService {
	private static Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	@Autowired
	private DataSource ds;
	
	public User findByName(String name) {
		try {
			logger.info("getDatabaseProductName: " + ds.getConnection().getMetaData().getDatabaseProductName());
			logger.info("getDatabaseId: " + sqlSessionFactory.getConfiguration().getDatabaseId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userMapper.findByName(name);
	}
	
	public int count() {
		return userMapper.count();
	}
	
	public int insert(User user) {
		return userMapper.insert(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("username: " + username);
		User user = findByName(username);
		if(user==null) {
			logger.info("throw new UsernameNotFoundException username: " + username);
			throw new UsernameNotFoundException(username);
		}
		MyUserDetails myUserDetails = new MyUserDetails(user);
		
		myUserDetails.setMenus(findMenusByName(username));
		
		return myUserDetails;
	}
	
	public List<Map<String, String>> findMenusByName(String name) {
		List<Map<String, String>> menus = null;
		logger.info("findMenusByName: " + name);
		menus =  userMapper.findMenusByName(name);
		
		return menus;
	}
	

}
