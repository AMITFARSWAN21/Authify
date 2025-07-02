# 📘 EduNotes - Backend (Spring Boot)

**EduNotes** is a secure and user-friendly platform for students to register, log in, upload notes (PDFs), and manage their profiles.  
Built using **Spring Boot**, it includes robust **JWT authentication**, **file handling**, and **role-based access**.

---

## 🚀 Features

✅ Secure JWT Authentication (with Cookies)

Student registration and login system using JWT stored in HTTP-only cookies.

Role-based route access (student, admin).

✅ Email Verification & OTP-based Password Reset

Automatic email verification on signup.

OTP-based reset functionality for forgotten passwords.

✅ User Profile Management

Fetch profile of the logged-in user via JWT context.

Welcome email sent on successful registration.

✅ Certificate Generator

Generate stylish PDF certificates from form data.

Download certificate using certificate ID.

✅ Student Registration System

Store and fetch detailed student information via RESTful APIs.

✅ Marks Registration Module

Add marks per student with duplicate checks.

Fetch all stored marks records.

✅ AI-based Notes Summarizer

Upload handwritten or typed notes as PDF.

Get AI-generated summaries and key concepts from uploaded notes.

Save processed summaries to the database.

✅ Study Plan Generator (AI-powered)

Personalized study plans using student name and subject/topic inputs.

Returns structured output to guide student learning.

✅ PDF Upload and Download Support

Upload and retrieve user-generated content like notes and certificates.

All downloads served as proper application/pdf with filename headers.

✅ Admin Dashboard Support

Role-based access to admin routes for user and content management.

✅ Clean RESTful API Architecture

Organized controller structure.

Consistent API versioning (/api/v1.0/).

Global exception handling and response management.

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


📘 Certificate APIs

| Method | Endpoint                               | Description                    | Auth Required |
| ------ | -------------------------------------- | ------------------------------ | ------------- |
| `POST` | `/api/v1.0/certificates/generate`      | Generate a PDF certificate     | ✅             |
| `GET`  | `/api/v1.0/certificates/download/{id}` | Download certificate PDF by ID | ✅             |


Student APIs

| Method | Endpoint                     | Description                 | Auth Required |
| ------ | ---------------------------- | --------------------------- | ------------- |
| `POST` | `/api/v1.0/student/register` | Register a student          | ✅             |
| `GET`  | `/api/v1.0/student`          | Get all registered students | ✅             |

📘 Marks APIs

| Method | Endpoint                   | Description                       | Auth Required |
| ------ | -------------------------- | --------------------------------- | ------------- |
| `POST` | `/api/v1.0/marks/register` | Add marks for a student           | ✅             |
| `GET`  | `/api/v1.0/marks`          | Get all students with their marks | ✅             |


📘 Notes Summarizer APIs

| Method | Endpoint                         | Description                      | Auth Required |
| ------ | -------------------------------- | -------------------------------- | ------------- |
| `POST` | `/api/v1.0/summarizer/summarize` | Upload and summarize a notes PDF | ✅             |

📘 Study Plan AI APIs

| Method | Endpoint                           | Description                               | Auth Required |
| ------ | ---------------------------------- | ----------------------------------------- | ------------- |
| `POST` | `/api/v1.0/study-plan-ai/generate` | Generate personalized study plan using AI | ✅             |


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


