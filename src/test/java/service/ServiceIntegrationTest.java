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

public class ServiceIntegrationTest {
    private static final Validator<Student> studentValidator = new StudentValidator();
    private static final Validator<Assignment> assignmentValidator = new AssignmentValidator();
    private static final Validator<Grade> gradeValidator = new GradeValidator();

    StudentRepository repo1;
    AssignmentRepository repo2;
    GradeRepository repo3;

    private Service service;

    @BeforeEach
    public void init(){
        repo1 = new StudentRepository(studentValidator);
        repo2 = new AssignmentRepository(assignmentValidator);
        repo3 = new GradeRepository(gradeValidator);

        service = new Service(repo1, repo2, repo3);
    }

    @Test
    public void testSaveStudent() {
        //Arrange
        String id = "5";
        String name = "Andrei";
        int group = 111;

        //Act
        int actual = service.saveStudent(id, name, group);

        //Assert
        Assertions.assertEquals(0, actual);
    }

    @Test
    public void testSaveAssignment() {
        // Arrange
        String id = "1";
        String description = "some desc";
        int deadline = 1;
        int startline = 1;

        // Act
        int result = service.saveAssignment(id, description, deadline, startline);

        // Assert
        Assertions.assertEquals(0, result);
    }

    @Test
    public void testSaveGrade(){
        //Arrange
        String idStudent = "1";
        String idAssignment = "123";
        int valGrade = 10;
        int predata = 7;
        String feedback = "Ok";

        //Act
        int actual = service.saveGrade(idStudent, idAssignment, valGrade, predata, feedback);

        //Assert
        Assertions.assertEquals(-1, actual);
    }

    @Test
    public void testIntegration(){
        //Arrange
        String idStudent = "5";
        String name = "Andrei";
        int group = 111;

        String idAssignment = "1";
        String description = "some desc";
        int deadline = 1;
        int startline = 1;

        int valGrade = 10;
        int predata = 7;
        String feedback = "Ok";

        //Act
        int resStudent = service.saveStudent(idStudent, name, group);
        int resAssignemnt = service.saveAssignment(idAssignment, description, deadline, startline);
        int resGrade = service.saveGrade(idStudent, idAssignment, valGrade, predata, feedback);

        //Assert
        Assertions.assertEquals(0, resStudent);
        Assertions.assertEquals(0, resAssignemnt);
        Assertions.assertEquals(0, resGrade);
    }

}
