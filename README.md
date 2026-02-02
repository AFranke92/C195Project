# Scheduling Management System (JavaFX + MySQL)
📅 Scheduling Management System (JavaFX + MySQL)
Overview

This project is a database-driven scheduling and customer management desktop application built with Java, JavaFX, and MySQL. The system is designed for appointment-based businesses such as medical offices, service providers, or repair shops, where accurate scheduling and time management are critical. The application demonstrates full-stack desktop development, including authentication, business-rule enforcement, time-zone–aware scheduling, and SQL-backed reporting.

Key Features:
Authentication

Secure login system backed by database-stored user credentials

Login validation before application access

Appointment Scheduling with Constraints:

Appointments cannot overlap for the same customer

Appointments are restricted to business hours

All scheduling rules are validated before saving

Error alerts display when validation rules are violated

Upcoming Appointment Alerts:

On login, users are notified if they have an appointment occurring within the next 15 minutes

Improves usability for time-sensitive environments

Customer Management:

Create, edit, and delete customers

Associate multiple appointments with each customer

Enforced relational integrity through database queries

Reporting:

Built-in reports generated directly from the database

Includes a custom report displaying the total number of customers in the system

Time Zone Handling:

Appointment timestamps are stored in UTC

Local date/time is converted using Java’s ZoneId and LocalDateTime

Ensures consistency across environments and prevents time-related errors

This design avoids common scheduling bugs caused by local time discrepancies.

Architecture & Design - Layered Design:

Model Layer

Core domain objects (Customer, Appointment, User, etc.)

DAO Layer

Encapsulates all SQL queries and database access using JDBC

Separates persistence logic from UI and business logic

Controller Layer

Handles UI events, validation, navigation, and application state

Utility Classes

Shared helpers for time conversion and reusable logic

Design Decisions:

DAO classes were used to isolate SQL logic from UI logic, improving maintainability and clarity

Appointment validation logic prevents conflicts both when creating and editing appointments

Queries are structured to exclude the currently edited appointment when checking for conflicts

Technologies Used:

Java

JavaFX

FXML

MySQL

JDBC

Object-Oriented Programming (OOP)

MVC-inspired architecture

How to Run:

Configure the MySQL database connection

Ensure required tables and user records exist

Launch the application

Log in with valid credentials

Manage customers, appointments, and reports through the GUI

Purpose:

This project demonstrates:

Database-backed desktop application development

Secure authentication workflows

Time-zone–aware scheduling logic

Business rule enforcement

SQL-driven reporting

Clean separation of concerns

Future Enhancements:

Role-based user permissions

Expanded reporting and analytics

Improved scheduling conflict visualization

UI/UX refinements

Unit and integration testing

Author Information:

Author: Abigail Franke

Application version: 1.0

Date: 11/16/2022


IDE and Java Information:

IDE Version: IntelliJ IDEA 2022.1 (Community Edition)

Build #IC-221.5080.210, built on April 11, 2022

Runtime version: 11.0.14.1+1-b2043.25 amd64

VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.

Kotlin: 221-1.6.20-release-285-IJ5080.210

JDK: corretto-17.0.4.1

Javafx: Javafx-sdk-18.0.1

mysql connector: mysql-connector-java-8.0.31

