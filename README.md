# CV Generator for IT Employees

Current build status:    [![Build Status](https://travis-ci.org/psolarski/Engineering-Thesis---CV-generator.svg?branch=master)](https://travis-ci.org/psolarski/Engineering-Thesis---CV-generator)

# Motivation
The system was created for the needs of my engineering thesis. 

# Features
1. Each user has a unique account in the system.
2. System ensures authentication with token based authentication - JWT.
3. System contains four access level: guest, developer, human resource and administrator.
4. Each developer can generate his own CV. 
5. Employee with human resource is allowed to generate other developer's CV.
6. System generates nitification for developer to remaind about his data updates.
7. System is integrated with Microsoft Outlook.
8. Users can create and send new emails with attachments and read their mails.

# Used technologies
* Spring 
* Spring Boot
* Spring Security
* Spring Data
* Thymeleaf
* JSON Web Tokens
* Maven
* Tomcat
* PostgreSQL
