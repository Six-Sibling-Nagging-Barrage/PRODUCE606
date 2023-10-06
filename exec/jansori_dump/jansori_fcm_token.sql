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
-- Table structure for table `fcm_token`
--

DROP TABLE IF EXISTS `fcm_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fcm_token` (
  `fcm_token_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `fcm_token` varchar(255) DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`fcm_token_id`),
  KEY `FKf1rbjf8lle4r2in6ovkcgl0w8` (`member_id`),
  CONSTRAINT `FKf1rbjf8lle4r2in6ovkcgl0w8` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fcm_token`
--

LOCK TABLES `fcm_token` WRITE;
/*!40000 ALTER TABLE `fcm_token` DISABLE KEYS */;
INSERT INTO `fcm_token` VALUES (1,'2023-08-17 14:49:04.675133','2023-08-17 14:49:04.675133','eJloOX8IB1gjESVbVs2lyN:APA91bHeO-g4YQUi62a_AAXjCPbhRtFF0MPRi_tFUerO8f4uPgKUJyok9uqNPK7_ws8wAKX4gsxeodOhdMz9_13tloAzfmAzncu6CK88_mo7j-DFVb8aPAU_B5O4qn3ihwDpWbHsvPTZ',22),(2,'2023-08-17 14:49:09.970942','2023-08-17 14:49:09.970942','eBtftt9CgUSyA_jbMegCKa:APA91bEuChvu-9qdhoHtn2RHmvVAbtfVtnEPdIM1eLZy-25Kj1KhUzj1_m-V69TKhgsENPm5NGubhRuTnzs-7CgrUYhWnIShs-oqWT4TCVVJwWt7_kbXRCiK2XEFDZ1PC3DwZtTOYCRV',23),(3,'2023-08-17 14:51:32.217330','2023-08-17 14:51:32.217330','cKTXmHbbkpKqM3P_W64T3X:APA91bG652lAA3eNVocOzP4O5nfwmVcTjnVi3ersU0urTCmYa3llFpNz_cZW_StDAdcWqURd3AHZccMKxEJN3RSiuwqEY4MPEuEsbT13QsXHHANhirK8Wtj5j5QP4BYvsZ52yr-f3Iiu',24),(4,'2023-08-17 14:52:29.979670','2023-08-17 14:52:29.979670','cF9m7IngdGue32s0CQN-5Z:APA91bEuKzAOfJn7981RvV40EPfUCQ_9k4ssvd7Qe8q9oLLqCjAEy4RO3Jmd62UYYMHDAs8XmI2YcMpCC6XUOA7dc2dcZjeWQN515H6p1mHfRQWXmad2fWmxje-ay7UG4aaUuw08aEZb',25),(5,'2023-08-17 15:04:22.969626','2023-08-17 15:04:22.969626','cKTXmHbbkpKqM3P_W64T3X:APA91bG652lAA3eNVocOzP4O5nfwmVcTjnVi3ersU0urTCmYa3llFpNz_cZW_StDAdcWqURd3AHZccMKxEJN3RSiuwqEY4MPEuEsbT13QsXHHANhirK8Wtj5j5QP4BYvsZ52yr-f3Iiu',16),(6,'2023-08-17 15:15:00.052898','2023-08-17 15:15:00.052898','fHHaGBeqFEhiQ_a_pSbiW4:APA91bGsrCD6LrEkl-ADQYNf6jc-SBFpWjqkKFj6EXinR_CbJwPGhQBtA2saAfwEXvBPP4awezPXtrkUFk8kxU27Sk2ATS7p_-lhSx0RNIc4QCPCXCc7QyPqjBIbv_K9pXd1tCbl2fxo',26),(7,'2023-08-17 15:15:48.149029','2023-08-17 15:15:48.149029','fHHaGBeqFEhiQ_a_pSbiW4:APA91bGsrCD6LrEkl-ADQYNf6jc-SBFpWjqkKFj6EXinR_CbJwPGhQBtA2saAfwEXvBPP4awezPXtrkUFk8kxU27Sk2ATS7p_-lhSx0RNIc4QCPCXCc7QyPqjBIbv_K9pXd1tCbl2fxo',3),(8,'2023-08-17 16:27:55.207324','2023-08-17 16:27:55.207324','eJloOX8IB1gjESVbVs2lyN:APA91bHeO-g4YQUi62a_AAXjCPbhRtFF0MPRi_tFUerO8f4uPgKUJyok9uqNPK7_ws8wAKX4gsxeodOhdMz9_13tloAzfmAzncu6CK88_mo7j-DFVb8aPAU_B5O4qn3ihwDpWbHsvPTZ',28),(9,'2023-08-17 17:57:28.604414','2023-08-17 17:57:28.604414','eBtftt9CgUSyA_jbMegCKa:APA91bEuChvu-9qdhoHtn2RHmvVAbtfVtnEPdIM1eLZy-25Kj1KhUzj1_m-V69TKhgsENPm5NGubhRuTnzs-7CgrUYhWnIShs-oqWT4TCVVJwWt7_kbXRCiK2XEFDZ1PC3DwZtTOYCRV',6),(10,'2023-08-17 22:32:55.897635','2023-08-17 22:32:55.897635','daQF8w6auup0nN1MbHDyGt:APA91bECB1O8AONezAkkgq3j2VisXIUYdAepmOa3VM6Xcn0fDrw26_qvQvrzy7iz8NBydISyQ9mE4VkMQW1eC8Fck6InDae7LdThayTzRcrNmKVXae-xSbdjev_y-pTpd688xMYQ6VGA',28),(11,'2023-08-17 23:47:46.931372','2023-08-17 23:47:46.931372','daQF8w6auup0nN1MbHDyGt:APA91bECB1O8AONezAkkgq3j2VisXIUYdAepmOa3VM6Xcn0fDrw26_qvQvrzy7iz8NBydISyQ9mE4VkMQW1eC8Fck6InDae7LdThayTzRcrNmKVXae-xSbdjev_y-pTpd688xMYQ6VGA',2),(12,'2023-08-17 23:51:05.336057','2023-08-17 23:51:05.336057','c5PT2PD8Om8_Ops88ITYDn:APA91bH4BvReynppkdyvvH-AektFSoLp8DHGOEIY3DlVgVCShL0MGYTutJ-kUExzxfamkBtAk894Ohm7mixrwD6kcjLO6xjqAXfRouMiDW9EI96OI2Q2XflY5HXDeH6jSA3KW_Y_I8Dn',25),(13,'2023-08-17 23:59:09.518044','2023-08-17 23:59:09.518044','c5PT2PD8Om8_Ops88ITYDn:APA91bH4BvReynppkdyvvH-AektFSoLp8DHGOEIY3DlVgVCShL0MGYTutJ-kUExzxfamkBtAk894Ohm7mixrwD6kcjLO6xjqAXfRouMiDW9EI96OI2Q2XflY5HXDeH6jSA3KW_Y_I8Dn',15),(14,'2023-08-18 00:00:17.473298','2023-08-18 00:00:17.473298','eOlcwEH8CFqfEFi78pI0Fv:APA91bF4GgV_c81zJLjND4Be6GNTU8exCMIK0wWc2xkbFUrLZU_Hs6ddcXphfbPecX-d5Xzr2G5c7B1cAvGYPc-8IynKmwuFXtgi575PyrRkarQNmTTG2QkOV96JCeDEh7qEIRuL37s4',36),(15,'2023-08-18 00:01:17.771179','2023-08-18 00:01:17.771179','eOlcwEH8CFqfEFi78pI0Fv:APA91bF4GgV_c81zJLjND4Be6GNTU8exCMIK0wWc2xkbFUrLZU_Hs6ddcXphfbPecX-d5Xzr2G5c7B1cAvGYPc-8IynKmwuFXtgi575PyrRkarQNmTTG2QkOV96JCeDEh7qEIRuL37s4',3),(16,'2023-08-18 00:11:25.830461','2023-08-18 00:11:25.830461','daQF8w6auup0nN1MbHDyGt:APA91bECB1O8AONezAkkgq3j2VisXIUYdAepmOa3VM6Xcn0fDrw26_qvQvrzy7iz8NBydISyQ9mE4VkMQW1eC8Fck6InDae7LdThayTzRcrNmKVXae-xSbdjev_y-pTpd688xMYQ6VGA',38),(17,'2023-08-18 00:13:43.082465','2023-08-18 00:13:43.082465','eOlcwEH8CFqfEFi78pI0Fv:APA91bF4GgV_c81zJLjND4Be6GNTU8exCMIK0wWc2xkbFUrLZU_Hs6ddcXphfbPecX-d5Xzr2G5c7B1cAvGYPc-8IynKmwuFXtgi575PyrRkarQNmTTG2QkOV96JCeDEh7qEIRuL37s4',4),(18,'2023-08-18 00:25:37.837687','2023-08-18 00:25:37.837687','eqBT-GPFMiaG1ycaAtIPEV:APA91bGpMHzYXN4cD23nlGyvC-8I6knqmzWIYDiPvAzxSwwYi3GLmuyO9iceOUm87vLEC6amu2WhEfsXhyN6bc1rI-Vs_UrghQnPFWcybYe-7C9AlxfhK3fsedGUHxbn27CHmtDqNfsE',3),(19,'2023-08-18 01:20:20.590636','2023-08-18 01:20:20.590636','daQF8w6auup0nN1MbHDyGt:APA91bECB1O8AONezAkkgq3j2VisXIUYdAepmOa3VM6Xcn0fDrw26_qvQvrzy7iz8NBydISyQ9mE4VkMQW1eC8Fck6InDae7LdThayTzRcrNmKVXae-xSbdjev_y-pTpd688xMYQ6VGA',11),(20,'2023-08-18 03:58:05.845338','2023-08-18 03:58:05.845338','c5PT2PD8Om8_Ops88ITYDn:APA91bFRORvL0X6OGZAI6KYREpuXqzwati4Q8Snx65PxaAxkLFg33zvd7Qw-LRunM66BfZAS-xDZm-KB3WIjslf0B4y7ZGdjhEK_y6bDSf-UplUiyXZAvakhTigCXXoOZ8_QbndEi_1W',7),(21,'2023-08-18 03:59:04.995090','2023-08-18 03:59:04.995090','c5PT2PD8Om8_Ops88ITYDn:APA91bFRORvL0X6OGZAI6KYREpuXqzwati4Q8Snx65PxaAxkLFg33zvd7Qw-LRunM66BfZAS-xDZm-KB3WIjslf0B4y7ZGdjhEK_y6bDSf-UplUiyXZAvakhTigCXXoOZ8_QbndEi_1W',15);
/*!40000 ALTER TABLE `fcm_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-18  9:35:35