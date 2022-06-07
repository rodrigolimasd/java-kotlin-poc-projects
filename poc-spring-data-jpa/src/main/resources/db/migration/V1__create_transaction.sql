-- -----------------------------------------------------
-- Table `transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transaction` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `create` DATETIME NOT NULL DEFAULT now(),
    `edit` DATETIME NULL DEFAULT NULL,
    `date` DATE NOT NULL,
    `income` DECIMAL(10,2) NULL,
    `expense` DECIMAL(10,2) NULL,
    `balance_value` DECIMAL(10,2) NULL,
    `note` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`));