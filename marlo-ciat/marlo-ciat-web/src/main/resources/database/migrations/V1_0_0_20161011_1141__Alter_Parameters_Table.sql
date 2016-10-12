ALTER TABLE `parameters` CHANGE COLUMN `crp_id` `center_id` INT NOT NULL  , 
  ADD CONSTRAINT `fk_parameters_center`
  FOREIGN KEY (`center_id` )
  REFERENCES `research_center` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, DROP INDEX `crp_id` 
, ADD INDEX `crp_id` USING BTREE (`center_id` ASC) 
, ADD INDEX `fk_parameters_center` (`center_id` ASC) ;
