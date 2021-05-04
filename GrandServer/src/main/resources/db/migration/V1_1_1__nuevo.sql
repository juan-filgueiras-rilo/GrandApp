CREATE TABLE IF NOT EXISTS `Device` (
    `idDevice` BIGINT AUTO_INCREMENT,
    `name` VARCHAR(60) NOT NULL,
    `description` VARCHAR(60) NOT NULL,
    `userId` BIGINT NOT NULL,
    CONSTRAINT `DevicePK` PRIMARY KEY (`idDevice`),
    CONSTRAINT `DeviceUserFK` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `Routine` (
    `idRoutine` BIGINT AUTO_INCREMENT,
    `name` VARCHAR(60) NOT NULL,
    `description` VARCHAR(60) NOT NULL, 
    `userId` BIGINT NOT NULL,
    CONSTRAINT `RoutinePK` PRIMARY KEY (`idRoutine`),
    CONSTRAINT `RoutineUserFK` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `Routine_Device` (
	`idRoutine` BIGINT NOT NULL,
	`idDevice` BIGINT NOT NULL,
	CONSTRAINT `DeviceRoutine` PRIMARY KEY (`idRoutine`, `idDevice`),
	CONSTRAINT `DeviceRoutineRoutineFK` FOREIGN KEY (`idRoutine`) REFERENCES `Routine` (`idRoutine`),
	CONSTRAINT `DeviceRoutineDeviceFK` FOREIGN KEY (`idDevice`) REFERENCES `Device` (`idDevice`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;