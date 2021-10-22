-- MySQL dump 10.13  Distrib 5.7.34, for Linux (x86_64)
--
-- Host: localhost    Database: siniia
-- ------------------------------------------------------
-- Server version	5.7.34-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `mobileCountry` varchar(20) DEFAULT NULL,
  `mobileNumber` varchar(45) DEFAULT NULL,
  `profilePicUrl` varchar(256) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isOTPVerified` int(1) NOT NULL DEFAULT '0',
  `otp` int(11) DEFAULT '0',
  `LastLocationLat` varchar(100) DEFAULT NULL,
  `LastLocationLong` varchar(100) DEFAULT NULL,
  `notificationsCount` int(11) NOT NULL DEFAULT '0',
  `basketOrdersCount` int(11) DEFAULT '0',
  `userType` varchar(30) DEFAULT NULL,
  `isProfileComplete` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `otp` (`id`,`otp`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (2,'phanivaddamani@gmail.com','3c1b27f8f9234ea07e855376ac9cf742','Phani','91','9000103955','https://siniiatest.s3.amazonaws.com/user_2_banner1_09012021203730.PNG','2020-12-31 12:44:25',1,1385,NULL,NULL,0,0,'1',0),(5,'bapuvvari@gmail.com','','Bala Puvvari','91','9676738936','https://siniiatest.s3.amazonaws.com/user_5_image_20012021200450.JPEG','2021-01-03 05:55:41',1,1051,NULL,NULL,0,0,'1',0),(11,'aliwig@gmail.com','','Anne','1','12025037374','https://siniiatest.s3.amazonaws.com/user_11_image_16042021162902.JPEG','2021-01-24 05:49:39',1,1618,NULL,NULL,0,0,'3',0),(29,'murthygadd@gmail.com','','murthy gaddi','91','19010932605',NULL,'2021-04-20 11:42:50',1,2342,NULL,NULL,0,0,'1',0),(30,'nanina@gmail.com','','Naresh','91','19912429239',NULL,'2021-04-20 11:53:25',1,2196,NULL,NULL,0,0,'2',0),(33,NULL,'',NULL,'1','15025037374',NULL,'2021-04-20 15:33:51',0,1053,NULL,NULL,0,0,NULL,0),(37,'bala.puvvari@gmail.com','42863274caa8bd59bdc0fa91d6227858','Bala Puvvari ','91','9676738936',NULL,'2021-04-28 08:47:16',1,2702,NULL,NULL,0,0,'1',0),(38,'naninar@gmail.com','fcb38489b1190a05dc6a51fa2f98998b','Naresh','91','9912429239',NULL,'2021-05-14 13:41:00',1,2781,NULL,NULL,0,0,'2',0),(39,'aliwigs@gmail.com','96e976f42cb07f762f8acb5bc11372af','Anne Liwigs','1','2025037374',NULL,'2021-05-18 07:13:11',1,1199,NULL,NULL,0,0,'2',0),(40,'playstorecnxnew@gmail.com','25d38330f3f5e0cffca116f06c6dd132','playsrore cnx','91','9154191151',NULL,'2021-05-22 06:16:07',1,1150,NULL,NULL,0,0,'3',0),(43,'murthygaddi@gmail.com','5e1c97fa343c879103613628d05f011c','Murthy','91','9010932605',NULL,'2021-05-24 12:55:15',1,2217,NULL,NULL,0,0,'1',0),(46,'test@gmail.com','098f6bcd4621d373cade4e832627b4f6','Test','91','9676738934',NULL,'2021-05-25 07:57:57',1,2135,NULL,NULL,0,0,'1',0),(47,'ysuparna93@gmail.com','1ce5fc0ff3bf43ab3719f2c57a625d89','Suparna','91','6281956145',NULL,'2021-05-25 09:27:27',1,2717,NULL,NULL,0,0,'1',0);
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banners`
--

DROP TABLE IF EXISTS `banners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `banners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `URL` varchar(2000) DEFAULT NULL,
  `UserType` int(11) DEFAULT NULL,
  `StartTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `endTime` timestamp NULL DEFAULT NULL,
  `productId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banners`
--

LOCK TABLES `banners` WRITE;
/*!40000 ALTER TABLE `banners` DISABLE KEYS */;
INSERT INTO `banners` VALUES (1,'https://siniiatest.s3.ap-south-1.amazonaws.com/banner1.png',1,'2021-02-18 08:22:59',NULL,'18'),(2,'https://siniiatest.s3.ap-south-1.amazonaws.com/Corn.png',1,'2021-02-18 07:45:58',NULL,'3'),(3,'https://siniiatest.s3.ap-south-1.amazonaws.com/coconut.png',1,'2021-02-18 07:46:10',NULL,'16'),(4,'https://siniiatest.s3.ap-south-1.amazonaws.com/Mango.png',1,'2021-02-18 07:46:22',NULL,'13'),(5,'https://siniiatest.s3.ap-south-1.amazonaws.com/Eggs.png',1,'2021-02-18 07:46:32',NULL,'5'),(6,'https://siniiatest.s3.ap-south-1.amazonaws.com/Rice.png',1,'2021-02-18 08:23:03',NULL,'1'),(7,'https://siniiatest.s3.ap-south-1.amazonaws.com/Banana+Banner.png',1,'2021-03-02 08:14:02',NULL,'23'),(8,'https://siniiatest.s3.ap-south-1.amazonaws.com/Herbs+Banner.png',1,'2021-03-02 08:24:36',NULL,'22'),(9,'https://siniiatest.s3.ap-south-1.amazonaws.com/Fish+Banner.png',1,'2021-03-02 08:26:02',NULL,'24');
/*!40000 ALTER TABLE `banners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (5,7,2,2,'2021-01-09 13:03:46'),(6,7,3,2,'2021-01-09 13:04:08'),(7,7,4,1,'2021-01-09 13:39:47'),(16,12,10,1,'2021-01-24 06:17:23'),(35,10,4,4,'2021-03-02 16:50:49'),(36,16,2,8,'2021-03-03 08:24:46'),(38,16,3,1,'2021-03-03 09:02:53'),(39,16,59,1,'2021-03-03 09:11:06'),(43,16,4,2,'2021-03-03 09:16:16'),(44,10,13,1,'2021-03-03 12:22:48'),(47,10,2,1,'2021-03-11 02:22:43'),(48,5,16,1,'2021-04-09 09:43:07'),(52,11,22,1,'2021-04-16 04:55:56'),(53,29,2,2,'2021-04-20 11:55:39'),(54,29,3,1,'2021-04-20 11:55:50'),(55,30,2,1,'2021-04-20 11:58:10'),(56,30,4,1,'2021-04-20 12:03:20'),(57,29,6,1,'2021-05-06 05:00:14'),(58,37,2,1,'2021-05-20 08:04:39'),(59,38,2,1,'2021-05-25 12:49:34'),(60,43,3,1,'2021-05-25 12:50:13');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `devicetypedata`
--

DROP TABLE IF EXISTS `devicetypedata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devicetypedata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `versionName` varchar(50) NOT NULL,
  `versionCode` int(11) NOT NULL,
  `deviceType` varchar(150) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devicetypedata`
--

