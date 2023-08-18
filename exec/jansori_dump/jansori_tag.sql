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
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `tag_id` bigint NOT NULL AUTO_INCREMENT,
  `follow_count` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,0,'운동'),(2,0,'일상'),(3,0,'독서'),(4,0,'연애'),(5,1,'공부'),(6,1,'다이어트'),(7,1,'여행'),(8,0,'일'),(9,1,'기타'),(10,0,'산책'),(11,0,'요리'),(12,0,'취미'),(13,0,'친목'),(14,0,'집안일'),(15,0,'명상'),(16,0,'침묵'),(17,0,'휴식'),(18,0,'일기'),(19,0,'연주'),(20,0,'음악'),(21,0,'모임'),(22,0,'개발'),(23,0,'정리'),(24,0,'건강'),(25,0,'제과제빵'),(26,0,'영어'),(27,0,'중국어'),(28,0,'일본어'),(29,0,'스페인어'),(30,0,'한국어'),(31,0,'스트레칭'),(32,0,'댕댕이'),(33,0,'집사'),(34,0,'가족'),(35,0,'사랑'),(36,0,'냠냠'),(37,0,'먹방'),(38,0,'친구'),(39,0,'결혼'),(40,0,'과제'),(41,0,'시험'),(42,0,'취준'),(43,0,'입사'),(44,0,'공채'),(45,0,'혼자'),(46,0,'분위기'),(47,0,'코딩'),(48,0,'디자인'),(49,0,'그림'),(50,0,'당근'),(51,0,'종교'),(52,0,'생활'),(53,0,'학교'),(54,0,'반복'),(55,0,'삶'),(56,0,'굳이'),(57,0,'챌린지'),(58,0,'회의'),(59,0,'퇴사'),(60,0,'월급'),(61,0,'적금'),(62,0,'예금'),(63,0,'투자'),(64,0,'금융'),(65,0,'집중'),(66,0,'공유'),(67,0,'반려동물'),(68,0,'행사'),(69,0,'축제'),(70,0,'파티'),(71,0,'해외'),(72,0,'국내'),(73,0,'공용'),(74,0,'도전'),(75,0,'같이'),(76,0,'함께'),(77,0,'심심'),(78,0,'게임'),(79,0,'자유'),(80,0,'의리'),(81,0,'맛집'),(82,0,'혼밥'),(83,0,'혼술'),(84,0,'스타일'),(85,0,'아기'),(86,0,'육아'),(87,0,'바보'),(88,0,'냥이'),(89,0,'잔소리'),(90,0,'필수'),(91,0,'오늘'),(92,0,'평일'),(93,0,'주말'),(94,0,'봄'),(95,0,'여름'),(96,0,'가을'),(97,0,'겨울'),(98,0,'행복'),(99,0,'계획'),(100,0,'자극'),(101,0,'목표'),(102,0,'초보'),(103,0,'웨이트'),(104,0,'문화생활'),(105,0,'영화'),(106,0,'공연'),(107,0,'관람'),(108,0,'가족'),(109,0,'소소한'),(110,0,'여유'),(111,0,'독서'),(112,0,'교양'),(113,0,'봉사'),(114,0,'미용'),(115,0,'감사'),(116,0,'원예'),(117,0,'실험'),(118,0,'연구'),(119,0,'대학원생'),(120,0,'친구'),(121,0,'싸피'),(122,0,'귀찮'),(123,0,'일상'),(124,0,'마라탕'),(125,0,'CSS'),(126,0,'FE'),(127,0,'개발'),(128,1,'55'),(129,1,'11'),(130,3,'나쁜말'),(131,0,'개발'),(132,0,'존잘'),(133,0,'수면'),(134,0,'발표');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-18  9:35:39
