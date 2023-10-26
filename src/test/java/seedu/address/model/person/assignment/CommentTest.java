package seedu.address.model.person.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalComments.COMMENT1;
import static seedu.address.testutil.TypicalComments.COMMENT1_COPY;
import static seedu.address.testutil.TypicalComments.COMMENT2;

import org.junit.jupiter.api.Test;

public class CommentTest {

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(COMMENT1.equals(COMMENT1));

        // same values -> returns true
        assertTrue(COMMENT1.equals(COMMENT1_COPY));

        // null -> returns false
        assertFalse(COMMENT1.equals(null));

        // different type -> returns false
        assertFalse(COMMENT1.equals(5));

        // different comment -> returns false
        assertFalse(COMMENT1.equals(COMMENT2));

        // different grading status -> returns false
        assertFalse(COMMENT1.equals(new Comment()));
    }

    @Test
    public void constructor_emptyComment_failure() {
        assertThrows(IllegalArgumentException.class , () -> new Comment(""));
    }

    @Test
    public void constructor_veryLongComment_failure() {
        assertThrows(IllegalArgumentException.class , () -> new Comment(INVALID_COMMENT));
    }

    @Test
    public void constructor_noArgument_success() {
        assertTrue(!new Comment().getIsCommented());
    }

    @Test
    public void isValidCommentMethod() {
        // empty comment -> return false
        assertFalse(Comment.isValidComment(""));

        // very long comment -> return false
        assertFalse(Comment.isValidComment(INVALID_COMMENT));

        // valid comment -> return true
        assertTrue(Comment.isValidComment(VALID_COMMENT));
    }

    @Test
    public void getIsCommentedMethod() {
        assertFalse(new Comment().getIsCommented());
        assertTrue(new Comment(VALID_COMMENT).getIsCommented());
    }

    @Test
    public void toStringMethod() {
        assertTrue(COMMENT1.toString().equals("First."));
        assertTrue(new Comment().toString().equals(""));
    }
}
