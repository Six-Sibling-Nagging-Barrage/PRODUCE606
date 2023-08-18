-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: i9a606.p.ssafy.io    Database: jansori
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `notification_setting`
--

DROP TABLE IF EXISTS `notification_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_setting` (
  `notification_setting_id` bigint NOT NULL AUTO_INCREMENT,
  `activated` bit(1) DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `notification_type_id` bigint DEFAULT NULL,
  PRIMARY KEY (`notification_setting_id`),
  KEY `FKlfw5h9l15kryakerd2pw23mkp` (`member_id`),
  KEY `FKl6n3kuppxvxssj8j4jmi080sg` (`notification_type_id`),
  CONSTRAINT `FKl6n3kuppxvxssj8j4jmi080sg` FOREIGN KEY (`notification_type_id`) REFERENCES `notification_type` (`notification_type_id`),
  CONSTRAINT `FKlfw5h9l15kryakerd2pw23mkp` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_setting`
--

LOCK TABLES `notification_setting` WRITE;
/*!40000 ALTER TABLE `notification_setting` DISABLE KEYS */;
INSERT INTO `notification_setting` VALUES (1,_binary '',1,1),(2,_binary '',1,2),(3,_binary '',1,3),(4,_binary '',1,4),(5,_binary '',2,1),(6,_binary '',2,2),(7,_binary '',2,3),(8,_binary '',2,4),(9,_binary '',3,1),(10,_binary '',3,2),(11,_binary '',3,3),(12,_binary '',3,4),(13,_binary '',4,1),(14,_binary '',4,2),(15,_binary '',4,3),(16,_binary '',4,4),(17,_binary '',5,1),(18,_binary '',5,2),(19,_binary '',5,3),(20,_binary '',5,4),(21,_binary '',6,1),(22,_binary '',6,2),(23,_binary '',6,3),(24,_binary '',6,4),(25,_binary '',7,1),(26,_binary '',7,2),(27,_binary '',7,3),(28,_binary '',7,4),(29,_binary '',8,1),(30,_binary '',8,2),(31,_binary '',8,3),(32,_binary '',8,4),(33,_binary '',9,1),(34,_binary '',9,2),(35,_binary '',9,3),(36,_binary '',9,4),(37,_binary '',10,1),(38,_binary '',10,2),(39,_binary '',10,3),(40,_binary '',10,4),(41,_binary '',11,1),(42,_binary '',11,2),(43,_binary '',11,3),(44,_binary '',11,4),(45,_binary '',12,1),(46,_binary '',12,2),(47,_binary '',12,3),(48,_binary '',12,4),(49,_binary '',13,1),(50,_binary '',13,2),(51,_binary '',13,3),(52,_binary '',13,4),(53,_binary '',14,1),(54,_binary '',14,2),(55,_binary '',14,3),(56,_binary '',14,4),(57,_binary '',15,1),(58,_binary '',15,2),(59,_binary '',15,3),(60,_binary '',15,4),(61,_binary '',16,1),(62,_binary '',16,2),(63,_binary '',16,3),(64,_binary '',16,4),(65,_binary '',17,1),(66,_binary '',17,2),(67,_binary '',17,3),(68,_binary '',17,4),(69,_binary '',18,1),(70,_binary '',18,2),(71,_binary '',18,3),(72,_binary '',18,4),(73,_binary '',19,1),(74,_binary '',19,2),(75,_binary '',19,3),(76,_binary '',19,4),(77,_binary '',20,1),(78,_binary '',20,2),(79,_binary '',20,3),(80,_binary '',20,4),(81,_binary '',21,1),(82,_binary '',21,2),(83,_binary '',21,3),(84,_binary '',21,4),(85,_binary '',22,1),(86,_binary '',22,2),(87,_binary '',22,3),(88,_binary '',22,4),(89,_binary '',23,1),(90,_binary '',23,2),(91,_binary '',23,3),(92,_binary '',23,4),(93,_binary '',24,1),(94,_binary '',24,2),(95,_binary '',24,3),(96,_binary '',24,4),(97,_binary '',25,1),(98,_binary '',25,2),(99,_binary '',25,3),(100,_binary '',25,4),(101,_binary '',26,1),(102,_binary '',26,2),(103,_binary '',26,3),(104,_binary '',26,4),(105,_binary '',27,1),(106,_binary '',27,2),(107,_binary '',27,3),(108,_binary '',27,4),(109,_binary '',28,1),(110,_binary '',28,2),(111,_binary '',28,3),(112,_binary '',28,4),(113,_binary '',29,1),(114,_binary '',29,2),(115,_binary '',29,3),(116,_binary '',29,4),(117,_binary '',30,1),(118,_binary '',30,2),(119,_binary '',30,3),(120,_binary '',30,4),(121,_binary '',31,1),(122,_binary '',31,2),(123,_binary '',31,3),(124,_binary '',31,4),(125,_binary '',32,1),(126,_binary '',32,2),(127,_binary '',32,3),(128,_binary '',32,4),(129,_binary '',33,1),(130,_binary '',33,2),(131,_binary '',33,3),(132,_binary '',33,4),(133,_binary '',34,1),(134,_binary '',34,2),(135,_binary '',34,3),(136,_binary '',34,4),(137,_binary '',35,1),(138,_binary '',35,2),(139,_binary '',35,3),(140,_binary '',35,4),(141,_binary '',36,1),(142,_binary '',36,2),(143,_binary '',36,3),(144,_binary '',36,4),(145,_binary '',37,1),(146,_binary '',37,2),(147,_binary '',37,3),(148,_binary '',37,4),(149,_binary '',38,1),(150,_binary '',38,2),(151,_binary '',38,3),(152,_binary '',38,4),(153,_binary '',39,1),(154,_binary '',39,2),(155,_binary '',39,3),(156,_binary '',39,4);
/*!40000 ALTER TABLE `notification_setting` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-18  9:35:44
