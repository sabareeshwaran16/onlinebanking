Deployed Link:https://onlinebanking-t58j.onrender.com


user:user
password:pass


Database(Sql):aiven
Frontend and Backend=Render
# 🏦 Online Banking System - Admin Portal

A premium Spring Boot application for managing banking operations. This project features a secure administrative interface with a modern, glassmorphic design.

## ✨ Key Features

- **Secure Admin Authentication**: Protected dashboard and utility pages.
- **User Management**: Create, update, and delete bank users.
- **Account Operations**: Manage accounts, perform deposits, withdrawals, and transfers.
- **Data Visualization**: View comprehensive banking data in a structured format.
- **Modern UI**: Dark-themed, responsive interface with premium aesthetics.

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Gradle
- MySQL Database (or compatible)

### Configuration

Ensure your database settings are correctly configured in `src/main/resources/application.properties`.

### Running the Application

```bash
./gradlew bootRun
```

Or build the JAR and run:

```bash
./gradlew build
java -jar build/libs/onlinebanking-0.0.1-SNAPSHOT.jar
```

## 🔐 Administrative Access

The administrative dashboard is locked behind a login portal. Use the following credentials to gain access:

| Field    | Value  |
|----------|--------|
| **Username** | `user` |
| **Password** | `pass` |

> [!IMPORTANT]
> These credentials are hardcoded for demonstration purposes in `AdminLoginController.java`.

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.x
- **Frontend**: Thymeleaf, Vanilla CSS (Glassmorphism)
- **Database**: MySQL / TiDB
- **Build Tool**: Gradle

## 📂 Project Structure

- `src/main/java/com/banking/controller`: Web and API controllers.
- `src/main/java/com/banking/model`: Data models and DTOs.
- `src/main/resources/templates`: Thymeleaf HTML templates.
- `Dockerfile`: Containerization setup for easy deployment.

---
Built with ❤️ by Antigravity
