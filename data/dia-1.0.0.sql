SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `dia` ;
CREATE SCHEMA IF NOT EXISTS `dia` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `dia` ;

-- -----------------------------------------------------
-- Table `dia`.`garden`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dia`.`garden` ;

CREATE TABLE IF NOT EXISTS `dia`.`garden` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `garden_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dia`.`device`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dia`.`device` ;

CREATE TABLE IF NOT EXISTS `dia`.`device` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `device_name` VARCHAR(45) NOT NULL,
  `pin` VARCHAR(45) NOT NULL,
  `device_mask` VARCHAR(45) NOT NULL,
  `garden_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `device_name_UNIQUE` (`device_name` ASC),
  INDEX `fk_device_garden1_idx` (`garden_id` ASC),
  CONSTRAINT `fk_device_garden1`
    FOREIGN KEY (`garden_id`)
    REFERENCES `dia`.`garden` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dia`.`device_access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dia`.`device_access` ;

CREATE TABLE IF NOT EXISTS `dia`.`device_access` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `device_id` INT NOT NULL,
  `user_name` VARCHAR(45) NULL,
  `user_mask` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_mask_UNIQUE` (`user_mask` ASC),
  CONSTRAINT `fk_device_access_device`
    FOREIGN KEY (`device_id`)
    REFERENCES `dia`.`device` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
