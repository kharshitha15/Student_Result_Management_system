package com.edupulse.portal;

import com.edupulse.portal.model.Student;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    public void testGradeCalculation() {
        Student s = new Student();
        s.setPhysics(90);
        s.setChemistry(90);
        s.setMath(90);
        
        assertEquals(270, s.getTotal());
        assertEquals(90.0, s.getPercentage());
        assertEquals("A+", s.getGrade());
        assertEquals("Pass", s.getStatus());
    }

    @Test
    public void testFailStatus() {
        Student s = new Student();
        s.setPhysics(30); // Below 33
        s.setChemistry(80);
        s.setMath(80);
        
        assertEquals("Fail", s.getStatus());
    }
}
