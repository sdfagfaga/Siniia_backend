package com.siniia.app.dao;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.siniia.app.dbo.DeviceTypeData;
import com.siniia.app.dbo.SearchedData;
import com.siniia.app.dbo.UserAddress;
import com.siniia.app.dbo.UserProfile;
import com.siniia.app.dbo.Version;

public class UserProfileDAOImpl implements UserProfileDAO {

	private static final Log logger = LogFactory.getLog(UserProfileDAOImpl.class);
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private String INSERT_USER_PROFILE = "insert into Users (email,name,mobileCountry,mobileNumber,created_date,isOTPVerified,isProfileComplete,notificationsCount,basketOrdersCount,otp,password) values (?,?,?,?,?,0,0,0,0,?,?)";

	private String INSERT_USER_PROFILE_LOGIN = "insert into Users (email,name,mobileCountry,mobileNumber,created_date,isOTPVerified,isProfileComplete,notificationsCount,basketOrdersCount,otp) values (?,?,?,?,?,0,0,0,0,?)";

	private String UPDATE_USER_PROFILE = "UPDATE Users set email=?,name=?,mobileCountry=?,mobileNumber=? where id=?";
	private String UPDATE_USER_PROFILE_DATA = "UPDATE Users set email=?,name=?,mobileCountry=?,mobileNumber=?,password=?,isOTPVerified=?,otp=? where id=?";

	private String UPDATE_USER_COUNTS = "UPDATE Users set notificationsCount=?,basketOrdersCount=? where id=?";

	private String GET_USER_BY_PHONE = "select * from Users where mobileNumber=? and mobileCountry = ?";

	private String GET_USER_BY_EMAIL = "select * from Users where email=?";

	private String GET_USER_BY_USERID = "select * from Users where id=?";

	private String GET_USER_ADDRESS_DETAILS_BY_USERID = "select * from user_address ua where ua.userId=? order by id desc";

	private String GET_USER_ADDRESS_DETAILS_BY_USER_ID = "select * from Users u inner join user_address ua on ua.userId=u.id where u.id=?";

	private String GET_USER_ADDRESS_BY_USERID = "select count(*) from user_address where userId=?";

	private String GET_EXISTS_USER = "select * from Users where mobileNumber=?";

	private String GET_EXISTS_USER_BY_EMAIL = "select * from Users where email=?";

	private String VERIFY_USER_OTP = "select id from Users where mobileNumber=? and mobileCountry =? and otp=?";

	private String VERIFY_USER_OTP_EMAIL = "select id from Users where email=? and otp=?";

	private String UPDATE_USER_OTP_VERIFIED = "update Users set isOTPVerified=? where mobileNumber=? and mobileCountry=?";

	private String UPDATE_USER_OTP_VERIFIED_EMAIL = "update Users set isOTPVerified=? where email=?";

	private String UPDATE_USER_OTP = "update Users set isOTPVerified=? where id=?";

	private String UPDATE_USER_OTP_GENERATED = "update Users set isOTPVerified=?,otp=? where id=?";

	private String UPDATE_USER_TYPE = " update Users set userType=? where id=?";

	private String GET_USER_TYPE = " select id from usertype_meta where lower(userType)=?";

