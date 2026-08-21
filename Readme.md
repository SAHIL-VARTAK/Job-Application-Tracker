# Job Application Tracker

A production-style Job Application Tracker built with Spring Boot to help users manage job applications, interview stages, and recruitment progress through REST APIs.

The project follows a clean layered architecture and demonstrates modern backend development practices, including API documentation, validation, testing, code quality checks, CI/CD, containerization, and automated coverage reporting.

## Features

* Add new job applications
* View all applications
* Search applications by company name
* Update application status
* Delete applications
* View application statistics
* Persistent storage using SQLite and Spring Data JPA
* Request validation using Jakarta Validation
* Global exception handling with ProblemDetail responses
* Interactive API documentation using Swagger/OpenAPI
* Unit, controller, and repository tests with JUnit 5 and Mockito
* Code coverage reports using JaCoCo
* Automated code formatting with Spotless
* Static code analysis with Checkstyle
* CI/CD pipelines using GitHub Actions
* Multi-stage Docker support

## Tech Stack

* Java 22
* Spring Boot 3.5.4
* Spring Data JPA
* SQLite
* Maven
* Swagger/OpenAPI
* JUnit 5
* Mockito
* MockMvc
* JaCoCo
* Spotless
* Checkstyle
* Docker
* GitHub Actions

## Architecture

```text
REST Controller Layer
        ↓
Service Layer
        ↓
Repository Layer (Spring Data JPA)
        ↓
SQLite Database
```

## Project Structure

```text
src/main/java/com/jobtracker/app
│
├── config
│   └── OpenApiConfig
│
├── controller
│   └── JobApplicationController
│
├── dto
│   ├── CreateJobApplicationRequest
│   └── UpdateStatusRequest
│
├── exception
│   ├── ApplicationNotFoundException
│   └── GlobalExceptionHandler
│
├── model
│   ├── ApplicationStatus
│   └── JobApplication
│
├── repository
│   └── JobApplicationRepository
│
├── service
│   └── JobApplicationService
│
└── JobTrackerApplication
```

## API Endpoints

| Method | Endpoint                            | Description                 |
| ------ | ----------------------------------- | --------------------------- |
| GET    | `/api/applications`                 | Get all applications        |
| GET    | `/api/applications/{id}`            | Get application by ID       |
| GET    | `/api/applications/search?company=` | Search by company           |
| POST   | `/api/applications`                 | Create a new application    |
| PUT    | `/api/applications/{id}/status`     | Update application status   |
| DELETE | `/api/applications/{id}`            | Delete an application       |
| GET    | `/api/applications/statistics`      | View application statistics |

## API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

## Testing & Code Quality

The project includes:

* Unit tests for the service layer
* Controller tests using MockMvc
* Repository tests with SQLite
* Automated code coverage using JaCoCo
* Code formatting with Spotless
* Static analysis with Checkstyle
* Continuous Integration using GitHub Actions

## CI/CD

GitHub Actions automatically:

* Checks code formatting with Spotless
* Runs Checkstyle validation
* Executes all tests
* Builds the application JAR
* Generates JaCoCo coverage reports
* Publishes build artifacts

## Docker

The application uses a multi-stage Docker build.

Build the image:

```bash
docker build -t job-tracker .
```

Run the container:

```bash
docker run -p 8080:8080 job-tracker
```

## GraalVM Native Image

The project also supports building a native executable using [GraalVM Native Image](https://www.graalvm.org/latest/reference-manual/native-image/).

The `native` Maven profile replaces Tomcat with Jetty and configures the GraalVM Native Maven Plugin with native-image optimizations.

## GraalVM Native Image Requirements

To build the native Windows executable, the following are required:

* Windows
* GraalVM JDK 22
* GraalVM Native Image
* Visual Studio Build Tools with C++ build tools
* Maven Wrapper (`mvnw.cmd`)

The native build uses the Maven `native` profile and GraalVM Native Image to compile the Spring Boot application into a standalone Windows executable.

### Build Native Executable

Make sure GraalVM is installed and configured as the active JDK.

```bash
mvn -Pnative native:compile
```

Or build it through the Maven package lifecycle:

```bash
mvn -Pnative package
```

The generated native executable will be available in:

```text
target/job-application-tracker.exe
```

on Windows, or:

```text
target/job-application-tracker
```

on Linux/macOS.

### Run Native Executable

Windows:

```bash
target\job-application-tracker.exe
```

Linux/macOS:

```bash
./target/job-application-tracker
```

The native build uses:

* GraalVM Native Image
* Spring Boot Native support
* Jetty for the native profile
* Serial GC
* Compatibility CPU architecture
* English locale only
* Reduced charset set
* `--no-fallback`
* Native Image build report
