CREATE TABLE `stocks` (
	`BlockType` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`Quantity` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`BlockType`) USING BTREE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

INSERT INTO stocks (BlockType,Quantity) VALUES (1,0);
INSERT INTO stocks (BlockType,Quantity) VALUES (2,0);
INSERT INTO stocks (BlockType,Quantity) VALUES (3,0);
INSERT INTO stocks (BlockType,Quantity) VALUES (4,0);
INSERT INTO stocks (BlockType,Quantity) VALUES (5,0);
INSERT INTO stocks (BlockType,Quantity) VALUES (6,0);
INSERT INTO stocks (BlockType,Quantity) VALUES (7,0);
INSERT INTO stocks (BlockType,Quantity) VALUES (8,0);
INSERT INTO stocks (BlockType,Quantity) VALUES (9,0);