	private String INSERT_USER_ADDRESS = "INSERT INTO user_address (`userId`, `nickName`, `pincode`, `Address1`, `Address2`, `Landmark`, `city`, `state`, `country`, `addressLat`, `addressLong`, `createdDate`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	private String INSERT_USER_ADDRESS_DATA = "INSERT INTO user_address (`userId`, `pincode`, `Address1`, `Address2`, `Landmark`, `city`, `state`, `country`, `addressLat`, `addressLong`, `createdDate`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

	private String UPDATE_USER_ADDRESS = "UPDATE user_address  SET  `nickName`=?, `pincode`=? , `Address1`=?, `Address2`=?, `Landmark`=?, `city`=?, `state`=?, `country`=?, `addressLat`=?, `addressLong`=? where `userId`=?";

	private String GET_USER_ADDRESS_DATA_ADDRRESS_ID = "SELECT * FROM `user_address` where id=? and userId=?";

	private String INSERT_SEARCH_DATA = "INSERT INTO `searchedData`(`userId`, `SearchString`,`createdDate`) VALUES (?,?,?)";

	private String INSERT_DEVICE_TYPE_DATA = "INSERT INTO `devicetypedata`(`userId`, `versionName`, `versionCode`, `deviceType`, `createdDate`) VALUES (?,?,?,?,?)";

	private String CHECK_EXISTS_USER = "SELECT * FROM `devicetypedata` WHERE `userId`=?";
	
	private String UPDATE_DEVICE_TYPE_DATA= "UPDATE `devicetypedata` SET `versionName`=?,`versionCode`=?,`deviceType`=?,`createdDate`=? WHERE `userId`=?";

	@Override
	public long registerUser(final UserProfile profile) throws DataAccessException {
		try {
			if (profile.getMobileNumber() != null) {
				List<UserProfile> lst = jdbcTemplate.query(GET_EXISTS_USER, new Object[] { profile.getMobileNumber() },
						new UserProfileMapper());
				if (lst == null || lst.size() == 0) {
					PreparedStatementCreator creator = new PreparedStatementCreator() {
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement insert = con.prepareStatement(INSERT_USER_PROFILE,
									Statement.RETURN_GENERATED_KEYS);
							insert.setString(1, profile.getEmail());
							insert.setString(2, profile.getName());
							insert.setString(3, profile.getMobileCountry());
							insert.setString(4, profile.getMobileNumber());
							insert.setTimestamp(5, new Timestamp(new Date().getTime()));
							insert.setInt(6, profile.getOtp());
							insert.setString(7, profile.getPassword());
							
							return insert;
						}
					};
					KeyHolder keyHolder = new GeneratedKeyHolder();
					jdbcTemplate.update(creator, keyHolder);
					return keyHolder.getKey().longValue();
				} else {
					return -1;
				}
			} else if (profile.getEmail() != null) {
				List<UserProfile> lst = jdbcTemplate.query(GET_EXISTS_USER_BY_EMAIL,
						new Object[] { profile.getEmail() }, new UserProfileMapper());
				if (lst == null || lst.size() == 0) {
					PreparedStatementCreator creator = new PreparedStatementCreator() {
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement insert = con.prepareStatement(INSERT_USER_PROFILE,
									Statement.RETURN_GENERATED_KEYS);
							insert.setString(1, profile.getEmail());
							insert.setString(2, profile.getName());
							insert.setString(3, profile.getMobileCountry());
							insert.setString(4, profile.getMobileNumber());
							insert.setTimestamp(5, new Timestamp(new Date().getTime()));
							return insert;
						}
					};
					KeyHolder keyHolder = new GeneratedKeyHolder();
					jdbcTemplate.update(creator, keyHolder);
					return keyHolder.getKey().longValue();
				} else {
					return -1;
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return 0;
	}

	@Override
	public UserProfile getUserByUserId(int userId) throws DataAccessException {
		List<UserProfile> lst = jdbcTemplate.query(GET_USER_BY_USERID, new Object[] { userId },
				new UserProfileMapper());
		if (lst != null && lst.size() > 0) {
			return lst.get(0);
		}
		return null;
	}

	@Override
	public List<UserProfile> getUserListByUserId(int userId) throws DataAccessException {
		List<UserProfile> lst = jdbcTemplate.query(GET_USER_BY_USERID, new Object[] { userId },
				new UserProfileMapper());
		if (lst != null && lst.size() > 0) {
			return lst;
		}
		return null;
	}

	private class UserProfileMapper implements RowMapper<UserProfile> {
		public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserProfile user = new UserProfile();
			if (rs.getString("email") != null && rs.getString("email").trim() != "") {
				user.setEmail(rs.getString("email"));
			} else {
				user.setEmail(null);
			}
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setProfilePicUrl(rs.getString("profilePicUrl"));
			user.setMobileNumber(rs.getString("mobileNumber"));
			user.setCreated_date(rs.getTimestamp("created_date"));
			user.setIsOTPVerified(rs.getInt("isOTPVerified"));
			user.setOtp(rs.getInt("otp"));
			user.setBasketOrdersCount(rs.getString("basketOrdersCount"));
			/*
			 * user.setDeviceType(rs.getString("deviceType"));
			 * user.setGcmId(rs.getString("gcmId"));
			 */
			user.setIsProfileComplete(rs.getInt("isProfileComplete"));
			user.setLastLocationLat(rs.getString("lastLocationLat"));
			user.setLastLocationLong(rs.getString("lastLocationLong"));
			user.setNotificationsCount(rs.getString("notificationsCount"));
			user.setMobileCountry(rs.getString("mobileCountry"));
			user.setPassword(rs.getString("password"));

			return user;
		}
	}

	private class IdMapper implements RowMapper<Integer> {
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getInt("id");
		}
	}

