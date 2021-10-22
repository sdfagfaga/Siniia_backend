package com.siniia.app.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.siniia.app.dbo.BannersDetails;
import com.siniia.app.dbo.ProductCategories;
import com.siniia.app.dbo.ProductDetails;
import com.siniia.app.dbo.ProductImages;
import com.siniia.app.dbo.ProductType;
import com.siniia.app.dbo.UpdateProductData;
import com.siniia.app.dbo.productCategoriesMeta;

public interface ProductsDAO {

	public List<ProductDetails> getAllProducts();

	public int getProductsTableCount();

	public int saveProduct(ProductDetails product);

	public int updateProduct(ProductDetails product);

	public ProductDetails checkProductExists(int id);

	public List<ProductDetails> getProductsByUserId(int userId);

	public int updateProductQuantity(int productId, int quantity);

	public List<ProductImages> getAllProductImages();

	public ProductImages checkProductImagesExists(int id);

	public List<ProductDetails> checkProductListExists(int id);

	public List<ProductImages> checkProductImagesListExists(int id);

	public int updateProductImage(String Image, int id);

	public int getQuantityId(String quantityType);

	public List<ProductType> getAllProductTypes();

	public List<ProductDetails> getProductByProductType(String productType);

	public List<BannersDetails> getAllBanners();

	public List<ProductCategories> getAllProductCategories();

	public List<productCategoriesMeta> getAllProductCategoriesMeta();

	public List<ProductCategories> getAllProductCategoriesByMeta(String category);

	public int deleteProduct(int productId, int userId) throws DataAccessException;

	public int updateProductStatus(int productId, int userId, String status);

	public List<ProductDetails> getAllProductsByUserId(int userId);
	
	public int updateProductDetails(UpdateProductData product);
	
	public List<ProductDetails> getAllProductsOTHER(int userId);
	
	public List<ProductDetails> getAllProductsINDIAUSA(int userId);
	
	public int getProductsTableCountINDIAUSA(int userId);
	
	public int getProductsTableCountOTHER(int userId);
	
	public List<ProductDetails> getProductByProductTypeINDIAUSA(String productType,int userId);
	
	public List<ProductDetails> getProductByProductTypeOTHER(String productType,int userId);
	
	public int checkProductsExists(int id);
	
	List<String> getProductNamesByCategory(String productCategory);
	
	public List<String> getProductTypeByNameAndCategory(String productCategory,String productName);
	
	public List<String> getProductGradeByNameAndCategory(String productCategory,String productName);
	
	public List<String> getProductUnitsByNameAndCategory(String productCategory,String productName);
	
	public int saveProducts(ProductDetails product);
	
	public int saveAdvertisementVideo(BannersDetails banner);
}
