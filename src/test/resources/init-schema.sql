CREATE SCHEMA IF NOT EXISTS insurance;

CREATE TABLE IF NOT EXISTS insurance.system (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '구분',
  `description` text COMMENT '설명',
  `type` varchar(10) DEFAULT 'STRING' COMMENT '타입',
  `generator` varchar(20) DEFAULT NULL COMMENT '생성 주최',
  `length` int(11) DEFAULT NULL COMMENT '기본 자릿수',
  `display_status` varchar(10) DEFAULT 'SHOW' COMMENT '삭제여부',
  `create_by` varchar(20) DEFAULT NULL COMMENT '작성자',
  `create_time` datetime DEFAULT NULL COMMENT '작성시간',
  `modify_by` varchar(20) DEFAULT NULL COMMENT '수정자',
  `modify_time` datetime DEFAULT NULL COMMENT '수정시간',
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS insurance.key (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `system_id` bigint(20) DEFAULT NULL COMMENT 'system Id',
  `value` varchar(40) DEFAULT NULL COMMENT '생성된 키 값',
  `display_status` varchar(10) DEFAULT 'SHOW' COMMENT '삭제 여부',
  `create_by` varchar(20) DEFAULT NULL COMMENT '작성자',
  `create_time` datetime DEFAULT NULL COMMENT '작성시간',
  `modify_by` varchar(20) DEFAULT NULL COMMENT '수정자',
  `modify_time` datetime DEFAULT NULL COMMENT '수정시간',
  PRIMARY KEY (`id`)
);

