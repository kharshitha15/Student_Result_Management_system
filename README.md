# EduPulse: Student Result Management System 🎓

EduPulse is a professional-grade, full-stack application designed to manage student academic records efficiently. Built with a focus on modern UI/UX and secure backend architecture, it is ideal for educational institutions to track and display student performance.

## 🌟 Key Features

### 👤 Role-Based Access Control
- **Admin Portal**: Add, view, edit, and delete student records. View overall class statistics and export results.
- **Student Portal**: Secure login to view subject-wise marks, aggregate percentage, and final grades.

### 📊 Advanced Analytics
- **Dashboard**: Real-time stats including total students, average class score, and passing rates.
- **Auto-Calculations**: Automated grade assignment (A+, A, B, etc.) and Pass/Fail status based on subject performance.

### 📥 Data Management
- **Export to HTML**: Download student result tables for offline record-keeping.
- **Search & Filter**: Quickly find students by roll number or name.

### 🎨 Premium UI/UX
- **Glassmorphism Design**: A modern, translucent interface with vibrant gradients.
- **Responsive Layout**: Optimized for both desktop and mobile viewing.
- **Interactive Elements**: Smooth transitions, micro-animations, and dynamic data loading.

## 🛠️ Tech Stack

- **Frontend**: HTML5, CSS3 (Vanilla), JavaScript (ES6+), Lucide Icons.
- **Backend**: Java, Spring Boot 3, Spring Security.
- **Database**: JDBC Template, H2 (In-memory for testing) / MySQL compatible.
- **Build Tool**: Maven.
- **Testing**: JUnit 5.

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher.
- Maven installed.

### Setup Instructions
1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   ```
2. **Database Configuration**:
   - By default, the project uses **H2 in-memory database** for immediate testing.
   - To switch to MySQL, update `src/main/resources/application.properties` with your MySQL credentials.

3. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
4. **Access the Portal**:
   - Open `http://localhost:8080/index.html` in your browser.

### Default Credentials
| Role | Username | Password |
| :--- | :--- | :--- |
| **Admin** | `admin` | `admin123` |
| **Student** | `2024-CS-01` | `password123` |

## 🧪 Testing
Run the unit test suite to verify business logic:
```bash
mvn test
```

## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.

