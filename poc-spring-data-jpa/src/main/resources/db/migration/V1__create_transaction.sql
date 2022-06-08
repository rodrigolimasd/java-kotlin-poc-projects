-- -----------------------------------------------------
-- Table `transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transaction_tb` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `created` DATETIME NOT NULL DEFAULT now(),
    `edit` DATETIME NULL DEFAULT NULL,
    `date` DATE NOT NULL,
    `income` DECIMAL(10,2) NOT NULL DEFAULT 0,
    `expense` DECIMAL(10,2) NOT NULL DEFAULT 0,
    `balance_value` DECIMAL(10,2) NOT NULL DEFAULT 0,
    `note` VARCHAR(50) NULL,
    PRIMARY KEY (`id`));