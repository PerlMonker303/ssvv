package service;

import domain.Assignment;
import domain.Grade;
import domain.Student;
import junit.framework.TestCase;
import repository.*;
import validation.AssignmentValidator;
import validation.GradeValidator;
import validation.StudentValidator;
import validation.Validator;

public class ServiceTest extends TestCase {

    public void testSaveStudentRegular() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentRepository repo1 = new StudentRepository(studentValidator);
        AssignmentRepository repo2 = new AssignmentRepository(assignmentValidator);
        GradeRepository repo3 = new GradeRepository(gradeValidator);

        Service service = new Service(repo1, repo2, repo3);

        String id = "100";
        String name = "test_name";
        int group = 931;
        int result = service.saveStudent(id, name, group);
        assertEquals(0, result);
    }

    public void testSaveStudentInvalidGroup() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentRepository repo1 = new StudentRepository(studentValidator);
        AssignmentRepository repo2 = new AssignmentRepository(assignmentValidator);
        GradeRepository repo3 = new GradeRepository(gradeValidator);

        Service service = new Service(repo1, repo2, repo3);

        String id = "100";
        String name = "test_name";
        int group = 55555;
        int result = service.saveStudent(id, name, group);
        assertEquals(1, result);
    }
}
