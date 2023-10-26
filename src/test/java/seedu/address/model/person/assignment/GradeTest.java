package seedu.address.model.person.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalGrades.GRADE1;
import static seedu.address.testutil.TypicalGrades.GRADE2;
import static seedu.address.testutil.TypicalGrades.UNGRADED_GRADE;

import org.junit.jupiter.api.Test;

public class GradeTest {

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(GRADE1.equals(GRADE1));

        // same values -> returns true
        Grade gradeCopy = new Grade(UNGRADED_GRADE.getMax());
        assertTrue(UNGRADED_GRADE.equals(gradeCopy));

        // null -> returns false
        assertFalse(GRADE1.equals(null));

        // different type -> returns false
        assertFalse(GRADE1.equals(5));

        // different actual grade -> returns false
        Grade editedGrade2 = new Grade("1", GRADE2.getMax());
        assertFalse(GRADE2.equals(editedGrade2));

        // different max grade -> returns false
        Grade editedGradeUngraded = new Grade("UNGRADED", "1");
        assertFalse(UNGRADED_GRADE.equals(editedGradeUngraded));

        // different grading status -> returns false
        Grade editedGrade = new Grade("1", UNGRADED_GRADE.getMax());
        assertFalse(UNGRADED_GRADE.equals(editedGrade));
    }

    @Test
    public void getIsGraded() {
        assertTrue(GRADE1.getIsGraded());
        assertFalse(UNGRADED_GRADE.getIsGraded());
    }

    @Test
    public void ungrade() {
        Grade grade = GRADE1;
        assertTrue(grade.getIsGraded());
        grade = grade.ungrade();
        assertFalse(grade.getIsGraded());
    }

    @Test
    public void isValidGrade() {
        assertFalse(Grade.isValidGrade("haha", "800"));
        assertFalse(Grade.isValidGrade("10000", "800"));
        assertFalse(Grade.isValidGrade("UNGRADED", "800"));
        assertTrue(Grade.isValidGrade("875", "800"));
    }

    @Test
    public void isValidIncludingUngraded() {
        assertFalse(Grade.isValidIncludingUngraded("haha", "800"));
        assertFalse(Grade.isValidIncludingUngraded("10000", "800"));
        assertTrue(Grade.isValidIncludingUngraded("UNGRADED", "800"));
        assertTrue(Grade.isValidIncludingUngraded("875", "800"));
    }

    @Test
    public void gradeCopy_success() {
        Grade gradeCopy = GRADE1.copyGrade();
        assertTrue(GRADE1.equals(gradeCopy));
    }

    @Test
    public void toStringMethod() {
        // Ungraded grade -> returns true
        assertTrue("UNGRADED/1200".equals(UNGRADED_GRADE.toString()));

        //Graded grade -> returns true
        assertTrue("800/800".equals(GRADE1.toString()));
    }
}
