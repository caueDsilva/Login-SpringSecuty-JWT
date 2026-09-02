# Login-SpringSecuty-JWT

A small project developed for learning and understanding how Spring Security works with JWT authentication and route authorization in a Spring Boot application.

The main focus of this project is the security layer, rather than building a complete application.

### Purpose

The goal of this project is to understand how to:

- Authenticate users using JWT tokens.
- Generate and validate authentication tokens.
- Protect API routes using Spring Security.
- Define public and authenticated routes.
- Control access to endpoints based on user permissions.
- Implement a custom security filter.
- Understand the Spring Security authentication flow.

### Security

The application uses JWT (JSON Web Tokens) for authentication.

Most API routes require a valid JWT token to be accessed. Only the following routes are publicly available:

- POST /auth/login — User authentication.
- POST /auth/register — User registration.

All other routes require authentication.

The JWT token is sent through the Authorization header:

Authorization: Bearer <token>

The security filter intercepts incoming requests, extracts the token, validates it, and authenticates the user before allowing access to protected routes.

### What I Learned

This project was created primarily as a study project to better understand:

- How Spring Security handles authentication.
- How JWT-based authentication works.
- How security filters process requests.
- How to configure public and protected endpoints.
- How user authorities/permissions can be used to restrict access.
- How authentication information is stored in the Spring Security context.