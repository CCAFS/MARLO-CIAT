ALTER TABLE `research_output_nextsubusers` 
  ADD CONSTRAINT `fk_output_nextsubuser`
  FOREIGN KEY (`nextsubuser_id` )
  REFERENCES `output_nextsubuser` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_output_researchoutput`
  FOREIGN KEY (`research_output_id` )
  REFERENCES `research_outputs` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_output_nextsubuser` (`nextsubuser_id` ASC) 
, ADD INDEX `fk_output_researchoutput` (`research_output_id` ASC);