	@Override
	public void updateUserOtpVerified(String phoneNumber) throws DataAccessException {
		jdbcTemplate.update(UPDATE_USER_OTP_VERIFIED, "1", phoneNumber.split("-")[1], phoneNumber.split("-")[0]);
	}

	public String createRandomCode(int codeLength) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new SecureRandom();
		for (int i = 0; i < codeLength; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		System.out.println(output);
		return output;
	}

	/*
	 * @Override public List<Version> getAPKVersion() { // TODO Auto-generated
	 * method stub try { List<Version> lst =
	 * jdbcTemplate.query("Select * from version where status='Y'", new Object[]
	 * {}, new VersionMapper()); if (lst != null && lst.size() > 0) { return
	 * lst; }
	 * 
	 * } catch (Exception e) { // TODO: handle exception logger.error(e, e); }
	 * return null; }
	 */

	/*
	 * private class VersionMapper implements RowMapper<Version> { public
	 * Version mapRow(ResultSet rs, int rowNum) throws SQLException { Version
	 * user = new Version(); user.setId(rs.getInt("id"));
	 * user.setVersion(rs.getString("version"));
	 * user.setCreateddate(rs.getDate("createddate"));
	 * 
	 * return user; } }
	 */

	@Override
	public UserProfile getUserProfileByPhoneNumber(UserProfile phoneNumber) throws DataAccessException {
		List<UserProfile> lst = jdbcTemplate.query(GET_USER_BY_PHONE,
				new Object[] { phoneNumber.getMobileNumber(), phoneNumber.getMobileCountry() },
				new UserProfileMapper());
		if (lst != null && lst.size() > 0) {
			return lst.get(0);
		}
		return null;
	}

	@Override
	public UserProfile getUserProfileByEmail(UserProfile email) throws DataAccessException {
		List<UserProfile> lst = jdbcTemplate.query(GET_USER_BY_EMAIL, new Object[] { email.getEmail() },
				new UserProfileMapper());
		if (lst != null && lst.size() > 0) {
			return lst.get(0);
		}
		return null;
	}

	@Override
	public int getUserInfoByEmail(String phoneNumber, int otp) throws DataAccessException {
		List<Integer> lst = jdbcTemplate.query(VERIFY_USER_OTP_EMAIL, new Object[] { phoneNumber, otp },
				new IdMapper());
		if (lst != null && lst.size() > 0) {
			return lst.get(0);
		}
		return 0;
	}

	@Override
	public void updateUserOtpVerifiedByEmail(String phoneNumber) throws DataAccessException {
		jdbcTemplate.update(UPDATE_USER_OTP_VERIFIED_EMAIL, "1", phoneNumber);
	}

