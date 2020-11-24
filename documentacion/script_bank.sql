-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema BANK
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema BANK
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BANK` DEFAULT CHARACTER SET utf8mb4 ;
USE `BANK` ;

-- -----------------------------------------------------
-- Table `BANK`.`WORKDAYS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BANK`.`WORKDAYS` (
  `workday_id` INT NOT NULL AUTO_INCREMENT,
  `workday` VARCHAR(25) NOT NULL,
  `start_time` TIME NOT NULL,
  `end_time` TIME NOT NULL,
  PRIMARY KEY (`workday_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BANK`.`MANAGERS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BANK`.`MANAGERS` (
  `manager_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `workday_id` INT NOT NULL,
  `dpi` VARCHAR(15) NOT NULL,
  `address` VARCHAR(150) NOT NULL,
  `gender` TINYINT NOT NULL COMMENT '1 = man, 0 = woman',
  `password` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`manager_id`),
  INDEX `fk_MANAGER_WORKDAYS_idx` (`workday_id` ASC),
  CONSTRAINT `fk_MANAGER_WORKDAYS`
    FOREIGN KEY (`workday_id`)
    REFERENCES `BANK`.`WORKDAYS` (`workday_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BANK`.`CASHIERS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BANK`.`CASHIERS` (
  `cashier_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `workday_id` INT NOT NULL,
  `dpi` VARCHAR(15) NOT NULL,
  `address` VARCHAR(150) NULL,
  `gender` TINYINT NULL COMMENT '1 = man, 0 = woman\n',
  `password` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`cashier_id`),
  INDEX `fk_CASHIERS_WORKDAYS1_idx` (`workday_id` ASC),
  CONSTRAINT `fk_CASHIERS_WORKDAYS1`
    FOREIGN KEY (`workday_id`)
    REFERENCES `BANK`.`WORKDAYS` (`workday_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BANK`.`CLIENTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BANK`.`CLIENTS` (
  `client_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `dpi` VARCHAR(15) NOT NULL,
  `birth` DATE NOT NULL,
  `address` VARCHAR(150) NOT NULL,
  `gender` TINYINT NOT NULL COMMENT '1 = man, 0 = woman',
  `password` VARCHAR(75) NOT NULL,
  `pdf_dpi` MEDIUMBLOB NULL,
  PRIMARY KEY (`client_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BANK`.`ACCOUNTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BANK`.`ACCOUNTS` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `client_id` INT NOT NULL,
  `created_on` DATE NOT NULL,
  `credit` DECIMAL(11,2) NOT NULL,
  PRIMARY KEY (`account_id`),
  INDEX `fk_ACCOUNTS_CLIENTS1_idx` (`client_id` ASC),
  CONSTRAINT `fk_ACCOUNTS_CLIENTS1`
    FOREIGN KEY (`client_id`)
    REFERENCES `BANK`.`CLIENTS` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BANK`.`TRANSACTIONS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BANK`.`TRANSACTIONS` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT NOT NULL,
  `created_on` DATE NOT NULL,
  `created_at` TIME NOT NULL,
  `type` ENUM('CREDITO', 'DEBITO') NOT NULL,
  `amount` DECIMAL(8,2) NOT NULL,
  `cashier_id` INT NOT NULL,
  `balance` DECIMAL(11,2) NULL,
  PRIMARY KEY (`transaction_id`),
  INDEX `fk_TRANSACTIONS_ACCOUNTS1_idx` (`account_id` ASC),
  INDEX `fk_TRANSACTIONS_CASHIERS1_idx` (`cashier_id` ASC),
  CONSTRAINT `fk_TRANSACTIONS_ACCOUNTS1`
    FOREIGN KEY (`account_id`)
    REFERENCES `BANK`.`ACCOUNTS` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TRANSACTIONS_CASHIERS1`
    FOREIGN KEY (`cashier_id`)
    REFERENCES `BANK`.`CASHIERS` (`cashier_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BANK`.`ASSOCIATED_ACCOUNTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BANK`.`ASSOCIATED_ACCOUNTS` (
  `associated_id` INT NOT NULL AUTO_INCREMENT,
  `client_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  `status` ENUM('ACEPTADA', 'RECHAZADA', 'EN ESPERA') NOT NULL DEFAULT 'EN ESPERA',
  `try_number` INT NULL DEFAULT 1,
  INDEX `fk_ACCOUNTS_has_ACCOUNTS_ACCOUNTS1_idx` (`account_id` ASC),
  PRIMARY KEY (`associated_id`),
  INDEX `fk_ASSOCIATED_ACCOUNTS_CLIENTS1_idx` (`client_id` ASC),
  CONSTRAINT `fk_ACCOUNTS_has_ACCOUNTS_ACCOUNTS1`
    FOREIGN KEY (`account_id`)
    REFERENCES `BANK`.`ACCOUNTS` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ASSOCIATED_ACCOUNTS_CLIENTS1`
    FOREIGN KEY (`client_id`)
    REFERENCES `BANK`.`CLIENTS` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BANK`.`CHANGE_HISTORY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BANK`.`CHANGE_HISTORY` (
  `change_id` INT NOT NULL AUTO_INCREMENT,
  `manager_manager_id` INT NOT NULL,
  `client_id` INT NULL DEFAULT NULL,
  `cashier_id` INT NULL DEFAULT NULL,
  `manager_id` INT NULL DEFAULT NULL,
  `description` TEXT NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`change_id`),
  INDEX `fk_CHANGE_HISTORY_MANAGER1_idx` (`manager_manager_id` ASC),
  INDEX `fk_CHANGE_HISTORY_CLIENTS1_idx` (`client_id` ASC),
  INDEX `fk_CHANGE_HISTORY_CASHIERS1_idx` (`cashier_id` ASC),
  INDEX `fk_CHANGE_HISTORY_MANAGER2_idx` (`manager_id` ASC),
  CONSTRAINT `fk_CHANGE_HISTORY_MANAGER1`
    FOREIGN KEY (`manager_manager_id`)
    REFERENCES `BANK`.`MANAGERS` (`manager_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CHANGE_HISTORY_CLIENTS1`
    FOREIGN KEY (`client_id`)
    REFERENCES `BANK`.`CLIENTS` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CHANGE_HISTORY_CASHIERS1`
    FOREIGN KEY (`cashier_id`)
    REFERENCES `BANK`.`CASHIERS` (`cashier_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CHANGE_HISTORY_MANAGER2`
    FOREIGN KEY (`manager_id`)
    REFERENCES `BANK`.`MANAGERS` (`manager_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
