[English Version](#java-back-end-developer-test) | [Versão em Português](#teste-para-vaga-de-desenvolvedor-back-end-java)

<br>

# Java Back-End Developer Test

Test applied for the Java Back-End Developer selection process.

## Technologies Used in Project Development

- Java 17
- Maven
- Spring Boot
- Spring Cloud
- Spring HATEOAS
- PostgreSQL
- MySQL
- MongoDB
- RabbitMQ
- Flyway
- JUnit 5
- Mockito
- Git
- GitHub Actions
- Docker
- VSCode

Only the Establishment microservice was implemented using TDD (unit and integration tests were created).

### To Do:

- Configure Swagger;
- Implement the Authentication microservice;
- Create a reporting API:
  - Summary of the number of entries and exits;
  - Summary of the number of vehicle entries and exits per hour;
- Implement the Front-end.

## Running the Project

### Required Installed Tools:

- Git ([https://git-scm.com/downloads](https://git-scm.com/downloads))
- Docker ([https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop))

### Step-by-Step to Run the Project

1. Open the terminal and run the following command:

```
git clone https://github.com/hcbtechsolution/java-backend-test.git
```

2. Enter the java-backend-test\parking-lots-management directory:

```
cd java-backend-test\parking-lots-management
```

3. Open the file explorer with the following command:

```
explorer .
```

4. Create a `.env` file with the environment variables below, set their values, and save:

```
EUREKA_USER=
EUREKA_PASSWORD=
MONGODB_USER=
MONGODB_PASSWORD=
MYSQL_ROOT_PASSWORD=
MYSQL_USER=
MYSQL_PASSWORD=
POSTGRES_USER=
POSTGRES_PASSWORD=
RABBITMQ_USER=
RABBITMQ_PASS=
```

5. Go back to the terminal and run the command below to start the application. Wait for Docker to set up the entire structure and microservices. (The first time may take a few minutes to download the Docker images.)

```
docker compose up -d
```

### Available Endpoints

#### Establishment

```
POST /establishments

{
  "cnpj": "",
  "name": "",
  "address": {
    "name": "",
    "number": "",
    "complement": "",
    "district": "",
    "city": "",
    "state": "",
    "cep": ""
  },
  "phone": {
    "ddd": "",
    "number": ""
  },
  "numberSpaceMotorcycle": ,
  "numberSpaceCar":
}

PUT /establishments/{id}

{
  "cnpj": "",
  "name": "",
  "address": {
    "name": "",
    "number": "",
    "complement": "",
    "district": "",
    "city": "",
    "state": "",
    "cep": ""
  },
  "phone": {
    "ddd": "",
    "number": ""
  },
  "numberSpaceMotorcycle": ,
  "numberSpaceCar":
}

GET /establishments

GET /establishments/{id}

DELETE /establishments/{id}
```

#### Vehicle

```
POST /vehicles

{
  "brand": "",
  "model": "",
  "color": "",
  "licensePlate": "",
  "type": ""
}

PUT /vehicles/{id}

{
  "brand": "",
  "model": "",
  "color": "",
  "licensePlate": "",
  "type": ""
}

GET /vehicles

GET /vehicles/{id}

DELETE /vehicles/{id}
```

#### Parking Control

```
POST /parking-control/checkin

{
  "establishmentId": "",
  "licensePlate": ""
}

PUT /parking-control/checkout/{id}

{
  "establishmentId": "",
  "licensePlate": ""
}
```

## Specification

Create a REST API to manage a parking lot for cars and motorcycles. The response must support both JSON and XML formats.

### Features

- **Establishment:** CRUD;
- **Vehicles:** CRUD;
- **Vehicle entry and exit control.**

### Requirements

#### 1. Establishment Registration

Create a company registration with the following fields:

- Name;
- CNPJ;
- Address;
- Phone;
- Number of motorcycle spaces;
- Number of car spaces.

**All** fields are required.

#### 2. Vehicle Registration

Create a vehicle registration with the following fields:

- Brand;
- Model;
- Color;
- License Plate;
- Type.

**All** fields are required.

#### 3. Data Modeling

##### 3.1 Establishment Microservice

![Establishment Microservice Data Modeling](images/establishment_service.png)

##### 3.2 Vehicle Microservice

![Vehicle Microservice Data Modeling](images/vehicle_service.png)

##### 3.3 Parking Control Microservice

![Parking Control Microservice Data Modeling - Establishments](images/parking_control_service_establishments.png)

![Parking Control Microservice Data Modeling - Vehicles](images/parking_control_service_vehicles.png)

![Parking Control Microservice Data Modeling - Parking Logs](images/parking_control_service_parking_logs.png)

<br><br>

#

<br><br>

# Teste para vaga de Desenvolvedor Back-end Java

Teste realizado para o processo seletivo de Desenvolvedor Back-end Java

## Tecnologias utilizadas no Desenvolvimento do Projeto

- Java 17
- Maven
- Spring Boot
- Spring Cloud
- Spring Hateoas
- PostgreSQL
- MySql
- MongoDB
- RabbitMQ
- FlyWay
- JUnit 5
- Mockito
- Git
- Github Actions
- Docker
- VSCode

Somente o microsserviço de Estabelecimento foi implementado com TDD (Foi implementado testes unitários e de integração).

### A fazer:

- Configurar o Swagger;
- Implementar o microsserviço de Autenticação;
- Criar API de relatório;
  - Sumário da quantidade de entrada e saída;
  - Sumário da quantidade de entrada e saída de veículos por hora;
- Implementar o Front-end;

## Executar o Projeto

### Ferramentas Instaladas Necessárias:

- Git ([https://git-scm.com/downloads](https://git-scm.com/downloads))
- Docker ([https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop))

### Passo a Passo para Execuatar o Projeto

1. Abra o terminal e execute o seguinte comando

```
git clone https://github.com/hcbtechsolution/java-backend-test.git
```

2. Entre no diretório java-backend-test\parking-lots-management

```
cd java-backend-test\parking-lots-management
```

3. Abra o explorer com o seguinte comando

```
explorer .
```

4. Crie um arquivo .env com as seguintes variaveis de ambientes abaixo e defina os valores para cada um e salve.

```
EUREKA_USER=
EUREKA_PASSWORD=
MONGODB_USER=
MONGODB_PASSWORD=
MYSQL_ROOT_PASSWORD=
MYSQL_USER=
MYSQL_PASSWORD=
POSTGRES_USER=
POSTGRES_PASSWORD=
RABBITMQ_USER=
RABBITMQ_PASS=
```

5. De volta ao terminal, execute comando abaixo para subir a aplicação e aguarde o docker subir toda estrutura e os microsserviços (Na primeira vez, pode demorar alguns minutos para baixar as imagens docker).

```
docker compose up -d
```

### Endpoints disponiveis

#### Estabelecimento

```
POST /establishments

{
  "cnpj": "",
  "name": "",
  "address": {
    "name": "",
    "number": "",
    "complement": "",
    "district": "",
    "city": "",
    "state": "",
    "cep": ""
  },
  "phone": {
    "ddd": "",
    "number": ""
  },
  "numberSpaceMotorcycle": ,
  "numberSpaceCar":
}

PUT /establishments/{id}

{
  "cnpj": "",
  "name": "",
  "address": {
    "name": "",
    "number": "",
    "complement": "",
    "district": "",
    "city": "",
    "state": "",
    "cep": ""
  },
  "phone": {
    "ddd": "",
    "number": ""
  },
  "numberSpaceMotorcycle": ,
  "numberSpaceCar":
}

GET /establishments

GET /establishments/{id}

DELETE /establishments/{id}
```

#### Veículo

```
POST /vehicles

{
  "brand": "",
  "model": "",
  "color": "",
  "licensePlate": "",
  "type": ""
}

PUT /vehicles/{id}

{
  "brand": "",
  "model": "",
  "color": "",
  "licensePlate": "",
  "type": ""
}

GET /vehicles

GET /vehicles/{id}

DELETE /vehicles/{id}
```

#### Estacionamento

```
POST /parking-control/checkin

{
  "establishmentId": "",
  "licensePlate": ""
}

PUT /parking-control/checkout/{id}

{
  "establishmentId": "",
  "licensePlate": ""
}
```

## Especificação

Criar uma API REST para gerenciar um estacionamento de carros e motos. O retorno deverá ser em formato JSON e XML;

### Funcionalidades

- **Estabelecimento:** CRUD;
- **Veículos:** CRUD;
- **Controle de entrada e saída de veículos.**

### Requisitos:

#### 1. Cadastro de estabelecimento

Criar um cadastro da empresa com os seguintes campos:

- Nome;
- CNPJ;
- Endereço;
- Telefone;
- Quantidade de vagas para motos;
- Quantidade de vagas para carros.

**Todos** os campos são de preenchimento obrigatório.

#### 2. Cadastro de veículos

Criar um cadastro de veículos com os seguintes campos:

- Marca;
- Modelo;
- Cor;
- Placa;
- Tipo.

**Todos** os campos são de preenchimento obrigatório.

#### 3. Modelagem de dados

##### 3.1 Microsserviço de Estabelecimento

![Modelagem de Dados do Microsserviço de Estabelecimento](images/establishment_service.png)

##### 3.2 Microsserviço de Veículo

![Modelagem de Dados do Microsserviço de Estabelecimento](images/vehicle_service.png)

##### 3.3 Microsserviço de Controle do Estacionamento

![Modelagem de Dados do Microsserviço de Controle de Escionamento - Estabelecimento](images/parking_control_service_establishments.png)

![Modelagem de Dados do Microsserviço de Controle de Escionamento - Estabelecimento](images/parking_control_service_vehicles.png)

![Modelagem de Dados do Microsserviço de Controle de Escionamento - Estabelecimento](images/parking_control_service_parking_logs.png)
