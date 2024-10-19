CREATE TABLE establishments (
	`uuid` varchar(36) PRIMARY KEY,
	cnpj varchar(18) NOT NULL UNIQUE,
	available_slots_car int NOT NULL,
	available_slots_motorcycle int NOT NULL,
	total_slots_car int NOT NULL,
	total_slots_motorcycle int NOT NULL
);