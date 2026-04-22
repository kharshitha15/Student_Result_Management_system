package com.edupulse.portal.model;

public class Student {
    private String rollNumber;
    private String name;
    private String password;
    private int physics;
    private int chemistry;
    private int math;

    // Getters and Setters
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public int getPhysics() { return physics; }
    public void setPhysics(int physics) { this.physics = physics; }
    
    public int getChemistry() { return chemistry; }
    public void setChemistry(int chemistry) { this.chemistry = chemistry; }
    
    public int getMath() { return math; }
    public void setMath(int math) { this.math = math; }

    public int getTotal() {
        return physics + chemistry + math;
    }

    public double getPercentage() {
        return (getTotal() / 3.0);
    }

    public String getGrade() {
        double p = getPercentage();
        if (p >= 90) return "A+";
        if (p >= 80) return "A";
        if (p >= 70) return "B";
        if (p >= 60) return "C";
        if (p >= 50) return "D";
        return "F";
    }

    public String getStatus() {
        return (physics >= 33 && chemistry >= 33 && math >= 33) ? "Pass" : "Fail";
    }
}
