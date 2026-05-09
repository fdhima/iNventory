# iNventory

A modern, secure Inventory Management System built with Spring Boot, PostgreSQL, and integrated Telegram notifications.

## 🚀 Features

- 📦 **Product Management**: Full CRUD operations for managing your product catalog.
- 📋 **Order Tracking**: Efficiently manage and track orders across multiple locations.
- 📉 **Stock Level Monitoring**: Real-time tracking of stock levels with threshold management.
- 🔔 **Telegram Notifications**: Automated alerts sent directly to your Telegram bot when stock levels drop below defined thresholds.
- 🔐 **Secure Authentication**: Robust security using Spring Security and JWT (JSON Web Tokens).
- 📖 **Interactive API Documentation**: Explore and test the API endpoints easily with integrated Swagger UI.
- 🛠️ **Database Migrations**: Reliable database schema management using Flyway.

## 🛠️ Tech Stack

- **Backend**: Java 21, Spring Boot 4.0.5
- **Database**: PostgreSQL 16
- **Security**: Spring Security, JJWT
- **API Documentation**: Springdoc OpenAPI (Swagger UI)
- **Migrations**: Flyway
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **Notifications**: Telegram Bot API

## 🏁 Getting Started

### Prerequisites

- **Java 21** or higher
- **Docker** and **Docker Compose**
- **Maven** (optional, wrapper provided)

### ⚙️ Configuration

The application requires several environment variables to function correctly. You can set these in your shell or create a `.env` file (if using a plugin/tool that supports it).

| Variable | Description |
|----------|-------------|
| `JWT_SECRET` | Secret key for signing JWT tokens |
| `JWT_EXPIRATION` | Token expiration time in milliseconds |
| `BOT_TOKEN` | Your Telegram Bot API token |
| `CHAT_ID` | Your Telegram Chat ID for receiving notifications |

### 🏃 Running the Application

1.  **Clone the repository**:
    ```bash
    git clone <repository-url>
    cd iNventory
    ```

2.  **Start the Database**:
    Use Docker Compose to spin up the PostgreSQL instance:
    ```bash
    docker-compose up -d
    ```

3.  **Set Environment Variables**:
    Ensure the required environment variables are set in your environment.

4.  **Run the Application**:
    ```bash
    ./mvnw spring-boot:run
    ```

The application will be available at `http://localhost:8080`.

## 📖 API Documentation

Once the application is running, you can access the interactive Swagger UI at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🗄️ Database Migrations

This project uses **Flyway** for database versioning. Migrations are located in `src/main/resources/db/migration` and are automatically applied when the application starts.

## 🧪 Testing

To run the unit and integration tests:
```bash
./mvnw test
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is open-source and available under the MIT License.
