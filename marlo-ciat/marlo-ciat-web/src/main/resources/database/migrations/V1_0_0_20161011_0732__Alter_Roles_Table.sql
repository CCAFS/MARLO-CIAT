ALTER TABLE `roles` CHANGE COLUMN `crp_id` `center_id` INT NOT NULL  
, DROP INDEX `crp_id` 
, ADD INDEX `crp_id` USING BTREE (`center_id` ASC) ;