LOCK TABLES `devicetypedata` WRITE;
/*!40000 ALTER TABLE `devicetypedata` DISABLE KEYS */;
INSERT INTO `devicetypedata` VALUES (1,2,'1.0.3',4,'Android','2021-05-28 10:31:25'),(2,5,'1.0.2',3,'Android','2021-05-23 05:08:06'),(3,10,'1.0',1,'Android','2021-04-20 09:18:22'),(4,11,'1.0',1,'Android','2021-05-18 07:06:13'),(5,17,'1.0',1,'Android','2021-03-14 15:39:08'),(6,21,'1.0',1,'Android','2021-03-12 07:38:51'),(7,26,'1.0',1,'Android','2021-04-16 11:34:42'),(8,27,'1.0',1,'Android','2021-04-16 11:49:43'),(9,29,'1.0',1,'Android','2021-05-10 07:00:05'),(10,30,'1.0',1,'Android','2021-05-14 13:38:45'),(11,31,'1.0',1,'Android','2021-04-20 12:20:16'),(12,32,'1.0',1,'Android','2021-04-20 12:29:02'),(13,34,'1.0',1,'Android','2021-04-26 18:01:07'),(14,37,'1.0.1',2,'Android','2021-05-21 11:38:35'),(15,38,'1.0.2',3,'Android','2021-05-25 12:49:22'),(16,39,'1.0',1,'Android','2021-05-18 07:15:00'),(17,40,'1.0.1',2,'Android','2021-05-22 06:16:41'),(18,41,'1.0.2',3,'Android','2021-05-23 05:07:10'),(19,42,'1.0.2',3,'Android','2021-05-23 05:24:59'),(20,44,'1.0.2',3,'Android','2021-05-24 13:12:38'),(21,45,'1.0.2',3,'Android','2021-05-25 07:50:19'),(22,46,'1.0.2',3,'Android','2021-05-27 00:44:53'),(23,47,'1.0.2',3,'Android','2021-05-25 09:28:01'),(24,43,'1.0.3',4,'Android','2021-06-08 04:29:20');
/*!40000 ALTER TABLE `devicetypedata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donationData`
--

DROP TABLE IF EXISTS `donationData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donationData` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `paymentDetails` varchar(2000) NOT NULL,
  `paymentAmount` double NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donationData`
--

LOCK TABLES `donationData` WRITE;
/*!40000 ALTER TABLE `donationData` DISABLE KEYS */;
INSERT INTO `donationData` VALUES (1,5,'test',1203.65,'2021-02-25 11:28:02'),(2,5,'{\n    \"client\": {\n        \"environment\": \"mock\",\n        \"paypal_sdk_version\": \"2.15.3\",\n        \"platform\": \"Android\",\n        \"product_name\": \"PayPal-Android-SDK\"\n    },\n    \"response\": {\n        \"create_time\": \"2014-02-12T22:29:49Z\",\n        \"id\": \"PAY-6RV70583SB702805EKEYSZ6Y\",\n        \"intent\": \"sale\",\n        \"state\": \"approved\"\n    },\n    \"response_type\": \"payment\"\n}',100,'2021-02-26 07:50:59');
/*!40000 ALTER TABLE `donationData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locationBasedShipper`
--

DROP TABLE IF EXISTS `locationBasedShipper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `locationBasedShipper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(50) DEFAULT NULL,
  `shippers` varchar(500) DEFAULT NULL,
  `shipperLink` varchar(1000) DEFAULT NULL,
  `shipperContact` varchar(100) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locationBasedShipper`
--

LOCK TABLES `locationBasedShipper` WRITE;
/*!40000 ALTER TABLE `locationBasedShipper` DISABLE KEYS */;
INSERT INTO `locationBasedShipper` VALUES (1,'INDIA','pickupnow','https://pickupnow.com/',NULL,'2021-02-22 11:45:59'),(2,'AFRICA','pickupnow','https://pickupnow.com/',NULL,'2021-02-22 11:45:59'),(3,'USA','pickupnow','https://pickupnow.com/',NULL,'2021-02-22 11:47:32'),(4,'INDIA','dtdc','https://www.dtdc.in/',NULL,'2021-02-22 11:47:32'),(5,'INDIA','Professional Couriers','https://www.tpcindia.com/',NULL,'2021-02-22 11:47:32'),(6,'USA','Parcel Monkey','https://www.parcelmonkey.com/',NULL,'2021-02-25 05:54:59'),(7,'AFRICA','HERI WORLD WIDE','https://www.heriworldwide.com/',NULL,'2021-02-25 05:54:59');
/*!40000 ALTER TABLE `locationBasedShipper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newsletter`
--

DROP TABLE IF EXISTS `newsletter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newsletter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newsletter`
--

LOCK TABLES `newsletter` WRITE;
/*!40000 ALTER TABLE `newsletter` DISABLE KEYS */;
INSERT INTO `newsletter` VALUES (1,4,'Phani','phaniv@gmail.com','2021-02-25 08:50:15'),(2,5,'Phani','phaniv@gmail.com','2021-02-25 09:02:29'),(3,5,'Bala Puvvari','bala.puvvari@gmail.com','2021-02-25 13:06:07'),(4,10,'murthy gaddi','murthygaddi@gmail.com','2021-02-27 16:41:22'),(5,2,'Phani','phanivaddamani@gmail.com','2021-03-03 08:03:20');
/*!40000 ALTER TABLE `newsletter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` int(11) NOT NULL,
  `productStatus` int(11) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `availableAddressId` int(11) DEFAULT NULL,
  `deliveryAddressID` int(11) DEFAULT NULL,
  `shipmentId` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `quantityTypeId` int(11) DEFAULT NULL,
  `quantityPrice` double DEFAULT NULL,
  `deliveryStatus` varchar(30) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `paymentDetails` varchar(500) DEFAULT NULL,
  `paymentAmount` double DEFAULT NULL,
  `address1` varchar(100) DEFAULT NULL,
  `address2` varchar(100) DEFAULT NULL,
  `addressLat` varchar(50) DEFAULT NULL,
  `addressLong` varchar(50) DEFAULT NULL,
  `landmark` varchar(100) DEFAULT NULL,
  `pinCode` varchar(10) DEFAULT NULL,
  `paymentType` varchar(100) DEFAULT NULL,
  `isPayoutDone` varchar(1) NOT NULL DEFAULT 'P',
  `shipmentObjectId` varchar(500) DEFAULT NULL,
  `shipmentTrackingId` varchar(500) DEFAULT NULL,
  `shipmentStatus` varchar(500) NOT NULL DEFAULT 'Pending',
  `provider` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (5,2,2,46,2,0,0,10,-1,150,'orderPlaced','2021-05-27 00:42:21','{\n    \"client\": {\n        \"environment\": \"mock\",\n        \"paypal_sdk_version\": \"2.15.3\",\n        \"platform\": \"Android\",\n        \"product_name\": \"PayPal-Android-SDK\"\n    },\n    \"response\": {\n        \"create_time\": \"2014-02-12T22:29:49Z\",\n        \"id\": \"PAY-6RV70583SB702805EKEYSZ6Y\",\n        \"intent\": \"sale\",\n        \"state\": \"approved\"\n    },\n    \"response_type\": \"payment\"\n}',150,'Test','Test','','','Test','523245','pay_pal','P','siniainfo059@gmail.com',NULL,'Pending',NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderstatus_meta`
--

DROP TABLE IF EXISTS `orderstatus_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderstatus_meta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shippingWaiting` varchar(30) NOT NULL,
  `shippingStarted` varchar(30) NOT NULL,
  `delivered` varchar(30) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderstatus_meta`
--

LOCK TABLES `orderstatus_meta` WRITE;
/*!40000 ALTER TABLE `orderstatus_meta` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderstatus_meta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paypalAccountsData`
--

DROP TABLE IF EXISTS `paypalAccountsData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paypalAccountsData` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `payPalAccountId` varchar(100) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paypalAccountsData`
--

LOCK TABLES `paypalAccountsData` WRITE;
/*!40000 ALTER TABLE `paypalAccountsData` DISABLE KEYS */;
INSERT INTO `paypalAccountsData` VALUES (1,5,'phaniv1235','2021-02-25 11:28:29'),(2,5,'bala@123','2021-02-26 09:36:45'),(3,16,'66','2021-03-03 09:07:06'),(4,5,'test','2021-04-09 01:22:43');
/*!40000 ALTER TABLE `paypalAccountsData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_categories`
--

DROP TABLE IF EXISTS `product_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productCategory` varchar(50) NOT NULL,
  `productName` varchar(2000) NOT NULL,
  `productType` varchar(2000) NOT NULL,
  `productGrade` varchar(2000) NOT NULL,
  `productUnits` varchar(2000) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_categories`
--

LOCK TABLES `product_categories` WRITE;
/*!40000 ALTER TABLE `product_categories` DISABLE KEYS */;
INSERT INTO `product_categories` VALUES (1,'Grains','Rice','Sonamasori','A+,A,B','Kgs,Tonns','2021-01-20 08:07:44'),(2,'Fruits ','Apple','Kashmiri','A+,A','pieces,Dozens','2021-01-20 08:13:59'),(3,'Grains','Wheat','SuperSelect,Sharbathi,Normal','A+,A','kgs,Tonns','2021-01-20 08:41:58'),(4,'Grains','Corn','SuperSelect','A,A+','pieces','2021-01-20 08:41:58'),(5,'Fruits','Mango','Bhanginpalli,Himayath,Rasalu,Beneshan','A+,A,B+,B','kgs','2021-01-20 08:43:59'),(6,'Fruits','Bananas','Chekkerakheli,Amruthapani,normal','A+,A','Dozens','2021-01-20 08:43:59'),(7,'Meat','Eggs','type','A+,A','pieces','2021-01-29 14:35:34'),(8,'Grains','Barley','Staple Food','A+,A','Kgs,Tonns','2021-01-31 14:29:30'),(10,'Grains','Soybeans','Staple Food','A,A+','Kgs,Tonns','2021-01-31 14:31:13'),(11,'Grains','Oat','Staple Food','A+,A','Kgs,Tonns','2021-01-31 14:31:13'),(12,'Grains','Other','Staple Food','A+,A','Kgs,Tonns','2021-01-31 14:32:55'),(13,'Grains','Sorgham','Staple Food','A+,A','Kgs,Tonns','2021-01-31 14:32:55'),(14,'Grains','Flex','Staple Food','A+,A','Kgs,Tonns','2021-01-31 14:33:15'),(15,'Grains','Teff','Staple Food','A+,A','Kgs,Tonns','2021-01-31 14:33:15'),(16,'Meat','hen','Staple Food','A+,A','Kgs','2021-01-31 14:37:07'),(17,'Meat','in shell','Staple Food','A+,A','pieces','2021-01-31 14:37:07'),(18,'Meat','Chicken','Staple Food','A+,A','Kgs','2021-01-31 14:37:07'),(19,'Meat','Cattle','Staple Food','A+,A','Kgs','2021-01-31 14:37:07'),(20,'Meat','Cows Milk','Staple Food','A+','Litres,Gallons','2021-01-31 14:42:57'),(21,'Meat','Whole Fresh','Staple Food','A+','Kgs','2021-01-31 14:42:57'),(22,'Meat','Sheep Meat','Staple Food','A+,A','Kgs','2021-01-31 14:42:57'),(23,'Meat','Buffalo Milk','Staple Food','A+,A','Litres,Gallons','2021-01-31 14:42:57'),(24,'Meat','Goat Meat','Staple Food','A+,A','Kgs','2021-01-31 14:42:57'),(25,'Meat','other bird','Staple Food','A+,A','Kgs','2021-01-31 14:42:57'),(26,'Meat','Other','Staple Food','A+,A','Kgs','2021-01-31 14:42:57'),(27,'Legume','Peas','Staple Food','A+,A','Kgs','2021-01-31 14:44:27'),(28,'Legume','Grapes','Staple Food','A+,A','Kgs','2021-01-31 14:44:27'),(29,'Legume','Other','Staple Food','A+,A','Kgs','2021-01-31 14:45:04'),(30,'Fruits','Passimon','Staple Food','A+,A','Pieces','2021-01-31 14:45:04'),(31,'Fruits','Watermelons','Staple Food','A+,A','Pieces','2021-01-31 15:15:51'),(32,'Fruits','Guava','Staple Food','A+,A','Kgs','2021-01-31 15:15:51'),(33,'Fruits','Oranges','Staple Food','A+,A','Pieces','2021-01-31 15:15:51'),(34,'Fruits','Tangerines','Staple Food','A+,A','Pieces','2021-01-31 15:15:51'),(35,'Fruits','Mandarins','Staple Food','A+,A','Kgs','2021-01-31 15:15:51'),(36,'Fruits','Clementines','Staple Food','A+,A','Kgs','2021-01-31 15:15:51'),(37,'Fruits','Satsumas','Staple Food','A+,A','Kgs','2021-01-31 15:15:51'),(38,'Fruits','Peaches','Staple Food','A+,A','Kgs','2021-01-31 15:15:51'),(39,'Fruits','nectarines','Staple Food','A+','Kgs','2021-01-31 15:15:51'),(40,'Fruits','Strawberries','Staple Food','A+','Pieces','2021-01-31 15:15:51'),(41,'Fruits','Raspberries','Staple Food','A+,A','Pieces','2021-01-31 15:15:51'),(42,'Fruits','Blackberry','Staple Food','A+,A','Kgs','2021-01-31 15:15:51'),(43,'Fruits','PineApple','Staple Food','A+,A','Kgs','2021-01-31 15:15:51'),(44,'Fruits','Dates','Staple Food','A+,A','Kgs','2021-01-31 15:15:51'),(45,'Fruits','Raisin','Staple Food','A+,A','Kgs','2021-01-31 15:15:51'),(46,'Fruits','Figs','Staple Food','A+,A','Kgs','2021-01-31 15:15:51'),(47,'Fruits','Prune','Staple Food','A+','Kgs','2021-01-31 15:15:51'),(48,'Fruits','Peach','Staple Food','A+','Kgs','2021-01-31 15:15:51'),(49,'Fruits','Apricot','Staple Food','A+','Kgs','2021-01-31 15:15:51'),(50,'Fruits','Cherry','Staple Food','A+','Kgs','2021-01-31 15:15:51'),(51,'Fruits','Cranberries','Staple Food','A+,A','Kgs','2021-01-31 15:16:36'),(52,'Fruits','Other','Staple Food','A+,A','Kgs','2021-01-31 15:16:36'),(53,'Vegetables','Tomatoes','Staple Food','A+,A','Kgs','2021-01-31 15:44:21'),(54,'Vegetables','Potatoes','Staple Food','A+,A','Kgs','2021-01-31 15:44:21'),(55,'Vegetables','Spinach','Staple Food','A+','Pieces','2021-01-31 15:44:21'),(56,'Vegetables','Rapeseed','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(57,'Vegetables','Onions','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(58,'Vegetables','Cucumbers and Gherkins','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(59,'Vegetables','Groundnuts with shell\r\n','Staple Food','A+,A','Kgs','2021-01-31 15:44:21'),(60,'Vegetables','Chillis','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(61,'Vegetables','Green pepper','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(62,'Vegetables','Sweet Potatoes','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(63,'Vegetables','Eggplants aubergines','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(64,'Vegetables','Cucumbers','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(65,'Vegetables','Avocado','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(66,'Vegetables','Garlic','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(67,'Vegetables','Oil palm fruit','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(68,'Vegetables','Cassava yuca','Staple Food','A+','Kgs','2021-01-31 15:44:21'),(69,'Vegetables','Palm oil','Staple Food','A+','Litres,Gallons','2021-01-31 15:44:21'),(70,'Vegetables','Seed Cotton','Staple Food','A+,A','Kgs','2021-01-31 15:44:21'),(71,'Vegetables','Cabbages','Staple Food','A+,A','Kgs','2021-01-31 15:44:21'),(72,'Vegetables','Lettuce','Staple Food','A+,A','Kgs','2021-01-31 15:44:21'),(73,'Vegetables','Chicory','Staple Food','A+,A','Kgs','2021-01-31 15:46:09'),(74,'Vegetables','Peas Green','Staple Food','A+,A','Kgs','2021-01-31 15:46:09'),(75,'Vegetables','Mushrooms','Staple Food','A+,A','Kgs','2021-01-31 15:46:09'),(76,'Vegetables','Other','Staple Food','A+,A','Kgs','2021-01-31 15:46:09'),(77,'Herbs','Cilantro','Staple Food','A+','pieces','2021-01-31 15:51:15'),(78,'Herbs','Persely','Staple Food','A+,A','pieces','2021-01-31 15:51:15'),(79,'Herbs','Rosemary','Staple Food','A+,A','pieces','2021-01-31 15:51:15'),(80,'Herbs','Oregano','Staple Food','A+,A','pieces','2021-01-31 15:51:15'),(81,'Herbs','Mint','Staple Food','A+','pieces','2021-01-31 15:51:15'),(82,'Herbs','Lemon Grass','Staple Food','A+','Pieces','2021-01-31 15:51:15'),(83,'Herbs','Caraway','Staple Food','A+','pieces','2021-01-31 15:51:15'),(84,'Herbs','Fennel','Staple Food','A+','pieces','2021-01-31 15:51:15'),(85,'Herbs','Paprika','Staple Food','A+','pieces','2021-01-31 15:51:15'),(86,'Herbs','Cinammon','Staple Food','A+','pieces','2021-01-31 15:51:15'),(87,'Herbs','OTHER','Staple Food','A+','pieces','2021-01-31 15:54:57'),(88,'Spices','Cardamom','Staple Food','A+,A','Kgs','2021-01-31 15:54:57'),(89,'Spices','Cumin','Staple Food','A+,A','Kgs','2021-01-31 15:54:57'),(90,'Spices','Black Pepper','Staple Food','A+,A','Kgs','2021-01-31 15:54:57'),(91,'Spices','Anise','Staple Food','A+','Kgs','2021-01-31 15:54:57'),(92,'Spices','Nutmeg','Staple Food','A+','Kgs','2021-01-31 15:54:57'),(93,'Spices','Majorum','Staple Food','A+','Kgs','2021-01-31 15:54:57'),(94,'Spices','Cloves','Staple Food','A+','Kgs','2021-01-31 15:54:57'),(95,'Spices','OTHER','Staple Food','A+','Kgs','2021-01-31 15:54:57'),(96,'Nuts','Groundnuts','Staple Food','A+,A','Kgs','2021-01-31 15:57:35'),(97,'Nuts','Cashew','Staple Food','A+,A','Kgs','2021-01-31 15:57:35'),(98,'Nuts','Almond','Staple Food','A+,A','Kgs','2021-01-31 15:59:14'),(99,'Nuts','Coconut','Staple Food','A+,A','Pieces','2021-01-31 15:59:14'),(100,'Nuts','Pistacchio','Staple Food','A+,A','Kgs','2021-01-31 15:59:14'),(101,'Nuts','Peacan','Staple Food','A+,A','Kgs','2021-01-31 15:59:14'),(102,'Nuts','Hezelnut','Staple Food','A+,A','Kgs','2021-01-31 15:59:14'),(103,'Nuts','Pine','Staple Food','A+,A','Kgs','2021-01-31 15:59:14'),(104,'Nuts','Chestnut','Staple Food','A+,A','Kgs','2021-01-31 15:59:14'),(105,'Nuts','Other','Staple Food','A+,A','Kgs','2021-01-31 15:59:14'),(106,'Beverages','Tea','Staple Food','A+','Pieces','2021-01-31 16:01:36'),(107,'Beverages','Coffee','Staple Food','A+','Pieces','2021-01-31 16:01:36'),(108,'Beverages','Edible Flowers','Staple Food','A+','Pieces','2021-01-31 16:01:36'),(109,'Beverages','Passion','Staple Food','A+','Pieces','2021-01-31 16:01:36'),(110,'Beverages','OTHER','Staple Food','A+','Pieces','2021-01-31 16:01:36'),(111,'Oils','Olives','Staple Food','A+','Pieces','2021-01-31 16:02:54'),(112,'Oils','Groundnuts','Staple Food','A+','Pieces','2021-01-31 16:02:54'),(113,'Oils','Coconut','Staple Food','A+','Pieces','2021-01-31 16:02:54'),(114,'Oils','Seseme','Staple Food','A+','Pieces','2021-01-31 16:02:54'),(115,'Oils','OTHER','Staple Food','A+','Pieces','2021-01-31 16:02:54'),(116,'Seeds','Sunflower seeds','Staple Food','A+,A','Kgs','2021-01-31 16:03:43'),(117,'Seeds','Seseme','Staple Food','A+,A','Kgs','2021-01-31 16:03:43'),(118,'Seeds','Other','Staple Food','A+,A','Kgs','2021-01-31 16:03:58');
/*!40000 ALTER TABLE `product_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category_meta`
--

DROP TABLE IF EXISTS `product_category_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_category_meta` (
  `id` int(11) NOT NULL,
  `productCategory` varchar(50) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category_meta`
--

LOCK TABLES `product_category_meta` WRITE;
/*!40000 ALTER TABLE `product_category_meta` DISABLE KEYS */;
INSERT INTO `product_category_meta` VALUES (1,'Grains','2021-01-20 09:37:54'),(2,'Fruits','2021-01-20 09:37:54'),(3,'Meat','2021-01-22 10:52:05'),(4,'Legume','2021-01-22 10:52:05'),(5,'Vegetables','2021-01-31 13:58:56'),(6,'Herbs','2021-01-31 13:58:56'),(7,'Nuts','2021-01-31 13:59:20'),(8,'Spices','2021-01-31 13:59:20'),(9,'Beverages','2021-01-31 14:01:19'),(10,'Oils','2021-01-31 14:01:19'),(11,'Seeds','2021-01-31 14:01:19'),(12,'Ship','2021-02-25 05:57:29');
/*!40000 ALTER TABLE `product_category_meta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` int(11) NOT NULL,
  `imageURLSmall` varchar(100) DEFAULT NULL,
  `imageURLMedium` varchar(100) DEFAULT NULL,
  `imageURLLarge` varchar(100) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,1,'https://siniiatest.s3.ap-south-1.amazonaws.com/ricesmall.png','https://siniiatest.s3.ap-south-1.amazonaws.com/rice.jpg','https://siniiatest.s3.ap-south-1.amazonaws.com/ricebig.png','2021-01-04 13:41:10');
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(200) DEFAULT NULL,
  `productName` varchar(60) DEFAULT NULL,
  `thumbImageURL` varchar(1000) DEFAULT NULL,
  `productSubName` varchar(30) DEFAULT NULL,
  `productType` varchar(60) DEFAULT NULL,
  `productGrade` varchar(30) DEFAULT NULL,
  `quantityType` varchar(30) DEFAULT NULL,
  `quantityPerUnit` int(11) DEFAULT NULL,
  `pricePerUnit` int(11) DEFAULT NULL,
  `minQuantity` int(11) DEFAULT NULL,
  `quantityAvailable` int(11) DEFAULT NULL,
  `highlight` varchar(300) DEFAULT NULL,
  `productDescription` varchar(2000) DEFAULT NULL,
  `availableAddressId` int(11) DEFAULT NULL,
  `productOwnerID` int(11) DEFAULT NULL,
  `productOwnerName` varchar(60) DEFAULT NULL,
  `productOwnerContact` varchar(50) DEFAULT NULL,
  `radius` varchar(50) DEFAULT NULL,
  `productStatus` int(11) DEFAULT '0',
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `address1` varchar(10000) DEFAULT NULL,
  `address2` varchar(2000) DEFAULT NULL,
  `addressLat` varchar(100) DEFAULT NULL,
  `addressLong` varchar(100) DEFAULT NULL,
  `landmark` varchar(2000) DEFAULT NULL,
  `pinCode` varchar(10) DEFAULT NULL,
  `city` varchar(300) NOT NULL,
  `state` varchar(300) NOT NULL,
  `country` varchar(300) NOT NULL,
  `length` double NOT NULL,
  `height` double DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `width` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Grains','Rice','https://siniiatest.s3.ap-south-1.amazonaws.com/rice.jpg,https://siniiatest.s3.ap-south-1.amazonaws.com/ricesmall.png,https://siniiatest.s3.ap-south-1.amazonaws.com/rice.jpg,https://siniiatest.s3.ap-south-1.amazonaws.com/ricebig.png','Rice','Sonamasori','A','Kgs',1,1250,100,490,'test','test',77,5,NULL,NULL,'10Kms',0,'2020-12-27 20:35:38','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,10,10,12),(2,'Fruits','Apple','https://siniiatest.s3.ap-south-1.amazonaws.com/apple.jpg','apple','Fruits','A+','Basket',30,15,10,494,'test','tasty apple',2,5,'TEST',NULL,'15Kms',1,'2021-01-04 07:55:26','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(3,'Cerals','Corn','https://siniiatest.s3.ap-south-1.amazonaws.com/MaizeThumb.png','maize','cerals','A','Tonn',1,80,1,24,'test,test','sweetest sweet corn',4,5,'Bala Puvvari',NULL,'200Km',1,'2021-01-04 13:20:51','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(4,'Fruits','Mango','https://siniiatest.s3.ap-south-1.amazonaws.com/mangoThumb.png','mango Bhanginapalli','Fruits','A+','Basket',30,50,15,355,'Tasty','sweet Mango',4,5,'Bala Puvvari',NULL,'5Kms',1,'2021-01-04 13:20:51','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(5,'Milk','Eggs','https://siniiatest.s3.ap-south-1.amazonaws.com/EggsThumb.png','Egg','Legume','A+','Dozen',12,15,10,-14,'Good for health','sunday hoya monday roj khao ande',2,5,'TEST',NULL,'12Kms',0,'2021-01-04 13:27:09','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(6,'Grains','Wheat ','https://siniiatest.s3.ap-south-1.amazonaws.com/fresh-wheat-crop-500x500.jpg','Sharbati atta','Grains','A+','Bags',10,325,10,330,'The grains of Sharbati atta are bigger in size and has a golden sheen to it.','Sharbati wheat is a regional variety of atta, derived from the wheat grown in the Sehore and Vidisha regions of MP. Also known as the MP wheat, Sharbati atta is sweeter in taste and better in texture. The grains of Sharbati atta are bigger in size and has a golden sheen to it',5,5,'Bala',NULL,'250Kms',1,'2021-01-09 19:50:58','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(7,'Cerals','Barley','https://siniiatest.s3.ap-south-1.amazonaws.com/Barley+Group+2.png','Yasmin','Cerals','A','Bags',50,1000,10,85,NULL,'Barley is a member of the grass family. It is a self-pollinating, diploid species with 14 chromosomes. The wild ancestor of domesticated barley, Hordeum vulgare subsp. spontaneum, is abundant in grasslands and woodlands throughout the Fertile Crescent area of Western Asia and northeast Africa, and is abundant in disturbed habitats, roadsides, and orchards. Outside this region, the wild barley is less common and is usually found in disturbed habitats.[3] However, in a study of genome-wide diversity markers, Tibet was found to be an additional center of domestication of cultivated barley',2,5,NULL,NULL,'12Kms',1,'2021-01-09 19:54:46','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(8,'Grains','Rice','https://siniiatest.s3.amazonaws.com/product_Rice_image_2_20012021195149.JPEG,https://siniiatest.s3.amazonaws.com/product_Rice_image_1_20012021195152.JPEG','Rice','Sonamasori','A','Kgs',1,0,25,-2,'Test','Test',36,5,NULL,NULL,'5Kms',1,'2021-01-20 14:21:55','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(9,'Grains','Rice Alpha','https://siniiatest.s3.amazonaws.com/product_Rice%20Alpha_eggThumb_21012021021236.PNG','Rice','Grains','A','Kgs',25,23,59,25,'Test','Test',21,5,'Bala',NULL,'2Kms',1,'2021-01-20 20:42:39','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(10,'Grains','Wheat','https://siniiatest.s3.amazonaws.com/product_Wheat_image_1_22012021153252.JPEG','Wheat','Grains','A','kgs',5,245,2,-21,'Test','Best Wheat For Poori',30,5,NULL,NULL,'15',0,'2021-01-22 10:02:56','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(11,'Grains','Rice','https://siniiatest.s3.amazonaws.com/product_Apple_image_2_23012021223718.JPEG,https://siniiatest.s3.amazonaws.com/product_Apple_image_1_23012021223720.JPEG','Rice','Sonamasori','A+','Kgs',1,100,10,100,'Test','Good Quality ',37,5,NULL,NULL,'1000',1,'2021-01-23 17:07:22','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(12,'Grains','Wheat','https://siniiatest.s3.ap-south-1.amazonaws.com/wheat.png','Wheat','Sharbathi','A+','kgs',500,42,15,500,'Tasty Product','Good For Health',42,5,NULL,NULL,'3',1,'2021-02-01 05:52:31','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(13,'Fruits','Mango','https://siniiatest.s3.amazonaws.com/product_Mango_image_1_01022021212234.JPEG','Mango','Himayath','A+','kgs',1200,1000,500,700,'Pure Farm','Best Farm Land',43,5,NULL,NULL,'12',1,'2021-02-01 15:52:36','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(14,'Spices','Cardamom','https://siniiatest.s3.amazonaws.com/product_Cardamom_image_1_02022021114133.JPEG','Cardamom','Staple Food','A+','Kgs',2,2,10,2,'Gggg','Gg',44,5,NULL,NULL,'2',1,'2021-02-02 06:11:35','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,6,30,12),(15,'Grains','Rice Alpha','','Rice','Sonamasori-&','A','Kgs',25,23,59,25,'Test','Test',45,5,NULL,NULL,'2Kms',1,'2021-02-03 15:15:55','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City','test','98.4','13.8','test','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(16,'Oils','Coconut','https://siniiatest.s3.amazonaws.com/product_Coconut_image_1_06022021145722.JPEG','Coconut','Staple Food','A+','Pieces',23,63,96,23,'Test','Description ',49,5,NULL,NULL,'6',1,'2021-02-06 09:27:26','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Pedarikatla ','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(17,'Grains','Rice','https://siniiatest.s3.amazonaws.com/product_Rice_image_1_11022021133640.JPEG','Rice','Sonamasori','A+','Kgs',222,1225,1,222,'12','Ggg',54,5,NULL,NULL,'2',1,'2021-02-11 08:06:43','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'17.447313','78.3793419','Ddff','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(18,'Vegetables','Onions','https://siniiatest.s3.ap-south-1.amazonaws.com/Onion.png','Onions','Vegetables','A+','Kgs',23,36,23,23,'Test Headlights','Product Description ',55,5,NULL,NULL,'23',1,'2021-02-11 09:41:08','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'17.4472191','78.3792231','67/2','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(19,'Nuts','Pistacchio','https://siniiatest.s3.amazonaws.com/product_Pistacchio_image_1_11022021151733.JPEG','Pistacchio','Staple Food','A+','Kgs',263,69,2,263,'Test','Test Description ',0,5,NULL,NULL,'9',1,'2021-02-11 09:47:36','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'17.4472117','78.3791732','Test','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(20,'Grains','Rice','https://siniiatest.s3.amazonaws.com/product_Rice_image_1_18022021155402.JPEG','Rice','Sonamasori','A+','Kgs',1,36,2,25,NULL,'Test',0,5,NULL,NULL,NULL,1,'2021-02-18 10:24:05','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'17.4473581','78.3793507','Test Landmark','500081','Hyderabad','Telangana','INDIA',10,8,7,2),(22,'Herbs','OTHER','https://siniiatest.s3.amazonaws.com/product_OTHER_image_1_21022021235227.JPEG','OTHER','Herbs','A+','pieces',1,45,2,-4,NULL,'Tea',0,11,'Anne',NULL,NULL,1,'2021-02-21 18:22:29','17717 Meadow Vista Way, Montgomery County',NULL,NULL,NULL,'Gaithersburg','20877','Gaithersburg','MD','US',10,6,7,2),(23,'Fruits','Banana','https://siniiatest.s3.ap-south-1.amazonaws.com/banana.png','Banana','Staple Food','A+','Kgs',1,50,2,6,NULL,'Fruits',0,11,'Anne',NULL,NULL,0,'2021-02-21 18:22:29','17717 Meadow Vista Way, Montgomery County',NULL,NULL,NULL,'Gaithersburg','20877','Gaithersburg','MD','US',10,6,7,2),(24,'Fish','Snapper','https://siniiatest.s3.ap-south-1.amazonaws.com/snapper.png','Snapper Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,11,'Anne',NULL,NULL,1,'2021-02-21 18:22:29','17717 Meadow Vista Way, Montgomery County',NULL,NULL,NULL,'Gaithersburg','20877','Gaithersburg','MD','US',10,6,7,2),(25,'Cerals','Shourgram','https://siniiatest.s3.ap-south-1.amazonaws.com/Shourgram.png','Shourgram','Cerals','A','Kgs',5,1000,10,85,NULL,'Shourgram',2,5,'Phani',NULL,'12Kms',1,'2021-01-09 19:54:46','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,12,10,5),(26,'Fish','Grouper','https://siniiatest.s3.ap-south-1.amazonaws.com/grouper.png','Grouper Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,11,'Anne',NULL,NULL,1,'2021-02-21 18:22:29','17717 Meadow Vista Way, Montgomery County',NULL,NULL,NULL,'Gaithersburg','20877','Gaithersburg','MD','US',10,6,7,2),(27,'Fish','Anchvoy','https://siniiatest.s3.ap-south-1.amazonaws.com/anchvoy.png','Anchvoy Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,11,'Anne',NULL,NULL,1,'2021-02-21 18:22:29','17717 Meadow Vista Way, Montgomery County',NULL,NULL,NULL,'Gaithersburg','20877','Gaithersburg','MD','US',10,6,7,2),(28,'Fish','Cat Fish','https://siniiatest.s3.ap-south-1.amazonaws.com/catfish.png','Cat Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,11,'Anne',NULL,NULL,1,'2021-02-21 18:22:29','17717 Meadow Vista Way, Montgomery County',NULL,NULL,NULL,'Gaithersburg','20877','Gaithersburg','MD','US',10,6,7,2),(29,'Herbs','Basil','https://siniiatest.s3.ap-south-1.amazonaws.com/basil.png','Basil','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,11,'Anne',NULL,NULL,1,'2021-02-21 18:22:29','17717 Meadow Vista Way, Montgomery County',NULL,NULL,NULL,'Gaithersburg','20877','Gaithersburg','MD','US',10,6,7,2),(30,'Herbs','Chives','https://siniiatest.s3.ap-south-1.amazonaws.com/chives.png','Chives','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,11,'Anne',NULL,NULL,1,'2021-02-21 18:22:29','17717 Meadow Vista Way, Montgomery County',NULL,NULL,NULL,'Gaithersburg','20877','Gaithersburg','MD','US',10,6,7,2),(31,'Herbs','Laurel','https://siniiatest.s3.ap-south-1.amazonaws.com/laurel.png','Laurel','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,11,'Anne',NULL,NULL,1,'2021-02-21 18:22:29','17717 Meadow Vista Way, Montgomery County',NULL,NULL,NULL,'Gaithersburg','20877','Gaithersburg','MD','US',10,6,7,2),(32,'Herbs','RoseMary','https://siniiatest.s3.ap-south-1.amazonaws.com/rosemary.png','RoseMary','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,11,'Anne',NULL,NULL,1,'2021-02-21 18:22:29','17717 Meadow Vista Way, Montgomery County',NULL,NULL,NULL,'Gaithersburg','20877','Gaithersburg','MD','US',10,6,7,2),(33,'Vegetables','Tomato','https://siniiatest.s3.ap-south-1.amazonaws.com/Tomato.png','Tomato','Vegetables','A+','Kgs',23,36,23,23,'','Product Description ',55,5,'Bala',NULL,'23',1,'2021-02-11 09:41:08','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'17.4472191','78.3792231','67/2','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(34,'Vegetables','PumpKin','https://siniiatest.s3.ap-south-1.amazonaws.com/Pumpkin.png','PumpKin','Vegetables','A+','Kgs',23,36,23,23,'','Product Description ',55,5,'Bala',NULL,'23',1,'2021-02-11 09:41:08','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'17.4472191','78.3792231','67/2','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(35,'Vegetables','Bitter Guard','https://siniiatest.s3.ap-south-1.amazonaws.com/Bitter+guard.png','Bitter Guard','Vegetables','A+','Kgs',23,36,23,23,'','Product Description ',55,5,'Bala',NULL,'23',1,'2021-02-11 09:41:08','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'17.4472191','78.3792231','67/2','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(36,'Fruits','Mango','https://siniiatest.s3.ap-south-1.amazonaws.com/grape.png','Mango','Himayath','A+','kgs',1,1000,500,700,'Pure Farm','Best Farm Land',43,5,NULL,NULL,'12',1,'2021-02-01 15:52:36','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,NULL,'500081','Hyderabad','Telangana','INDIA',10,9,12,7),(37,'Fish','Snapper','https://siniiatest.s3.ap-south-1.amazonaws.com/snapper.png','Snapper Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,5,'Bala',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(38,'Fish','Grouper','https://siniiatest.s3.ap-south-1.amazonaws.com/grouper.png','Grouper Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,5,'Bala',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(39,'Fish','Anchvoy','https://siniiatest.s3.ap-south-1.amazonaws.com/anchvoy.png','Anchvoy Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,5,'Bala',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(40,'Grains','Rice','https://siniiatest.s3.ap-south-1.amazonaws.com/catfish.png','Rice','Sonamasori','A+','Kgs',1,100,2,6,NULL,'Fish',81,5,NULL,NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',6,6,7,2),(41,'Fish','Cat Fish','https://siniiatest.s3.ap-south-1.amazonaws.com/catfish.png','Cat Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,5,'test',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(42,'Fish','Anchvoy','https://siniiatest.s3.ap-south-1.amazonaws.com/anchvoy.png','Anchvoy Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,5,'test',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(43,'Fish','Grouper','https://siniiatest.s3.ap-south-1.amazonaws.com/grouper.png','Grouper Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,5,'test',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(44,'Fish','Snapper','https://siniiatest.s3.ap-south-1.amazonaws.com/snapper.png','Snapper Fish','Fish','A+','Kgs',1,100,2,6,NULL,'Fish',0,5,'test',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(45,'Herbs','Basil','https://siniiatest.s3.ap-south-1.amazonaws.com/basil.png','Basil','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,5,'Bala',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(46,'Herbs','Chives','https://siniiatest.s3.ap-south-1.amazonaws.com/chives.png','Chives','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,5,'Bala',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(47,'Herbs','Cilantro','https://siniiatest.s3.ap-south-1.amazonaws.com/laurel.png','Cilantro','Staple Food','A+','pieces',1,100,2,4,NULL,'Leafy Vegetables',82,5,NULL,NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',6,6,7,2),(48,'Herbs','RoseMary','https://siniiatest.s3.ap-south-1.amazonaws.com/rosemary.png','RoseMary','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,5,'Bala',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(49,'Herbs','Basil','https://siniiatest.s3.ap-south-1.amazonaws.com/basil.png','Basil','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,5,'test',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(50,'Herbs','Chives','https://siniiatest.s3.ap-south-1.amazonaws.com/chives.png','Chives','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,5,'test',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(51,'Herbs','Laurel','https://siniiatest.s3.ap-south-1.amazonaws.com/laurel.png','Laurel','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,5,'test',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(52,'Herbs','RoseMary','https://siniiatest.s3.ap-south-1.amazonaws.com/rosemary.png','RoseMary','Herbs','A+','Kgs',1,100,2,6,NULL,'Leafy Vegetables',0,5,'test',NULL,NULL,1,'2021-02-21 18:22:29','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Gaithersburg','500081','Hyderabad','Telangana','INDIA',10,6,7,2),(53,'Vegetables','Tomato','https://siniiatest.s3.ap-south-1.amazonaws.com/Tomato.png','Tomato','Vegetables','A+','Kgs',23,36,23,23,'','Product Description ',55,11,'Anne',NULL,'23',0,'2021-02-11 09:41:08','17717 Meadow Vista Way, Montgomery County',NULL,'17.4472191','78.3792231','67/2','20877','Gaithersburg','MD','US',10,6,30,12),(54,'Vegetables','PumpKin','https://siniiatest.s3.ap-south-1.amazonaws.com/Pumpkin.png','PumpKin','Vegetables','A+','Kgs',23,36,23,23,'','Product Description ',55,11,'Anne',NULL,'23',1,'2021-02-11 09:41:08','17717 Meadow Vista Way, Montgomery County',NULL,'17.4472191','78.3792231','67/2','20877','Gaithersburg','MD','US',10,6,30,12),(55,'Vegetables','Bitter Guard','https://siniiatest.s3.ap-south-1.amazonaws.com/Bitter+guard.png','Bitter Guard','Vegetables','A+','Kgs',23,36,23,23,'','Product Description ',55,11,'Anne',NULL,'23',1,'2021-02-11 09:41:08','17717 Meadow Vista Way, Montgomery County',NULL,'17.4472191','78.3792231','67/2','20877','Gaithersburg','MD','US',10,6,30,12),(56,'Vegetables','Tomato','https://siniiatest.s3.ap-south-1.amazonaws.com/Tomato.png','Tomato','Vegetables','A+','Kgs',23,36,23,23,'','Product Description ',55,5,'test',NULL,'23',1,'2021-02-11 09:41:08','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'17.4472191','78.3792231','67/2','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(57,'Vegetables','PumpKin','https://siniiatest.s3.ap-south-1.amazonaws.com/Pumpkin.png','PumpKin','Vegetables','A+','Kgs',23,36,23,23,'','Product Description ',55,5,'test',NULL,'23',1,'2021-02-11 09:41:08','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'17.4472191','78.3792231','67/2','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(58,'Vegetables','Bitter Guard','https://siniiatest.s3.ap-south-1.amazonaws.com/Bitter+guard.png','Bitter Guard','Vegetables','A+','Kgs',23,36,23,23,'','Product Description ',55,5,'test',NULL,'23',1,'2021-02-11 09:41:08','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'17.4472191','78.3792231','67/2','500081','Hyderabad','Telangana','INDIA',10,6,30,12),(59,'Grains','Rice','https://siniiatest.s3.amazonaws.com/product_Rice_image_1_03032021143753.JPEG','Rice','Sonamasori','A+','Kgs',1,50,1,1,NULL,'Ffh',72,5,NULL,NULL,NULL,1,'2021-03-03 09:07:56','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,NULL,NULL,'Hyderabad','500081','Hyderabad','Telangana','INDIA',10,1,1,1),(60,'Grains','Rice','','Rice','Sonamasori','A+','Kgs',25,36,2,25,NULL,'Test',78,11,NULL,NULL,NULL,0,'2021-04-07 15:10:13','17717 Meadow Vista Way, Montgomery County',NULL,'17.4473581','78.3793507','Test Landmark','20877','Gaithersburg','MD','US',12,56,7,6),(61,'Vegetables','Tomatoes','https://siniiatest.s3.amazonaws.com/product_Tomatoes_image_1_09042021065243.JPEG','Tomatoes','Staple Food','A+','Kgs',2,30,2,2,NULL,'Test',83,5,NULL,NULL,NULL,0,'2021-04-09 01:22:47','JSP Imperia,D.No.1-98/4/B/24,4th Floor, Patrika Nagar, HITEC City',NULL,'15.5141226','79.5273253','Test Landmark ','500081','Hyderabad','Telangana','INDIA',5,4,30,2),(62,'Grains','Rice','https://siniiatest.s3.amazonaws.com/product_Rice_image_1_20042021115853.JPEG','Rice','Sonamasori','A+','Kgs',23,36,2,23,NULL,'Test Description ',11,5,NULL,NULL,NULL,0,'2021-04-20 06:28:57','SH 54, Pedda Arikatla, Andhra Pradesh 523245, India',NULL,'15.5141674','79.5273283','Near Ayyaswamy Temple','523245','Prakasam','Andhra Pradesh','India',2,5,2,2),(72,'Grains','Corn','https://siniiatest.s3.amazonaws.com/0_21052021114442.JPG','Corn','SuperSelect','A','pieces',10,10,10,100,NULL,'SuperGraded High Quality Corn',0,9999,'Admin','12021234523',NULL,1,'2021-05-21 06:14:47','17717 Meadow Vista Way','Montgomery County',NULL,NULL,NULL,'20877','Gaithersburg','MaryLand','US',10,10,2,10),(73,'Meat','in shell','https://siniiatest.s3.amazonaws.com/0_21052021204941.JPG','in shell','Staple Food','A+','pieces',10,100,10,2000,NULL,'inShell without Corona',0,9999,'Admin','12029767656',NULL,1,'2021-05-21 15:19:55','test','test',NULL,NULL,NULL,'20877','Gaithersburg','MaryLand','US',10,10,10,10);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producttype_meta`
--

DROP TABLE IF EXISTS `producttype_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producttype_meta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productType` varchar(50) NOT NULL,
  `createddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producttype_meta`
--

LOCK TABLES `producttype_meta` WRITE;
/*!40000 ALTER TABLE `producttype_meta` DISABLE KEYS */;
INSERT INTO `producttype_meta` VALUES (1,'Grain','2021-01-31 14:24:56'),(2,'Meat','2021-01-31 14:24:56'),(3,'Legume','2021-01-31 14:24:56'),(4,'Fruits','2021-01-31 14:24:56'),(5,'Vegetables','2021-01-31 14:24:56'),(6,'Herbs','2021-01-31 14:24:56'),(7,'Nuts','2021-01-31 14:24:56'),(8,'Spices','2021-01-31 14:24:56'),(9,'Beverages','2021-01-31 14:24:56'),(10,'Oils','2021-01-31 14:24:56'),(11,'Seeds','2021-01-31 14:25:15'),(12,'Ship','2021-02-25 05:58:05');
/*!40000 ALTER TABLE `producttype_meta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quantitytype_meta`
--

DROP TABLE IF EXISTS `quantitytype_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quantitytype_meta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(50) NOT NULL,
  `subCategory` varchar(50) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quantitytype_meta`
--

LOCK TABLES `quantitytype_meta` WRITE;
/*!40000 ALTER TABLE `quantitytype_meta` DISABLE KEYS */;
INSERT INTO `quantitytype_meta` VALUES (1,'Bags','Kgs','2020-12-26 11:29:08'),(2,'pieces','pieces','2020-12-26 11:29:22');
/*!40000 ALTER TABLE `quantitytype_meta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `searchedData`
--

DROP TABLE IF EXISTS `searchedData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `searchedData` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `SearchString` varchar(4000) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `searchedData`
--

LOCK TABLES `searchedData` WRITE;
/*!40000 ALTER TABLE `searchedData` DISABLE KEYS */;
INSERT INTO `searchedData` VALUES (1,38,'corn','2021-05-21 06:23:52');
/*!40000 ALTER TABLE `searchedData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shippertable`
--

DROP TABLE IF EXISTS `shippertable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shippertable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(50) NOT NULL,
  `shipperName` varchar(100) NOT NULL,
  `link` varchar(200) DEFAULT NULL,
  `shipperContact` varchar(50) DEFAULT NULL,
  `shipperLocation` varchar(2000) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shippertable`
--

LOCK TABLES `shippertable` WRITE;
/*!40000 ALTER TABLE `shippertable` DISABLE KEYS */;
INSERT INTO `shippertable` VALUES (1,'INDIA','DTDC','https://www.dtdc.in/','siniainfo059@gmail.com','INDIA','2021-02-25 07:36:49'),(2,'USA','PARCEL MONKEY','www.parcelmonkey.com','siniiafresh@gmail.com','LA','2021-02-25 07:36:49'),(3,'AFRICA','HERI WORLD','www.heriworldwide.com','nagoliacademy@gmail.com','KENYA','2021-02-25 07:41:40'),(12,'US','PARCEL MONKEY','www.parcelmonkey.com','siniiafresh@gmail.com','LA','2021-02-25 07:36:49');
/*!40000 ALTER TABLE `shippertable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shippingtable`
--

DROP TABLE IF EXISTS `shippingtable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shippingtable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shipperId` int(11) NOT NULL,
  `shipperName` varchar(30) NOT NULL,
  `ShipperDetails` varchar(1000) NOT NULL,
  `shipperContact` varchar(20) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shippingtable`
--

LOCK TABLES `shippingtable` WRITE;
/*!40000 ALTER TABLE `shippingtable` DISABLE KEYS */;
INSERT INTO `shippingtable` VALUES (1,101,'HERIWORLD WIDE','www.heriworldwide.com','9014524526','2021-01-05 05:34:11'),(2,102,'PARCEL MONKEY','www.parcelmonkey.com','9014524526','2021-01-05 05:34:11'),(3,103,'PICKUP NOW','https://pickupnow.com/contact-us/','9014524526','2021-02-10 12:58:02');
/*!40000 ALTER TABLE `shippingtable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `nickName` varchar(20) DEFAULT NULL,
  `pincode` varchar(10) DEFAULT NULL,
  `Address1` varchar(4000) DEFAULT NULL,
  `Address2` varchar(30) DEFAULT NULL,
  `Landmark` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `addressLat` varchar(30) DEFAULT NULL,
  `addressLong` varchar(30) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
INSERT INTO `user_address` VALUES (1,5,NULL,'523245','Unnamed Road, Pedda Arikatla, Andhra Pradesh 523245, India','Gaithersburg','Pedda Arikatla','Prakasam','Andhra Pradesh','INDIA\n','15.5230162','79.5275819','2021-04-20 11:19:45'),(6,29,NULL,'501505','Hyderabad','Ranga Reddy','Kuntloor','Ranga Reddy','Telangana','India','','','2021-04-20 11:46:30'),(7,30,NULL,'501505','House No.44','Ranga Reddy','Kuntloor','Ranga Reddy','Telangana','India','','','2021-04-20 11:57:53'),(8,30,NULL,'501505','House No.44','Ranga Reddy','Kuntloor','Ranga Reddy','Telangana','INDIA',NULL,NULL,'2021-04-20 12:03:10'),(12,11,NULL,'20877','17719 Meadow Vista Way, Gaithersburg, MD 20877, USA','Montgomery County','Gaithersburg','Montgomery County','Maryland','US','39.1469503','-77.1662719','2021-04-20 15:56:43'),(13,34,NULL,'500035','Kothapet','Ranga Reddy','Hyderabad','Ranga Reddy','Telangana','INDIA','','','2021-04-26 18:00:53'),(14,35,NULL,'500035','Kothapet','Ranga Reddy','Hyderabad','Ranga Reddy','Telangana','','','','2021-04-26 19:43:49'),(15,36,NULL,'500035','Kothapet','Ranga Reddy','Hyderabad','Ranga Reddy','Telangana','','','','2021-04-28 07:48:20'),(16,37,NULL,'523245','Near Ayyappa Swamy Temple','Prakasam','Pedda Arikatla','Prakasam','Andhra Pradesh','','','','2021-04-28 08:47:16'),(17,38,NULL,'500035','Kothapet','Ranga Reddy','Hyderabad','Ranga Reddy','Telangana','','','','2021-05-04 09:21:53'),(18,38,NULL,'500074','Harupuri Colony','Ranga Reddy','Hyderabad','Ranga Reddy','Telangana','','','','2021-05-14 13:41:00'),(19,39,NULL,'20877','17717 Meadow Vista Way, Gaithersburg, Maryland 20877','Montgomery County','Gaithersburg','Montgomery County','Maryland','','','','2021-05-18 07:13:11'),(20,40,NULL,'562123','Bangalore ','Bangalore ','Bangalore ','Bangalore ','Karnataka ','','','','2021-05-22 06:16:07'),(21,41,NULL,'500035','Kothapet','Ranga Reddy','Hyderabad','Ranga Reddy','Telangana','','','','2021-05-23 05:05:44'),(22,42,NULL,'523245','Test','Test','Test','Trst','Test','','','','2021-05-23 05:24:27'),(23,43,NULL,'500074','Hyd','Ranga Reddy','Hyderabad','Ranga Reddy','Telangana','','','','2021-05-24 12:55:15'),(24,44,NULL,'500035','Kothapet','Ranga Reddy','Hyderabad','Ranga Reddy','Telangana','','','','2021-05-24 13:11:23'),(25,45,NULL,'500035','Kothapet','Ranga Reddy','Hyderabad','Ranga Reddy','Telangana','','','','2021-05-25 07:49:40'),(26,46,NULL,'523245','Test','Test','Test','Test','Test','','','','2021-05-25 07:57:57'),(27,47,NULL,'500035','Kothapet','Ranga Reddy','Hyderabad','Ranga Reddy','Telangana','','','','2021-05-25 09:27:27');
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_devices`
--

DROP TABLE IF EXISTS `user_devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_devices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `gcmId` varchar(256) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deviceType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_devices`
--

LOCK TABLES `user_devices` WRITE;
/*!40000 ALTER TABLE `user_devices` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_devices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertype_meta`
--

DROP TABLE IF EXISTS `usertype_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertype_meta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userType` varchar(30) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertype_meta`
--

LOCK TABLES `usertype_meta` WRITE;
/*!40000 ALTER TABLE `usertype_meta` DISABLE KEYS */;
INSERT INTO `usertype_meta` VALUES (1,'Farmer','2020-12-26 11:22:40'),(2,'Retailer','2020-12-26 11:22:40'),(3,'Buyer','2020-12-26 11:23:44'),(4,'Shipper','2020-12-26 11:23:44'),(5,'Admin','2020-12-26 11:24:11');
/*!40000 ALTER TABLE `usertype_meta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `version`
--

DROP TABLE IF EXISTS `version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `status` varchar(1) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `version`
--

LOCK TABLES `version` WRITE;
/*!40000 ALTER TABLE `version` DISABLE KEYS */;
INSERT INTO `version` VALUES (1,1,'Y','2021-03-05 08:56:59');
/*!40000 ALTER TABLE `version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-11 15:35:13
