package seedu.address.model.person.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT1;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT2;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.AssignmentBuilder;

public class AssignmentTest {

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(ASSIGNMENT1.equals(ASSIGNMENT1));

        // same values -> returns true
        Assignment assignmentCopy = new AssignmentBuilder(ASSIGNMENT1).build();
        assertTrue(ASSIGNMENT1.equals(assignmentCopy));

        // null -> returns false
        assertFalse(ASSIGNMENT1.equals(null));

        // different type -> returns false
        assertFalse(ASSIGNMENT1.equals(5));

        // different name -> returns false
        Assignment editedAssignment =
            new AssignmentBuilder(ASSIGNMENT1).withName(ASSIGNMENT2.getName().toString()).build();
        assertFalse(ASSIGNMENT1.equals(editedAssignment));

        // different grade -> returns false
        editedAssignment =
            new AssignmentBuilder(ASSIGNMENT1).withGrade(ASSIGNMENT2.getGrade().toString()).build();
        assertFalse(ASSIGNMENT1.equals(editedAssignment));
    }

    @Test
    public void assignmentCopy_success() {
        Assignment assignmentCopy = ASSIGNMENT1.copyAssignment();
        assertTrue(ASSIGNMENT1.equals(assignmentCopy));
    }
}
