package cn.sy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.sy.domain.User;

@Mapper
public interface UserMapper {

	public User findByName(String name);
	
	public int count();
	
	public int insert(User user);
	
	public List<Map<String, String>> findMenusByName(String name);
	
}
