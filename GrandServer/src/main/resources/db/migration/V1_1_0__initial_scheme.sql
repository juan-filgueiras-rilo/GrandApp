-- Indexes for primary keys have been explicitly created.
CREATE TABLE IF NOT EXISTS `User` (
    `id` BIGINT AUTO_INCREMENT,
    `userName` VARCHAR(60) NOT NULL,
    `password` VARCHAR(60) NOT NULL, 
    `email` VARCHAR(60) NOT NULL,
    `role` TINYINT NOT NULL,
    CONSTRAINT `UserPK` PRIMARY KEY (`id`),
    CONSTRAINT `UserNameUniqueKey` UNIQUE (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE INDEX UserIndexByUserName ON User (userName);