CREATE DATABASE  IF NOT EXISTS `myPeriodics` /*!40100 DEFAULT CHARACTER SET utf16 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `myPeriodics`;
-- MySQL dump 10.13  Distrib 8.0.19, for Linux (x86_64)
--
-- Host: localhost    Database: myPeriodics
-- ------------------------------------------------------
-- Server version	8.0.19-0ubuntu5

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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(450) NOT NULL,
  `balance` decimal(13,2) DEFAULT '0.00',
  `user_role` varchar(45) NOT NULL,
  `account_status` varchar(45) NOT NULL DEFAULT 'active',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf16;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'Masha-23','Masha','Emelyanova','masha@ukr.net','1000:fe6f9fb74acb9ffac67270976ee024b5:f140abafc792837eb195c91e964ceb69e779ffbc8f8ee72c6bd1cb2d8a26ef8839877be8a0c885c014d08eb4b5fc713ae75d518a6636549241d97cfc382dec6d',12.00,'subscriber','ACTIVE'),(4,'Nikolay','Nikolay','Olifirenko','nikolay@ukr.net','1000:2633dd384544ae6e1d721c11e755df52:959648cb19f203a3440cd51e623c08422f252ece2d8da897dcf88e904d314bf9c93e870a6d3f7e4e47abfda6e96040dc8353b2ff64d0a6ab69bcc5ab3d31d573',78.00,'subscriber','BLOCKED'),(5,'Sergey_Kyiv','Sergiy','Kolesnikov','Sergiy@ukr.net','1000:a52486723aa91bf27bc2a80fbd03eb92:c4e3d54a520b9572a3c46f7249e6a498b2c7d1c571df6ccf057848c81e0392922caae1cc40165f7eb1e7aa53d7154eae947b0e456ce86e6d321158c472eb3154',22.00,'subscriber','BLOCKED'),(6,'Venkat','Venkat','Subramaniam','Venkat@ukr.nett','1000:649eb1cf433b2c1c42f8b836554a8c3f:267fbb38881491ec2fd6ba3e2f40cfba34c7088a29493126dc42dd0edbd65f62f1ccaa9250c497d2000b8083ac7c1386a3802bb6917dcdacbd886a065f3aad9e',126.00,'subscriber','active'),(7,'Nikolas','Nikolas','Ohayo','nikolas@ukr.net','1000:2a9d76778ea9b13179905d1073fecd67:8f34e0432b187faccc3b3ca78dbf0c8af1893cc375786695676cf7f91d684be47bec8996a493df700555aecf7fb3a65b7b382bc37d53a7148bbfa0f58899c6c3',54.00,'subscriber','active'),(8,'Natasha_Odessa','Natali','Sergienko','nata@gmail.com','1000:a35fbbb34e6dccefd1ff361b13c67a81:a18595a8482c8b018f0c4af451726d48ca58b0b1174e9764e18ca2d49d3265ffc2836e9518b0cd38a942bd51bdd4cf82c3891714afb7a4ea335540db825cf6d0',44.00,'subscriber','BLOCKED'),(9,'Ihtiandr','Alex','Sidorenko','alex@ukr.net','1000:6aaeed351dee9d6b55ba17f55ae7f449:589ca379cc9f1b0c0bc5e47a116c13d9bde2de8cda16a5dd0a88ec8aae7e41b632f0121649d1ed6c855cbb35de9e6d00bc530a75144297b69cb27f5271ae9067',87.00,'subscriber','active'),(10,'Svetka','Svetlana','Afanasyeva','svetik@new.net','1000:7ff375ed5675e324d830e908fb583dfe:a7201f89355ac0dbd1d62e4ae042f97f6d476acbed743fb2bde1ece73500bc31b6612c8b9e45568d49bae90972aa7941c9cc8d9dc1c52b0cfba156aed0f5375a',345.00,'subscriber','BLOCKED'),(11,'Mustafa','Mustafa','Nasredin','mustafa@google.com','1000:307f8d6ae678c7d2a4df54f963013044:c442411435e5568e274686542e3cd7ae5c851c4f54e580a7d7e354d919fafb29b62866d21bd88ea5eada08f9220a558aa79bb7ef0d292adce953e4db788d2c1f',56.00,'subscriber','active'),(12,'Santana','Santana','Alehandro','santana@ukr.net','1000:aa44d64cc536abbe5d955a6415c6a293:f246bb2f24002db12ba38400efe61a9482540b22f2b83b0d9576691faf6068fa55f33921ee057fee1cc0d78af276af8f3ec6a7b33b553c790a5e109b783c90f3',23.00,'subscriber','active'),(13,'Matador','Ikander','Ambroskin','iskander@ukr.net','1000:208d8edbc5dcdb1dc1c9b006c0c79836:fb0b5873010783a09fa879a65db8c8f3fe18ee5549c80e7efd0c76bea81c0f4aac9ab898825640d685783a876102be3ee47432ac26b0694f285f0bbac24d0e46',17.00,'subscriber','active'),(14,'GreatReader','Игорь','Малянов','igor@ukr.net','1000:7e6e86b61eff849a4c472cec1825fda8:1e4cb5ec31ef82309867d1345633c62ff39f00f275168b1d088fbad7fb34f8ccbcc2cd53c665c15f4cf024b5209973f23b3c731ace62dd88c693c63af1646e84',43.00,'subscriber','ACTIVE'),(15,'SuperAdmin','Admin','Админов','admin@ukr.net','1000:385ad9b4feef0cbf47834fe799b3915a:905c23c3c4f6579ae1131ce9794a10405b4beffa3f2cde7c97c21be0e14bd682c22a45dee663c0a1f122a12f85d1d2cb94f3b0eda40b7b14d69263816a46e034',76.00,'admin','active'),(16,'Kate','Ekaterina','Milashka','kate@ukr.net','1000:76b60f68fbe0b70b4e9a51ef5628a69c:49471c739839f38b637da0b0d5f6e730a9f6a73de0e3f4f2f1944c258ad5d02ae7bff41cd4b53ed328018af40912f6882b599dc2442d988730927e5dd826a46a',440.00,'subscriber','ACTIVE'),(17,'NewUser','Igor','Noname','igor@gmail.com','1000:a737f7be2658d8e0cae942dce64bc4bd:fa13b959038cd0f1fb77d165a05abf20bc10d9d5d556d642ecea48c9ad7cd2ebd105e2c9405ca1513442350ae601738d4ad2bceeb12499b512930d758ff00d2a',0.00,'SUBSCRIBER','ACTIVE'),(18,'testLogin','testName','testSurname','testEmail@test.ua','1000:944c4d27a0d891b3a6b1aef5c7690ead:29ec8ea2e490e26091fbadcb7eca45e009ac8adac9bbba0473a284be6948d3943912e19108672aa3812c1eb7fdf80483b8f70639535769cd6fdb75e926b081a2',0.00,'SUBSCRIBER','ACTIVE'),(19,'Checker','Checker','Checker','checker@ukr.net','1000:c4e5ef987904a5188443ae2af4eff30b:0638c576bbfb9c3db533025827b03b1c2f0980896e0fd14c59118af0bcc17f90c5fdc263d72adca4c086c630f2ba2aa474a44cfeaa72b30ebe7a67bf845c3503',0.00,'SUBSCRIBER','ACTIVE'),(20,'Checker_2','Игорь','Checkerw','alex2@ukr.net','1000:50ffc72b4d7e51b657fe61834050d91f:d14bfadd9fbbbf323b1e33a356abd61b2ec33a857b83b6db50ed03338cb83f93f5442bbe064c34c5f0a08f8472920d20feb75926b0d4f020413537e1c5201882',0.00,'SUBSCRIBER','ACTIVE'),(21,'WonderfulUser','Алексей','Никифоров','alexey@ukr.net','1000:b92f620c564399ec168d5d559f4a9fd4:d8052f4d426ca42abb26378d584d6c68a198c7e5e30f1b0dafdb4787afa76f520fc480cd1e2e04d47425445ef971dfbcc0934419ad6fe2efa38a4fa7e2bb5683',20.00,'SUBSCRIBER','ACTIVE');
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

-- Dump completed on 2021-06-11 14:43:32
