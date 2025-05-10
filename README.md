## test-hospital

<p id="description">
A microservice was developed for registering and modifying medical appointments as well as consultations. 
</p>


# Project Status
Project Status: Currently developed


***
## Table of Contents
- [Prerequisites](#prerequisites)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Instructions for the developer](#instructions-for-the-developer)
- [Run the TestHospitalApplication application](#run-the-testhospitalapplication-application)
- [Identified errors](#identified-errors)
- [Authors](#authors)
- [Last Modification Date](#last-modification-date)

***
### Prerequisites
In order to correctly deploy the test-hospital project, the following requirements must be met:

***
## Architecture

- Have the application.properties file that the micro needs
- Use Java version 17
- Use Maven 3.8 or higher
- Please use Loombok version 1.18.30 or higher.
- Use Spring Boot version 3.4.5

***
## Project Structure

![Project Structure](docs/readme/project_structure.png)

***
## Configuration

### Instructions for the developer

 > Clone the project locally with the command
 <center>`git clone https://github.com/rodConG/test-hospital`</center>
 
 > Configure properties
  - Configure the "application.properties" file

 > Enter the project folder with the command
 <center>`cd ./test-hospital`</center>
 
 > Run the maven command to install the dependency(ies) into the local repository
 <center>`mvn clean install -DskipTests`</center> 

 > Microservice build jar in (root) ./test-hospital/target/**

### Run the TestHospitalApplication application

 > Run the maven command `mvn spring-boot:run` to start a microservice instance or using the tools provided by an IDE (eclipse, netbeans, intellij, etc.).
 > Run the collection included in the docs folder and test the crud it includes to test each of the endpoints.

***
## Identified errors
***
N/A
***
## Useful Resources
***
N/A
***

## Authors
Rodrigo Contreras Garcia - rodri_cong@hotmail.com
***

## Last Modification Date
09 de Mayo del 2025
