# Online Student Management System
## Spring Framework + Hibernate ORM Mini Project

### 📋 Project Overview
This is a comprehensive Student Management System built using Spring Framework and Hibernate ORM. The project demonstrates core enterprise Java concepts including Dependency Injection, CRUD operations, and Transaction Management.

---

## 🎯 Key Features

### 1. **Dependency Injection (DI)**
- Java-based Spring Configuration using `@Configuration` and `@Bean`
- Component scanning with `@ComponentScan`
- Automatic bean wiring with `@Autowired`
- Layered architecture with proper separation of concerns

### 2. **CRUD Operations**
- **Create**: Add new students, courses, and payment records
- **Read**: Retrieve student/course details, view all records, search functionality
- **Update**: Modify student information, course details
- **Delete**: Remove students and courses with proper validation

### 3. **Transaction Management**
- `@Transactional` annotation for declarative transaction management
- Atomic payment processing - ensures all-or-nothing execution
- Automatic rollback on exceptions
- Payment and refund operations with data consistency

### 4. **Spring + Hibernate Integration**
- SessionFactory configuration using Spring
- HibernateTransactionManager for transaction handling
- Entity relationship mapping (One-to-Many, Many-to-One)
- HQL queries for complex data retrieval

---

## 🛠️ Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | JDK 11+ | Programming Language |
| Spring Framework | 5.3.27 | Dependency Injection & Transaction Management |
| Hibernate ORM | 5.6.15 | Object-Relational Mapping |
| MySQL | 8.0+ | Database |
| HikariCP | 5.0.1 | Connection Pooling |
| Maven | 3.6+ | Build & Dependency Management |
| SLF4J + Logback | Latest | Logging |

---

## 📁 Project Structure

```
student-management-system/
│
├── src/main/java/com/studentmgmt/
│   ├── model/               # Entity Classes
│   │   ├── Student.java
│   │   ├── Course.java
│   │   └── Payment.java
│   │
│   ├── dao/                 # Data Access Layer
│   │   ├── StudentDAO.java
│   │   ├── CourseDAO.java
│   │   └── PaymentDAO.java
│   │
│   ├── service/             # Service Layer (Business Logic)
│   │   ├── StudentService.java
│   │   ├── CourseService.java
│   │   └── FeeService.java
│   │
│   ├── config/              # Spring Configuration
│   │   └── AppConfig.java
│   │
│   └── MainApp.java         # Main Application
│
├── src/main/resources/
│   └── logback.xml          # Logging Configuration
│
├── database/
│   └── schema.sql           # Database Schema
│
└── pom.xml                  # Maven Dependencies
```
- You can also add schema.sql file directly under the resources folder.
---

## 🚀 Setup Instructions

### 1. Prerequisites
- Java JDK 11 or higher
- MySQL Server 8.0+
- Maven 3.6+
- IDE (IntelliJ IDEA / Eclipse)

### 2. Create Database
Run the SQL script:
```bash
mysql -u root -p < src/main/resources/schema.sql
```

Or manually:
```sql
CREATE DATABASE student_management_db;
USE student_management_db;
-- Run the schema.sql content
```

### 3. Configure Database Connection
Edit `AppConfig.java` and update database credentials:
```java
dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/student_management_db");
dataSource.setUsername("root");
dataSource.setPassword("your_password"); // Change this
```

