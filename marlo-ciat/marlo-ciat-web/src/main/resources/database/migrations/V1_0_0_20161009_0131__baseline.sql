
-- -----------------------------------------------------
-- Table `research_center`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `research_center` ;

CREATE  TABLE IF NOT EXISTS `research_center` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'The primary key of the research center table.' ,
  `name` VARCHAR(100) NOT NULL COMMENT 'The name of the center' ,
  `acronym` VARCHAR(10) NOT NULL COMMENT 'The Acronym for the center' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `research_area`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `research_area` ;

CREATE  TABLE IF NOT EXISTS `research_area` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL COMMENT 'The Name of the research area such as DAPA' ,
  `acronym` VARCHAR(50) NOT NULL COMMENT 'The short form or acronym of the research area e.g DAPA' ,
  `research_center_id` INT NOT NULL COMMENT 'The foreign key of the research center.' ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_research_area_center` (`research_center_id` ASC) ,
  CONSTRAINT `fk_research_area_center`
    FOREIGN KEY (`research_center_id` )
    REFERENCES `research_center` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `research_program`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `research_program` ;

CREATE  TABLE IF NOT EXISTS `research_program` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  `acronym` VARCHAR(50) NOT NULL ,
  `program_type` INT(11) NOT NULL ,
  `color` VARCHAR(8) NULL DEFAULT NULL ,
  `is_active` TINYINT(1) NOT NULL ,
  `research_area_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_rprogram_rarea` (`research_area_id` ASC) ,
  CONSTRAINT `fk_rprogram_rarea`
    FOREIGN KEY (`research_area_id` )
    REFERENCES `research_area` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 112
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `research_impact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `research_impact` ;

CREATE  TABLE IF NOT EXISTS `research_impact` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `impact` TEXT NOT NULL ,
  `target_year` VARCHAR(4) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `research_topic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `research_topic` ;

CREATE  TABLE IF NOT EXISTS `research_topic` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `research_topic` TEXT NOT NULL ,
  `research_program_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_rtopic_rprogram` (`research_program_id` ASC) ,
  CONSTRAINT `fk_rtopic_rprogram`
    FOREIGN KEY (`research_program_id` )
    REFERENCES `research_program` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `impact_outcome`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `impact_outcome` ;

CREATE  TABLE IF NOT EXISTS `impact_outcome` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `description` TEXT NULL DEFAULT NULL ,
  `year` INT(11) NULL DEFAULT NULL ,
  `value` DECIMAL(10,2) NULL DEFAULT NULL ,
  `target_unit_id` BIGINT(20) NULL DEFAULT NULL ,
  `research_impact_id` INT NOT NULL ,
  `is_active` TINYINT(1) NOT NULL ,
  `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `research_topic_id` INT NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_outcomes_rimpact` (`research_impact_id` ASC) ,
  INDEX `fk_outcomes_rtopic` (`research_topic_id` ASC) ,
  CONSTRAINT `fk_outcomes_rimpact`
    FOREIGN KEY (`research_impact_id` )
    REFERENCES `research_impact` (`id` ),
  CONSTRAINT `fk_outcomes_rtopic`
    FOREIGN KEY (`research_topic_id` )
    REFERENCES `research_topic` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 48
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `research_milestones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `research_milestones` ;

