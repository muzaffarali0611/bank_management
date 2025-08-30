# Bank Management System - Spring Boot Implementation

A comprehensive, enterprise-grade banking management system built with Spring Boot 3.2.0, replacing the previous C++ implementation with modern Java architecture.

## 🚀 Features

### Core Banking Operations
- **Customer Management**: Complete customer lifecycle with KYC verification
- **Account Management**: Multiple account types (Savings, Checking, Fixed Deposit, etc.)
- **Transaction Processing**: Secure money transfers, deposits, withdrawals
- **Loan Management**: Loan application, approval, and payment processing
- **Card Management**: Debit/credit card operations with security features

### Security & Compliance
- **Multi-layered Security**: JWT authentication, role-based access control
- **Password Security**: BCrypt hashing with salt rounds
- **Audit Logging**: Complete audit trail for compliance
- **KYC Verification**: Know Your Customer compliance features
- **Input Validation**: Comprehensive data validation and sanitization

### Technical Features
- **Modern Architecture**: Spring Boot 3.2.0 with Java 17
- **Database**: PostgreSQL with Flyway migrations
- **Caching**: Multi-level caching strategy
- **API Design**: RESTful endpoints with proper versioning
- **Performance**: Optimized queries, connection pooling, async processing

## 🏗️ Architecture

### System Layers
```
┌─────────────────────────────────────┐
│           REST Controllers          │  ← API endpoints
├─────────────────────────────────────┤
│           Service Layer             │  ← Business logic
├─────────────────────────────────────┤
│         Repository Layer            │  ← Data access
├─────────────────────────────────────┤
│         Entity Layer                │  ← Domain models
├─────────────────────────────────────┤
│         Database Layer              │  ← PostgreSQL
└─────────────────────────────────────┘
```

### Entity Design
- **User Hierarchy**: Abstract User class with Customer, Staff, and Admin implementations
- **Inheritance Strategy**: JOINED table strategy for optimal performance
- **Relationships**: Proper JPA mappings with lazy loading
- **Auditing**: Automatic timestamp and version management

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Security**: Spring Security 6.2.0
- **Persistence**: Spring Data JPA + Hibernate
- **Database**: PostgreSQL 14+
- **Migration**: Flyway

### Security
- **Authentication**: JWT tokens
- **Password Hashing**: BCrypt
- **Authorization**: Role-based access control (RBAC)
- **CORS**: Configurable cross-origin resource sharing

### Additional
- **Validation**: Bean Validation (Jakarta)
- **Caching**: Spring Cache
- **Monitoring**: Spring Boot Actuator
- **Documentation**: OpenAPI/Swagger ready

## 📋 Prerequisites

- **Java**: JDK 17 or higher
- **Maven**: 3.6+ or higher
- **Database**: PostgreSQL 14+
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd bank_management
```

### 2. Database Setup
```bash
# Create PostgreSQL database
createdb bank_management

# Update application.yml with your database credentials
```

### 3. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Or build and run
mvn clean package
java -jar target/bank-management-system-1.0.0.jar
```

### 4. Access the Application
- **API Base URL**: http://localhost:8080/api/v1
- **Health Check**: http://localhost:8080/actuator/health
- **Swagger UI**: http://localhost:8080/swagger-ui/

## 🗄️ Database Schema

### Core Tables
- **users**: Base user information (abstract table)
- **customers**: Customer-specific data and KYC information
- **staff**: Staff member details and authorizations
- **admins**: Administrative user information
- **accounts**: Bank account details and balances
- **transactions**: Transaction history and details
- **roles**: User roles and permissions
- **permissions**: System permissions and access controls

### Key Features
- **Audit Trail**: Automatic timestamp and version tracking
- **Soft Deletes**: Data retention with is_active flag
- **Optimistic Locking**: Version-based concurrency control
- **Indexing**: Strategic database indexes for performance

## 🔐 Security Implementation

### Authentication Flow
1. **User Registration**: Secure password hashing with BCrypt
2. **Login**: JWT token generation and validation
3. **Authorization**: Role-based access control
4. **Session Management**: Stateless JWT-based sessions

