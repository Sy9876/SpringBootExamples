package cn.sy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.sy.domain.User;

@Mapper
public interface UserMapper {

    List<User> findAll();
    
    List<User> findByIds(@Param("user_id1") int user_id1, @Param("user_id2") int user_id2);
    
    User findById(@Param("user_id") int user_id);
    
}
