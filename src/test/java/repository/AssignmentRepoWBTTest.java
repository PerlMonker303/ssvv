package repository;

import domain.Assignment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validation.AssignmentValidator;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentRepoWBTTest {
    private static final AssignmentValidator  validator = new AssignmentValidator();
    private              AssignmentRepository repo;

    private static final Assignment initialAssignment = new Assignment("11", "Description", 2, 1);

    @BeforeEach
    public void beforeEach() {
        repo = new AssignmentRepository(validator);
        repo.save(initialAssignment);
    }

    @Test
    public void testSaveTC1() {
        // Arrange
        Assignment invalidAssignment = new Assignment("", "", 2, 1);

        // Act
        Assignment result = repo.save(invalidAssignment);

        // Assert
        assertNull(result);
    }

    @Test
    public void testSaveTC2() {
        // Arrange
        Assignment validAssignment = new Assignment("22", "Done", 7, 6);

        // Act
        Assignment result = repo.save(validAssignment);

        // Assert
        Assertions.assertEquals(validAssignment, result);
    }

    @Test
    public void testSaveTC3(){
        // Arrange
        Assignment duplicateAssignment = new Assignment("11", "Done", 7, 6);

        // Act
        Assignment result = repo.save(duplicateAssignment);

        // Assert
        assertNull(result);
    }
}
