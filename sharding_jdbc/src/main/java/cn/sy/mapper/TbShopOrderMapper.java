package cn.sy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.sy.domain.Order;
import cn.sy.domain.OrderItem;
import cn.sy.domain.OrderWithItem;
import cn.sy.domain.OrderWithItemAndPrice;
import cn.sy.domain.ShopOrder;
import cn.sy.domain.ShopOrderItem;
import cn.sy.domain.TbShopOrder;
import cn.sy.domain.TbUserOrder;

@Mapper
public interface TbShopOrderMapper {

    int insertOrder(TbShopOrder record);

    List<TbShopOrder> findAll();
    
    TbShopOrder findById(@Param("orderId") String orderId);
    
    List<TbShopOrder> findByShop(@Param("shopCode") String shopCode);
    
}