### 4. Build the Project
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn exec:java -Dexec.mainClass="com.studentmgmt.MainApp"
```

Or run `MainApp.java` from your IDE.

---

## 📊 Database Schema

### Tables

#### 1. **courses**
| Column | Type | Description |
|--------|------|-------------|
| course_id | BIGINT (PK) | Auto-generated ID |
| course_name | VARCHAR(100) | Course name (unique) |
| duration | INT | Duration in months |
| fee | DOUBLE | Course fee |

#### 2. **students**
| Column | Type | Description |
|--------|------|-------------|
| student_id | BIGINT (PK) | Auto-generated ID |
| name | VARCHAR(100) | Student name |
| email | VARCHAR(100) | Email (unique) |
| course_id | BIGINT (FK) | Reference to courses |
| balance | DOUBLE | Current fee balance |

#### 3. **payments**
| Column | Type | Description |
|--------|------|-------------|
| payment_id | BIGINT (PK) | Auto-generated ID |
| student_id | BIGINT (FK) | Reference to students |
| amount | DOUBLE | Payment/Refund amount |
| payment_date | DATETIME | Transaction date |
| payment_type | VARCHAR(20) | PAYMENT or REFUND |
| description | VARCHAR(255) | Transaction description |

---

## 💡 Core Concepts Demonstrated

1. **Dependency Injection** - Java-based Spring configuration
2. **CRUD Operations** - Complete Hibernate ORM implementation
3. **Transaction Management** - @Transactional for atomic operations
4. **Spring + Hibernate Integration** - SessionFactory and TransactionManager





---

## 🎮 Usage Examples

### Main Menu
```
========== MAIN MENU ==========
1. Student Management
2. Course Management
3. Fee Payment & Refund
4. View Reports
5. Exit
================================
```

### Example Operations

#### Add Student
```
=== Add New Student ===
Enter name: John Doe
Enter email: john.doe@email.com
Student added successfully with ID: 6
```

#### Process Payment
```
=== Process Payment ===
Enter Student ID: 1
Enter payment amount: 15000
Enter description: First installment
Payment of Rs.15000.0 processed successfully!
New balance: Rs.-15000.0
```

#### View Fee Summary
```
===== Fee Summary for Rahul Sharma =====
Course Fee: Rs.45000.0
Total Paid: Rs.15000.0
Total Refunded: Rs.0.0
Net Paid: Rs.15000.0
Current Balance: Rs.-15000.0
==========================================
```

---

## 📝 Key Learning Points

1. **Dependency Injection**
    - Constructor injection vs Field injection
    - Bean lifecycle management
    - Component scanning

2. **Hibernate ORM**
    - Entity mapping with annotations
    - Session management
    - HQL queries
    - Lazy vs Eager loading

3. **Transaction Management**
    - ACID properties
    - Declarative transactions with @Transactional
    - Transaction propagation
    - Rollback scenarios

4. **Layered Architecture**
    - Separation of concerns
    - DAO pattern
    - Service layer for business logic

---

## 🔧 Configuration Properties

### Hibernate Properties
```properties
hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
hibernate.show_sql=true
hibernate.format_sql=true
hibernate.hbm2ddl.auto=update
```

### Connection Pool (HikariCP)
```properties
maximumPoolSize=10
minimumIdle=5
connectionTimeout=30000
idleTimeout=600000
```

---

## 📚 Additional Features You Can Add

1. **Authentication & Authorization**
    - Spring Security integration
    - Role-based access control

2. **REST API**
    - Spring Web MVC
    - RESTful endpoints
    - JSON responses

3. **Front-end**
    - Thymeleaf templates
    - JSP pages
    - React/Angular integration

4. **Advanced Features**
    - Email notifications
    - PDF report generation
    - Excel export/import
    - Audit logging

---

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
    - Check MySQL is running
    - Verify credentials in AppConfig.java
    - Ensure database exists

2. **Hibernate Dialect Error**
    - Use correct dialect for your MySQL version
    - MySQL8Dialect for MySQL 8.x

3. **Transaction Not Rolling Back**
    - Ensure @Transactional is on public methods
    - Check exception types (checked vs unchecked)

---

## 📖 References

- [Spring Framework Documentation](https://spring.io/projects/spring-framework)
- [Hibernate ORM Documentation](https://hibernate.org/orm/documentation/)
- [Spring Transaction Management](https://docs.spring.io/spring-framework/reference/data-access/transaction.html)

---

## 👨‍💻 Author

**Chandan-int**
- GitHub: [@Chandan-int](https://github.com/Chandan-int)

---

## 📄 License

This project is created for educational purposes.

---

## 🎓 Conclusion

This project successfully demonstrates:
- ✅ Dependency Injection using Spring Java-based configuration
- ✅ CRUD operations using Hibernate ORM
- ✅ Transaction Management for fee payment and refund operations
- ✅ Integration of Spring + Hibernate for real-world data management

Perfect for understanding enterprise Java application development
