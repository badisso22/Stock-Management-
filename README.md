# Stock Management – Java Console Application

## Overview

This project is a simple stock management system implemented as a Java console application.  
It allows a user to manage products and track stock movements (entries and exits) stored in a PostgreSQL database.

Main features:

- Manage products (add, list, update, delete).
- Record stock entries and exits.
- Automatically update product quantities after each movement.
- Display the movement history of a given product.

This version is a standalone Java console app using JDBC.  
A future version may expose the same features via a Spring Boot + Thymeleaf web interface.

---

## Technologies

- Java (JDK 17 or later recommended)
- PostgreSQL
- JDBC PostgreSQL driver

---

## Database design (current version)

### Tables

- **product**
  - `id` (serial, primary key)
  - `code` (varchar, unique, not null)
  - `name` (varchar, not null)
  - `category` (varchar, nullable)
  - `unit_price` (numeric, not null)
  - `quantity` (int, not null, default 0)

- **movement**
  - `id` (serial, primary key)
  - `movement_date` (timestamp, not null, default now)
  - `type` (enum `movement_type` with values `ENTRY`, `EXIT`)
  - `quantity` (int, not null)
  - `product_id` (int, not null, foreign key → `product.id`)

---

## Future improvements
### Planned evolutions:

- Replace the console interface with a Spring Boot REST API.

- Add a Thymeleaf web UI for managing products and movements in the browser.

- Add user accounts and roles (admin vs stock clerk).

- Implement reports such as low‑stock alerts and stock valuation.
