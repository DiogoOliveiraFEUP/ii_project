CREATE TABLE `unloading` (
	`Roller` INT(11) NOT NULL,
	`PieceType` INT(11) NOT NULL,
	`Quant` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`Roller`, `PieceType`) USING BTREE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;

INSERT INTO unloading (Roller,PieceType,Quant) VALUES (1,1,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (1,2,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (1,3,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (1,4,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (1,5,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (1,6,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (1,7,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (1,8,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (1,9,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (2,1,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (2,2,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (2,3,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (2,4,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (2,5,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (2,6,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (2,7,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (2,8,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (2,9,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (3,1,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (3,2,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (3,3,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (3,4,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (3,5,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (3,6,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (3,7,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (3,8,0);
INSERT INTO unloading (Roller,PieceType,Quant) VALUES (3,9,0);