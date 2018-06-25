package cn.sy.service.dao;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import cn.sy.dto.Driver;

@Component
public interface DriverRepository extends Repository<Driver, Long> {

	Driver findByPhoneNo(String phoneNo);

}
