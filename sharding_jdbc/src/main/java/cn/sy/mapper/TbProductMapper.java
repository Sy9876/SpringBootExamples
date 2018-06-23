package cn.sy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.sy.domain.TbProduct;
import cn.sy.domain.TbUser;
import cn.sy.domain.User;

@Mapper
public interface TbProductMapper {

    List<TbProduct> findAll();

    TbProduct findById(@Param("id") String id);
    
}
