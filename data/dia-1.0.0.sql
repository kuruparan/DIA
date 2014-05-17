SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `dia` ;
CREATE SCHEMA IF NOT EXISTS `dia` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `dia` ;

-- -----------------------------------------------------
-- Table `dia`.`device`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dia`.`device` ;

CREATE TABLE IF NOT EXISTS `dia`.`device` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `device_id` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NOT NULL,
  `device_msisdn` VARCHAR(45) NULL,
  `device_mask` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `device_id_UNIQUE` (`device_id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dia`.`device_access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dia`.`device_access` ;

CREATE TABLE IF NOT EXISTS `dia`.`device_access` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `device_id` INT NOT NULL,
  `user_msisdn` VARCHAR(45) NOT NULL,
  `user_mask` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_device_access_device`
    FOREIGN KEY (`device_id`)
    REFERENCES `dia`.`device` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
GRANT USAGE ON *.* TO diauser;
 DROP USER diauser;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'diauser' IDENTIFIED BY 'dia@yit';


GRANT ALL ON `dia`.* TO 'diauser';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
