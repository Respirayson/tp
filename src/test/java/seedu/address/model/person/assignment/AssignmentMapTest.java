package seedu.address.model.person.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalAssignments;

public class AssignmentMapTest {

    private AssignmentMap assignments = new AssignmentMap();

    @Test
    public void containsMethod() {
        AssignmentName assignmentNameTrue = new AssignmentName("Finding ELDRIC");
        assertTrue(assignments.contains(assignmentNameTrue));

        AssignmentName assignmentNameFalse = new AssignmentName("Finding BOYD");
        assertFalse(assignments.contains(assignmentNameFalse));
    }

    @Test
    public void asUnmodifiableObservableMap_modifyMap_throwsUnsupportedOperationException() {
        AssignmentName assignmentName = new AssignmentName("Finding ELDRIC");
        assertThrows(UnsupportedOperationException.class, ()
                -> assignments.asUnmodifiableObservableMap().remove(assignmentName));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(assignments.equals(assignments));

        // same values -> returns true
        AssignmentMap assignmentsCopy = new AssignmentMap();
        assertTrue(assignmentsCopy.equals(assignments));

        // null -> returns false
        assertFalse(assignments.equals(null));

        //different type -> returns false
        assertFalse(assignments.equals(5));

        // different values -> returns false
        assertFalse(assignments.equals(TypicalAssignments.getSampleAssignmentMap()));
    }
}
