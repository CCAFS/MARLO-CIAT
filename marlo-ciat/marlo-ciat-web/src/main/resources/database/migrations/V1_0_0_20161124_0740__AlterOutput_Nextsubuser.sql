ALTER TABLE `output_nextsubuser` DROP FOREIGN KEY `fk_nextsubuser_nextuser`;
ALTER TABLE `output_nextsubuser` CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT, CHANGE COLUMN `next_userid` `next_userid` INT NULL DEFAULT NULL;