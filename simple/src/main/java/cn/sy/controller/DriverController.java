package cn.sy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.dto.Driver;
import cn.sy.service.DriverService;


@RestController
public class DriverController {

	@Autowired
	DriverService driverService;
	
	/*
	 * request:
	 *     curl -i "http://localhost:8083/driver?phoneNo=13613035402"
	 * response:
	 *     Driver: id=0000287f77f411e5bfe0005056ab7a42 name=韩军 company=null phoneNo=null status=1 passwd=null
	 */
    @ResponseBody
    @RequestMapping("/driver")
    public String getDriverStr(
			@RequestParam(value="phoneNo", required=true) String phoneNo) {
    	
    	Driver d = null;
    	
    	System.out.println("DriverController getDriverStr. phoneNo=" + phoneNo);
    	try {
    		d=driverService.getDriver(phoneNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("DriverController getDriverStr. return: " + d);
    	return d.toString();
    }

	/*
	 * request:
	 *     curl -i "http://localhost:8083/driver.do?phoneNo=13613035402"
	 * response:
	 *     {"id":"0000287f77f411e5bfe0005056ab7a42","name":"韩军","company":null,"phoneNo":null,"status":"1","passwd":null}
	 */
    @ResponseBody
    @RequestMapping("/driver.do")
    public Driver getDriver(
			@RequestParam(value="phoneNo", required=true) String phoneNo) {
    	
    	Driver d = null;
    	
    	System.out.println("DriverController getDriver. phoneNo=" + phoneNo);
    	try {
    		d=driverService.getDriver(phoneNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("DriverController getDriver. return: " + d);
    	return d;
    }
}
