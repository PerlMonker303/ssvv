package service;

import domain.Assignment;
import domain.Grade;
import domain.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.AssignmentRepository;
import repository.GradeRepository;
import repository.StudentRepository;
import validation.AssignmentValidator;
import validation.GradeValidator;
import validation.StudentValidator;
import validation.Validator;

public class ServiceWBTTest {

    private static final Validator<Student>    studentValidator    = new StudentValidator();
    private static final Validator<Assignment> assignmentValidator = new AssignmentValidator();
    private static final Validator<Grade>      gradeValidator      = new GradeValidator();
    private Service service;
    StudentRepository repo1;
    AssignmentRepository repo2;
    GradeRepository repo3;

    @BeforeEach
    public void init(){
        repo1 = new StudentRepository(studentValidator);
//        repo2 = Mockito.spy(new AssignmentRepository(assignmentValidator));
        repo2 = new AssignmentRepository(assignmentValidator);
        repo3 = new GradeRepository(gradeValidator);

        service = new Service(repo1, repo2, repo3);
    }


    @Test
    public void testSaveAssignmentTC1() {
        // Arrange
        String id = "";
        String description = "";
        int deadline = 6;
        int startline = 5;

        // Act
        int result = service.saveAssignment(id, description, deadline, startline);

        // Assert
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testSaveAssignmentTC2() {
        // Arrange
        String id = "22";
        String description = "Done";
        int deadline = 7;
        int startline = 6;

        // Act
        int result = service.saveAssignment(id, description, deadline, startline);

        // Assert
        Assertions.assertEquals(0, result);
    }

    @Test
    public void testSaveAssignmentTC3(){
        // Arrange
        String id = "22";
        String description = "Done";
        int deadline = 7;
        int startline = 6;

        // Act
        int result1 = service.saveAssignment(id, description, deadline, startline);
        int result2 = service.saveAssignment(id, description, deadline, startline);

        // Assert
        Assertions.assertEquals(0, result1);
        Assertions.assertEquals(1, result2);
    }

//    @Test
//    public void testSaveAssignmentTC4(){
//        // repo test - valid assignment
//        // Arrange
//        String id = "22";
//        String description = "Done";
//        int deadline = 6;
//        int startline = 5;
//        Assignment assignment = new Assignment(id, description, deadline, startline);
//
//        // Act
//        Assignment result = repo2.save(assignment);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(assignment, result);
//    }
//
//    @Test
//    public void testSaveAssignmentTC5(){
//        // repo test - existing entity
//        // Arrange
//        String id = "22";
//        String description = "Done";
//        int deadline = 6;
//        int startline = 5;
//        Assignment assignment1 = new Assignment(id, description, deadline, startline);
//        Assignment assignment2 = new Assignment(id, description, deadline, startline);
//
//        // Act
//        Assignment result1 = repo2.save(assignment1);
//        Assignment result2 = repo2.save(assignment2);
//
//        // Assert
//        Assertions.assertNotNull(result1);
//        Assertions.assertEquals(assignment1, result1);
//        Assertions.assertNull(result2);
//    }

}
