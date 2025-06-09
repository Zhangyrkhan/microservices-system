Sure! Here's your **Microservices Project documentation translated into English**:

---

# 🌐 Microservices Project: User-Service & Company-Service

A microservices-based application that includes `User Service`, `Company Service`, `Config Server`, `Eureka`, `API Gateway`, and two separate PostgreSQL databases. All services are auto-configured via Spring Cloud Config and registered with Eureka.

---

## 🚀 Project Startup

Make sure you have Docker and Maven installed before starting.

### 1. Build the Project

```bash
mvn clean install
```

### 2. Run with Docker Compose

```bash
docker compose up --build
```

All services will be accessible through the gateway at `http://localhost:8080`.

---

## 🧭 Architecture

* **Config Server** (`localhost:8888`) — Stores configurations for services.
* **Eureka Server** (`localhost:8761`) — Service discovery server.
* **API Gateway** (`localhost:8080`) — Proxies requests to microservices.
* **User Service** (`/user-service/...`)
* **Company Service** (`/company-service/...`)
* **Postgres** Databases:

  * `users_db` on port `5433`
  * `companies_db` on port `5434`

---

## 📦 User Service

Base URL: `http://localhost:8080/user-service/users`

### ➕ POST `/users`

```json
{
  "firstName": "Zhangir",
  "lastName": "Faizulla",
  "phone": "3123123",
  "companyId": "1"
}
```

**Response:**

```json
{
  "id": 3,
  "firstName": "Zhangir",
  "lastName": "Faizulla",
  "phone": "3123123",
  "company": {
    "id": 1,
    "name": "Hanzada",
    "budget": 2342342.0
  }
}
```

---

### 📄 GET `/users/1`

**Response:**

```json
{
  "id": 1,
  "firstName": "PutRequest",
  "lastName": "asdasda",
  "phone": "3123123",
  "company": {
    "id": 1,
    "name": "Hanzada",
    "budget": 2342342.0
  }
}
```

---

### 📄 GET `/users`

**Response:**

```json
[
  {
    "id": 2,
    "firstName": "Zhangir",
    "lastName": "Faizulla",
    "phone": "3123123",
    "company": {
      "id": 1,
      "name": "Hanzada",
      "budget": 2342342.0
    }
  },
  ...
]
```

---

### 🔄 PUT `/users/1`

```json
{
  "firstName": "PutRequest",
  "lastName": "asdasda",
  "phone": "3123123",
  "companyId": "1"
}
```

**Response:**

```json
{
  "id": 1,
  "firstName": "PutRequest",
  "lastName": "asdasda",
  "phone": "3123123",
  "company": {
    "id": 1,
    "name": "Hanzada",
    "budget": 2342342.0
  }
}
```

---

### ❌ DELETE `/users/1`

Deletes a user by ID.

---

## 🏢 Company Service

Base URL: `http://localhost:8080/company-service/companies`

### ➕ POST `/companies`

```json
{
  "name": "Hanzada",
  "budget": 2342342
}
```

**Response:**

```json
{
  "id": 3,
  "name": "Hanzada",
  "budget": 2342342.0,
  "employees": []
}
```

---

### 📄 GET `/companies/1`

**Response:**

```json
{
  "id": 1,
  "name": "Hanzada",
  "budget": 2342342.0,
  "employees": [
    {
      "id": 2,
      "firstName": "Zhangir",
      "lastName": "Faizulla",
      "phone": "3123123"
    }
  ]
}
```

---

### 📄 GET `/companies`

**Response:**

```json
[
  {
    "id": 1,
    "name": "Hanzada",
    "budget": 2342342.0,
    "employees": [
      {
        "id": 2,
        "firstName": "Zhangir",
        "lastName": "Faizulla",
        "phone": "3123123"
      },
      ...
    ]
  },
  ...
]
```

---

### 🔄 PUT `/companies/1`

```json
{
  "name": "Hanzada",
  "budget": 2342342
}
```

Updates the company with ID = 1.

---

### ❌ DELETE `/companies/1`

Deletes a company by ID.

---

## 📎 Useful Links

* **Eureka Dashboard**: [http://localhost:8761](http://localhost:8761)
* **Gateway**: [http://localhost:8080](http://localhost:8080)
* **Config Server**: [http://localhost:8888](http://localhost:8888)
