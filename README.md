# EduResult - Student Result Management System 🎓 📊

![EduResult Banner](assets/banner.png)

## 🚀 Overview
**EduResult** is a professional-grade, full-stack Student Result Management System designed to streamline academic record-keeping. Built with a robust **Spring Boot** backend and a high-performance **React.js** frontend, it provides a secure, scalable, and intuitive platform for educational institutions to manage students, subjects, and results with automated CGPA/SGPA calculations.

---

## ✨ Key Features

### 🔐 1. Role-Based Access Control (RBAC)
- **Admin**: Full control over students, faculty, and subject management.
- **Faculty**: Specialized portal for entering marks and managing academic records.
- **Student**: Personalized dashboard to view results, SGPA/CGPA trends, and academic history.

### 📊 2. Automated Result Generation
- **Instant SGPA/CGPA Calculation**: Eliminates manual errors with real-time academic scoring.
- **Multi-Semester Tracking**: Seamlessly manage data across various academic terms.
- **Performance Analytics**: Visual feedback on student progress.

### 🛡️ 3. Security & Integrity
- **JWT Authentication**: Secure, stateless session management.
- **Data Validation**: Robust backend validation to ensure data accuracy and integrity.
- **Audit Ready**: Organized database schema (SQLite/MySQL) for clean reporting.

---

## 🛠️ Tech Stack

### Frontend
- **Framework**: [React.js](https://reactjs.org/) (with [Vite](https://vitejs.dev/))
- **Styling**: Vanilla CSS (Custom Design System)
- **State Management**: React Context API
- **Networking**: Axios

### Backend
- **Framework**: [Spring Boot 3](https://spring.io/projects/spring-boot)
- **Security**: Spring Security + JWT
- **Persistence**: Spring Data JPA
- **Database**: SQLite (Zero-Config) / MySQL

---

## 📂 Project Structure
```text
EduResult/
├── backend/                # Spring Boot Backend
│   ├── src/main/java/      # Java Source Code
│   ├── src/main/resources/ # Configuration & Assets
│   └── pom.xml             # Maven Dependencies
├── frontend/               # React Frontend
│   ├── src/                # UI Components & Logic
│   ├── public/             # Static Assets
│   └── package.json        # Node.js Dependencies
├── assets/                 # Documentation Assets
└── README.md               # Project Documentation
```

---

## 🚦 Getting Started

### Prerequisites
- **Java**: JDK 17 or higher
- **Node.js**: v18 or higher
- **Maven**: (Bundled with `./mvnw`)

### 1. Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
3. **Initial Data Setup**: 
   Once the server is running, trigger the initial user creation by sending a POST request to:
   `http://localhost:8080/api/auth/setup`
   *This creates default `admin/admin123` and `faculty/faculty123` accounts.*

### 2. Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```

---

## 🤝 Contributing
Contributions are welcome! Feel free to open an issue or submit a pull request to help improve EduResult.

## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.
