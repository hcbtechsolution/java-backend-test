CREATE TABLE parking_lots.establishments (
	id uuid PRIMARY KEY,
	cnpj varchar(18) NOT NULL UNIQUE,
	"name" varchar(100) NOT NULL,
	address_id uuid NOT NULL REFERENCES parking_lots.addresses(id),
	phone_id uuid NOT NULL REFERENCES parking_lots.phones(id),
	number_space_motorcycle int4 NOT NULL,
	number_space_car int4 NOT NULL
);