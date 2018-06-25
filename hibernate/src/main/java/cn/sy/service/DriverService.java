package cn.sy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.sy.dto.Driver;
import cn.sy.service.dao.DriverRepository;

@Service
public class DriverService {

	@Autowired
	private DriverRepository driverRepository;

    public Driver getDriver(String phoneNo) {
    	
    	Driver d = null;
    	
    	System.out.println("DriverService getDriver start. phoneNo=" + phoneNo);
    	try {
    		d=driverRepository.findByPhoneNo(phoneNo);
    		//System.out.println(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("DriverService getDriver  end. return: " + d);
    	return d;
    }

}
