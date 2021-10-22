package com.siniia.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.google.gson.Gson;
import com.siniia.app.dbo.CartData;
import com.siniia.app.dbo.DonationData;
import com.siniia.app.dbo.LocationBasedShipper;
import com.siniia.app.dbo.NewsLetter;
import com.siniia.app.dbo.OrderDetails;
import com.siniia.app.dbo.PayPalAccountData;
import com.siniia.app.dbo.ShipperDetails;
import com.siniia.app.dbo.ShippingDetails;

public class OrderDAOImpl implements OrderDAO {

	private static final Log logger = LogFactory.getLog(OrderDAOImpl.class);

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	private Gson gson = new Gson();

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@PersistenceContext(unitName = "entityManager")
	protected EntityManager entityManager;

	private String GET_EXISTS_ORDERS = "SELECT * FROM `orders` WHERE `productId` = ? and `productStatus`=0 and `userId`=?";
	private String GET_EXISTS_CART_ORDERS = "SELECT * FROM `cart` WHERE `productId` = ? and `userId`=?";
	private String UPDATE_USER_ORDERS = "UPDATE orders set quantity=quantity+? where id=?";
	private String UPDATE_USER_CART_ORDERS = "UPDATE cart set quantity=quantity+? where id=?";
	private String UPDATE_USERS_CART_ORDERS = "UPDATE cart set quantity=quantity-? WHERE `productId` = ? and `userId`=?";
	private String DELETE_CART_ORDERS = "DELETE FROM cart WHERE `productId` = ? and `userId`=?";
	private String INSERT_ORDERS = "INSERT INTO `orders`( `productId`, `productStatus`, `userId`, `availableAddressId`, `deliveryAddressID`, `shipmentId`, `quantity`, `quantityTypeId`, `quantityPrice`, `deliveryStatus`, `createdDate`, `paymentDetails`, `paymentAmount`,`pinCode`, `Address1`, `Address2`, `Landmark`, `addressLat`, `addressLong`,`paymentType`,`shipmentObjectId`,`shipmentTrackingId`,`provider`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private String INSERT_CART_ORDERS = "INSERT INTO `cart`( `productId`, `userId`,`quantity`, `created_date`) VALUES (?,?,?,?);";
	private String UPDATE_ORDERS = "UPDATE `orders` SET `productStatus`=? WHERE `id`=?";
	private String GET_CART_COUNT_USERID = "select count(*) from cart where userId=?";
	private String CHECK_ORDERS = "SELECT * FROM `orders` WHERE `id`=?";
	private String UPDATE_ORDER_STATUS = "UPDATE `orders` SET deliveryStatus=? where id=?";
	private String SAVE_SHIPPER = "INSERT INTO `shippertable`(`country`, `shipperName`, `link`, `shipperContact`, `shipperLocation`, `createdDate`) VALUES (?,?,?,?,?,?)";
	private String SAVE_NEWSLETTER = "INSERT INTO `newsletter`( `userId`, `name`, `email`, `createdDate`) VALUES (?,?,?,?)";
	private String SAVE_PAYPALDATA = "INSERT INTO `paypalAccountsData`(`userId`, `payPalAccountId`, `createdDate`) VALUES (?,?,?)";
	private String SAVE_DONATIONDATA = "INSERT INTO `donationData`(`userId`, `paymentDetails`, `paymentAmount`, `createdDate`) VALUES (?,?,?,?)";
	private String UPDATE_ORDER_PAYOUT_STATUS = "UPDATE `orders` SET isPayoutDone=? where id=?";

