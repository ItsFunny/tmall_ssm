package com.tmall.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tmall.vo.OrderDetailVO;

@Mapper
public interface OrderDetailDao
{
	/*
	 * 增
	 * 增加一个订单详情
	 */
//	@Insert("insert into order_detail (order_detail_id,order_id,product_id,product_name,product_price,product_quantity,product_pitcure_id,content,order_status,pay_status) values (#{orderDetailId},#{orderId},#{productId},#{productName},#{productPrice},#{productQuantity},#{productPitcureId},#{content},#{orderStatus},#{payStatus})")
	void save(OrderDetailVO orderDetail);
	
	/*
	 * 删
	 * 删除单个订单
	 */
	@Delete("delete from order_detail where order_detail_id=#{orderDetailId}")
	void deleteByOrderDetailId(String orderDetailId);
	/*
	 * 查
	 * 查找用户订单 先通过orderMaster查找用户所有订单,然后再查找orderDetail in 查询
	 * 查找用户所有类型的订单(已完成,未支付等等)
	 * 查找单个orderdetail
	 * 查找所有ordermaster的所有orderdetail订单 in查询
	 */
	List<OrderDetailVO> findUserAllOrderDetails(List<String> orderIds);
	List<OrderDetailVO> findUserAllOrderDetailsByStatusInIds(Map<String, Object>params);
	@Select("select * from order_detail where order_detail_id=#{orderDetailId}")
	OrderDetailVO findOneOrderDetail(String orderDetailId);
	List<OrderDetailVO>findAllDetailsWithAllOrderMasters(List<String>orderMasterIdList);
	
	/*
	 * 改
	 * 修改orderdetail的状态,若想通过orderId则需要另外再写一个,如果只需要orderDetailId更新,这个即可
	 * 批量修改orderDetail的状态,
	 */
//	@Update("update order_detail set order_status=#{orderStatus},pay_status=#{payStatus} where order_detail_id=#{orderDetailId}")
	void updateOrderDetail(OrderDetailVO orderDetail);
	void updateOrderDetailByOrderId(OrderDetailVO orderDetailVO);

}
