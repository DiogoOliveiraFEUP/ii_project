CREATE TABLE `unldorders` (
	`MainID` INT(11) NOT NULL,
	`ID` INT(11) NOT NULL,
	`Status` INT(11) NOT NULL,
	`BlockType` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`Destination` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`Priority` BINARY(50) NOT NULL,
	PRIMARY KEY (`MainID`, `ID`) USING BTREE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
;
