ALTER TABLE `center_users` DROP FOREIGN KEY `center_users_ibfk_4` , DROP FOREIGN KEY `center_users_ibfk_3` , DROP FOREIGN KEY `center_users_ibfk_1` ;
ALTER TABLE `center_users` 
DROP INDEX `fk_crp_users_modified_by_users_id` 
, DROP INDEX `fk_crp_users_created_by_users_id` ;