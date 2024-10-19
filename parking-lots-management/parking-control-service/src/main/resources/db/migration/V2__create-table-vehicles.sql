CREATE TABLE vehicles (
	`id` varchar(30) PRIMARY KEY,
	license_plate varchar(8) NOT NULL UNIQUE,
	`type` enum('CAR','MOTORCYCLE') NOT NULL
);