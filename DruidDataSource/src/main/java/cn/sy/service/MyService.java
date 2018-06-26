package cn.sy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.sy.mapper.ds1.DS1Mapper;
import cn.sy.mapper.ds2.DS2Mapper;

@Service
public class MyService {

	@Autowired
	DS1Mapper ds1Mapper;

	@Autowired
	DS2Mapper ds2Mapper;

    public String getDs(String ds) {
    	
    	String result = null;
    	try {
    		if(ds==null || "1".equals(ds)) {
    			result = ds1Mapper.findAll();
    		}
    		else {
    			result = ds2Mapper.findAll();
    		}

		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("getDs  end. return: " + result);
    	return result;
    }

}
