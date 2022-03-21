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

public class ServiceTestWBT {

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
        repo2 = Mockito.spy(new AssignmentRepository(assignmentValidator));
        repo3 = new GradeRepository(gradeValidator);

        service = new Service(repo1, repo2, repo3);
    }


    @Test
    public void testSaveStudentTC1() {
        // Arrange
        String id = "1";
        String description = "some desc";
        int deadline = 1;
        int startline = 1;
        Assignment mockAssignment = new Assignment(id, description, deadline, startline);
        Mockito.doReturn(null).when(repo2).save(mockAssignment);

        // Act
        int result = service.saveAssignment(id, description, deadline, startline);

        // Assert
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testSaveStudentTC2() {
        // Arrange
        String id = "1";
        String description = "some desc";
        int deadline = 1;
        int startline = 1;
        Assignment mockAssignment = new Assignment(id, description, deadline, startline);
        Mockito.doReturn(mockAssignment).when(repo2).save(mockAssignment);

        // Act
        int result = service.saveAssignment(id, description, deadline, startline);

        // Assert
        Assertions.assertEquals(0, result);
    }

}
