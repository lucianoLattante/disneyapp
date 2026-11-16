# Disney application

Educational Java/Spring project created for Alkemy practice. This repository is a historical learning example; it is not a production service or employer project.

## Actual stack and scope

Java 8, Spring Boot 2.6.2, Spring MVC/Thymeleaf, Spring Data JPA, Spring Security and MySQL. Browse the existing controllers, services, entities and templates for the implemented features. The legacy dependency versions and authorization policies still require review.

## Local setup

1. Install a compatible JDK and Maven; create a disposable MySQL database named `disney`.
2. Export `DB_USERNAME` and `DB_PASSWORD` in the process environment. [.env.example](.env.example) is a reference, not an automatically loaded file.
3. Run `mvn clean spring-boot:run` from this directory, then open http://localhost:8080.

These are source-derived setup instructions, not a verified end-to-end startup result. Hibernate currently uses `ddl-auto: update`; use disposable local data only. Versioned migrations remain future work.

Mail defaults to a local SMTP sink at 127.0.0.1:1025, without authentication or TLS. Run a local sink if testing email. External SMTP requires explicit host, port, credentials and TLS configuration; do not use live accounts for tests.

## Security and verification status

SMTP and database credentials are externalized in this proposed change. Previously exposed credentials still require revocation by their owner; changing the file does not remove Git history. No credential-validity probe or mail delivery was performed.

This patch was checked for configuration placeholders and documentation consistency. Application startup, database integration and the full test suite have not been executed. Authentication/authorization, CSRF, dependency advisories and history scanning remain open.

## Original project note

# disneyapp
Aplicacion de disney para alkemy

1-Clonar,2-Buildear,3-Correr e ingresar a localhost:8080

