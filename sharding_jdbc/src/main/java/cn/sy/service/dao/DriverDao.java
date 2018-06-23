package cn.sy.service.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DriverDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

//	public Driver findByPhoneNo(String phoneNo) {
//		return sqlSessionTemplate.selectOne("findByPhoneNo", phoneNo);
//	}

}
