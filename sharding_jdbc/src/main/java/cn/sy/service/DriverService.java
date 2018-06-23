package cn.sy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.sy.service.dao.DriverDao;

@Service
public class DriverService {

	@Autowired
	private DriverDao driverDao;
//
//    public Driver getDriver(String phoneNo) {
//    	
//    	Driver d = null;
//    	
//    	System.out.println("DriverService getDriver start. phoneNo=" + phoneNo);
//    	try {
//    		d=driverDao.findByPhoneNo(phoneNo);
//    		//System.out.println(d);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	System.out.println("DriverService getDriver  end. return: " + d);
//    	return d;
//    }

}
