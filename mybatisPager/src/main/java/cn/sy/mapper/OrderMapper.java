package cn.sy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;

import cn.sy.domain.Order;

/**
 * 传入mybatis自带的RowBounds，可以使SQL带上limit。
 * 分页插件提供个一个更强大的PageRowBounds，此对象可以返回总数。
 * 
 * Page<T>是带分页信息的结果集，实现了LIST
 * 
 */
@Mapper
public interface OrderMapper {


//    List<Order> findAll(RowBounds rowBounds);
	List<Order> findAll(PageRowBounds rowBounds);
//	List<Order> findAll();
//	List<Order> findAll(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
    

	Page<Order> findByUserId(@Param("user_id") int user_id, PageRowBounds rowBounds);
    

}
