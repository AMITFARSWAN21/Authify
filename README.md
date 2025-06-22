📘 EduNotes - Backend (Spring Boot)
EduNotes is a secure and user-friendly platform for students to register, login, upload notes (PDFs), and manage their profiles. Built using Spring Boot, it includes robust JWT authentication, file handling, and role-based access.



🚀 Features
✅ Student registration and login with JWT cookies
✅ Email verification and OTP-based password reset
✅ Upload and download PDF notes
✅ Profile fetch with current user context
✅ Logout with cookie clearance
✅ Role-based route access (e.g., admin dashboard)
✅ Clean RESTful APIs with exception handling



🧱 Tech Stack
Java 17
Spring Boot 3
Spring Security
JWT Authentication
MySQL (or H2 for testing)
Lombok
Maven
React (frontend) – connected via API

📁 Project Structure (Backend)
src/
├── controller/         // API endpoints
├── entity/             // JPA entity classes
├── repository/         // Spring Data JPA interfaces
├── service/            // Business logic
├── io/                 // DTOs for input/output
├── utils/              // JWT utils and helpers


📦 API Endpoints

🔐 AuthController (/api/v1.0)
POST /login – Authenticate and return JWT in HttpOnly cookie
POST /logout – Clear the JWT cookie
POST /send-otp – Send OTP to email:-
POST /verify-otp – Verify entered OTP
POST /reset-password – Reset password using OTP
GET /is-authenticated – Check if JWT is valid
GET /role?email={email} – Get user role by email
POST /send-resend-otp – Send or resend OTP for verification

👤 ProfileController (/api/v1.0)
POST /register – Register a new user
GET /profile – Fetch profile of the currently logged-in user

📄 NoteController (/api/v1.0/notes)
GET / – Get all notes
POST / – Upload a new note (title, description, date, and PDF file)
DELETE /{id} – Delete note by ID
GET /{id}/download – Download note as PDF



Running the App:-
1.Clone the repo:
git clone https://github.com/your-username/edunotes-backend.git

2.Configure application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/edunotes
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your_jwt_secret_key


3.Build and run:
./mvnw spring-boot:run





