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
-- Table structure for table `nag_interaction`
--

DROP TABLE IF EXISTS `nag_interaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nag_interaction` (
  `nag_interaction_id` bigint NOT NULL AUTO_INCREMENT,
  `nag_like` bit(1) DEFAULT NULL,
  `nag_unlock` bit(1) DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `nag_id` bigint DEFAULT NULL,
  PRIMARY KEY (`nag_interaction_id`),
  UNIQUE KEY `idx_unique_nag_member` (`nag_id`,`member_id`),
  KEY `FKictso5tfw2frbn5yl2972if7g` (`member_id`),
  CONSTRAINT `FKictso5tfw2frbn5yl2972if7g` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKqkmcb9i9tbi9k0elsbcl1x3yi` FOREIGN KEY (`nag_id`) REFERENCES `nag` (`nag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nag_interaction`
--

LOCK TABLES `nag_interaction` WRITE;
/*!40000 ALTER TABLE `nag_interaction` DISABLE KEYS */;
INSERT INTO `nag_interaction` VALUES (1,_binary '\0',_binary '',21,6),(2,_binary '\0',_binary '',21,16),(3,_binary '',_binary '',21,13),(4,_binary '\0',_binary '',21,2),(5,_binary '\0',_binary '',21,4),(6,_binary '\0',_binary '',21,9),(7,_binary '\0',_binary '',21,14),(8,_binary '',_binary '',24,21),(9,_binary '\0',_binary '',22,6),(10,_binary '\0',_binary '',27,7),(11,_binary '\0',_binary '',27,12),(12,_binary '\0',_binary '',27,13),(13,_binary '\0',_binary '',3,13),(14,_binary '\0',_binary '',22,16),(15,_binary '\0',_binary '',22,12),(16,_binary '\0',_binary '',22,14),(17,_binary '\0',_binary '',22,9),(18,_binary '\0',_binary '',22,4),(19,_binary '\0',_binary '',22,1),(20,_binary '',_binary '',21,24),(21,_binary '\0',_binary '',22,7),(22,_binary '\0',_binary '',26,10),(23,_binary '',_binary '',25,24),(24,_binary '\0',_binary '',31,9),(25,_binary '\0',_binary '',32,9),(26,_binary '\0',_binary '',32,24),(27,_binary '\0',_binary '',32,19),(28,_binary '\0',_binary '',32,14),(29,_binary '',_binary '',6,9),(30,_binary '',_binary '',28,9),(31,_binary '',_binary '',28,19),(32,_binary '\0',_binary '',21,1),(33,_binary '\0',_binary '',21,8),(34,_binary '\0',_binary '',21,12),(35,_binary '\0',_binary '',21,29),(36,_binary '',_binary '',35,9),(37,_binary '',_binary '',35,14),(38,_binary '',_binary '',2,33),(39,_binary '\0',_binary '',28,7),(40,_binary '\0',_binary '',28,14),(41,_binary '',_binary '',3,17),(42,_binary '\0',_binary '',28,12),(43,_binary '\0',_binary '',28,13),(44,_binary '',_binary '',11,29),(45,_binary '\0',_binary '',11,14),(46,_binary '',_binary '',11,15),(47,_binary '\0',_binary '',15,42),(48,_binary '',_binary '',25,42),(49,_binary '\0',_binary '',21,43),(50,_binary '\0',_binary '',21,44),(51,_binary '\0',_binary '',21,22),(52,_binary '\0',_binary '',1,45),(53,_binary '\0',_binary '',1,46),(54,_binary '\0',_binary '',1,33),(55,_binary '\0',_binary '',21,23),(56,_binary '\0',_binary '',1,47),(57,_binary '\0',_binary '',21,48),(58,_binary '',_binary '',21,33),(59,_binary '\0',_binary '',21,49),(60,_binary '',_binary '',21,19),(61,_binary '\0',_binary '',1,50),(62,_binary '',_binary '',1,51),(63,_binary '',_binary '',1,9),(64,_binary '',_binary '',1,14),(65,_binary '',_binary '',1,7),(66,_binary '',_binary '',1,12),(67,_binary '',_binary '',1,13),(68,_binary '\0',_binary '',1,19),(69,_binary '\0',_binary '',1,43),(70,_binary '',_binary '',25,9),(71,_binary '\0',_binary '',21,52),(72,_binary '\0',_binary '',21,34),(73,_binary '',_binary '',15,38),(74,_binary '',_binary '',15,9),(75,_binary '',_binary '',15,35),(76,_binary '\0',_binary '',15,31),(77,_binary '\0',_binary '',15,16),(78,_binary '\0',_binary '',27,43),(79,_binary '\0',_binary '',27,22);
/*!40000 ALTER TABLE `nag_interaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-18  9:36:33
