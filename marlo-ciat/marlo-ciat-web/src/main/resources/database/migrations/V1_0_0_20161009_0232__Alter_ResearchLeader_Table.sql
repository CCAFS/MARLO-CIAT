ALTER TABLE `marlo_ciat_db`.`research_leader` 
  ADD CONSTRAINT `fk_leader_res_area`
  FOREIGN KEY (`research_area_id` )
  REFERENCES `marlo_ciat_db`.`research_area` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_leader_res_program`
  FOREIGN KEY (`research_program_id` )
  REFERENCES `marlo_ciat_db`.`research_program` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_leader_res_center`
  FOREIGN KEY (`research_center_id` )
  REFERENCES `marlo_ciat_db`.`research_center` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_leader_res_area` (`research_area_id` ASC) 
, ADD INDEX `fk_leader_res_program` (`research_program_id` ASC) 
, ADD INDEX `fk_leader_res_center` (`research_center_id` ASC) ;