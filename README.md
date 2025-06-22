# 📘 EduNotes - Backend (Spring Boot)

**EduNotes** is a secure and user-friendly platform for students to register, log in, upload notes (PDFs), and manage their profiles.  
Built using **Spring Boot**, it includes robust **JWT authentication**, **file handling**, and **role-based access**.

---

## 🚀 Features

- ✅ Student registration and login with JWT cookies  
- ✅ Email verification and OTP-based password reset  
- ✅ Upload and download PDF notes  
- ✅ Profile fetch with current user context  
- ✅ Logout with cookie clearance  
- ✅ Role-based route access (e.g., admin dashboard)  
- ✅ Clean RESTful APIs with exception handling  

---

## 🧱 Tech Stack

- Java 17  
- Spring Boot 3  
- Spring Security  
- JWT Authentication  
- MySQL (or H2 for testing)  
- Lombok  
- Maven  
- React (Frontend) – connected via API  

---

## 📁 Project Structure (Backend)

src/
├── controller/ # API endpoints
├── entity/ # JPA entity classes
├── repository/ # Spring Data JPA interfaces
├── service/ # Business logic
├── io/ # DTOs for input/output
├── utils/ # JWT utilities and helpers




---

## 📦 API Endpoints


### 👤 AuthController /api/v1.0

| Method | Endpoint            | Description                                    |
| ------ | ------------------- | ---------------------------------------------- |
| POST   | `/login`            | Authenticate and return JWT in HttpOnly cookie |
| POST   | `/logout`           | Clear the JWT cookie                           |
| POST   | `/send-otp`         | Send OTP to registered email                   |
| POST   | `/verify-otp`       | Verify the entered OTP                         |
| POST   | `/reset-password`   | Reset password using OTP                       |
| GET    | `/is-authenticated` | Check if JWT is valid                          |
| GET    | `/role?email=...`   | Get user role by email                         |
| POST   | `/send-resend-otp`  | Send or resend OTP for verification            |

---

### 👤 ProfileController `/api/v1.0`

| Method | Endpoint     | Description                                |
|--------|--------------|--------------------------------------------|
| POST   | `/register`  | Register a new user                        |
| GET    | `/profile`   | Fetch profile of the logged-in user       |

---

### 📄 NoteController `/api/v1.0/notes`

| Method | Endpoint              | Description                               |
|--------|-----------------------|-------------------------------------------|
| GET    | `/`                   | Get all uploaded notes                    |
| POST   | `/`                   | Upload a note with title, desc, date, PDF |
| DELETE | `/{id}`               | Delete a note by its ID                   |
| GET    | `/{id}/download`      | Download the note as a PDF                |

---

## 📥 Running the App

### 1️⃣ Clone the repo
git clone https://github.com/your-username/edunotes-backend.git
cd edunotes-backend

2️⃣ Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/edunotes
spring.datasource.username=root
spring.datasource.password=your_password

jwt.secret=your_jwt_secret_key
spring.jpa.hibernate.ddl-auto=update

3️⃣ Build & Run
./mvnw spring-boot:run


