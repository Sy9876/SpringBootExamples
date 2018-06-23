package cn.sy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sy.domain.Order;
import cn.sy.domain.OrderItem;
import cn.sy.domain.OrderWithItem;
import cn.sy.domain.OrderWithItemAndPrice;
import cn.sy.domain.ShopOrder;
import cn.sy.domain.ShopOrderItem;
import cn.sy.mapper.OrderMapper;

@Service
public class OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private ShopService shopService;

	private Map<Integer, String> userMap = new HashMap<Integer, String>();

	private Map<Integer, String> shopMap = new HashMap<Integer, String>();

	private static int order_id=0;
//	private int item_id=0;

	
	public List<Order> findAll() {
		List<Order> result = orderMapper.findAll();
		
		return result;
		
	}
	
	public Order findById(int order_id) {
		Order result = orderMapper.findById(order_id);
		
		return result;
		
	}
	
	
	public List<Order> findByUser(int user_id) {
		List<Order> result = orderMapper.findByUser(user_id);
		
		return result;
		
	}
	
	public List<ShopOrder> findByShop(int shop_id) {
		List<ShopOrder> result = orderMapper.findByShop(shop_id);
		
		return result;
		
	}
	
	public List<ShopOrder> findByShops(List<Integer> shopList) {
		List<ShopOrder> result = orderMapper.findByShops(shopList);
		
		return result;
		
	}
	
	public List<OrderWithItem> findOrderWithItem() {
		List<OrderWithItem> result = orderMapper.findOrderWithItem();
		
		return result;
		
	}

	public List<OrderWithItemAndPrice> findOrderWithPrice() {
		List<OrderWithItemAndPrice> result = orderMapper.findOrderWithPrice();
		
		return result;
		
	}

	/**
	 * 新建客户订单，花店订单
	 * @param user
	 * @param item
	 * @param shop
	 */
	@Transactional
    public void createOrder(int user, int item, int shop) {

    	int orderId = 0;
    	
    	
//    	System.out.println("OrderService createOrder ");
    	
    	orderId = newUserOrder(user, item);
    	newShopOrder(orderId, item, shop);
    	
    }

    
    /**
     * 新建客户订单，订单详情
     * @param user
     * @param item
     * @param shop
     */
    public int newUserOrder(int user, int item) {
    	
    	Order order = null;
    	OrderItem orderItem = null;
    	int order_id = nextOrderId();
    	int user_id = user;
    	int item_id = item;
    	
    	int ret = 0;
    	
    	System.out.println("OrderService create user order. "
				+ "  order_id=" + order_id + "  user_id=" + user_id + "  item_id=" + item_id);
    	
    	try {
    		order = new Order();
    		order.setOrder_id(order_id);
    		order.setUser_id(user_id);
    		order.setUser_name(userName(user_id));
    		ret = orderMapper.insertOrder(order);
    		
    		orderItem = new OrderItem();
    		orderItem.setOrder_id(order_id);
    		orderItem.setUser_id(user);
    		orderItem.setItem_name("商品" + (order_id % 10 + 1));
    		ret = orderMapper.insertOrderItem(orderItem);

		} catch (Exception e) {
			e.printStackTrace();
		}

//    	System.out.println("OrderService create user order end. return: " + order_id);
    	
    	return order_id;
    }

    
    
    /**
     * 新建花店订单，订单详情
     * @param user
     * @param item
     * @param shop
     */
    public void newShopOrder(int order_id, int item, int shop) {
    	
    	ShopOrder shopOrder = null;
    	ShopOrderItem shopOrrderItem = null;
    	int shop_id = shop;
    	int item_id = item;
    	
    	int ret = 0;
    	
//    	System.out.println("OrderService create shop order. "
//				+ "  order_id=" + order_id + "  shop_id=" + shop_id + "  item_id=" + item_id);
    	
    	try {
    		shopOrder = new ShopOrder();
    		shopOrder.setOrder_id(order_id);
    		shopOrder.setShop_id(shop_id);
    		shopOrder.setShop_name(shopName(shop_id));
    		ret = orderMapper.insertShopOrder(shopOrder);
    		
    		shopOrrderItem = new ShopOrderItem();
    		shopOrrderItem.setOrder_id(order_id);
    		shopOrrderItem.setShop_id(shop);
    		shopOrrderItem.setItem_name("商品_" + (order_id % 10 + 1));
    		ret = orderMapper.insertShopOrderItem(shopOrrderItem);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

//    	System.out.println("OrderService create shop order end. return: " + ret);
    }

    public String userName(int user_id) {
    	if(userMap.containsKey(user_id)) {
    		return userMap.get(user_id);
    	}
    	else {
    		String userName = userService.findById(user_id).getName();
    		userMap.put(user_id, userName);
    		return userName;
    	}
    }
    
    public String shopName(int shop_id) {
    	if(shopMap.containsKey(shop_id)) {
    		return shopMap.get(shop_id);
    	}
    	else {
    		String userName = shopService.findById(shop_id).getName();
    		shopMap.put(shop_id, userName);
    		return userName;
    	}

    }
    
    public int nextOrderId() {
//    	return ++order_id;
    	
    	if(this.order_id>0) {
    		this.order_id ++;
    	}
    	else {
        	int maxOrderId = 0;
        	List<Order> result = orderMapper.findAll();
        	for(Order order : result) {
        		if(maxOrderId<order.getOrder_id()) {
        			maxOrderId = order.getOrder_id();
        		}
        	}
        	
        	maxOrderId++;
        	this.order_id = maxOrderId;
    	}
    	
    	return this.order_id;
    }
    
}
