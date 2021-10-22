package com.siniia.app.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.siniia.app.dbo.CartData;
import com.siniia.app.dbo.DonationData;
import com.siniia.app.dbo.LocationBasedShipper;
import com.siniia.app.dbo.NewsLetter;
import com.siniia.app.dbo.OrderDetails;
import com.siniia.app.dbo.PayPalAccountData;
import com.siniia.app.dbo.ProductImages;
import com.siniia.app.dbo.ShipperDetails;
import com.siniia.app.dbo.ShippingDetails;

public interface OrderDAO {

	public int saveOrder(OrderDetails order) throws DataAccessException;
	
	public List<CartData> getOrdersInCartByUserId(int userId);
	
	public List<OrderDetails> getOrdersByUserId(int userId);
	
	public int updateOrdersByOrderId(int orderId);
	
	public OrderDetails getOrdersByOrderId(int orderId);
	
	public int saveCartOrders(CartData order) throws DataAccessException;
	
	public long getCartCount(long userId) ;
	
	public List<ShippingDetails> getShippersAvailable();
	
	public List<ShipperDetails> getAllShippersAvailable();
	
	public int updateCartOrders(CartData order) throws DataAccessException;
	
	public int deleteFromCart(CartData order) throws DataAccessException;
	
	public int deleteFromCart(int productId ,int userId) throws DataAccessException;
	
	public OrderDetails checkOrderExists(int id);
	
	public int updateOrderStatus(int orderId, String status);
	
	public List<LocationBasedShipper> getShippersByLocation(String country);
	
	public int updateOrderPayoutStatus(int orderId, String status);
	
	public List<LocationBasedShipper> getAllShippers() throws DataAccessException;
	
	public List<String> getAllShippersCountries() throws DataAccessException;
	
	public int saveShipper(ShipperDetails shipperdetails);
	
	public int saveNewsLetter(NewsLetter newsletter);
	
	public int savePayPalData(PayPalAccountData paypalData);
	
	public int saveDonationData(DonationData donationData);
	
	public List<ShipperDetails> getAllShippersAvailableByCountry(String country);
	
	public List<OrderDetails> getPayoutOrders();
	
	public PayPalAccountData getPaypalAccountDataByUserId(long userId);
	
	public List<OrderDetails> getOrdersByStatus(String status, String date);
	
	public boolean updatePayOutStatus(long orderId,String ShipmentObjectId,String status);
}
