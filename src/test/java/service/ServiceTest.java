package service;

import domain.Assignment;
import domain.Grade;
import domain.Student;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import repository.*;
import validation.*;

public class ServiceTest extends TestCase {
    private static final Validator<Student> studentValidator = new StudentValidator();
    private static final Validator<Assignment> assignmentValidator = new AssignmentValidator();
    private static final Validator<Grade> gradeValidator = new GradeValidator();

    private Service service;

    @BeforeEach
    public void init(){
        StudentRepository repo1 = new StudentRepository(studentValidator);
        AssignmentRepository repo2 = new AssignmentRepository(assignmentValidator);
        GradeRepository repo3 = new GradeRepository(gradeValidator);

        service = new Service(repo1, repo2, repo3);
    }

    @Test
    public void testSaveStudentTC1() {
        //Arrange
        String id = "";
        String name = "Andrei";
        int group = 110;
        int expected = 1;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudentTC2() {
        //Arrange
        String id = null;
        String name = "Andrei";
        int group = 110;
        int expected = 1;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudentTC3() {
        //Arrange
        String id = "1";
        String name = "";
        int group = 110;
        int expected = 1;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudentTC4() {
        //Arrange
        String id = "1";
        String name = null;
        int group = 110;
        int expected = 1;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudentTC5() {
        //Arrange
        String id = "1";
        String name = "Andrei";
        int group = 109;
        int expected = 1;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudentTC6() {
        //Arrange
        String id = "1";
        String name = "Andrei";
        int group = 939;
        int expected = 1;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudentTC7() {
        //Arrange
        String id = "100";
        String name1 = "Andrei";
        int group1 = 111;
        String name2 = "Dragos";
        int group2 = 932;

        //Act
        int res1 = service.saveStudent(id, name1, group1);
        int res2 = service.saveStudent(id, name2, group2);

        //Assert
        Assertions.assertEquals(0, res1);
        Assertions.assertEquals(1, res2);
    }

    @Test
    public void testSaveStudentTC8() {
        //Arrange
        String id = "1";
        String name = "Andrei";
        int group = 111;
        int expected = 0;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudentTC9() {
        //Arrange
        String id = "1";
        String name = "Andrei";
        int group = 937;
        int expected = 0;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSaveStudentTC10() {
        //Arrange
        String id = "1";
        String name = "Andrei";
        int group = 937;
        int expected = 0;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(expected, actual);
    }
}
