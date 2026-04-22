CREATE TABLE IF NOT EXISTS students (
    roll_number VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    physics INT DEFAULT 0,
    chemistry INT DEFAULT 0,
    math INT DEFAULT 0
);

INSERT INTO students (roll_number, name, password, physics, chemistry, math) VALUES 
('2024-CS-01', 'Alice Johnson', 'password123', 98, 97, 100),
('2024-CS-02', 'Bob Smith', 'password123', 75, 82, 78),
('2024-CS-03', 'Charlie Brown', 'password123', 32, 45, 28);
