CREATE TABLE `emp_mst` (
  `emp_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(100) NOT NULL,
  `emp_bu` varchar(100) NOT NULL,
  `emp_dob` date DEFAULT NULL,
  `email_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

CREATE TABLE `emp_profile` (
  `emp_profile_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `emp_profile_date` date DEFAULT NULL,
  `weight` decimal(5,2) DEFAULT NULL,
  `height` decimal(5,2) DEFAULT NULL,
  `bp` int(11) DEFAULT NULL,
  `sugar` int(11) DEFAULT NULL,
  `heart_rate` int(11) DEFAULT NULL,
  PRIMARY KEY (`emp_profile_id`),
  KEY `emp_id` (`emp_id`),
  CONSTRAINT `emp_profile_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `emp_mst` (`emp_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

CREATE TABLE `emp_physician_recommendation` (
  `emp_phy_recommend_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `emp_profile_id` int(11) DEFAULT NULL,
  `running_min` int(11) DEFAULT NULL,
  `cycling_min` int(11) DEFAULT NULL,
  `steps_cnt` int(11) DEFAULT NULL,
  `distinct_mtr` int(11) DEFAULT NULL,
  `calories` float(8,2) DEFAULT NULL,
  PRIMARY KEY (`emp_phy_recommend_id`),
  KEY `emp_id` (`emp_id`),
  KEY `emp_profile_id` (`emp_profile_id`),
  CONSTRAINT `emp_physician_recommendation_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `emp_mst` (`emp_id`) ON DELETE CASCADE,
  CONSTRAINT `emp_physician_recommendation_ibfk_2` FOREIGN KEY (`emp_profile_id`) REFERENCES `emp_profile` (`emp_profile_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

CREATE TABLE `emp_workout_stats` (
  `emp_workout_stats_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `running_min` decimal(7,2) DEFAULT NULL,
  `cycling_min` decimal(7,2) DEFAULT NULL,
  `steps_cnt` int(11) DEFAULT NULL,
  `distinct_mtr` decimal(7,2) DEFAULT NULL,
  `calories` float(8,2) DEFAULT NULL,
  `running_adh` decimal(5,2) DEFAULT NULL,
  `cycling_adh` decimal(5,2) DEFAULT NULL,
  `steps_adh` decimal(5,2) DEFAULT NULL,
  `distance_adh` decimal(5,2) DEFAULT NULL,
  `calories_adh` decimal(5,2) DEFAULT NULL,
  `overall_adh` decimal(5,2) DEFAULT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `from_period` timestamp NULL DEFAULT NULL,
  `to_period` timestamp NULL DEFAULT NULL,
  `zone_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`emp_workout_stats_id`),
  KEY `emp_id` (`emp_id`),
  CONSTRAINT `emp_workout_stats_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `emp_mst` (`emp_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2412 DEFAULT CHARSET=utf8;


CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_role_roleid_idx` (`role_id`),
  KEY `fk_user_role_userid` (`user_id`),
  CONSTRAINT `fk_user_role_roleid` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

CREATE TABLE `zones` (
  `zone_id` int(11) NOT NULL AUTO_INCREMENT,
  `zone_start` decimal(5,2) DEFAULT NULL,
  `zone_end` decimal(5,2) DEFAULT NULL,
  `zone_desc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`zone_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `error_log` (
  `error_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `error_desc` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`error_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `food_menu` (
  `food_menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_date` date DEFAULT NULL,
  `food_item_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`food_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `food_items` (
  `food_item_id` int(11) NOT NULL AUTO_INCREMENT,
  `food_item_desc` varchar(200) DEFAULT NULL,
  `food_item_qty` decimal(5,2) DEFAULT NULL,
  `food_item_calories` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`food_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
