--ALTER TABLE `roles` DROP FOREIGN KEY `roles_ibfk_1` ;
--ALTER TABLE `roles` CHANGE COLUMN `crp_id` `center_id` INT NOT NULL  , 
--  ADD CONSTRAINT `roles_ibfk_1`
--  FOREIGN KEY (`center_id` )
--  REFERENCES `research_center` (`id` )
--  ON DELETE CASCADE
--  ON UPDATE CASCADE
--, DROP INDEX `crp_id` 
--, ADD INDEX `crp_id` USING BTREE (`center_id` ASC) ;

