ALTER TABLE `research_output_nextusers` 
  ADD CONSTRAINT `fk_outputs_researchoutput`
  FOREIGN KEY (`research_output_id` )
  REFERENCES `research_outputs` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_outputs_researchoutput` (`research_output_id` ASC);