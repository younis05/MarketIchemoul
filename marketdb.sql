-- MariaDB dump 10.17  Distrib 10.4.11-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: marketbenackcha
-- ------------------------------------------------------
-- Server version	10.4.11-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `marketbenackcha`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `marketbenackcha` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `marketbenackcha`;

--
-- Table structure for table `backup_sales`
--

DROP TABLE IF EXISTS `backup_sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `backup_sales` (
  `app_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sal_id` bigint(20) NOT NULL,
  `p_id` bigint(20) NOT NULL,
  `cus_id` bigint(20) NOT NULL,
  `inv_id` varchar(225) NOT NULL,
  `barcode` varchar(225) NOT NULL,
  `name` varchar(225) NOT NULL,
  `price` double NOT NULL,
  `qty` int(11) NOT NULL,
  `total` double NOT NULL,
  `discount` int(11) NOT NULL,
  `datetime` datetime NOT NULL,
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backup_sales`
--



--
-- Table structure for table `categore`
--

DROP TABLE IF EXISTS `categore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categore` (
  `cat_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `catname` varchar(225) NOT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categore`
--



--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `cus_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `address` text NOT NULL,
  PRIMARY KEY (`cus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--



--
-- Table structure for table `info_products`
--

DROP TABLE IF EXISTS `info_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info_products` (
  `e_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `p_id` bigint(20) NOT NULL,
  `datefab` date NOT NULL,
  `dateexp` date NOT NULL,
  `alqty` int(11) NOT NULL,
  PRIMARY KEY (`e_id`),
  KEY `info_products_ibfk_1` (`p_id`),
  CONSTRAINT `info_products_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `stock` (`p_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `info_products`
--



--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoices` (
  `inv_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `n_inv` varchar(225) NOT NULL,
  `total` double NOT NULL,
  `pay` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `path` text DEFAULT NULL,
  `datetime` datetime NOT NULL,
  PRIMARY KEY (`inv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--



--
-- Table structure for table `productimage`
--

DROP TABLE IF EXISTS `productimage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productimage` (
  `img_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `p_id` bigint(20) NOT NULL,
  `path` text DEFAULT NULL,
  `picture` longblob DEFAULT NULL,
  PRIMARY KEY (`img_id`),
  KEY `productimage_ibfk_1` (`p_id`),
  CONSTRAINT `productimage_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `stock` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productimage`
--



--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sales` (
  `sal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `p_id` bigint(20) NOT NULL,
  `cus_id` bigint(20) NOT NULL,
  `inv_id` varchar(225) NOT NULL,
  `barcode` varchar(225) NOT NULL,
  `name` int(225) NOT NULL,
  `price` double NOT NULL,
  `qty` int(11) NOT NULL,
  `total` double NOT NULL,
  `discount` int(11) NOT NULL,
  `datetime` datetime NOT NULL,
  PRIMARY KEY (`sal_id`),
  KEY `p_id` (`p_id`),
  KEY `sales_ibfk_1` (`cus_id`),
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--



--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock` (
  `p_id` bigint(20) NOT NULL,
  `cat_id` bigint(20) NOT NULL,
  `barcode` varchar(225) NOT NULL,
  `p_name` varchar(225) NOT NULL,
  `price` double NOT NULL,
  `qty` int(11) NOT NULL,
  `total` double NOT NULL,
  PRIMARY KEY (`p_id`),
  KEY `cat_id` (`cat_id`),
  CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`cat_id`) REFERENCES `categore` (`cat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--



--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL DEFAULT 'admin',
  `password` varchar(50) NOT NULL DEFAULT 'admin',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin'),(2,'admin','1234'),(3,'1','1');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-12 13:24:01
