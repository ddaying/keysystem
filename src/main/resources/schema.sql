CREATE DATABASE IF NOT EXISTS `insurance` DEFAULT CHARACTER SET = `utf8mb4`;

CREATE TABLE IF NOT EXISTS insurance.key_channel (
 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
 `name` varchar(20) NOT NULL DEFAULT '' COMMENT '구분',
 `description` text NOT NULL COMMENT '설명',
 `type` varchar(10) NOT NULL COMMENT '타입',
 `generator` varchar(20) DEFAULT NULL COMMENT '생성 주최',
 `length` int(11) DEFAULT NULL COMMENT '기본 자릿수',
 `display_status` varchar(10) DEFAULT NULL COMMENT '삭제여부',
 `create_by` varchar(20) DEFAULT NULL COMMENT '작성자',
 `create_time` datetime DEFAULT NULL COMMENT '작성시간',
 `modify_by` varchar(20) DEFAULT NULL COMMENT '수정자',
 `modify_time` datetime DEFAULT NULL COMMENT '수정시간',
 PRIMARY KEY (`id`),
 UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Key 정보';

CREATE TABLE IF NOT EXISTS insurance.key_data (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `channel_id` bigint(20) DEFAULT NULL COMMENT 'key channel Id',
  `value` varchar(40) DEFAULT NULL COMMENT '생성된 키 값',
  `display_status` varchar(10) DEFAULT NULL COMMENT '삭제 여부',
  `create_by` varchar(20) DEFAULT NULL COMMENT '작성자',
  `create_time` datetime DEFAULT NULL COMMENT '작성시간',
  `modify_by` varchar(20) DEFAULT NULL COMMENT '수정자',
  `modify_time` datetime DEFAULT NULL COMMENT '수정시간',
  PRIMARY KEY (`id`),
  KEY `uk_value` (`channel_id`,`value`),
  KEY `index_key_id` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='각 시스템 별 발급된 키';
