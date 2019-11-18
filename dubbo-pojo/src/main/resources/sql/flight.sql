SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `flight`
-- ----------------------------
DROP TABLE IF EXISTS `flight`;
CREATE TABLE `flight` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `departCity` varchar(255) DEFAULT NULL,
  `departCityCode` varchar(255) DEFAULT NULL,
  `arriveCity` varchar(255) DEFAULT NULL,
  `arriveCityCode` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `departDate` varchar(255) DEFAULT NULL,
  `departTime` varchar(255) DEFAULT NULL,
  `sms` tinyint(4) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sendEmail` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `key_sms` (`sms`) USING BTREE,
  KEY `key_email` (`sendEmail`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
