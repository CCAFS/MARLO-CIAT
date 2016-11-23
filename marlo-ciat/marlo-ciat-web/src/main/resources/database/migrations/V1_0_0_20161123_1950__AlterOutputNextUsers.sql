  ALTER TABLE `research_output_nextusers` 
  ADD CONSTRAINT `fk_output_nextusers`
  FOREIGN KEY (`nextuser_id` )
  REFERENCES `output_nextusers` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_output_nextusers` (`nextuser_id` ASC) ;

ALTER TABLE `research_output_nextsubusers` 
  ADD CONSTRAINT `fk_output_nextsubuser`
  FOREIGN KEY (`nextsubuser_id` )
  REFERENCES `nextoutput_user` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_output_researchoutput`
  FOREIGN KEY (`research_output_id` )
  REFERENCES `reserach_outputs` (`created_by` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_output_nextsubuser` (`nextsubuser_id` ASC) 
, ADD INDEX `fk_output_researchoutput` (`research_output_id` ASC) ;

ALTER TABLE `nextoutput_user` 
  ADD CONSTRAINT `fk_nextsubuser_nextuser`
  FOREIGN KEY (`next_userid` )
  REFERENCES `output_nextusers` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_nextsubuser_nextuser` (`next_userid` ASC) ;