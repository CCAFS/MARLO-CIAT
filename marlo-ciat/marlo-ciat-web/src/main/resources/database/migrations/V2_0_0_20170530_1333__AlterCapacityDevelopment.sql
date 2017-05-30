ALTER TABLE `capacity_development` 
ADD CONSTRAINT `capdev_researhArea_fk`
  FOREIGN KEY (`research_area`)
  REFERENCES `research_areas` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `capdev_researchProgram_fk`
  FOREIGN KEY (`research_program`)
  REFERENCES `research_programs` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `capdev_researchCenter`
  FOREIGN KEY (`crp`)
  REFERENCES `research_centers` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `capdev_project_fk`
  FOREIGN KEY (`project`)
  REFERENCES `projects` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;