	@Override
	public void updateUserOtp(UserProfile userProfile) throws DataAccessException {
		try {
			if (userProfile.getEmail() != null) {
				jdbcTemplate.update(UPDATE_USER_OTP_VERIFIED_EMAIL, "1", userProfile.getEmail());
			} else if (userProfile.getMobileCountry() != null) {
				jdbcTemplate.update(UPDATE_USER_OTP, "1", userProfile.getId());
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public long updateUserType(String userId, String userType) {
		try {
			List<Integer> lst = jdbcTemplate.query(GET_USER_TYPE, new Object[] { userType }, new IdMapper());
			if (lst != null && lst.size() > 0) {
				return jdbcTemplate.update(UPDATE_USER_TYPE, lst.get(0), userId);
			} else {
				return 0;
			}
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public long updateAddress(UserProfile user) {
		try {
			int count = jdbcTemplate.queryForInt(GET_USER_ADDRESS_BY_USERID, user.getId());
			if (count == 0) {
				if (user.getAddress1() != null || user.getAddress2() != null || user.getAddressLat() != null
						|| user.getAddressLong() != null || user.getPinCode() != null || user.getCity() != null
						|| user.getState() != null || user.getCountry() != null || user.getLandmark() != null
						|| user.getId() != 0) {
					// `userId`, `nickName`, `pincode`, `Address1`, `Address2`,
					// `Landmark`, `city`, `state`, `country`, `addressLat`,
					// `addressLong`, `createdDate`
					return jdbcTemplate.update(INSERT_USER_ADDRESS, user.getId(), user.getNickName(), user.getPinCode(),
							user.getAddress1(), user.getAddress2(), user.getLandmark(), user.getCity(), user.getState(),
							user.getCountry(), user.getAddressLat(), user.getAddressLong(),
							new Timestamp(new Date().getTime()));
				} else {
					return -1;
				}
			} else {
				if (user.getAddress1() != null || user.getAddress2() != null || user.getAddressLat() != null
						|| user.getAddressLong() != null || user.getPinCode() != null || user.getCity() != null
						|| user.getState() != null || user.getCountry() != null || user.getLandmark() != null) {
					// UPDATE user_address SET `nickName`=?, `pincode`=? ,
					// `Address1`=?, `Address2`=?, `Landmark`=?, `city`=?,
					// `state`=?, `country`=?, `addressLat`=?, `addressLong`=?
					// where `userId`=?
					return jdbcTemplate.update(UPDATE_USER_ADDRESS, user.getNickName(), user.getPinCode(),
							user.getAddress1(), user.getAddress2(), user.getLandmark(), user.getCity(), user.getState(),
							user.getCountry(), user.getAddressLat(), user.getAddressLong(), user.getId());
				} else {
					return -1;
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}

	}

	@Override
	public int getUserInfo(String email, int otp) throws DataAccessException {
		List<Integer> lst = jdbcTemplate.query(VERIFY_USER_OTP,
				new Object[] { email.split("-")[1], email.split("-")[0], otp }, new IdMapper());
		if (lst != null && lst.size() > 0) {
			return lst.get(0);
		}
		return -1;
	}

	@Override
	public int updateUserProfile(UserProfile userProfile) throws DataAccessException {
		try {// email=?,name=?,mobileCountry=?,mobileNumber=?
			return jdbcTemplate.update(UPDATE_USER_PROFILE, userProfile.getEmail(), userProfile.getName(),
					userProfile.getMobileCountry(), userProfile.getMobileNumber(), userProfile.getId());
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public int updateUserCounts(UserProfile userProfile) throws DataAccessException {
		try {// email=?,name=?,mobileCountry=?,mobileNumber=?
			return jdbcTemplate.update(UPDATE_USER_COUNTS, userProfile.getNotificationsCount(),
					userProfile.getBasketOrdersCount(), userProfile.getId());
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public void updateUserOtpGenerated(UserProfile userProfile) throws DataAccessException {
		try {
			logger.info(">> OTP is " + userProfile.getOtp());
			if (userProfile.getEmail() != null) {
				jdbcTemplate.update(UPDATE_USER_OTP_GENERATED, "0", userProfile.getOtp(), userProfile.getId());
			} else if (userProfile.getMobileCountry() != null) {
				jdbcTemplate.update(UPDATE_USER_OTP_GENERATED, "0", userProfile.getOtp(), userProfile.getId());
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public long createUser(final UserProfile profile) throws DataAccessException {
		try {
			PreparedStatementCreator creator = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement insert = con.prepareStatement(INSERT_USER_PROFILE_LOGIN,
							Statement.RETURN_GENERATED_KEYS);
					insert.setString(1, profile.getEmail());
					insert.setString(2, profile.getName());
					insert.setString(3, profile.getMobileCountry());
					insert.setString(4, profile.getMobileNumber());
					insert.setTimestamp(5, new Timestamp(new Date().getTime()));
					insert.setInt(6, profile.getOtp());
					return insert;
				}
			};
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(creator, keyHolder);
			return keyHolder.getKey().longValue();
		} catch (Exception e) {
			logger.error(e, e);
		}
		return 0;
	}

	@Override
	public long updateUser(UserProfile usersProfile) throws DataAccessException {
		try {
			return jdbcTemplate.update("UPDATE Users set LastLocationLat=?,LastLocationLong=? where id=?",
					usersProfile.getLastLocationLat(), usersProfile.getLastLocationLong(), usersProfile.getId());
		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	@Override
	public List<UserAddress> getUserAddressListByUserId(int userId) throws DataAccessException {
		List<UserAddress> lst = jdbcTemplate.query(GET_USER_ADDRESS_DETAILS_BY_USERID, new Object[] { userId },
				new UserAddressMapper());
		if (lst != null && lst.size() > 0) {
			return lst;
		}
		return null;
	}

	private class UserProfileAddressMapper implements RowMapper<UserProfile> {
		public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserProfile user = new UserProfile();
			if (rs.getString("email") != null && rs.getString("email").trim() != "") {
				user.setEmail(rs.getString("email"));
			} else {
				user.setEmail(null);
			}
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setProfilePicUrl(rs.getString("profilePicUrl"));
			user.setMobileNumber(rs.getString("mobileNumber"));
			user.setCreated_date(rs.getTimestamp("created_date"));
			user.setIsOTPVerified(rs.getInt("isOTPVerified"));
			user.setOtp(rs.getInt("otp"));
			user.setBasketOrdersCount(rs.getString("basketOrdersCount"));
			/*
			 * user.setDeviceType(rs.getString("deviceType"));
			 * user.setGcmId(rs.getString("gcmId"));
			 */
			user.setIsProfileComplete(rs.getInt("isProfileComplete"));
			user.setLastLocationLat(rs.getString("lastLocationLat"));
			user.setLastLocationLong(rs.getString("lastLocationLong"));
			user.setNotificationsCount(rs.getString("notificationsCount"));
			user.setMobileCountry(rs.getString("mobileCountry"));
			user.setAddress1(rs.getString("address1"));
			user.setAddress2(rs.getString("address2"));
			user.setCity(rs.getString("city"));
			user.setState(rs.getString("state"));
			user.setCountry(rs.getString("country"));
			user.setLandmark(rs.getString("landmark"));
			user.setPinCode(rs.getString("pinCode"));
			user.setAddressLat(rs.getString("addressLat"));
			user.setAddressLong(rs.getString("addressLong"));
			return user;
		}
	}

	@Override
	public UserProfile getUserAddressByUserId(int userId) throws DataAccessException {
		try {
			List<UserProfile> lst = jdbcTemplate.query(GET_USER_ADDRESS_DETAILS_BY_USERID, new Object[] { userId },
					new UserProfileAddressMapper());
			if (lst != null && lst.size() > 0) {
				return lst.get(0);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int updateUserImage(String Image, int userId) {
		try {
			return jdbcTemplate.update("UPDATE Users set profilePicUrl=? where id=?", Image, userId);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	private class UserAddressMapper implements RowMapper<UserAddress> {
		public UserAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserAddress user = new UserAddress();
			user.setUserId(rs.getInt("userId"));
			user.setAddressId(rs.getInt("id"));
			user.setAddress1(rs.getString("address1"));
			user.setAddress2(rs.getString("address2"));
			user.setCity(rs.getString("city"));
			user.setCountry(rs.getString("country"));
			user.setState(rs.getString("state"));
			user.setLandmark(rs.getString("landmark"));
			user.setPinCode(rs.getString("pinCode"));
			user.setAddressLat(rs.getString("addressLat"));
			user.setAddressLong(rs.getString("addressLong"));
			return user;
		}
	}

	@Override
	public List<UserProfile> getUserAddressListsByUserId(int userId) throws DataAccessException {
		List<UserProfile> lst = jdbcTemplate.query(GET_USER_ADDRESS_DETAILS_BY_USER_ID, new Object[] { userId },
				new UserProfileAddressMapper());
		if (lst != null && lst.size() > 0) {
			return lst;
		}
		return null;
	}

	@Override
	public int updateUserProfileData(UserProfile userProfile) throws DataAccessException {
		try {// email=?,name=?,mobileCountry=?,mobileNumber=?
			return jdbcTemplate.update(UPDATE_USER_PROFILE_DATA, userProfile.getEmail(), userProfile.getName(),userProfile.getMobileCountry()
					,userProfile.getMobileNumber(),userProfile.getPassword(),0,userProfile.getOtp(),userProfile.getId());
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public int updateUserAddress(UserAddress user) throws DataAccessException {
		try {// email=?,name=?,mobileCountry=?,mobileNumber=?

			PreparedStatementCreator creator = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement insert = con.prepareStatement(INSERT_USER_ADDRESS_DATA,
							Statement.RETURN_GENERATED_KEYS);
					insert.setInt(1, user.getUserId());
					insert.setString(2, user.getPinCode());
					insert.setString(3, user.getAddress1());
					insert.setString(4, user.getAddress2());
					insert.setString(5, user.getLandmark());
					insert.setString(6, user.getCity());
					insert.setString(7, user.getState());
					insert.setString(8, user.getCountry());
					insert.setString(9, user.getAddressLat());
					insert.setString(10, user.getAddressLong());
					insert.setTimestamp(11, new Timestamp(new Date().getTime()));
					return insert;
				}
			};
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(creator, keyHolder);
			return keyHolder.getKey().intValue();

			/* return jdbcTemplate.update(INSERT_USER_ADDRESS_DATA, ); */
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public UserAddress getUserAddressListByAddressId(int addressId, int userId) throws DataAccessException {
		try {
			List<UserAddress> lst = jdbcTemplate.query(GET_USER_ADDRESS_DATA_ADDRRESS_ID,
					new Object[] { addressId, userId }, new UserAddressMapper());
			if (lst != null && lst.size() > 0) {
				return lst.get(0);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public UserAddress getLatestUserAddressByUserId(int userId) throws DataAccessException {
		List<UserAddress> list = new ArrayList<UserAddress>();
		try{
		List<UserAddress> lst = jdbcTemplate.query(GET_USER_ADDRESS_DETAILS_BY_USERID, new Object[] { userId },
				new UserAddressMapper());
		if (lst != null && lst.size() > 0) {
			//list.add(lst.get(0));
			return lst.get(0);
		}
		}catch (Exception e) {
			logger.error(e,e);
		}
		return null;
	}

	@Override
	public int saveSearchData(SearchedData searchData) throws DataAccessException {
		PreparedStatementCreator creator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement insert = con.prepareStatement(INSERT_SEARCH_DATA, Statement.RETURN_GENERATED_KEYS);
				insert.setString(1, searchData.getUserId());
				insert.setString(2, searchData.getSearchedString());
				insert.setTimestamp(3, new Timestamp(new Date().getTime()));
				return insert;
			}
		};
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(creator, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public int saveDeviceTypeData(DeviceTypeData deviceData) throws DataAccessException {
		try {
			List<DeviceTypeData> lst = jdbcTemplate.query(CHECK_EXISTS_USER, new Object[] { deviceData.getUserId() },
					new DeviceTypeDataMapper());
			if (lst!=null && lst.size()>0) {
				int k = jdbcTemplate.update(UPDATE_DEVICE_TYPE_DATA,deviceData.getVersionName(),deviceData.getVersionCode(),deviceData.getDeviceType(),new Timestamp(new Date().getTime()),deviceData.getUserId());
				return k;
			} else {
				PreparedStatementCreator creator = new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement insert = con.prepareStatement(INSERT_DEVICE_TYPE_DATA,
								Statement.RETURN_GENERATED_KEYS);
						insert.setInt(1, deviceData.getUserId());
						insert.setString(2, deviceData.getVersionName());
						insert.setInt(3, deviceData.getVersionCode());
						insert.setString(4, deviceData.getDeviceType());
						insert.setTimestamp(5, new Timestamp(new Date().getTime()));
						return insert;
					}
				};
				KeyHolder keyHolder = new GeneratedKeyHolder();
				jdbcTemplate.update(creator, keyHolder);
				return keyHolder.getKey().intValue();
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	@Override
	public List<Version> getAPKVersion() {
		try {
			List<Version> lst = jdbcTemplate.query("Select * from version where status='Y'", new Object[] {},
					new VersionMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e, e);
		}
		return null;
	}

	private class VersionMapper implements RowMapper<Version> {
		public Version mapRow(ResultSet rs, int rowNum) throws SQLException {
			Version user = new Version();
			user.setId(rs.getInt("id"));
			user.setVersion(rs.getString("version"));
			user.setCreateddate(rs.getTimestamp("createddate"));

			return user;
		}
	}

	private class DeviceTypeDataMapper implements RowMapper<DeviceTypeData> {
		public DeviceTypeData mapRow(ResultSet rs, int rowNum) throws SQLException {
			DeviceTypeData device = new DeviceTypeData();
			device.setDeviceType(rs.getString("deviceType"));
			device.setUserId(rs.getInt("userId"));
			device.setVersionCode(rs.getInt("versionCode"));
			device.setVersionName(rs.getString("versionName"));
			return device;
		}
	}

}
