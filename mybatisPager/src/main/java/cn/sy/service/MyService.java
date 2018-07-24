package cn.sy.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;

import cn.sy.domain.Order;
import cn.sy.mapper.OrderMapper;

@Service
public class MyService {

	@Autowired
	OrderMapper orderMapper;

    public String getDs(String ds) {
    	
//    	RowBounds rowBounds = new RowBounds(0, 10);
    	PageRowBounds rowBounds = new PageRowBounds(1, 10);
    	
//    	List<Order> result = null;
    	Page<Order> result = null;
    	try {
//			result = orderMapper.findAll();
//    		result = orderMapper.findAll(2, 10);
//    		result = orderMapper.findAll(rowBounds);
    		int userId = 10;
    		result = orderMapper.findByUserId(userId, rowBounds);
    		
    		System.out.println("got PageRowBounds: " + rowBounds.getTotal());
//    		if(result instanceof Page) {
    			Page p = (Page)result;
    			System.out.println("got PageInfo: "
    					+ " getTotal: " + p.getTotal()
    					+ " getPages: " + p.getPages()
    					+ " getSize: " + p.getPageNum()
    			);
//    		}
    		

		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("getDs  end. return: " + result.size());
    	return "OK";
    }

}
