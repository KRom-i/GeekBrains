CREATE DATABASE  IF NOT EXISTS `lesson1_urok` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `lesson1_urok`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: lesson1_urok
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id_category` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'Первичный ключ категории',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Наименование категории',
  `date_upd` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'продовольствие',NULL),(2,'хозяйственные',NULL),(3,'канцелярия',NULL),(4,'косметика',NULL),(5,'парфюмерия',NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id_product` int unsigned NOT NULL AUTO_INCREMENT,
  `name_product` varchar(200) DEFAULT NULL,
  `descrition_product` varchar(255) DEFAULT NULL,
  `price_product` decimal(10,2) DEFAULT NULL,
  `count_product` decimal(10,2) DEFAULT NULL,
  `id_category` int unsigned DEFAULT NULL,
  `product_col` char(50) DEFAULT NULL,
  `data_prib` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `data_creat` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `data` date DEFAULT NULL,
  `vasss` set('m','f','h') DEFAULT NULL,
  PRIMARY KEY (`id_product`),
  KEY `fk_category_idx` (`id_category`),
  KEY `idx_name` (`name_product`(10)),
  CONSTRAINT `fk_category` FOREIGN KEY (`id_category`) REFERENCES `category` (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'колбаса','Докторская',123.70,15.00,1,NULL,'2021-02-16 09:36:17','2021-02-11 18:55:40',NULL,NULL),(2,'мыло','чистая линия',14.50,12.60,2,NULL,'2021-02-16 09:39:25','2021-01-29 19:24:48',NULL,NULL),(3,'сосиски','Мясокомбинат',78.40,23.00,1,'23',NULL,'2021-03-02 15:58:19',NULL,NULL),(4,'альбом','фирма Натали',10.05,33.00,3,'33',NULL,'2021-03-02 16:21:11',NULL,NULL),(6,'губная помада','Орифлейм',100.00,10.00,5,NULL,NULL,'2021-03-02 19:06:51',NULL,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zakaz`
--

DROP TABLE IF EXISTS `zakaz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zakaz` (
  `id_zakaz` int unsigned NOT NULL,
  `data_zakaz` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status_zakaz` tinyint NOT NULL,
  `id_manager` tinyint NOT NULL,
  PRIMARY KEY (`id_zakaz`),
  KEY `ind` (`status_zakaz`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zakaz`
--

LOCK TABLES `zakaz` WRITE;
/*!40000 ALTER TABLE `zakaz` DISABLE KEYS */;
INSERT INTO `zakaz` VALUES (1,'2021-03-02 17:59:56',1,1),(2,'2021-03-02 18:01:35',1,2),(3,'2021-03-02 18:23:23',1,3);
/*!40000 ALTER TABLE `zakaz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zakaz_product`
--

DROP TABLE IF EXISTS `zakaz_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zakaz_product` (
  `id_zakaz_product` int unsigned NOT NULL AUTO_INCREMENT,
  `id_zakaz` int unsigned NOT NULL,
  `id_product` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id_zakaz_product`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zakaz_product`
--

LOCK TABLES `zakaz_product` WRITE;
/*!40000 ALTER TABLE `zakaz_product` DISABLE KEYS */;
INSERT INTO `zakaz_product` VALUES (1,1,2),(2,2,1),(3,3,NULL);
/*!40000 ALTER TABLE `zakaz_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'lesson1_urok'
--

--
-- Dumping routines for database 'lesson1_urok'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-02 21:13:17
