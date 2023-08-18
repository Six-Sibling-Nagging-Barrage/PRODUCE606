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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `member_role` varchar(255) DEFAULT NULL,
  `member_state` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `oauth_identifier` varchar(255) DEFAULT NULL,
  `oauth_type` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ticket` bigint DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'2023-08-17 14:48:17.707298','2023-08-18 04:26:56.805422','자기소개는 생략한다.','email0@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member1.jpg','USER','ACTIVE','SpongeBob',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',47),(2,'2023-08-16 14:48:17.728651','2023-08-17 23:54:47.319342','자기소개는 생략한다.','email1@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member2.png','USER','ACTIVE','Bubble',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',49),(3,'2023-08-15 14:48:17.732806','2023-08-18 00:41:53.804853','자기소개는 생략한다.','email2@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member3.jpg','USER','ACTIVE','징징이',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(4,'2023-08-14 14:48:17.736052','2023-08-14 14:48:17.736052','자기소개는 생략한다.','email3@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member4.jpg','USER','ACTIVE','짱구',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(5,'2023-08-13 14:48:17.739349','2023-08-13 14:48:17.739349','자기소개는 생략한다.','email4@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member5.jpg','USER','ACTIVE','친절한양',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(6,'2023-08-12 14:48:17.743008','2023-08-17 18:00:28.479211','자기소개는 생략한다.','email5@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member6.jpg','USER','ACTIVE','판다스',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(7,'2023-08-11 14:48:17.746400','2023-08-11 14:48:17.746400','자기소개는 생략한다.','email6@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member7.jpg','USER','ACTIVE','fin',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(8,'2023-08-10 14:48:17.749878','2023-08-10 14:48:17.749878','자기소개는 생략한다.','email7@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member8.jpg','USER','ACTIVE','아무것도하기싫어',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(9,'2023-08-09 14:48:17.753271','2023-08-09 14:48:17.753271','자기소개는 생략한다.','email8@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member9.jpg','USER','ACTIVE','야도란',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(10,'2023-08-08 14:48:17.756698','2023-08-08 14:48:17.756698','자기소개는 생략한다.','email9@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member10.jpg','USER','ACTIVE','귀여운파이리입니다만',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(11,'2023-08-07 14:48:17.760242','2023-08-18 01:26:06.300181','자기소개는 생략한다.','email10@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member11.jpg','USER','ACTIVE','양파쿵야',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',47),(12,'2023-08-06 14:48:17.763665','2023-08-06 14:48:17.763665','자기소개는 생략한다.','email11@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member12.jpg','USER','ACTIVE','영국남자',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(13,'2023-08-05 14:48:17.766922','2023-08-05 14:48:17.766922','자기소개는 생략한다.','email12@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member13.jpg','USER','ACTIVE','쿠로미이즈마인',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(14,'2023-08-04 14:48:17.770375','2023-08-04 14:48:17.770375','자기소개는 생략한다.','email13@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member14.jpg','USER','ACTIVE','BTS못잃어',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(15,'2023-08-03 14:48:17.773844','2023-08-18 09:13:52.029341','자기소개는 생략한다.','email14@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member15.jpg','USER','ACTIVE','싸피쾌남123',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',44),(16,'2023-08-02 14:48:17.778076','2023-08-17 15:04:57.286105','자기소개는 생략한다.','email15@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member16.jpg','USER','ACTIVE','유애나',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',51),(17,'2023-08-01 14:48:17.781482','2023-08-01 14:48:17.781482','자기소개는 생략한다.','email16@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member17.jpg','USER','ACTIVE','Beeeeee',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(18,'2023-07-31 14:48:17.784623','2023-07-31 14:48:17.784623','자기소개는 생략한다.','email17@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member18.png','USER','ACTIVE','HTML싸개',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(19,'2023-07-30 14:48:17.787952','2023-07-30 14:48:17.787952','자기소개는 생략한다.','email18@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member19.jpg','USER','ACTIVE','스누피조콜',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(20,'2023-07-29 14:48:17.791427','2023-07-29 14:48:17.791427','자기소개는 생략한다.','email19@ssafy.com','https://jansori.s3.ap-northeast-2.amazonaws.com/upload/member20.jpg','USER','ACTIVE','테디베어',NULL,NULL,'$2a$10$sUIvQz.218ms3B6yTIFlvOoemUtt4GCVzovRVqNDiam8lcUrAAwF6',50),(21,'2023-08-17 14:48:47.120835','2023-08-18 09:09:55.507288','gdgd','legend123@ssafy.com','djxoqic8jnrmy.cloudfront.net/upload/812836bd-7895-4dbf-8feb-8e8beef4c26c.png','USER','ACTIVE','용_태',NULL,NULL,'$2a$10$0/I5qOuWe.NfjntCSkNjI.sESnag5ezYr0MgGqjyFiXd3VOAtLkgS',0),(22,'2023-08-17 14:48:59.651413','2023-08-17 16:15:15.736069','안냐세요','ccc@ssafy.com','djxoqic8jnrmy.cloudfront.net/upload/a41d0c3c-ed1b-47e4-ac9e-256af6cc606b.png','USER','ACTIVE','크리미라떼',NULL,NULL,'$2a$10$gzCC7KgK2Ft0c/QgGQ5B9ekM4XEVpPXXmpjeay.0ht8Sh4.e4NEjG',0),(23,'2023-08-17 14:49:03.362119','2023-08-17 15:11:08.878971','sdf','bysuk05@ssafy.com','https://djxoqic8jnrmy.cloudfront.net/upload/843948e7-dc28-4981-a21f-420549209cc8.jpg','USER','ACTIVE','병구테스트1',NULL,NULL,'$2a$10$YIO1lDUVTySd21DatLhdAuSB.ghdizw7jisnuxHqlNLl4889Q0Bv.',5),(24,'2023-08-17 14:51:26.004492','2023-08-17 20:46:52.867675','마라탕조아','janjanjan@jan.com','https://djxoqic8jnrmy.cloudfront.net/upload/2b69bc7d-1d8f-4241-974c-c3f91a2d5ca4.jpeg','USER','ACTIVE','마라탕킬러',NULL,NULL,'$2a$10$k6TM74wGmtHb67ypORe5ou3dhRLDelzmjKtZcGyuX6NzyopOFVEBe',4),(25,'2023-08-17 14:52:23.151379','2023-08-18 08:55:19.553824','내가 짱이얌','sungbaek@ssafy.com','https://djxoqic8jnrmy.cloudfront.net/upload/3935bc11-6c3c-4581-8e85-f322326adc49.jpg','USER','ACTIVE','성백짱짱',NULL,NULL,'$2a$10$LJtawnsoGt6cnj/OdxCafuiqzYhGVeKzAoIirfjhcDSZDC1AD2xHK',3),(26,'2023-08-17 15:14:55.769520','2023-08-18 01:31:21.811896','그만 멈춰!','lcj000107@naver.com','https://djxoqic8jnrmy.cloudfront.net/upload/5433c054-5929-4164-b04d-eef1831cd220.png','USER','ACTIVE','나는야멍충이',NULL,NULL,'$2a$10$DE94fakY8e3J9ZScwELGKe9p3HaqAGdL3ea3qiI4LErmbp5XdSZNG',5),(27,'2023-08-17 15:25:03.110457','2023-08-18 09:31:38.660500','잔소리 잘해요','namani@naver.com',NULL,'USER','ACTIVE','잔소리대마왕',NULL,NULL,'$2a$10$l9Zjz8MOrlCBAQC/zqf/FOkeTz8J8BbSDrNgcvm8ISBgowrRf3Y7i',0),(28,'2023-08-17 16:27:50.177479','2023-08-18 01:03:57.544822','훠궈 먹고 싶어요','aaa@ssafy.com','https://djxoqic8jnrmy.cloudfront.net/upload/6950b840-bbb5-4e7e-87ec-3ecc2d6dabc6.png','USER','ACTIVE','정혀니',NULL,NULL,'$2a$10$Cxw9r3F0Phf6j9eEwxpwHui3r4YYbHjEdA3p84lwOzGGBVUEcVUri',0),(29,'2023-08-17 17:33:06.911442','2023-08-17 17:33:06.911442','다시는 닉네임을 비워두지 않겠습니다.','ws_45@naver.com',NULL,'GUEST','ACTIVE','푱푱',NULL,NULL,'$2a$10$MVWEZm.ILMCuLWsfljSDRuJs1UYL90KwWvA6xWYmCGLVMGCpTwr8i',5),(30,'2023-08-17 17:33:10.539151','2023-08-17 17:34:05.351959','으넌넌','kimeuinyun1@gmail.com',NULL,'USER','ACTIVE','으넌넌',NULL,NULL,'$2a$10$fPmIWQe5gL6RAqMt0BCzLueko7UEUSsuhEA9I9KdUtf9Fr0W4NwMy',6),(31,'2023-08-17 17:34:06.747986','2023-08-17 17:35:42.657661',' ','2@2.2',NULL,'USER','ACTIVE','22',NULL,NULL,'$2a$10$M.zPv/4NBxo0uJ0g83zctOM10ElN6VvHx6g3inkn4RMyLnaJt59EK',6),(32,'2023-08-17 17:45:51.162090','2023-08-17 17:48:33.645369','eeee','testtest@test.com',NULL,'USER','ACTIVE','eeeeeee',NULL,NULL,'$2a$10$d7u/.RXECiOkBZMf7d0eg.EkZMr75IU/JZhNwjTEjcMWyvby/jk.2',2),(33,'2023-08-17 17:47:38.383703','2023-08-17 17:48:24.944314','안녕하세요','parkyehan2060@gmail.com',NULL,'USER','ACTIVE','한한',NULL,NULL,'$2a$10$My.vRsT5DhLjpKQtUDRsmeWAm5fL9D1Xs0YA5UqGJacK0dz9q9Oam',5),(34,'2023-08-17 18:57:06.425930','2023-08-17 18:59:11.705109','다시는 닉네임을 비워두지 않겠습니다.','trashcan1159@gmail.com',NULL,'GUEST','ACTIVE','쓰레기통',NULL,NULL,'$2a$10$nsEp7bN5YRe3T9c/xmUMTOxRH0KNFisVIHrrPtk4ugd9b6OpH1z4e',6),(35,'2023-08-17 21:38:07.539144','2023-08-17 21:41:57.921145','다시는 닉네임을 비워두지 않겠습니다.','legend1234@ssafy.com',NULL,'GUEST','ACTIVE','전설',NULL,NULL,'$2a$10$98WTTgRH0N0v4.VmyglMMOMXNtFaaRnPvCSpUS/p8.mkibWxD9NX2',5),(36,'2023-08-18 00:00:09.870095','2023-08-18 00:00:42.749263','ㅌ','bysuk06@ssafy.com',NULL,'USER','ACTIVE','영석테스트',NULL,NULL,'$2a$10$0Eq6Fw3M0J1QTftSSYEkQ.p2l6FrPnBfzqvlxW0kZ9m7Y45WmQwYe',5),(37,'2023-08-18 00:06:14.136591','2023-08-18 00:06:14.136591',NULL,'sma07174@naver.com',NULL,'GUEST','ACTIVE',NULL,NULL,NULL,'$2a$10$mzvjVp5lTTS9Bx70ulzHquhsd6s5.fWtaza1c6BwwNr1xF/sPU8gG',5),(38,'2023-08-18 00:11:18.993818','2023-08-18 00:11:18.993818',NULL,'ddd@ssafy.com',NULL,'GUEST','ACTIVE',NULL,NULL,NULL,'$2a$10$WGCZ8qvjfD3u4ZRejf1WEuntcxe21anM77XUwCu4O14UX48ihuhSS',5),(39,'2023-08-18 07:27:52.818313','2023-08-18 07:27:52.818313',NULL,'42.4.woonchoi@gmail.com',NULL,'GUEST','ACTIVE',NULL,NULL,NULL,'$2a$10$snnDR4A62VfASMjD63WZhuJ0geDX.Znw.OBui.YC/4En8XAztCfT6',5);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-18  9:36:17
