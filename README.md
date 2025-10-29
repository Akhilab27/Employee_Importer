# Employee Importer (Java + SQLite)

This project imports employee data from a CSV file into a SQLite database using JDBC.

## Features
- Imports employee details from a CSV file.
- Stores records in a local SQLite database.
- Displays imported data on the console.
- Uses JDBC for database connectivity.

## How to Run
1. Compile the project:
   javac -d out -cp "lib/sqlite-jdbc-3.50.3.0.jar" src\EmployeeImporter.java

2. Run the program:
   java -cp "out;lib/sqlite-jdbc-3.50.3.0.jar" EmployeeImporter

## Project Structure
Employee_Importer/
+-- src/
¦   +-- EmployeeImporter.java
+-- lib/
¦   +-- sqlite-jdbc-3.50.3.0.jar
+-- out/
+-- data/
+-- .gitignore
+-- README.md

## Author
Created by Akhila Badiginchala
