package cn.sy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.sy.domain.TbShopSharding;
import cn.sy.domain.TbUser;
import cn.sy.domain.User;

@Mapper
public interface TbShopShardingMapper {

    List<TbShopSharding> findAll();

    int findByShop(@Param("shopCode") String shopCode);
    
}
