package cn.sy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.sy.domain.Shop;
import cn.sy.domain.TbShop;

@Mapper
public interface TbShopMapper {

    List<TbShop> findAll();
    
    TbShop findByShopCode(@Param("shopCode") String shopCode);

}
