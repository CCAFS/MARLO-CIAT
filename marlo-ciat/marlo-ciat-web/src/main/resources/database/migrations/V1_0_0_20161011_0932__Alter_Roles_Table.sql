ALTER TABLE `roles` 
  ADD CONSTRAINT `fk_roles_center`
  FOREIGN KEY (`center_id` )
  REFERENCES `research_center` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_roles_center` (`center_id` ASC) ;