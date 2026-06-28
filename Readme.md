# Job Application Tracker

A console-based Job Application Tracker built using Core Java, designed to help users manage job applications, interview stages, and recruitment progress.

This project is being developed with a clean, layered architecture and will later be extended into a Spring Boot REST API application.

## Features

- Add new job applications
- View all applications
- Search applications by company name
- Update application status
- Delete applications
- View application statistics
- Persistent storage using SQLite and JDBC
- Input validation and custom exception handling

## Architecture

```text
Console UI
    ↓
Service Layer
    ↓
DAO Layer
    ↓
SQLite Database
```