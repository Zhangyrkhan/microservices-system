# 🌐 Microservices Project: User-Service & Company-Service

Микросервисное приложение, включающее `User Service`, `Company Service`, `Config Server`, `Eureka`, `API Gateway` и две отдельные базы данных PostgreSQL. Все сервисы автоматически конфигурируются через Spring Cloud Config и регистрируются в Eureka.

---

## 🚀 Запуск проекта

Перед запуском убедитесь, что установлен Docker и Maven.

### 1. Сборка проекта

```bash
mvn clean install
```

### 2. Запуск с Docker Compose

```bash
docker compose up --build
```

Все сервисы будут доступны по адресу `http://localhost:8080` через gateway.

---

## 🧭 Архитектура

* **Config Server** (`localhost:8888`) — хранит конфигурации сервисов.
* **Eureka Server** (`localhost:8761`) — сервис-дискавери.
* **API Gateway** (`localhost:8080`) — проксирует запросы к микросервисам.
* **User Service** (`/user-service/...`)
* **Company Service** (`/company-service/...`)
* **Postgres** базы данных:

  * `users_db` на порту `5433`
  * `companies_db` на порту `5434`

---

## 📦 User Service

Базовый URL: `http://localhost:8080/user-service/users`

### ➕ POST `/users`

```json
{
  "firstName": "Zhangir",
  "lastName": "Faizulla",
  "phone": "3123123",
  "companyId": "1"
}
```

**Ответ:**

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

**Ответ:**

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

**Ответ:**

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

**Ответ:**

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

Удаляет пользователя по ID.

---

## 🏢 Company Service

Базовый URL: `http://localhost:8080/company-service/companies`

### ➕ POST `/companies`

```json
{
  "name": "Hanzada",
  "budget": 2342342
}
```

**Ответ:**

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

**Ответ:**

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

**Ответ:**

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

{
  "name": "Hanzada",
  "budget": 2342342
}

Обновляет информацию о компании с ID = 1.

---

### ❌ DELETE `/companies/1`

Удаляет компанию по ID.

---

## 📎 Полезные ссылки

* **Eureka Dashboard**: [http://localhost:8761](http://localhost:8761)
* **Gateway**: [http://localhost:8080](http://localhost:8080)
* **Config Server**: [http://localhost:8888](http://localhost:8888)

