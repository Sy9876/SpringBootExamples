package cn.sy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.sy.domain.Shop;

@Mapper
public interface ShopMapper {

    List<Shop> findAll();
    
    Shop findById(@Param("shop_id") int shop_id);
    
    int insertShop(@Param("id") int shop_id, @Param("name") String shop_name);
      
}
