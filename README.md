# GraphQL Vehicle Demo

A modern Spring Boot application demonstrating GraphQL implementation for vehicle management using the latest Java and Spring Boot technologies.

## 🚀 Features

- **Modern Stack**: Java 21, Spring Boot 3.3.2, Spring GraphQL
- **GraphQL API**: Full CRUD operations with GraphiQL interface
- **Database Support**: MySQL for production, H2 for development
- **Validation**: Input validation with Jakarta Bean Validation
- **Testing**: Comprehensive GraphQL integration tests
- **Docker Support**: Multi-stage Docker build with docker-compose
- **CI/CD**: GitHub Actions pipeline with security scanning
- **Monitoring**: Spring Boot Actuator endpoints

## 🛠️ Technology Stack

- **Java 17** (LTS)
- **Spring Boot 3.3.2**
- **Spring GraphQL** (Native GraphQL support)
- **Spring Data JPA**
- **MySQL 8.0** / **H2 Database**
- **Lombok**
- **Maven**
- **Docker & Docker Compose**
- **JUnit 5**

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose (optional)
- MySQL 8.0+ (for production)

## 🚀 Quick Start

### Development Mode (H2 Database)

```bash
# Clone the repository
git clone <repository-url>
cd graphql-java

# Run with development profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production Mode (MySQL Database)

```bash
# Set environment variables
export DB_URL=jdbc:mysql://localhost:3306/vehicle_db
export DB_USERNAME=your_username
export DB_PASSWORD=your_password

# Run the application
./mvnw spring-boot:run
```

### Docker Deployment

```bash
# Start with Docker Compose (includes MySQL)
docker-compose up -d

# Or build and run manually
docker build -t graphql-vehicle-demo .
docker run -p 8080:8080 graphql-vehicle-demo
```

## 🔧 Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_URL` | Database URL | `jdbc:mysql://localhost:3306/vehicle_db` |
| `DB_USERNAME` | Database username | `root` |
| `DB_PASSWORD` | Database password | `password` |
| `DDL_AUTO` | Hibernate DDL mode | `update` |
| `SHOW_SQL` | Show SQL queries | `false` |

### Profiles

- **dev**: Development profile with H2 database
- **docker**: Docker profile with MySQL
- **default**: Production profile with MySQL

## 📊 GraphQL API

### Endpoints

- **GraphQL Endpoint**: `http://localhost:8080/graphql`
- **GraphiQL Interface**: `http://localhost:8080/graphiql`
- **H2 Console** (dev profile): `http://localhost:8080/h2-console`

### Schema

#### Vehicle Type
```graphql
type Vehicle {
    id: ID!
    type: String!
    modelCode: String!
    brandName: String
    launchDate: String
    formattedDate: String
}
```

#### Queries
```graphql
# Get all vehicles with optional limit
vehicles(count: Int): [Vehicle!]!

# Get vehicle by ID
vehicle(id: ID!): Vehicle

# Get vehicles by type
vehiclesByType(type: String!): [Vehicle!]!

# Get vehicles by brand
vehiclesByBrand(brandName: String!): [Vehicle!]!
```

#### Mutations
```graphql
# Create a new vehicle
createVehicle(
    type: String!
    modelCode: String!
    brandName: String
    launchDate: String
): Vehicle!

# Update existing vehicle
updateVehicle(
    id: ID!
    type: String
    modelCode: String
    brandName: String
    launchDate: String
): Vehicle!

# Delete vehicle
deleteVehicle(id: ID!): Boolean!
```

## 📝 Example Queries

### Create a Vehicle
```graphql
mutation {
  createVehicle(
    type: "SUV"
    modelCode: "BMW-X5-2024"
    brandName: "BMW"
    launchDate: "2024-01-15"
  ) {
    id
    type
    modelCode
    brandName
    launchDate
  }
}
```

### Get All Vehicles
```graphql
query {
  vehicles(count: 10) {
    id
    type
    modelCode
    brandName
    formattedDate
  }
}
```

### Update a Vehicle
```graphql
mutation {
  updateVehicle(
    id: "1"
    type: "Sedan"
    brandName: "BMW Updated"
  ) {
    id
    type
    modelCode
    brandName
  }
}
```

## 🧪 Testing

### Run Tests
```bash
# Run all tests
./mvnw test

# Run tests with coverage
./mvnw test jacoco:report
```

### Test Categories
- **Unit Tests**: Service layer testing
- **Integration Tests**: GraphQL endpoint testing
- **Repository Tests**: Database layer testing

## 🐳 Docker

### Build Image
```bash
docker build -t graphql-vehicle-demo .
```

### Run with Docker Compose
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

## 📈 Monitoring

### Health Check
```bash
curl http://localhost:8080/actuator/health
```

### Metrics
```bash
curl http://localhost:8080/actuator/metrics
```

## 🔒 Security

- Input validation using Jakarta Bean Validation
- SQL injection prevention with JPA
- Docker security with non-root user
- Dependency vulnerability scanning with Trivy

## 🚀 CI/CD

The project includes a GitHub Actions workflow that:
- Runs tests on multiple Java versions
- Builds Docker images
- Performs security scanning
- Generates test reports

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── entity/          # JPA entities
│   │   ├── repository/      # Data repositories
│   │   ├── service/         # Business logic
│   │   ├── graphql/         # GraphQL controllers
│   │   ├── exception/       # Exception handlers
│   │   └── DemoApplication.java
│   └── resources/
│       ├── graphql/         # GraphQL schema files
│       ├── application*.properties
│       └── static/
└── test/
    └── java/com/example/demo/
        └── *Test.java       # Test files
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:
- Create an issue in the GitHub repository
- Check the [GraphQL documentation](https://graphql.org/)
- Review [Spring GraphQL documentation](https://docs.spring.io/spring-graphql/docs/current/reference/html/)

## 🔄 Version History

- **v1.0.0**: Initial release with modern Spring Boot 3.x and Java 17
- **v0.0.1**: Legacy version with Spring Boot 2.x and Java 8
