package com.siniia.app.dao;

import java.util.List;

import org.json.JSONObject;
import org.springframework.dao.DataAccessException;

import com.siniia.app.dbo.DeviceTypeData;
import com.siniia.app.dbo.SearchedData;
import com.siniia.app.dbo.UserAddress;
import com.siniia.app.dbo.UserProfile;
import com.siniia.app.dbo.Version;

public interface UserProfileDAO {

	long registerUser(final UserProfile profile) throws DataAccessException;

	UserProfile getUserByUserId(int userId) throws DataAccessException;
	
	List<UserProfile> getUserListByUserId(int userId) throws DataAccessException;

	public UserProfile getUserProfileByPhoneNumber(UserProfile phoneNumber);

	public UserProfile getUserProfileByEmail(UserProfile email);

	public int getUserInfoByEmail(String phoneNumber, int otp) throws DataAccessException;

	public void updateUserOtpVerifiedByEmail(String phoneNumber) throws DataAccessException;

	public long updateUserType(String userId, String userType);

	public long updateAddress(UserProfile user);
	
	public void updateUserOtpVerified(String phoneNumber) throws DataAccessException;
	
	int getUserInfo(String email, int otp) throws DataAccessException;
	
	public void updateUserOtp(UserProfile userProfile) throws DataAccessException;
	
	public int updateUserProfile(UserProfile userProfile) throws DataAccessException;
	
	public int updateUserCounts(UserProfile userProfile) throws DataAccessException;
	
	public void updateUserOtpGenerated(UserProfile userProfile) throws DataAccessException;
	
	public long createUser(final UserProfile profile) throws DataAccessException ;
	
	public long updateUser(UserProfile usersProfile) throws DataAccessException;
	
	public List<UserAddress> getUserAddressListByUserId(int userId) throws DataAccessException;
	
	public UserProfile getUserAddressByUserId(int userId) throws DataAccessException;
	
	public int updateUserImage(String Image,int userId);
	
	public List<UserProfile> getUserAddressListsByUserId(int userId) throws DataAccessException;
	
	public int updateUserProfileData(UserProfile userProfile) throws DataAccessException;
	
	public int updateUserAddress(UserAddress userAddress) throws DataAccessException;
	
	public UserAddress getUserAddressListByAddressId(int addressId,int userId) throws DataAccessException;
	
	public UserAddress getLatestUserAddressByUserId(int userId) throws DataAccessException;
	
	public int saveSearchData(SearchedData searchData) throws DataAccessException;
	
	public int saveDeviceTypeData(DeviceTypeData deviceData) throws DataAccessException;
	
	public List<Version> getAPKVersion();
}
