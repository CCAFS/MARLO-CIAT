ALTER TABLE `output_nextsubuser` 
  ADD CONSTRAINT `fk_nextsubuser_nextuser`
  FOREIGN KEY (`next_userid` )
  REFERENCES `output_nextusers` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_nextsubuser_nextuser` (`next_userid` ASC);