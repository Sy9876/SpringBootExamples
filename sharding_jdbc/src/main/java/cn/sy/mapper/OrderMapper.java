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

@Mapper
public interface OrderMapper {

    int insertOrder(Order record);

    int insertOrderItem(OrderItem record);

    int insertShopOrder(ShopOrder record);

    int insertShopOrderItem(ShopOrderItem record);

    List<Order> findAll();
    
    Order findById(@Param("order_id") int order_id);
    
    List<Order> findByUser(@Param("user_id") int user_id);
    
    List<ShopOrder> findByShop(@Param("shop_id") int shop_id);
    
    List<ShopOrder> findByShops(@Param("shopList") List<Integer> shopList);
    
    List<OrderWithItem> findOrderWithItem();
    
    List<OrderWithItemAndPrice> findOrderWithPrice();
}
