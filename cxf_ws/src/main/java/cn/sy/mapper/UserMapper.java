package cn.sy.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.sy.domain.User;

@Mapper
public interface UserMapper {

	public User findByName(String name);
	
	public int count();
	
	public int insert(User user);
	
}
