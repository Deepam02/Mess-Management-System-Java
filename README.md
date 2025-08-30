# 🚀 Hostel Mess Management System

A comprehensive Java Spring Boot application for managing hostel mess operations including menu display, feedback collection, and complaint tracking.

## 📋 Features

### Core Features (MVP)
- ✅ **Menu Management**: View daily and weekly mess menus
- ✅ **Feedback System**: Submit ratings and comments on meals (1-5 scale)
- ✅ **Complaint Tracking**: Submit complaints and track resolution status
- ✅ **Dashboard**: Overview of system statistics and current status

### Technical Features
- 🏗️ **OOP Design Principles**: Inheritance, Encapsulation, Polymorphism, Abstraction
- 🔄 **RESTful APIs**: Clean and well-documented REST endpoints
- 🗄️ **JPA/Hibernate**: Database operations with Spring Data JPA
- ✅ **Data Validation**: Comprehensive input validation
- 📝 **Logging**: Structured logging for monitoring and debugging
- 🧪 **H2 Database**: In-memory database for development and testing

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: H2 (Development), JPA/Hibernate
- **Build Tool**: Maven
- **Validation**: Jakarta Validation
- **Logging**: SLF4J + Logback
- **Documentation**: Lombok for reducing boilerplate

## 📁 Project Structure

```
src/main/java/com/hostel/mess/
├── model/                 # Domain entities
│   ├── BaseEntity.java   # Common entity fields
│   ├── Student.java      # Student entity
│   ├── Menu.java         # Menu entity
│   ├── MenuItem.java     # Menu item entity
│   ├── Feedback.java     # Feedback entity
│   ├── Complaint.java    # Complaint entity
│   └── enums/            # Enumerations
├── repository/           # Data access layer
├── service/             # Business logic layer
├── controller/          # REST API endpoints
├── dto/                # Data Transfer Objects
├── config/             # Configuration classes
└── MessManagementSystemApplication.java
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Running the Application

1. **Build the project**:
   ```bash
   mvn clean compile
   ```

2. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application**:
   - API Base URL: `http://localhost:8081`
   - H2 Database Console: `http://localhost:8081/h2-console`
   - Health Check: `http://localhost:8081/api/dashboard/health`

## 📚 API Endpoints

### Students
- `POST /api/students` - Register new student
- `GET /api/students/{id}` - Get student by ID
- `GET /api/students` - Get all active students
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Deactivate student

### Menus
- `POST /api/menus` - Create new menu
- `GET /api/menus/today` - Get today's menus
- `GET /api/menus/date/{date}` - Get menus for specific date
- `GET /api/menus/upcoming` - Get upcoming menus
- `GET /api/menus/week/{startDate}` - Get weekly menus

### Feedback
- `POST /api/feedback` - Submit feedback
- `GET /api/feedback/menu/{menuId}` - Get feedback for menu
- `GET /api/feedback/student/{studentId}` - Get student's feedback
- `GET /api/feedback/negative` - Get negative feedback
- `GET /api/feedback/positive` - Get positive feedback

### Complaints
- `POST /api/complaints` - Submit complaint
- `GET /api/complaints/{id}` - Get complaint details
- `GET /api/complaints/student/{studentId}` - Get student's complaints
- `GET /api/complaints/open` - Get open complaints
- `PUT /api/complaints/{id}/status` - Update complaint status

### Dashboard
- `GET /api/dashboard/overview` - System overview
- `GET /api/dashboard/health` - Health check

## 🗄️ Database Configuration

The application uses H2 in-memory database for development:

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:messdb
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
```

Access H2 Console: `http://localhost:8080/h2-console`

## 📊 Sample Data

The application automatically creates sample data on startup including:
- 3 sample students
- Today's and tomorrow's menus
- Sample feedback entries
- Sample complaints

## 🏗️ OOP Design Principles

### 1. Inheritance
- `BaseEntity` class provides common fields (id, createdAt, updatedAt)
- All entities extend BaseEntity

### 2. Encapsulation
- Private fields with public getters/setters
- Business logic encapsulated in service classes
- Data validation in DTOs

### 3. Polymorphism
- Interface-based programming with Spring Data repositories
- Method overriding in entity classes

### 4. Abstraction
- Repository pattern abstracts data access
- Service layer abstracts business logic
- Controller layer abstracts HTTP handling

## 🔧 Configuration

Key configuration files:
- `application.properties` - Application configuration
- `pom.xml` - Maven dependencies and build configuration

## 📝 Logging

The application uses structured logging:
- INFO level for general operations
- ERROR level for exceptions
- DEBUG level for detailed debugging (enabled for com.hostel.mess package)

## 🧪 Testing

Run tests with:
```bash
mvn test
```

## 🚀 Deployment

For production deployment:
1. Update database configuration in `application.properties`
2. Build the JAR file: `mvn clean package`
3. Run the JAR: `java -jar target/mess-management-system-1.0.0.jar`

## 📖 API Documentation

The application provides RESTful APIs with proper HTTP status codes:
- `200 OK` - Successful GET requests
- `201 Created` - Successful POST requests
- `400 Bad Request` - Validation errors
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server errors

## 🤝 Contributing

1. Follow Java coding standards
2. Use proper OOP principles
3. Add appropriate logging
4. Validate input data
5. Handle exceptions gracefully

## 📄 License

This project is developed as an educational example of Java Spring Boot with OOP principles.

---

**🎯 MVP Goals Achieved:**
- ✅ Students can view daily/weekly menus
- ✅ Students can submit feedback with ratings and comments
- ✅ Students can submit and track complaints
- ✅ Staff can update complaint resolution status
- ✅ System provides dashboard overview

**Built with ❤️ using Java Spring Boot and OOP best practices**
