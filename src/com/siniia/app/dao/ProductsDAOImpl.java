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

import com.siniia.app.dbo.BannersDetails;
import com.siniia.app.dbo.OrderDetails;
import com.siniia.app.dbo.ProductCategories;
import com.siniia.app.dbo.ProductDetails;
import com.siniia.app.dbo.ProductImages;
import com.siniia.app.dbo.ProductType;
import com.siniia.app.dbo.UpdateProductData;
import com.siniia.app.dbo.productCategoriesMeta;

public class ProductsDAOImpl implements ProductsDAO {

	private static final Log logger = LogFactory.getLog(ProductsDAOImpl.class);

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@PersistenceContext(unitName = "entityManager")
	protected EntityManager entityManager;

	private String INSERT_BANNER = "INSERT INTO `banners`(`URL`, `UserType`, `productId`) VALUES  (?,?,?)";
	private String GET_EXISTS_PRODUCT = "select * from products where productName=? and productOwnerID=?";
	private String GET_EXISTS_PRODUCT_BY_ID = "select * from products where id=?";
	private String GET_ALL_PRODUCT_BY_USER_ID = "select * from products where productOwnerID=?";
	private String GET_PRODUCT_BY_USER_ID = "select * from products where productOwnerID=? and productStatus != 0 ";
	private String GET_PRODUCT_BY_PRODUCT_TYPE = "select * from products where productType=?";
	private String GET_PRODUCT_BY_PRODUCT_TYPE_INDIA_USA = "select * from products where productStatus != 0 and lower(productType)=? and productOwnerID in (select id from Users where mobileCountry in (SELECT mobileCountry from Users where id =?))";
	private String GET_PRODUCT_BY_PRODUCT_TYPE_OTHER = "select * from products where productStatus != 0 and lower(productType)=? and productOwnerID in (select id from Users where mobileCountry not in (91,1))";
	private String INSERT_PRODUCT = "INSERT INTO `products`(`categoryName`, `productName`, `productSubName`, `productType`, `productGrade`, `quantityType`, `quantityPerUnit`, `pricePerUnit`, `minQuantity`, `quantityAvailable`, `highlight`, `productDescription`, `availableAddressId`, `productOwnerID`, `productOwnerName`, `createdDate`,`thumbImageURL`,`productOwnerContact`,`radius`,`productStatus`,`pinCode`, `Address1`, `Address2`, `Landmark`, `addressLat`, `addressLong`,`height`,`weight`,`width`,`city`,`state`,`country`,`length`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private String INSERT_PRODUCTS = "INSERT INTO `products`(`categoryName`, `productName`, `productSubName`, `productType`, `productGrade`, `quantityType`, `quantityPerUnit`, `pricePerUnit`, `minQuantity`, `quantityAvailable`, `highlight`, `productDescription`, `availableAddressId`, `productOwnerID`, `productOwnerName`, `createdDate`,`thumbImageURL`,`productOwnerContact`,`radius`,`productStatus`,`pinCode`, `Address1`, `Address2`, `Landmark`, `addressLat`, `addressLong`,`height`,`weight`,`width`,`city`,`state`,`country`,`length`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private String UPDATE_PRODUCT = "UPDATE `products` SET `categoryName`=?,`productType`=?,`productGrade`=?,`quantityType`=?,`quantityPerUnit`=?,`pricePerUnit`=?,`minQuantity`=?,`quantityAvailable`=?,`highlight`=?,`productDescription`=?,`availableAddressId`=?,`productOwnerID`=?,`productOwnerName`=?,`productName`=?,`productSubName`=?,`radius`=?,`productOwnerContact`=?,`height`=?,`weight`=?,`width`=?,`city`=?,`state`=?,`country`=?,`length`=?,`Address1`=?,`Landmark`=?,`pinCode`=? WHERE `id`=?";
	private String UPDATE_PRODUCT_QUANTITY = "UPDATE `products` SET quantityAvailable=quantityAvailable-? where id=?";
	private String GET_ALL_PRODUCT_IMAGES = "select * from product_images";
	private String GET_EXISTS_PRODUCT_IMAGES_BY_ID = "select * from product_images where productId=?";
	private String GET_ALL_BANNERS = "select * from banners";
	private String GET_ALL_PRODUCT_CATEGORIES = "select * from product_categories";
	private String GET_ALL_PRODUCT_CATEGORIES_META = "select * from product_category_meta";
	private String GET_ALL_PRODUCT_CATEGORIES_BY_NAME = "select * from product_categories where productCategory=?";
	private String DELETE_PRODUCT_LIST = "DELETE FROM products WHERE `id` = ? and `productOwenerID`=?";
	private String UPDATE_PRODUCT_STATUS = "UPDATE `products` SET `productStatus`=? WHERE `id` = ? and `productOwnerID`=?";
	private String UPDATE_PRODUCT_DETAILS = "UPDATE `products` SET `minQuantity`=?,`quantityAvailable`=?,`pricePerUnit`=? WHERE `id`=? and `productOwnerID` = ?";

	@Override
	public List<ProductDetails> getAllProducts() {
		try {
			List<ProductDetails> usr = (List<ProductDetails>) jdbcTemplate.query("select * from products where productStatus != 0 ",
					new Object[] {}, new ProductDetailsMapper());
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
	public List<ProductDetails> getAllProductsINDIAUSA(int userId) {
		try {
			List<ProductDetails> adminProducts = null;
			List<ProductDetails> usr = (List<ProductDetails>) jdbcTemplate.query("select * from products where productStatus != 0 and productOwnerID in (select id from Users where mobileCountry in (SELECT mobileCountry from Users where id ="+userId+"))",
					new Object[] {}, new ProductDetailsMapper());
			
			int n = jdbcTemplate.queryForInt("SELECT mobileCountry from Users where id ="+userId);
			if(n==1){
			adminProducts = (List<ProductDetails>) jdbcTemplate.query("select * from products where productStatus != 0 and productOwnerID = 9999 and country in ('US','USA')",
					new Object[] {}, new ProductDetailsMapper());
			}else if (n == 91){
				adminProducts = (List<ProductDetails>) jdbcTemplate.query("select * from products where productStatus != 0 and productOwnerID = 9999 and country in ('INDIA')",
						new Object[] {}, new ProductDetailsMapper());
			}
						
			if (usr != null && usr.size() > 0) {
				if (adminProducts != null && adminProducts.size()>0){
					for (ProductDetails productDetails : adminProducts) {
						usr.add(productDetails);
					}
				}
				return usr;
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}
	
	@Override
	public List<ProductDetails> getAllProductsOTHER(int userId) {
		try {
			List<ProductDetails> usr = (List<ProductDetails>) jdbcTemplate.query("select * from products where productStatus != 0 and productOwnerID in (select id from Users where mobileCountry not in (91,1))",
					new Object[] {}, new ProductDetailsMapper());
			List<ProductDetails> adminProducts = (List<ProductDetails>) jdbcTemplate.query("select * from products where productStatus != 0 and productOwnerID = 9999 and country not in ('US','USA','INDIA')",
					new Object[] {}, new ProductDetailsMapper());
			if (usr != null && usr.size() > 0) {
				if (adminProducts != null && adminProducts.size()>0){
					for (ProductDetails productDetails : adminProducts) {
						usr.add(productDetails);
					}
				}
				return usr;
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int getProductsTableCount() {
		try {
			int count = jdbcTemplate.queryForInt("select count(*) from products where productStatus != 0 ");
			return count;
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}
	
	@Override
	public int getProductsTableCountOTHER(int userId) {
		try {
			int count = jdbcTemplate.queryForInt("select count(*) from products where productStatus != 0 and productOwnerID in (select id from Users where mobileCountry not in (91,1))");
			return count;
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}
	
	@Override
	public int getProductsTableCountINDIAUSA(int userId) {
		try {
			int count = jdbcTemplate.queryForInt("select count(*) from products where productStatus != 0 and productOwnerID in (select id from Users where mobileCountry in (SELECT mobileCountry from Users where id ="+userId+")) ");
			return count;
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	private class ProductDetailsMapper implements RowMapper<ProductDetails> {
		public ProductDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductDetails product = new ProductDetails();
			product.setAvailableAddressId(rs.getInt("availableAddressId"));
			product.setCategoryName(rs.getString("categoryName"));
			product.setCreatedDate(rs.getTimestamp("createdDate"));
			product.setHighlight(rs.getString("highlight"));
			product.setId(rs.getInt("id"));
			product.setMinQuantity(rs.getInt("minQuantity"));
			product.setPricePerUnit(rs.getDouble("pricePerUnit"));
			product.setProductDescription(rs.getString("productDescription"));
			product.setProductGrade(rs.getString("productGrade"));
			product.setProductName(rs.getString("productName"));
			product.setProductOwenerID(rs.getInt("productOwnerID"));
			product.setProductOwnerName(rs.getString("productOwnerName"));
			product.setProductSubName(rs.getString("productSubName"));
			product.setProductType(rs.getString("productType"));
			product.setQuantityAvailable(rs.getInt("quantityAvailable"));
			product.setQuantityPerUnit(rs.getInt("quantityPerUnit"));
			product.setQuantityType(rs.getString("quantityType"));
			product.setThumbImageURL(rs.getString("thumbImageURL"));
			product.setProductOwnerContact(rs.getString("productOwnerContact"));
			product.setRadius(rs.getString("radius"));
			product.setProductStatus(rs.getInt("productStatus"));
			product.setProductId(rs.getInt("id"));
			product.setAddress1(rs.getString("address1"));
			product.setAddress2(rs.getString("address2"));
			product.setLandmark(rs.getString("landmark"));
			product.setPinCode(rs.getString("pinCode"));
			product.setAddressLat(rs.getString("addressLat"));
			product.setAddressLong(rs.getString("addressLong"));
			product.setHeight(rs.getDouble("height"));
			product.setWeight(rs.getDouble("weight"));
			product.setWidth(rs.getDouble("width"));
			product.setLength(rs.getDouble("length"));
			product.setCity(rs.getString("city"));
			product.setState(rs.getString("state"));
			product.setCountry(rs.getString("country"));
			return product;
		}
	}

	private class IdMapper implements RowMapper<Integer> {
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getInt("id");
		}
	}

	@Override
	public int saveProduct(ProductDetails product) {
		try {
			if (product.getProductName() != null && product.getProductOwenerID() != 0) {
				List<ProductDetails> lst = jdbcTemplate.query(GET_EXISTS_PRODUCT,
						new Object[] { product.getProductName(), product.getProductOwenerID() },
						new ProductDetailsMapper());
				/*if (lst != null && lst.size() > 0) {
					return 0;
				} else {*/
					PreparedStatementCreator creator = new PreparedStatementCreator() {
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement insert = con.prepareStatement(INSERT_PRODUCT,
									Statement.RETURN_GENERATED_KEYS);
							insert.setString(1, product.getCategoryName());
							insert.setString(2, product.getProductName());
							insert.setString(3, product.getProductSubName());
							insert.setString(4, product.getProductType());
							insert.setString(5, product.getProductGrade());
							insert.setString(6, product.getQuantityType());
							insert.setInt(7, product.getQuantityPerUnit());
							insert.setDouble(8, product.getPricePerUnit());
							insert.setInt(9, product.getMinQuantity());
							insert.setInt(10, product.getQuantityAvailable());
							insert.setString(11, product.getHighlight());
							insert.setString(12, product.getProductDescription());
							insert.setInt(13, product.getAvailableAddressId());
							insert.setInt(14, product.getProductOwenerID());
							insert.setString(15, product.getProductOwnerName());
							insert.setTimestamp(16, new Timestamp(new Date().getTime()));
							insert.setString(17, product.getThumbImageURL());
							insert.setString(18, product.getProductOwnerContact());
							insert.setString(19, product.getRadius());
							insert.setInt(20, 0);
							insert.setString(21, product.getPinCode());
							insert.setString(22, product.getAddress1());
							insert.setString(23, product.getAddress2());
							insert.setString(24, product.getLandmark());
							insert.setString(25, product.getAddressLat());
							insert.setString(26, product.getAddressLong());
							insert.setDouble(27, product.getHeight());
							insert.setDouble(28, product.getWeight());
							insert.setDouble(29, product.getWidth());
							insert.setString(30, product.getCity());
							insert.setString(31, product.getState());
							insert.setString(32, product.getCountry());
							insert.setDouble(33, product.getLength());
							return insert;
						}
					};
					KeyHolder keyHolder = new GeneratedKeyHolder();
					jdbcTemplate.update(creator, keyHolder);
					return keyHolder.getKey().intValue();
				//}
			} else {
				return -1;
			}
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
		// return -1;
	}

	@Override
	public ProductDetails checkProductExists(int id) {
		try {
			List<ProductDetails> lst = jdbcTemplate.query(GET_EXISTS_PRODUCT_BY_ID, new Object[] { id },
					new ProductDetailsMapper());
			if (lst != null && lst.size() > 0) {
				return lst.get(0);
			}
		}catch (NoResultException nre) {
		}catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}
	
	@Override
	public int checkProductsExists(int id) {
		int count = 0;
		try {
			count = jdbcTemplate.queryForObject("select count(*) from products where id="+id,Integer.class);
		}catch (NoResultException nre) {
		}catch (Exception e) {
			logger.error(e, e);
		}
		return count;
	}

	@Override
	public List<ProductDetails> checkProductListExists(int id) {
		try {
			List<ProductDetails> lst = jdbcTemplate.query(GET_EXISTS_PRODUCT_BY_ID, new Object[] { id },
					new ProductDetailsMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int updateProduct(ProductDetails product) {
		try {
			return jdbcTemplate.update(UPDATE_PRODUCT, product.getCategoryName(), product.getProductType(),
					product.getProductGrade(), product.getQuantityType(), product.getQuantityPerUnit(),
					product.getPricePerUnit(), product.getMinQuantity(), product.getQuantityAvailable(),
					product.getHighlight(), product.getProductDescription(), product.getAvailableAddressId(),
					product.getProductOwenerID(), product.getProductOwnerName(),product.getProductName(),
					product.getProductSubName(),product.getRadius(),product.getProductOwnerContact()
					,product.getHeight(),product.getWeight(),product.getWidth(), product.getCity(),product.getState(),
					product.getCountry(),product.getLength(),product.getAddress1(),product.getLandmark(),product.getPinCode(),product.getId());
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public List<ProductDetails> getProductsByUserId(int userId) {
		try {
			List<ProductDetails> lst = jdbcTemplate.query(GET_PRODUCT_BY_USER_ID, new Object[] { userId },
					new ProductDetailsMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int updateProductQuantity(int productId, int quantity) {
		try {
			return jdbcTemplate.update(UPDATE_PRODUCT_QUANTITY, quantity, productId);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	@Override
	public List<ProductImages> getAllProductImages() {
		try {
			List<ProductImages> lst = jdbcTemplate.query(GET_ALL_PRODUCT_IMAGES, new Object[] {},
					new ProductImagesMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	private class ProductImagesMapper implements RowMapper<ProductImages> {
		public ProductImages mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductImages prod = new ProductImages();
			prod.setId(rs.getInt("id"));
			prod.setImageURLLarge(rs.getString("imageURLLarge"));
			prod.setImageURLMedium(rs.getString("imageURLMedium"));
			prod.setImageURLSmall(rs.getString("imageURLSmall"));
			prod.setProductId(rs.getInt("productId"));

			return prod;
		}
	}

	@Override
	public ProductImages checkProductImagesExists(int id) {
		try {
			List<ProductImages> lst = jdbcTemplate.query(GET_EXISTS_PRODUCT_IMAGES_BY_ID, new Object[] { id },
					new ProductImagesMapper());
			if (lst != null && lst.size() > 0) {
				return lst.get(0);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public List<ProductImages> checkProductImagesListExists(int id) {
		try {
			List<ProductImages> lst = jdbcTemplate.query(GET_EXISTS_PRODUCT_IMAGES_BY_ID, new Object[] { id },
					new ProductImagesMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int updateProductImage(String Image, int id) {
		try {
			return jdbcTemplate.update("UPDATE products set thumbImageURL=? where id=?", Image, id);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	@Override
	public int getQuantityId(String quantityType) {
		try {
			return jdbcTemplate.queryForObject("select id from quantitytype_meta where category='" + quantityType + "'",
					Integer.class);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	@Override
	public List<ProductType> getAllProductTypes() {
		try {
			List<ProductType> usr = (List<ProductType>) jdbcTemplate.query("select * from producttype_meta",
					new Object[] {}, new ProductTypeMapper());
			if (usr != null && usr.size() > 0) {
				return usr;
			}
		} catch (NoResultException e) {
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	private class ProductTypeMapper implements RowMapper<ProductType> {
		public ProductType mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductType prod = new ProductType();
			prod.setCreateddate(rs.getTimestamp("createddate"));
			prod.setId(rs.getInt("id"));
			prod.setProductType(rs.getString("productType"));
			return prod;
		}
	}

	@Override
	public List<ProductDetails> getProductByProductType(String productType) {
		try {
			List<ProductDetails> lst = jdbcTemplate.query(GET_PRODUCT_BY_PRODUCT_TYPE, new Object[] { productType },
					new ProductDetailsMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public List<BannersDetails> getAllBanners() {
		try {
			List<BannersDetails> lst = jdbcTemplate.query(GET_ALL_BANNERS, new Object[] {}, new BannersDetailsMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	private class BannersDetailsMapper implements RowMapper<BannersDetails> {
		public BannersDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			BannersDetails banner = new BannersDetails();
			banner.setEndTime(rs.getTimestamp("endTime"));
			banner.setStartTime(rs.getTimestamp("startTime"));
			banner.setId(rs.getInt("id"));
			banner.setProductId(rs.getString("productId"));
			banner.setURL(rs.getString("uRL"));
			banner.setUserType(rs.getString("userType"));
			return banner;
		}
	}

	@Override
	public List<ProductCategories> getAllProductCategories() {
		try {
			List<ProductCategories> lst = jdbcTemplate.query(GET_ALL_PRODUCT_CATEGORIES, new Object[] {},
					new ProductCategoriesMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	private class ProductCategoriesMapper implements RowMapper<ProductCategories> {
		public ProductCategories mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductCategories banner = new ProductCategories();
			banner.setCreatedDate(rs.getTimestamp("createdDate"));
			banner.setId(rs.getInt("id"));
			banner.setProductCategory(rs.getString("productCategory"));
			banner.setProductGrade(rs.getString("productGrade"));
			banner.setProductType(rs.getString("productType"));
			banner.setProductUnits(rs.getString("productUnits"));
			banner.setProductName(rs.getString("productName"));
			return banner;
		}
	}

	@Override
	public List<productCategoriesMeta> getAllProductCategoriesMeta() {
		try {
			List<productCategoriesMeta> lst = jdbcTemplate.query(GET_ALL_PRODUCT_CATEGORIES_META, new Object[] {},
					new ProductCategoriesMetaMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	private class ProductCategoriesMetaMapper implements RowMapper<productCategoriesMeta> {
		public productCategoriesMeta mapRow(ResultSet rs, int rowNum) throws SQLException {
			productCategoriesMeta banner = new productCategoriesMeta();
			banner.setCreatedDate(rs.getTimestamp("createdDate"));
			banner.setId(rs.getInt("id"));
			banner.setProductCategory(rs.getString("productCategory"));
			return banner;
		}
	}

	@Override
	public List<ProductCategories> getAllProductCategoriesByMeta(String category) {
		try {
			List<ProductCategories> lst = jdbcTemplate.query(GET_ALL_PRODUCT_CATEGORIES_BY_NAME,
					new Object[] { category }, new ProductCategoriesMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int deleteProduct(int productId, int userId) throws DataAccessException {
		try {
			return jdbcTemplate.update(DELETE_PRODUCT_LIST, productId, userId);

		} catch (Exception e) {
			logger.error(e, e);
		}
		return -1;
	}

	@Override
	public int updateProductStatus(int productId, int userId, String status) {
		try {
			return jdbcTemplate.update(UPDATE_PRODUCT_STATUS, status, productId, userId);
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public List<ProductDetails> getAllProductsByUserId(int userId) {
		try {
			List<ProductDetails> lst = jdbcTemplate.query(GET_ALL_PRODUCT_BY_USER_ID, new Object[] { userId },
					new ProductDetailsMapper());
			if (lst != null && lst.size() > 0) {
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int updateProductDetails(UpdateProductData product) {
		try {
			//UPDATE `products` SET `minQuantity`=?,`quantityAvailable`=?,`pricePerUnit`=? WHERE `id`=? and `productOwnerID` = ?
			return jdbcTemplate.update(UPDATE_PRODUCT_DETAILS,product.getMinQuantity(),product.getQuantityAvailable(),product.getPricePerUnit(),product.getProductId(),product.getUserId());
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}
	
	@Override
	public List<ProductDetails> getProductByProductTypeINDIAUSA(String productType,int userId) {
		try {
			List<ProductDetails> adminProducts = null;
			List<ProductDetails> lst = jdbcTemplate.query(GET_PRODUCT_BY_PRODUCT_TYPE_INDIA_USA, new Object[] { productType,userId },
					new ProductDetailsMapper());
			int n = jdbcTemplate.queryForInt("SELECT mobileCountry from Users where id ="+userId);
			if(n==1){
			adminProducts = (List<ProductDetails>) jdbcTemplate.query("select * from products where productStatus != 0 and productOwnerID = 9999 and country in ('US','USA')",
					new Object[] {}, new ProductDetailsMapper());
			}else if (n == 91){
				adminProducts = (List<ProductDetails>) jdbcTemplate.query("select * from products where productStatus != 0 and productOwnerID = 9999 and country in ('INDIA')",
						new Object[] {}, new ProductDetailsMapper());
			}
						
			if (lst != null && lst.size() > 0) {
				if (adminProducts != null && adminProducts.size()>0){
					for (ProductDetails productDetails : adminProducts) {
						lst.add(productDetails);
					}
				}
				return lst;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}
	
	@Override
	public List<ProductDetails> getProductByProductTypeOTHER(String productType,int userId) {
		try {
			List<ProductDetails> lst = jdbcTemplate.query(GET_PRODUCT_BY_PRODUCT_TYPE_OTHER, new Object[] { productType },
					new ProductDetailsMapper());
			List<ProductDetails> adminProducts = (List<ProductDetails>) jdbcTemplate.query("select * from products where productStatus != 0 and productOwnerID = 9999 and country not in ('US','USA','INDIA')",
					new Object[] {}, new ProductDetailsMapper());
			if (lst != null && lst.size() > 0) {
				if (adminProducts != null && adminProducts.size()>0){
					for (ProductDetails productDetails : adminProducts) {
						lst.add(productDetails);
					}
				}
				return lst;
			}
		}catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public List<String> getProductNamesByCategory(String productCategory) {
		List<String> productNames = null;
		try {
			productNames = jdbcTemplate.queryForList("select distinct productName from product_categories where Lower(productCategory)='"+productCategory.toLowerCase()+"'",String.class);
			}catch (NoResultException nre) {
			}catch (Exception e) {
				logger.error(e, e);
			}
		return productNames;
	}

	@Override
	public List<String> getProductTypeByNameAndCategory(String productCategory, String productName) {
		List<String> productTypes = null;
		try {
			productTypes = jdbcTemplate.queryForList("select distinct productType from product_categories where Lower(productCategory)='"+productCategory.toLowerCase()+"' and Lower(productName)='"+productName.toLowerCase()+"'",String.class);
			}catch (NoResultException nre) {
			}catch (Exception e) {
				logger.error(e, e);
			}
		return productTypes;
	}

	@Override
	public List<String> getProductGradeByNameAndCategory(String productCategory, String productName) {
		List<String> productGrades = null;
		try {
			productGrades = jdbcTemplate.queryForList("select distinct productGrade from product_categories where Lower(productCategory)='"+productCategory.toLowerCase()+"' and Lower(productName)='"+productName.toLowerCase()+"'",String.class);
			}catch (NoResultException nre) {
			}catch (Exception e) {
				logger.error(e, e);
			}
		return productGrades;
	}

	@Override
	public List<String> getProductUnitsByNameAndCategory(String productCategory, String productName) {
		List<String> productUnits = null;
		try {
			productUnits = jdbcTemplate.queryForList("select distinct productUnits from product_categories where Lower(productCategory)='"+productCategory.toLowerCase()+"' and Lower(productName)='"+productName.toLowerCase()+"'",String.class);
			}catch (NoResultException nre) {
			}catch (Exception e) {
				logger.error(e, e);
			}
		return productUnits;
	}

	@Override
	public int saveProducts(ProductDetails product) {
		try {
			if (product.getProductName() != null && product.getProductOwenerID() != 0) {
				List<ProductDetails> lst = jdbcTemplate.query(GET_EXISTS_PRODUCT,
						new Object[] { product.getProductName(), product.getProductOwenerID() },
						new ProductDetailsMapper());
				/*if (lst != null && lst.size() > 0) {
					return 0;
				} else {*/
					PreparedStatementCreator creator = new PreparedStatementCreator() {
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement insert = con.prepareStatement(INSERT_PRODUCTS,
									Statement.RETURN_GENERATED_KEYS);
							insert.setString(1, product.getCategoryName());
							insert.setString(2, product.getProductName());
							insert.setString(3, product.getProductSubName());
							insert.setString(4, product.getProductType());
							insert.setString(5, product.getProductGrade());
							insert.setString(6, product.getQuantityType());
							insert.setInt(7, product.getQuantityPerUnit());
							insert.setDouble(8, product.getPricePerUnit());
							insert.setInt(9, product.getMinQuantity());
							insert.setInt(10, product.getQuantityAvailable());
							insert.setString(11, product.getHighlight());
							insert.setString(12, product.getProductDescription());
							insert.setInt(13, product.getAvailableAddressId());
							insert.setInt(14, product.getProductOwenerID());
							insert.setString(15, product.getProductOwnerName());
							insert.setTimestamp(16, new Timestamp(new Date().getTime()));
							insert.setString(17, product.getThumbImageURL());
							insert.setString(18, product.getProductOwnerContact());
							insert.setString(19, product.getRadius());
							insert.setInt(20, 1);
							insert.setString(21, product.getPinCode());
							insert.setString(22, product.getAddress1());
							insert.setString(23, product.getAddress2());
							insert.setString(24, product.getLandmark());
							insert.setString(25, product.getAddressLat());
							insert.setString(26, product.getAddressLong());
							insert.setDouble(27, product.getHeight());
							insert.setDouble(28, product.getWeight());
							insert.setDouble(29, product.getWidth());
							insert.setString(30, product.getCity());
							insert.setString(31, product.getState());
							insert.setString(32, product.getCountry());
							insert.setDouble(33, product.getLength());
							return insert;
						}
					};
					KeyHolder keyHolder = new GeneratedKeyHolder();
					jdbcTemplate.update(creator, keyHolder);
					return keyHolder.getKey().intValue();
				//}
			} else {
				return -1;
			}
		} catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}

	@Override
	public int saveAdvertisementVideo(BannersDetails banner) {
		try{
			PreparedStatementCreator creator = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement insert = con.prepareStatement(INSERT_BANNER,
							Statement.RETURN_GENERATED_KEYS);
					insert.setString(1, banner.getURL());
					insert.setString(2, banner.getUserType());
					insert.setString(3, banner.getProductId());
					return insert;
				}
			};
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(creator, keyHolder);
			return keyHolder.getKey().intValue();
		}catch (Exception e) {
			logger.error(e, e);
			return -1;
		}
	}
}
