CREATE TABLE `machine_times` (
	`Machine` INT(11) NOT NULL,
	`Time` INT(11) NOT NULL,
	PRIMARY KEY (`Machine`) USING BTREE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;


INSERT INTO machine_times (Machine,Time) VALUES (1,0);
INSERT INTO machine_times (Machine,Time) VALUES (2,0);
INSERT INTO machine_times (Machine,Time) VALUES (3,0);
INSERT INTO machine_times (Machine,Time) VALUES (4,0);
INSERT INTO machine_times (Machine,Time) VALUES (5,0);
INSERT INTO machine_times (Machine,Time) VALUES (6,0);
INSERT INTO machine_times (Machine,Time) VALUES (7,0);
INSERT INTO machine_times (Machine,Time) VALUES (8,0);