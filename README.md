# eBank - Simple Banking App

This is a web banking app with:
- **Backend:** Spring Boot (Java)
- **Frontend:** React (TypeScript)
- **Database:** MySQL

## Features

- Agents can add clients and create accounts
- Clients can see their accounts, make transfers, and view history
- Secure login with JWT tokens

## How to Run

### 1. Prerequisites

- **Backend:** Java 17+, Maven, MySQL
- **Frontend:** Node.js 16+, npm

### 2. Setup

#### Clone the project
```bash
git clone <your-repo-url>
cd examen-eBank
```

#### Database
- Make sure MySQL is running.
- Create a database called `ebank_db`.
- Default MySQL user: `root`, password: `root` (change in `application.yml` if needed).

#### Backend
```bash
cd ebank-backend
mvn clean install
mvn spring-boot:run
```
- Runs at: [http://localhost:8080/api](http://localhost:8080/api)

#### Frontend
```bash
cd ebank-frontend
npm install
npm start
```
- Runs at: [http://localhost:3000](http://localhost:3000)

### 3. Test Accounts

| Role         | Login    | Password |
|--------------|----------|----------|
| Agent        | agent1   | password |
| Client       | client1  | password |
| Client       | client2  | password |

## Project Structure

```
examen-eBank/
  ebank-backend/   # Java backend
  ebank-frontend/  # React frontend
```

## Common Issues

- **Backend won't start:** Check MySQL is running and credentials are correct.
- **Frontend can't connect:** Make sure backend is running on port 8080.
git
---

**For more details, see the full documentation or contact the project maintainer.**