package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedAssignment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT1;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_UNGRADED;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.model.person.assignment.Grade;

public class JsonAdaptedAssignmentTest {
    private static final String INVALID_ASSIGNMENT_NAME = "HeheHaha";
    private static final String VALID_ASSIGNMENT_NAME = "Finding ELDRIC";
    private static final String VALID_GRADE = "1200/1200";
    private static final String INVALID_GRADE_TOO_HIGH = "1300/1200";
    private static final String INVALID_GRADE_NOT_INT = "haha/1200";

    @Test
    public void toModelType_validNameAndGrade_returnsAssignment() throws Exception {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(ASSIGNMENT1);
        assertEquals(ASSIGNMENT1, assignment.toModelType());
    }

    @Test
    public void toModelType_validNameUngraded_returnsAssignment() throws Exception {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(ASSIGNMENT_UNGRADED);
        assertEquals(ASSIGNMENT_UNGRADED, assignment.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(INVALID_ASSIGNMENT_NAME, VALID_GRADE);
        String expectedMessage = AssignmentName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(null, VALID_GRADE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AssignmentName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_gradeOutOfRange_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, INVALID_GRADE_TOO_HIGH);
        String expectedMessage = Grade.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_gradeNotInt_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, INVALID_GRADE_NOT_INT);
        String expectedMessage = Grade.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullGrade_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }
    //Add tests for grade soon
}
