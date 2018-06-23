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
import cn.sy.domain.TbUserOrder;

@Mapper
public interface TbUserOrderMapper {

    int insertOrder(TbUserOrder record);

    List<TbUserOrder> findAll();
    
    TbUserOrder findById(@Param("orderId") String orderId);
    
    List<TbUserOrder> findByUser(@Param("userId") String userId);
    
}
