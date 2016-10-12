ALTER TABLE `research_impact` CHANGE COLUMN `target_year` `target_year` INT NULL  ;
ALTER TABLE `research_impact` ADD COLUMN `research_program_id` INT NOT NULL  AFTER `target_year` , 
  ADD CONSTRAINT `fk_impact_program`
  FOREIGN KEY (`research_program_id` )
  REFERENCES `marlo_ciat_db`.`research_program` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_impact_program` (`research_program_id` ASC) ;