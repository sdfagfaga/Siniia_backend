package com.siniia.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.siniia.app.dao.OrderDAO;
import com.siniia.app.dao.ProductsDAO;
import com.siniia.app.dao.UserProfileDAO;
import com.siniia.app.dbo.BannersDetails;
import com.siniia.app.dbo.LoginBean;
import com.siniia.app.dbo.OrderDetails;
import com.siniia.app.dbo.OrdersReportsData;
import com.siniia.app.dbo.ProductDetails;
import com.siniia.app.dbo.UserAddress;
import com.siniia.app.dbo.productCategoriesMeta;
import com.siniia.app.utils.ActionUtils;
import com.siniia.app.utils.S3Utils;

@Controller
public class DashboardController {

	private static final Log logger = LogFactory.getLog(DashboardController.class);
	
	private Gson gson = new Gson();

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private ProductsDAO productDAO;


	@Autowired
	private UserProfileDAO userProfileDAO;

	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public String homePagesearch(HttpServletRequest request, Model model) {
		try {
			LoginBean user = (LoginBean) request.getSession().getAttribute("user");
			if (user != null) {
				ProductDetails prodDetails = new ProductDetails();
				List<productCategoriesMeta> prodCategories = new ArrayList<productCategoriesMeta>();
				prodCategories = productDAO.getAllProductCategoriesMeta();
				model.addAttribute("productDetailsForm", prodDetails);
				model.addAttribute("prodCategories", prodCategories);
				return "home";
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/getProductNameFromCategory", method = RequestMethod.GET)
	public @ResponseBody List<String> getProductNameFromCategory(HttpServletRequest request, String prodCategory,
			Model model) {
		try {
			LoginBean user = (LoginBean) request.getSession().getAttribute("user");
			if (user != null) {
				List<String> productNames = productDAO.getProductNamesByCategory(prodCategory);
				if (productNames != null && productNames.size() > 0) {
					model.addAttribute("prodNames", productNames);
					return productNames;
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@RequestMapping(value = "/getProductTypeFromNameAndCategory", method = RequestMethod.GET)
	public @ResponseBody List<String> getProductTypeFromNameAndCategory(HttpServletRequest request, String prodCategory,
			String prodName, Model model) {
		List<String> productType = new ArrayList<String>();
		try {
			LoginBean user = (LoginBean) request.getSession().getAttribute("user");
			if (user != null) {
				List<String> productTypes = productDAO.getProductTypeByNameAndCategory(prodCategory, prodName);
				if (productTypes != null && productTypes.size() > 0) {
					for (String prodTypeString : productTypes) {
						if (prodTypeString.contains(",")) {
							String[] commaSeperatedData = prodTypeString.split(",");
							for (int i = 0; i < commaSeperatedData.length; i++) {
								productType.add(commaSeperatedData[i]);
							}
						} else {
							productType.add(prodTypeString);
						}
					}
					model.addAttribute("prodTypes", productType);
					return productType;
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@RequestMapping(value = "/getProductUnitsFromNameAndCategory", method = RequestMethod.GET)
	public @ResponseBody List<String> getProductUnitsFromNameAndCategory(HttpServletRequest request,
			String prodCategory, String prodName, Model model) {
		List<String> productUnit = new ArrayList<String>();
		try {
			LoginBean user = (LoginBean) request.getSession().getAttribute("user");
			if (user != null) {
				List<String> productUnits = productDAO.getProductUnitsByNameAndCategory(prodCategory, prodName);
				if (productUnits != null && productUnits.size() > 0) {
					for (String prodUnitsString : productUnits) {
						if (prodUnitsString.contains(",")) {
							String[] commaSeperatedData = prodUnitsString.split(",");
							for (int i = 0; i < commaSeperatedData.length; i++) {
								productUnit.add(commaSeperatedData[i]);
							}
						} else {
							productUnit.add(prodUnitsString);
						}
					}
					model.addAttribute("prodUnits", productUnit);
					return productUnit;
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@RequestMapping(value = "/getProductGradeFromNameAndCategory", method = RequestMethod.GET)
	public @ResponseBody List<String> getProductGradeFromNameAndCategory(HttpServletRequest request,
			String prodCategory, String prodName, Model model) {
		List<String> productGrade = new ArrayList<String>();
		try {
			LoginBean user = (LoginBean) request.getSession().getAttribute("user");
			if (user != null) {
				List<String> productGrades = productDAO.getProductGradeByNameAndCategory(prodCategory, prodName);
				if (productGrades != null && productGrades.size() > 0) {
					for (String prodGradesString : productGrades) {
						if (prodGradesString.contains(",")) {
							String[] commaSeperatedData = prodGradesString.split(",");
							for (int i = 0; i < commaSeperatedData.length; i++) {
								productGrade.add(commaSeperatedData[i]);
							}
						} else {
							productGrade.add(prodGradesString);
						}
					}
					model.addAttribute("prodGrades", productGrade);
					return productGrade;
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

	@RequestMapping(value = "/addProducts", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
	public String addProducts(HttpServletRequest request,Model model,
			@ModelAttribute("productDetailsForm") ProductDetails productDetails) throws JSONException {
		try {
			String advertisementUrl = null;
			logger.info(productDetails.toString());
			if (productDetails != null) {
				productDetails.setProductOwenerID(9999);
				productDetails.setProductSubName(productDetails.getProductName());
				productDetails.setProductStatus(1);
				if (productDetails.getUploadedImage() != null) {
					for (MultipartFile uploadedImage : productDetails.getUploadedImage()) {
						if (uploadedImage != null && uploadedImage.getSize() > 0) {
							String fileName = uploadedImage.getOriginalFilename();
							String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
							String imageName = fileName.substring(0, fileName.lastIndexOf('.'));
							LocalDateTime now = LocalDateTime.now();
							DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
							String formatDateTime = now.format(format);
							String keyName = imageName + "_" + formatDateTime + "." + fileType.toUpperCase();
							logger.info("image key : " + keyName);
							String imageUrl = S3Utils.uploadByteArray(keyName, uploadedImage.getBytes(),
									uploadedImage.getContentType());
							productDetails.setThumbImageURL(imageUrl);
						}
					}
				}
				
				if (productDetails.getAdvertisementVideo() != null) {
					for (MultipartFile uploadedImage : productDetails.getAdvertisementVideo()) {
						if (uploadedImage != null && uploadedImage.getSize() > 0) {
							String fileName = uploadedImage.getOriginalFilename();
							String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
							String imageName = fileName.substring(0, fileName.lastIndexOf('.'));
							LocalDateTime now = LocalDateTime.now();
							DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
							String formatDateTime = now.format(format);
							String keyName = imageName + "_" + formatDateTime + "." + fileType.toUpperCase();
							logger.info("image key : " + keyName);
							advertisementUrl = S3Utils.uploadByteArray(keyName, uploadedImage.getBytes(),
									uploadedImage.getContentType());
							
						}
					}
				}
			}
			long id = productDAO.saveProducts(productDetails);
			if (id == -1) {
				model.addAttribute("errorMessage", "Failed to save Product");
			} else {
				BannersDetails bannerD = new BannersDetails();
				bannerD.setProductId(id+"");
				bannerD.setURL(advertisementUrl);
				bannerD.setUserType(1+"");
				productDAO.saveAdvertisementVideo(bannerD);
				model.addAttribute("errorMessage", "Successfully added Product");
			}
			ProductDetails prodDetails = new ProductDetails();
			List<productCategoriesMeta> prodCategories = new ArrayList<productCategoriesMeta>();
			prodCategories = productDAO.getAllProductCategoriesMeta();
			model.addAttribute("productDetailsForm", prodDetails);
			model.addAttribute("prodCategories", prodCategories);
		} catch (Exception e) {
			logger.error(e, e);
			model.addAttribute("errorMessage", "Failed to save Product");
		}
		return "home";
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginProcess(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute(value = "userForm") LoginBean loginBean, Model model) throws IOException {
		Properties prop = new Properties();
		InputStream inputStream = ActionUtils.class.getClassLoader().getResourceAsStream("application.properties");
		prop.load(inputStream);
		String userName = prop.getProperty("dreamcric.admin.login.username");
		String password = prop.getProperty("dreamcric.admin.login.password");
		if (loginBean.getUserName().equalsIgnoreCase(userName) && loginBean.getPassword().equalsIgnoreCase(password)) {
			request.getSession().setAttribute("user", loginBean);
			return "redirect:homepage";
		} else {
			model.addAttribute("errorMsg", "Please enter valid credentails");
			return "userlogin";
		}
	}

	@RequestMapping(value = "/orderDashboard", method = RequestMethod.GET)
	public String edituserDashboard(HttpServletRequest request, Model model) {
		try {
			LoginBean user = (LoginBean) request.getSession().getAttribute("user");
			if (user != null) {
				model.addAttribute("OrderReportsData", new OrdersReportsData());
				return "orderDashboard";
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute("user");
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/getUserReportDetails", method = RequestMethod.POST, produces = "application/json")
	public String getUserReportDetails(@ModelAttribute("OrderReportsData") OrdersReportsData reportsData,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("OrderReportsData", reportsData);
			if (reportsData.getDate() != null && !reportsData.getDate().isEmpty()) {
				if (reportsData.getReportType().equalsIgnoreCase("1")) {
					String status = "P";
					List<OrderDetails> orders = orderDAO.getOrdersByStatus(status, reportsData.getDate());
					if (orders != null && orders.size() > 0) {
						for (OrderDetails orderdetails : orders) {
							ProductDetails prod = productDAO.checkProductExists(orderdetails.getProductId());
							if (prod != null) {
								orderdetails.setProductName(prod.getProductName());
							}
						}

						model.addAttribute("usersPanCardData", orders);
					}
				}
			} else {
				request.setAttribute("errorMessage", "Please Enter a Date to Process");
			}
		} catch (Exception e) {
			logger.error(e, e);
			request.setAttribute("errorMessage", "Some error in data, not able to process");
		}
		return "orderDashboard";
	}

	@RequestMapping(value = "/updateUserReportDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean updateUserReportDetails(@RequestParam(value = "userId", required = true) long userId,
			@RequestParam(value = "data", required = true) String data,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "status", required = true) String status, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (userId != 0) {
				if (data != null && !data.isEmpty()) {
					if (type.equalsIgnoreCase("pan")) {
						boolean updatedPanCardFlag = orderDAO.updatePayOutStatus(userId, data, status);
						if (updatedPanCardFlag) {
							return true;
						}
					}
				} else {
					request.setAttribute("errorMessage", "Invalid Shipment ID , unable to process");
				}
			} else {
				request.setAttribute("errorMessage", "Invalid OrderId, unable to process");
			}
		} catch (Exception e) {
			logger.error(e, e);
			request.setAttribute("errorMessage", "Some error in data, not able to process");
		}
		return false;
	}
	
	@RequestMapping(value = "/advertisementsDashboard", method = RequestMethod.GET)
	public String advertisementsDashboard(HttpServletRequest request, Model model) {
		try {
			LoginBean user = (LoginBean) request.getSession().getAttribute("user");
			if (user != null) {
				BannersDetails bannerDetails = new BannersDetails();
				model.addAttribute("bannerDetailsForm", bannerDetails);
				return "advertisementsDashboard";
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/addBanner", method = RequestMethod.POST, produces = "application/json", consumes = "multipart/form-data")
	public String addBanner(HttpServletRequest request,Model model,
			@ModelAttribute("bannerDetailsForm") BannersDetails bannerDetails) throws JSONException  {
		try {
			String advertisementUrl = null;
			logger.info(bannerDetails.toString());
			LoginBean user = (LoginBean) request.getSession().getAttribute("user");
			if (user != null) {
				
			if (bannerDetails.getUploadedImage() != null) {
				for (MultipartFile uploadedImage : bannerDetails.getUploadedImage()) {
					if (uploadedImage != null && uploadedImage.getSize() > 0) {
						String fileName = uploadedImage.getOriginalFilename();
						String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
						String imageName = fileName.substring(0, fileName.lastIndexOf('.'));
						LocalDateTime now = LocalDateTime.now();
						DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
						String formatDateTime = now.format(format);
						String keyName = imageName + "_" + formatDateTime + "." + fileType.toUpperCase();
						logger.info("video key : " + keyName);
						advertisementUrl = S3Utils.uploadByteArray(keyName, uploadedImage.getBytes(),
								uploadedImage.getContentType());
						
					}
				}
			}
				
			bannerDetails.setProductId("-9999");
			bannerDetails.setURL(advertisementUrl);
			bannerDetails.setUserType(1+"");
			productDAO.saveAdvertisementVideo(bannerDetails);
				model.addAttribute("errorMessage", "Successfully added Video");
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "advertisementsDashboard";
	}
}