### Security Features
- **Password Policies**: Configurable strength requirements
- **Account Lockout**: Automatic lockout after failed attempts
- **IP Whitelisting**: Configurable network restrictions
- **Audit Logging**: Complete security event tracking

## 📊 API Endpoints

### Customer Management
- `POST /customers/register` - Customer registration
- `GET /customers/{id}` - Get customer by ID
- `PUT /customers/{id}` - Update customer information
- `POST /customers/{id}/verify-kyc` - KYC verification
- `PUT /customers/{id}/credit-score` - Update credit score

### Account Management
- `GET /accounts/{id}` - Get account details
- `POST /accounts` - Create new account
- `PUT /accounts/{id}/status` - Update account status

### Transaction Processing
- `POST /transactions` - Create new transaction
- `GET /transactions/account/{accountId}` - Get account transactions
- `GET /transactions/customer/{customerId}` - Get customer transactions

## 🔧 Configuration

### Application Properties
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bank_management
    username: postgres
    password: password
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
  
  flyway:
    enabled: true
    baseline-on-migrate: true

jwt:
  secret: your-secure-secret-key
  expiration: 86400000  # 24 hours
```

### Environment Variables
- `DB_URL`: Database connection URL
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `JWT_SECRET`: JWT signing secret

## 🧪 Testing

### Test Categories
- **Unit Tests**: Individual component testing
- **Integration Tests**: Service layer testing
- **Repository Tests**: Data access testing
- **API Tests**: End-to-end API testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test category
mvn test -Dtest=CustomerServiceTest

# Run with coverage
mvn jacoco:report
```

## 📈 Performance & Monitoring

### Performance Features
- **Connection Pooling**: HikariCP for database connections
- **Caching**: Multi-level caching strategy
- **Query Optimization**: Efficient SQL generation
- **Async Processing**: Non-blocking operations

### Monitoring
- **Health Checks**: Database and service health
- **Metrics**: Performance metrics and custom measurements
- **Logging**: Structured logging with correlation IDs
- **Tracing**: Request tracing and performance analysis

## 🚀 Deployment

### Docker Deployment
```dockerfile
FROM openjdk:17-jre-slim
COPY target/bank-management-system-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Production Considerations
- **Environment Configuration**: Profile-based configuration
- **Security Hardening**: Production security settings
- **Monitoring**: Production monitoring and alerting
- **Backup Strategy**: Database backup and recovery

## 🔒 Security Best Practices

### Implementation
- **Input Validation**: Comprehensive input sanitization
- **SQL Injection Prevention**: Parameterized queries
- **XSS Protection**: Output encoding and validation
- **CSRF Protection**: Cross-site request forgery prevention

### Compliance
- **PCI DSS**: Payment card industry compliance
- **SOX**: Sarbanes-Oxley compliance
- **GDPR**: Data protection compliance
- **Audit Requirements**: Complete audit trail

## 🤝 Contributing

### Development Setup
1. Fork the repository
2. Create a feature branch
3. Implement your changes
4. Add tests and documentation
5. Submit a pull request

### Code Standards
- **Java**: Follow Google Java Style Guide
- **Spring**: Follow Spring Boot best practices
- **Testing**: Maintain high test coverage
- **Documentation**: Keep documentation updated

## 📚 Additional Resources

### Documentation
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security Reference](https://docs.spring.io/spring-security/site/docs/current/reference/html5/)
- [JPA/Hibernate Documentation](https://hibernate.org/orm/documentation/)

### Learning Resources
- [Spring Boot Tutorials](https://spring.io/guides)
- [Spring Security Tutorials](https://spring.io/guides/topicals/spring-security-architecture)
- [JPA Best Practices](https://hibernate.org/orm/documentation/6.0/userguide/html_single/Hibernate_User_Guide.html)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:
- **Issues**: Create an issue in the repository
- **Documentation**: Check the comprehensive documentation
- **Community**: Join our community discussions

---

**Note**: This is a production-ready banking system that demonstrates enterprise-level software engineering practices. Always ensure proper security measures and compliance requirements are met before deploying to production environments.
