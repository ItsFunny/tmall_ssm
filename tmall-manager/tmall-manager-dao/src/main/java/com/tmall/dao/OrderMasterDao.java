/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tmall.dto.OrderDTO;
import com.tmall.model.OrderMaster;

/**
 * @author Administrator
 *
 */
@Mapper
public interface OrderMasterDao
{
	@Insert("insert into order_master (order_id,buyer_name,buyer_phone,buyer_address,buyer_post,buyer_openid,order_amount,order_status,pay_status,store_id) values (#{orderId},#{buyerName},#{buyerPhone},#{buyerAddress},#{buyerPost},#{buyerOpenid},#{orderAmount},#{orderStatus},#{payStatus},#{storeId})")
	void save(OrderMaster orderMaster);
	
	/*
	 * 查
	 * 查找用户所有订单
	 * 查找用户ordermaster,条件查询type
	 * 查找用户待付款订单 条件:order_status=1 可舍弃
	 * 查找所有订单,包含所有所有用户
	 * 查找一个主订单
	 * 查找某店铺的所有订单,参数:storeId
	 * 
	 */
	@Select("select * from order_master where buyer_openid=#{openid} order by create_date desc ")
	List<OrderDTO> findUserOrderMastersByOpenid(String openid);
	@Select("select * from order_master where buyer_openid=#{openid} and pay_status=#{status} order by create_date desc")
	List<OrderDTO>findUserOrderMastersByOpenidAndType(@Param("openid")String openid,@Param("status")Integer status);
	
	@Select("select * from order_master where order_status=#{orderStatus} and pay_status=#{payStatus} and buyer_openid=#{openid} order by create_date desc")
	List<OrderDTO> findUserOrdersByOpenidAndType(@Param("orderStatus")Integer orderStatus,@Param("payStatus")Integer payStatus,@Param("openid")String openid);
	@Select("select * from order_master order by create_date desc")
	List<OrderMaster>findAllOrderMasters();
	@Select("select * from order_master where order_id=#{orderId} order by create_date desc")
	OrderMaster findOneOrderMaster(String orderId);
	
	@Select("select * from order_master where store_id=#{storeId} order by create_date desc")
	List<OrderMaster>findStoreOrderMasterByStoreId(Integer storeId);
	/**
	 * 通过paystaus查询ordermaster,获取相同父状态的master(等待退款和成功退款的父状态都为已取消)
	 * @param openid	用户的openid
	 * @param start	起始条件
	 * @param end	终止条件
	 * @return
	 */
	@Select("select * from order_master where buyer_openid=#{openid} and pay_status between #{start} and #{end} order by create_date desc")
	List<OrderDTO>findUserOrderMasterByOpenIdAndBetween(@Param("openid")String openid,@Param("start")Integer start,@Param("end")Integer end);
	/*
	 * 更新
	 * 更新ordermaster
	 */
	void updateOrderMaster(OrderMaster orderMaster);
	
}
