INSERT INTO insurance.key_channel (`id`, `name`, `description`, `type`, `generator`, `length`, `display_status`, `create_by`, `create_time`, `modify_by`, `modify_time`)
VALUES
(1,'claim-number','고객센터에서 고객 문의사항이 접수될 때 사용하는 Key','STRING',NULL,NULL,'SHOW','system','2021-06-04 00:00:00',NULL,NULL),
(2,'custom-key','redis 방식의 테스트를 위한 Key','NUMBER','redis',10,'SHOW','system','2021-06-05 15:50:00',NULL,NULL);
