CREATE SCHEMA IF NOT EXISTS insurance;

CREATE TABLE IF NOT EXISTS insurance.key_channel (
 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
 `name` varchar(20) NOT NULL DEFAULT '' COMMENT '구분',
 `description` text NOT NULL COMMENT '설명',
 `type` varchar(10) NOT NULL COMMENT '타입',
 `generator` varchar(20) DEFAULT NULL COMMENT '생성 주최',
 `length` int(11) DEFAULT NULL COMMENT '기본 자릿수',
 `value` bigint(20) DEFAULT NULL COMMENT '최근 생성 값',
 `display_status` varchar(10) DEFAULT NULL COMMENT '삭제여부',
 `create_by` varchar(20) DEFAULT NULL COMMENT '작성자',
 `create_time` datetime DEFAULT NULL COMMENT '작성시간',
 `modify_by` varchar(20) DEFAULT NULL COMMENT '수정자',
  `modify_time` datetime DEFAULT NULL COMMENT '수정시간',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
);

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
  UNIQUE KEY `uk_channel_value` (`channel_id`,`value`)
);
