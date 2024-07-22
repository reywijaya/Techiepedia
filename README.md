
# Techiepedia

Welcome to the Techiepedia e-Commerce Platform! This project is designed to facilitate the sales, maintenance, and trade of gadgets. It provides a comprehensive solution for managing product listings, customer orders, and transactions.


## Authors

- [@reywijaya](https://github.com/reywijaya)

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
  - [API Endpoints](#api-endpoints)
  - [Example Requests](#example-requests)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)


## Installation

- **Clone the Repository**

```sh
  git clone https://github.com/reywijaya/Techiepedia.git
```

- **Configure the Database**
Create a PostgreSQL database and update the application.properties file with your database credentials.

```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
```

- **Build the Package**

```sh
  mvn clean install
```

- **Run the application**

```bash
  mvn spring-boot:run
```
## Usage/Examples
### API Endpoints

#### Auth
```
POST /api/auth/signup: Register a new user (Customer)
POST /api/auth/signup/admin: Register a new admin
POST /api/auth/signin: Login
```
#### Products
```
GET /api/products: View all products
POST /api/products: Add a new product (Admin/Seller only)
PUT /api/products/{id}: Update a product (Admin/Seller only)
DELETE /api/products/{id}: Delete a product (Admin/Seller only)
```
#### Orders
```
POST /api/orders: Place a new order (Customer only)
GET /api/orders: View all orders (Admin/Seller)
GET /api/orders/{id}: View order details (Admin/Seller/Customer)
```
#### Transactions
```
POST /api/transactions: Request a loan (Customer only)
GET /api/transactions: View all transactions (Admin only)
```

### Example Requests
#### Register new Customer
```sh
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
        "username": "customer1",
        "password": "password123",
        "customerRequest": {
          "firstName": "John",
          "lastName": "Doe",
          "email": "johndoe@example.com",
          "phoneNumber": "1234567890",
          "address": "123 Main St"
        }
      }'
```

#### Login
```sh
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
        "username": "customer1",
        "password": "password123"
      }'
```

#### Add new product (Admin/Seller)
```sh
curl -X POST http://localhost:8080/api/products \
  -H "Authorization: Bearer your_jwt_token" \
  -H "Content-Type: application/json" \
  -d '{
        "name": "Smartphone",
        "description": "Latest model smartphone",
        "price": 999.99,
        "stock": 50
      }'
```
## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## Contact

For any inquiries or issues, please DM me.
```vbnet

This README provides a clear and concise overview of the project, along with instructions for setup and usage.
You can customize it further based on your project's specific requirements and structure.

```