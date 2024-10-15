CREATE TABLE parking_lots.addresses (
	id uuid PRIMARY KEY,
	"name" varchar(100) NOT NULL,
	"number" varchar(10) NOT NULL,
	complement varchar(60),
	district varchar(60) NOT NULL,
	city varchar(60) NOT NULL,
	"state" varchar(2) NOT NULL,
	cep varchar(9) NOT NULL
);