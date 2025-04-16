# Spring Account System Template

Template for fully setupped accountsystem for springboot

## Technologies

- **Spring Boot**: Main framework for the backend.
- **Spring Security**: For authentication and security.
- **JPA/Hibernate**: For database integration (MySQL, PostgreSQL, etc.).
- **REST API**: For communication between frontend and backend.

## Installation

1. **Clone the repository:**
```bash
git clone https://github.com/your-username/spring-accsystem-template.git
```
2. **Navigate to the project directory:**
```bash
git clone https://github.com/your-username/spring-accsystem-template.git
```
3. **Install dependencies:**
```bash
mvn install
gradle build
```
4. **Start application:**
```bash
mvn spring-boot:run
```

## API Endpoints
### Authentication
- **POST** `/auth/login`
  Authenticates a user and returns a session token.
  **Request Body:**
  ```json
  {
  "username": "your-username",
  "password": "your-password"
  }
  ```
   **Response:**
   
   ```json
   {
     "status": "OK",
     "message": "Successfully logged in.",
     "data": {
       "sessionId": "your-session-id",
       "username": "your-username",
       "email": "user-email"
     }
   }
   ```
- **POST** `/auth/register`
  Registers a new user.
  **Request Body:**
  ```json
  {
  "username": "new-username",
  "email": "new-user@example.com",
  "password": "new-password"
  }
  ```
   **Response:** 
   ```json
   {
  "status": "OK",
  "message": "User successfully registered."
  }
   ```
- **POST** `/auth/logout`
  Logs out the user by invalidating their session.
  **Request Headers:**
  ```yaml
  Authorization: Bearer your-session-id
  ```
   **Response:** 
   ```json
    {
      "status": "OK",
      "message": "Successfully logged out."
    }
   ```
- **POST** `/auth/check-session`
  Checks if the provided session is still valid.
  **Request Headers:**
  ```yaml
  Authorization: Bearer your-session-id
  ```
   **Response:** 
   ```json
    {
      "status": "OK",
      "message": "Session is valid."
    }
   ```

## Contributing
If you'd like to contribute to this project, feel free to fork the repository, create a branch, and submit a pull request. Please make sure your code follows the project's coding standards and includes tests where necessary.


## License
This project is licensed under the MIT License - see the LICENSE file for details.
```
This `README.md` provides an overview of the project, installation instructions, and documentation for the key API endpoints for authentication and board management. You can modify it further if needed for your specific requirements!
```
