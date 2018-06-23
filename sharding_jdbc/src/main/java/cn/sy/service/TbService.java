package cn.sy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sy.domain.TbProduct;
import cn.sy.domain.TbShop;
import cn.sy.domain.TbShopOrder;
import cn.sy.domain.TbUser;
import cn.sy.domain.TbUserOrder;
import cn.sy.mapper.TbProductMapper;
import cn.sy.mapper.TbShopMapper;
import cn.sy.mapper.TbShopOrderMapper;
import cn.sy.mapper.TbUserMapper;
import cn.sy.mapper.TbUserOrderMapper;

@Service
public class TbService {

	@Autowired
	private TbUserOrderMapper tbUserOrderMapper;
	
	@Autowired
	private TbShopOrderMapper tbShopOrderMapper;

	@Autowired
	private TbUserMapper tbUserMapper;

	@Autowired
	private TbShopMapper tbShopMapper;

	@Autowired
	private TbProductMapper tbProductMapper;

	Map<String, String> userMap = new HashMap<String, String>();

	Map<String, String> shopMap = new HashMap<String, String>();

	Map<String, String> productMap = new HashMap<String, String>();

	
	public List<TbUserOrder> findAllUserOrder() {
		List<TbUserOrder> result = tbUserOrderMapper.findAll();
		
		return result;
		
	}
	public TbUserOrder findByIdUserOrder(String orderId) {
		TbUserOrder result = tbUserOrderMapper.findById(orderId);
		
		return result;
		
	}
	public List<TbUserOrder> findByUserUserOrder(String userId) {
		List<TbUserOrder> result = tbUserOrderMapper.findByUser(userId);
		
		return result;
		
	}
	
	
	public List<TbShopOrder> findAllShopOrder() {
		List<TbShopOrder> result = tbShopOrderMapper.findAll();
		
		return result;
		
	}
	public TbShopOrder findByIdShopOrder(String orderId) {
		TbShopOrder result = tbShopOrderMapper.findById(orderId);
		
		return result;
		
	}
	public List<TbShopOrder> findByShopShopOrder(String shopId) {
		List<TbShopOrder> result = tbShopOrderMapper.findByShop(shopId);
		
		return result;
		
	}
	
	


	/**
	 * 新建客户订单，花店订单
	 * @param user
	 * @param item
	 * @param shop
	 */
	@Transactional
    public void createOrder(String userId, String itemId, String shopCode) {

    	String orderId = null;
    	
    	
//    	System.out.println("OrderService createOrder ");
    	
    	orderId = newUserOrder(userId, itemId);
    	newShopOrder(orderId, userId, itemId, shopCode);
    	
    }

    
    /**
     * 新建客户订单，订单详情
     * @param user
     * @param item
     * @param shop
     */
    public String newUserOrder(String user, String item) {
    	
    	TbUserOrder order = null;
    	String order_id = UUID.randomUUID().toString();
    	String user_id = user;
    	String item_id = item;
    	
    	int ret = 0;
    	
    	System.out.println("OrderService create user order. "
				+ "  order_id=" + order_id + "  user_id=" + user_id + "  item_id=" + item_id);
    	
    	try {
    		order = new TbUserOrder();
    		order.setOrderId(order_id);
    		order.setUserId(user_id);
    		order.setProductId(item);
    		order.setProductName(productName(item));
    		ret = tbUserOrderMapper.insertOrder(order);

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
    public void newShopOrder(String order_id, String userId, String item, String shop) {
    	
    	TbShopOrder shopOrder = null;
    	String shop_id = shop;
    	String item_id = item;
    	
    	int ret = 0;
    	
//    	System.out.println("OrderService create shop order. "
//				+ "  order_id=" + order_id + "  shop_id=" + shop_id + "  item_id=" + item_id);
    	
    	try {
    		shopOrder = new TbShopOrder();
    		shopOrder.setOrderId(order_id);
    		shopOrder.setShopCode(shop_id);
    		shopOrder.setShopName(shopName(shop_id));
    		shopOrder.setUserId(userId);
    		shopOrder.setProductId(item_id);
    		shopOrder.setProductName(productName(item_id));
    		ret = tbShopOrderMapper.insertOrder(shopOrder);
    		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

//    	System.out.println("OrderService create shop order end. return: " + ret);
    }

    public String userName(String user_id) {
    	if(userMap.containsKey(user_id)) {
    		return userMap.get(user_id);
    	}
    	else {
    		String userName = tbUserMapper.findById(user_id).getName();
    		userMap.put(user_id, userName);
    		return userName;
    	}
    }
    
    public String shopName(String shop_id) {
    	if(shopMap.containsKey(shop_id)) {
    		return shopMap.get(shop_id);
    	}
    	else {
    		String userName = tbShopMapper.findByShopCode(shop_id).getName();
    		shopMap.put(shop_id, userName);
    		return userName;
    	}

    }
    
    public String productName(String productId) {
    	if(productMap.containsKey(productId)) {
    		return productMap.get(productId);
    	}
    	else {
    		String userName = tbProductMapper.findById(productId).getName();
    		productMap.put(productId, userName);
    		return userName;
    	}

    }

    
	public List<TbUser> findAllUser() {
		List<TbUser> result = tbUserMapper.findAll();
		
		return result;
		
	}
    
	public List<TbShop> findAllShop() {
		List<TbShop> result = tbShopMapper.findAll();
		
		return result;
		
	}
    
	public List<TbProduct> findAllProduct() {
		List<TbProduct> result = tbProductMapper.findAll();
		
		return result;
		
	}
}
