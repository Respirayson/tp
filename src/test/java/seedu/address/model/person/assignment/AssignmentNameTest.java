package seedu.address.model.person.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class AssignmentNameTest {

    private AssignmentName assignmentName = new AssignmentName("Finding ELDRIC");

    @Test
    public void equals() {
        // same object -> returns true
        assertEquals(assignmentName, assignmentName);

        // same values -> returns true
        AssignmentName assignmentNameCopy = new AssignmentName(assignmentName.toString());
        assertEquals(assignmentName, assignmentNameCopy);

        // null -> returns false
        assertFalse(assignmentName.equals(null));

        // different type -> returns false
        assertFalse(assignmentName.equals(5));

        // different values -> returns false
        assertFalse(assignmentName.equals(new AssignmentName("Rune Trials")));
    }
}
