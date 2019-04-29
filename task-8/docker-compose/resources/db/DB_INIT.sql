create database vneklasniki;
USE vneklasniki;

CREATE TABLE `users` (
                       `id` int(11) NOT NULL AUTO_INCREMENT,
                       `name` varchar(20) DEFAULT NULL,
                       `surname` varchar(20) DEFAULT NULL,
                       `birthday` date DEFAULT NULL,
                       PRIMARY KEY (`id`),
                       UNIQUE KEY `name` (`name`,`surname`,`birthday`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `vgroups` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `groupName` varchar(50) DEFAULT NULL,
                         `author` varchar(50) DEFAULT NULL,
                         `creationDate` date DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `groupName` (`groupName`,`author`,`creationDate`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `services` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `login` varchar(30) DEFAULT NULL,
                          `pass` varchar(40) DEFAULT NULL,
                          `countStatus` varchar(30) DEFAULT NULL,
                          `requestCount` int(11) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;



CREATE TABLE `accesses` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `access` varchar(30) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `access` (`access`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `friends` (
                         `friendsId` int(11) NOT NULL AUTO_INCREMENT,
                         `selfUserId` int(11) DEFAULT NULL,
                         `friendUserId` int(11) DEFAULT NULL,
                         `creationDate` date DEFAULT NULL,
                         PRIMARY KEY (`friendsId`),
                         UNIQUE KEY `selfUserId` (`selfUserId`,`friendUserId`),
                         KEY `friendUserId` (`friendUserId`),
                         CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`selfUserId`) REFERENCES `users` (`id`),
                         CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`friendUserId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `messages` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `userFromId` int(11) DEFAULT NULL,
                          `userToId` int(11) DEFAULT NULL,
                          `creationDate` date DEFAULT NULL,
                          `message` varchar(200) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `userToId` (`userToId`),
                          KEY `userFromId` (`userFromId`),
                          CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`userToId`) REFERENCES `users` (`id`),
                          CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`userFromId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `presents` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `creationDate` date DEFAULT NULL,
                          `description` varchar(200) DEFAULT NULL,
                          `fromId` int(11) DEFAULT NULL,
                          `toId` int(11) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `fromId` (`fromId`),
                          KEY `toId` (`toId`),
                          CONSTRAINT `presents_ibfk_1` FOREIGN KEY (`fromId`) REFERENCES `users` (`id`),
                          CONSTRAINT `presents_ibfk_2` FOREIGN KEY (`toId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `serviceaccess` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `serviceId` int(11) DEFAULT NULL,
                               `accessId` int(11) DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `serviceId` (`serviceId`,`accessId`),
                               KEY `accessId` (`accessId`),
                               CONSTRAINT `serviceaccess_ibfk_1` FOREIGN KEY (`serviceId`) REFERENCES `services` (`id`),
                               CONSTRAINT `serviceaccess_ibfk_2` FOREIGN KEY (`accessId`) REFERENCES `accesses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

CREATE TABLE `usergroup` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `userId` int(11) DEFAULT NULL,
                           `groupId` int(11) DEFAULT NULL,
                           `subscribeDate` date DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `userId` (`userId`,`groupId`),
                           KEY `groupId` (`groupId`),
                           CONSTRAINT `usergroup_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
                           CONSTRAINT `usergroup_ibfk_2` FOREIGN KEY (`groupId`) REFERENCES `vgroups` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;