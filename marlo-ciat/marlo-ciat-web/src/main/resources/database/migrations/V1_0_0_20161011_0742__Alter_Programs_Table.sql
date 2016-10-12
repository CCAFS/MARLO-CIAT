ALTER TABLE `research_program` 
  ADD CONSTRAINT `fk_rprogram_type`
  FOREIGN KEY (`program_type` )
  REFERENCES `all_types` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_rprogram_type` (`program_type` ASC) ;