CREATE  TABLE IF NOT EXISTS `research_milestones` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `title` TEXT NULL DEFAULT NULL ,
  `year` INT(11) NULL DEFAULT NULL ,
  `value` DECIMAL(10,2) NULL DEFAULT NULL ,
  `target_unit_id` BIGINT(20) NULL DEFAULT NULL ,
  `impact_outcome_id` INT NOT NULL ,
  `is_active` TINYINT(1) NOT NULL ,
  `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`) ,
  INDEX `crp_program_outcome_id` USING BTREE (`impact_outcome_id` ASC) ,
  CONSTRAINT `fk_milestones_outcome`
    FOREIGN KEY (`impact_outcome_id` )
    REFERENCES `impact_outcome` (`id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 99
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `all_types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `all_types` ;

CREATE  TABLE IF NOT EXISTS `all_types` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `class_name` VARCHAR(45) NOT NULL COMMENT 'The name of the class where this type belongs. e.g ResearchArea, ResearchProgram.' ,
  `type_name` VARCHAR(100) NOT NULL COMMENT 'The type of the object or record. e.g research area leader, program coordinator, external partiner, internal partiner, research objective, strategic objective.' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
COMMENT = 'The table that tracks all types of entity data in the system' ;


-- -----------------------------------------------------
-- Table `research_objectives`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `research_objectives` ;

CREATE  TABLE IF NOT EXISTS `research_objectives` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `objective` TEXT NOT NULL ,
  `research_area_id` INT NULL DEFAULT NULL ,
  `type_id` INT NOT NULL COMMENT 'The type of objective such as strategic, research area, impact, research program.' ,
  `research_center_id` INT NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_objective_area` (`research_area_id` ASC) ,
  INDEX `fk_objective_type` (`type_id` ASC) ,
  INDEX `fk_objective_center` (`research_center_id` ASC) ,
  CONSTRAINT `fk_objective_area`
    FOREIGN KEY (`research_area_id` )
    REFERENCES `research_area` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_objective_type`
    FOREIGN KEY (`type_id` )
    REFERENCES `all_types` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_objective_center`
    FOREIGN KEY (`research_center_id` )
    REFERENCES `research_center` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `impact_output`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `impact_output` ;

CREATE  TABLE IF NOT EXISTS `impact_output` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `output` TEXT NOT NULL ,
  `date_added` DATE NOT NULL ,
  `impact_outcome_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_output_outcome` (`impact_outcome_id` ASC) ,
  CONSTRAINT `fk_output_outcome`
    FOREIGN KEY (`impact_outcome_id` )
    REFERENCES `impact_outcome` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `research_leader`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `research_leader` ;

CREATE  TABLE IF NOT EXISTS `research_leader` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `user_id` BIGINT NOT NULL ,
  `research_area_id` INT NULL DEFAULT NULL ,
  `research_program_id` INT NULL DEFAULT NULL ,
  `research_center_id` INT NULL DEFAULT NULL ,
  `type_id` INT NOT NULL COMMENT 'The foreign key for the type of leader (research area leader, research program leader, research center leader).' ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_leader_type` (`type_id` ASC) ,
  CONSTRAINT `fk_leader_type`
    FOREIGN KEY (`type_id` )
    REFERENCES `all_types` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB, 
COMMENT = 'The table that tracks the leaders for research area, researc' ;


-- -----------------------------------------------------
-- Table `impact_objective`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `impact_objective` ;

CREATE  TABLE IF NOT EXISTS `impact_objective` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `objective_id` INT NOT NULL ,
  `impact_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_impact_objective_impact` (`impact_id` ASC) ,
  INDEX `fk_impact_objective_objective` (`objective_id` ASC) ,
  CONSTRAINT `fk_impact_objective_impact`
    FOREIGN KEY (`impact_id` )
    REFERENCES `research_impact` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_impact_objective_objective`
    FOREIGN KEY (`objective_id` )
    REFERENCES `research_objectives` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB, 
COMMENT = 'The table that tracks the impact objectives.' ;


-- -----------------------------------------------------
-- Table `research_partner`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `research_partner` ;

CREATE  TABLE IF NOT EXISTS `research_partner` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL ,
  `type_id` INT NOT NULL COMMENT 'Type such as Internal or external' ,
  `organization` VARCHAR(100) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_partner_type` (`type_id` ASC) ,
  CONSTRAINT `fk_partner_type`
    FOREIGN KEY (`type_id` )
    REFERENCES `all_types` (`id` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;

