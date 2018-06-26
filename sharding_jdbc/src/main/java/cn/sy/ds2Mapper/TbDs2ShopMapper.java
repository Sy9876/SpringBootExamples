package cn.sy.ds2Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.sy.domain.Shop;
import cn.sy.domain.TbShop;

@Mapper
public interface TbDs2ShopMapper {

    List<TbShop> findAll();
    
    TbShop findByShopCode(@Param("shopCode") String shopCode);

    List<TbShop> findByShopOrBranch(@Param("shopCode") String shopCode, @Param("branchCode") String branchCode);

}
