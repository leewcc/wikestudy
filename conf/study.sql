/*
SQLyog v10.2 
MySQL - 5.5.28 : Database - wikestudy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wikestudy` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wikestudy`;

/* Function  structure for function  `return_article_id` */

/*!50003 DROP FUNCTION IF EXISTS `return_article_id` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `return_article_id`(art_type_id INT(11),art_author_id INT(11),art_title VARCHAR(50),art_content TEXT,art_time DATETIME,art_click INT) RETURNS int(11)
BEGIN
	INSERT INTO t_article(art_type_id,art_author_id,art_title,art_content,art_time,art_click) 
	VALUES (art_type_id,art_author_id,art_title,art_content,art_time,art_click);
	RETURN LAST_INSERT_ID();
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
