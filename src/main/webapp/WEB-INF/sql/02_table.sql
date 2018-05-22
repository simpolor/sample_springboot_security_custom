CREATE TABLE `demo` (
	`seq` INT(11) NOT NULL AUTO_INCREMENT COMMENT '번호',
	`name` VARCHAR(50) NOT NULL COMMENT '이름',
	`age` INT(11) NULL DEFAULT NULL COMMENT '나이',
	`hobby` VARCHAR(50) NULL DEFAULT NULL COMMENT '취미',
	PRIMARY KEY (`seq`)
);

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(500) NOT NULL,
  `name` varchar(20) NOT NULL DEFAULT '',
  `isAccountNonExpired` tinyint(1) NOT NULL,
  `isAccountNonLocked` tinyint(1) NOT NULL,
  `isCredentialsNonExpired` tinyint(1) NOT NULL,
  `isEnabled` tinyint(1) NOT NULL
);

CREATE TABLE `authority` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `authority_name` varchar(20) NOT NULL DEFAULT ''
);