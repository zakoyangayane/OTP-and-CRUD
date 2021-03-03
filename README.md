# OTP-and-CRUD

The project is written with Java 11. The database is MySQL. The connections you can find in the application.properties.
The postman for testing the project is included here, too.

----------------------------------

Implementing OTP and CRUD

The project has implemented One time password. For using it the user must be logged in and then his authorization will
be performed by email. In case of successful authorization, OTP is generated and sent to the user's email. The lifetime
of one time password is 5 minutes. Besides, is done an OTP validation. Access to the crud is only possible with a token.

The implementation of the CRUD is for User entity with these fields: email, first name, last name, date of birth,
marital status Implemented also error handling. Also used Spring Security in the project.