	@Override
	public int saveOrder(OrderDetails order) throws DataAccessException {
		try {
			/*
			 * List<OrderDetails> lst = jdbcTemplate.query(GET_EXISTS_ORDERS,
			 * new Object[] { order.getProductId(), order.getUserId() }, new
			 * OrdersMapper()); if(lst!=null && lst.size()>0){ logger.info(">>"+
			 * lst.get(0).getId()+" \t >>"+ lst.get(0).getQuantity());
			 * if(lst.get(0).getQuantity()==0){
			 * jdbcTemplate.update(UPDATE_USER_ORDERS,0,lst.get(0).getId());
			 * return 0; }else{
			 * jdbcTemplate.update(UPDATE_USER_ORDERS,lst.get(0).getQuantity(),
			 * lst.get(0).getId()); return 0; } }else{
			 */
			PreparedStatementCreator creator = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement insert = con.prepareStatement(INSERT_ORDERS, Statement.RETURN_GENERATED_KEYS);
					insert.setInt(1, order.getProductId());
					insert.setInt(2, order.getProductStatus());
					insert.setLong(3, order.getUserId());
					insert.setInt(4, order.getAvailableAddressId());
					insert.setInt(5, 0);
					insert.setInt(6, order.getShipmentId());
					insert.setInt(7, order.getQuantity());
					insert.setInt(8, order.getQuantityTypeId());
					insert.setDouble(9, order.getQuantityPrice());
					insert.setString(10, order.getDeliveryStatus());
					insert.setTimestamp(11, new Timestamp(new Date().getTime()));
					insert.setString(12, order.getPaymentDetails());
					insert.setDouble(13, order.getPaymentAmount());
					insert.setString(14, order.getPinCode());
					insert.setString(15, order.getAddress1());
					insert.setString(16, order.getAddress2());
					insert.setString(17, order.getLandmark());
					insert.setString(18, order.getAddressLat());
					insert.setString(19, order.getAddressLong());
					insert.setString(20, order.getPaymentType());
					insert.setString(21, order.getShipmentObjectId());
					insert.setString(22, order.getShipmentTrackingId());
					insert.setString(23, order.getProvider());
					return insert;
				}
			};
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(creator, keyHolder);
			return keyHolder.getKey().intValue();
			// }
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public int saveCartOrders(CartData order) throws DataAccessException {
		try {
			List<CartData> lst = jdbcTemplate.query(GET_EXISTS_CART_ORDERS,
					new Object[] { order.getProductId(), order.getUserId() }, new CartMapper());
			if (lst != null && lst.size() > 0) {
				// logger.info(">>" + lst.get(0).getId() + " \t >>" +
				// lst.get(0).getQuantity());
				if (lst.get(0).getQuantity() == 0) {
					jdbcTemplate.update(UPDATE_USER_CART_ORDERS, 1, lst.get(0).getId());
					return 0;
				} else {
					jdbcTemplate.update(UPDATE_USER_CART_ORDERS, lst.get(0).getQuantity(), lst.get(0).getId());
					return 0;
				}
			} else {
				PreparedStatementCreator creator = new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement insert = con.prepareStatement(INSERT_CART_ORDERS,
								Statement.RETURN_GENERATED_KEYS);
						insert.setInt(1, order.getProductId());
						insert.setInt(2, order.getUserId());
						insert.setLong(3, order.getQuantity() == 0 ? 1 : order.getQuantity());
						insert.setTimestamp(4, new Timestamp(new Date().getTime()));
						return insert;
					}
				};
				KeyHolder keyHolder = new GeneratedKeyHolder();
				jdbcTemplate.update(creator, keyHolder);
				return keyHolder.getKey().intValue();
			}
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	private class OrdersMapper implements RowMapper<OrderDetails> {
		public OrderDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrderDetails order = new OrderDetails();
			order.setId(rs.getInt("id"));
			order.setAvailableAddressId(rs.getInt("availableAddressId"));
			order.setDeliveryAddressID(rs.getInt("deliveryAddressID"));
			order.setProductId(rs.getInt("productId"));
			order.setProductStatus(rs.getInt("productStatus"));
			order.setQuantity(rs.getInt("quantity"));
			order.setQuantityPrice(rs.getDouble("quantityPrice"));
			order.setQuantityTypeId(rs.getInt("quantityTypeId"));
			order.setShipmentId(rs.getInt("shipmentId"));
			order.setUserId(rs.getInt("userId"));
			order.setDeliveryStatus(rs.getString("deliveryStatus"));
			order.setCreatedDate(rs.getTimestamp("createdDate"));
			order.setAddress1(rs.getString("address1"));
			order.setAddress2(rs.getString("address2"));
			order.setLandmark(rs.getString("landmark"));
			order.setPinCode(rs.getString("pinCode"));
			order.setAddressLat(rs.getString("addressLat"));
			order.setAddressLong(rs.getString("addressLong"));
			order.setPaymentAmount(rs.getDouble("paymentAmount"));
			order.setPaymentDetails(rs.getString("paymentDetails"));
			order.setPaymentType(rs.getString("paymentType"));
			order.setShipmentStatus(rs.getString("shipmentStatus"));
			order.setShipmentTrackingId(rs.getString("shipmentTrackingId"));
			order.setShipmentObjectId(rs.getString("shipmentObjectId"));
			order.setIsPayoutDone(rs.getString("isPayoutDone"));
			order.setProvider(rs.getString("provider"));

			return order;
		}
	}

	private class CartMapper implements RowMapper<CartData> {
		public CartData mapRow(ResultSet rs, int rowNum) throws SQLException {
			CartData order = new CartData();
			order.setId(rs.getInt("id"));
			order.setProductId(rs.getInt("productId"));
			order.setQuantity(rs.getInt("quantity"));
			order.setUserId(rs.getInt("userId"));
			order.setCreated_date(rs.getTimestamp("created_date"));
			return order;
		}
	}

	@Override
	public List<CartData> getOrdersInCartByUserId(int userId) {
		try {
			List<CartData> orderData = jdbcTemplate.query("select * from cart where userId=?", new Object[] { userId },
					new CartMapper());
			if (orderData != null && orderData.size() > 0) {
				return orderData;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public List<OrderDetails> getOrdersByUserId(int userId) {
		try {
			List<OrderDetails> orderData = jdbcTemplate.query(
					"select * from orders where userId=? and productStatus != 1", new Object[] { userId },
					new OrdersMapper());
			if (orderData != null && orderData.size() > 0) {
				return orderData;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int updateOrdersByOrderId(int orderId) {
		try {
			return jdbcTemplate.update(UPDATE_ORDERS, 2, orderId);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	@Override
	public OrderDetails getOrdersByOrderId(int orderId) {
		try {
			List<OrderDetails> orderData = jdbcTemplate.query("select * from orders where id=? and productStatus != 1",
					new Object[] { orderId }, new OrdersMapper());
			if (orderData != null && orderData.size() > 0) {
				return orderData.get(0);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public long getCartCount(long userId) {
		try {
			long count = jdbcTemplate.queryForInt(GET_CART_COUNT_USERID, userId);
			return count;
		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	@Override
	public List<ShippingDetails> getShippersAvailable() {
		try {
			List<ShippingDetails> usr = (List<ShippingDetails>) jdbcTemplate.query("select * from shippingtable",
					new Object[] {}, new ShippingDetailsMapper());
			if (usr != null && usr.size() > 0) {
				return usr;
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	private class ShippingDetailsMapper implements RowMapper<ShippingDetails> {
		public ShippingDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			ShippingDetails order = new ShippingDetails();
			order.setId(rs.getInt("id"));
			order.setCreatedDate(rs.getTimestamp("createdDate"));
			order.setShipperContact(rs.getString("shipperContact"));
			order.setShipperDetails(rs.getString("shipperDetails"));
			order.setShipperId(rs.getInt("shipperId"));
			order.setShipperName(rs.getString("shipperName"));
			return order;
		}
	}

	@Override
	public int updateCartOrders(CartData order) throws DataAccessException {
		try {
			return jdbcTemplate.update(UPDATE_USERS_CART_ORDERS, order.getQuantity(), order.getProductId(),
					order.getUserId());

		} catch (Exception e) {
			logger.error(e, e);
		}
		return 0;
	}

	@Override
	public int deleteFromCart(CartData order) throws DataAccessException {
		try {
			return jdbcTemplate.update(DELETE_CART_ORDERS, order.getProductId(), order.getUserId());

		} catch (Exception e) {
			logger.error(e, e);
		}
		return 0;
	}

	@Override
	public int deleteFromCart(int productId, int userId) throws DataAccessException {
		try {
			return jdbcTemplate.update(DELETE_CART_ORDERS, productId, userId);

		} catch (Exception e) {
			logger.error(e, e);
		}
		return 0;
	}

	@Override
	public OrderDetails checkOrderExists(int id) {
		try {
			List<OrderDetails> orderData = jdbcTemplate.query("select * from orders where id=?", new Object[] { id },
					new OrdersMapper());
			if (orderData != null && orderData.size() > 0) {
				return orderData.get(0);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int updateOrderStatus(int orderId, String status) {
		try {
			return jdbcTemplate.update(UPDATE_ORDER_STATUS, status, orderId);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	@Override
	public List<LocationBasedShipper> getShippersByLocation(String country) {
		try {
			List<LocationBasedShipper> usr = (List<LocationBasedShipper>) jdbcTemplate.query(
					"select * from locationBasedShipper where LOWER(country) = '" + country + "'", new Object[] {},
					new LocationBasedShipperMapper());
			if (usr != null && usr.size() > 0) {
				return usr;
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	private class LocationBasedShipperMapper implements RowMapper<LocationBasedShipper> {
		public LocationBasedShipper mapRow(ResultSet rs, int rowNum) throws SQLException {
			LocationBasedShipper order = new LocationBasedShipper();
			order.setId(rs.getInt("id"));
			order.setCreatedDate(rs.getTimestamp("createdDate"));
			order.setCountry(rs.getString("country"));
			order.setShippers(rs.getString("shippers"));
			order.setShipperLink(rs.getString("shipperLink"));
			return order;
		}
	}

	@Override
	public List<LocationBasedShipper> getAllShippers() throws DataAccessException {
		try {
			List<LocationBasedShipper> lbs = (List<LocationBasedShipper>) jdbcTemplate
					.query("select * from locationBasedShipper", new Object[] {}, new LocationBasedShipperMapper());
			if (lbs != null && lbs.size() > 0) {
				return lbs;
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public List<String> getAllShippersCountries() throws DataAccessException {
		try {
			String sql = "select distinct country from locationBasedShipper";
			List<String> lbs = jdbcTemplate.queryForList(sql, String.class);
			/*
			 * (List<String>) jdbcTemplate.
			 * query("select distinct country from locationBasedShipper", new
			 * Object[] {}, new LocationBasedShipperMapper());
			 */
			if (lbs != null && lbs.size() > 0) {
				return lbs;
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public List<ShipperDetails> getAllShippersAvailable() {
		try {
			List<ShipperDetails> usr = (List<ShipperDetails>) jdbcTemplate.query("select * from shippertable",
					new Object[] {}, new ShipperDetailsMapper());
			if (usr != null && usr.size() > 0) {
				return usr;
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	private class ShipperDetailsMapper implements RowMapper<ShipperDetails> {
		public ShipperDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			ShipperDetails order = new ShipperDetails();
			order.setId(rs.getInt("id"));
			order.setCreatedDate(rs.getTimestamp("createdDate"));
			order.setShipperContact(rs.getString("shipperContact"));
			order.setShipperName(rs.getString("shipperName"));
			order.setCountry(rs.getString("country"));
			order.setLink(rs.getString("link"));
			order.setShipperLocation(rs.getString("shipperLocation"));
			return order;
		}
	}

	@Override
	public int saveShipper(ShipperDetails sD) {
		try {
			PreparedStatementCreator creator = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement insert = con.prepareStatement(SAVE_SHIPPER, Statement.RETURN_GENERATED_KEYS);
					insert.setString(1, sD.getCountry());
					insert.setString(2, sD.getShipperName());
					insert.setString(3, sD.getLink());
					insert.setString(4, sD.getShipperContact());
					insert.setString(5, sD.getShipperLocation());
					insert.setTimestamp(6, new Timestamp(new Date().getTime()));

					return insert;
				}
			};
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(creator, keyHolder);
			return keyHolder.getKey().intValue();
			// }
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public int saveNewsLetter(NewsLetter nl) {
		try {
			PreparedStatementCreator creator = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement insert = con.prepareStatement(SAVE_NEWSLETTER, Statement.RETURN_GENERATED_KEYS);
					insert.setInt(1, nl.getUserId());
					insert.setString(2, nl.getName());
					insert.setString(3, nl.getEmail());
					insert.setTimestamp(4, new Timestamp(new Date().getTime()));

					return insert;
				}
			};
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(creator, keyHolder);
			return keyHolder.getKey().intValue();
			// }
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public int savePayPalData(PayPalAccountData paypalData) {
		try {
			PreparedStatementCreator creator = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement insert = con.prepareStatement(SAVE_PAYPALDATA, Statement.RETURN_GENERATED_KEYS);
					insert.setInt(1, paypalData.getUserId());
					insert.setString(2, paypalData.getPayPalAccountId());
					insert.setTimestamp(3, new Timestamp(new Date().getTime()));

					return insert;
				}
			};
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(creator, keyHolder);
			return keyHolder.getKey().intValue();
			// }
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public int saveDonationData(DonationData donationData) {
		try {
			PreparedStatementCreator creator = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement insert = con.prepareStatement(SAVE_DONATIONDATA, Statement.RETURN_GENERATED_KEYS);
					insert.setInt(1, donationData.getUserId());
					insert.setString(2, donationData.getPaymentDetails());
					insert.setDouble(3, donationData.getPaymentAmount());
					insert.setTimestamp(4, new Timestamp(new Date().getTime()));

					return insert;
				}
			};
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(creator, keyHolder);
			return keyHolder.getKey().intValue();
			// }
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public List<ShipperDetails> getAllShippersAvailableByCountry(String country) {
		try {
			List<ShipperDetails> usr = (List<ShipperDetails>) jdbcTemplate.query(
					"select * from shippertable where lower(country)='" + country + "'", new Object[] {},
					new ShipperDetailsMapper());
			if (usr != null && usr.size() > 0) {
				return usr;
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public List<OrderDetails> getPayoutOrders() {
		try {
			List<OrderDetails> usr = (List<OrderDetails>) jdbcTemplate.query(
					"select * from orders where paymentType='pay_pal' and isPayoutDone='N'", new Object[] {},
					new OrdersMapper());
			if (usr != null && usr.size() > 0) {
				return usr;
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int updateOrderPayoutStatus(int orderId, String status) {
		try {
			return jdbcTemplate.update(UPDATE_ORDER_PAYOUT_STATUS, status, orderId);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	@Override
	public PayPalAccountData getPaypalAccountDataByUserId(long userId) {
		try {
			List<PayPalAccountData> usr = (List<PayPalAccountData>) jdbcTemplate.query(
					"select * from paypalAccountsData where userId=? order by id desc", new Object[] { userId },
					new PayPalAccountDataMapper());
			if (usr != null && usr.size() > 0) {
				return usr.get(0);
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e, e);
		}
		return null;
	}

	private class PayPalAccountDataMapper implements RowMapper<PayPalAccountData> {
		public PayPalAccountData mapRow(ResultSet rs, int rowNum) throws SQLException {
			PayPalAccountData order = new PayPalAccountData();
			order.setId(rs.getInt("id"));
			order.setCreatedDate(rs.getTimestamp("createdDate"));
			order.setPayPalAccountId(rs.getString("payPalAccountId"));
			order.setUserId(rs.getInt("userId"));
			return order;
		}
	}

	@Override
	public List<OrderDetails> getOrdersByStatus(String status, String date) {
		try {
			List<OrderDetails> orderData = jdbcTemplate.query("select * from orders where  `paymentAmount`!= 0 and createdDate like '" + date + "%'",
					new Object[] {  }, new OrdersMapper());
			if (orderData != null && orderData.size() > 0) {
				return orderData;
			}
		} catch (NoResultException nre) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public boolean updatePayOutStatus(long orderId, String ShipmentObjectId, String status) {
		try{
			int count = jdbcTemplate.update("Update orders set isPayoutDone='"+status+"' where id="+orderId+" and `shipmentObjectId` = '"+ShipmentObjectId+"'");
			if(count != 0)
				return true;
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e,e);
		}
		return false;
	}

}