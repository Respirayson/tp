package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.ConsultationListBook;
import seedu.address.model.GradedTestListBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SessionListBook;
import seedu.address.model.TaskListBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.model.person.assignment.Comment;

public class EditCommentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new TaskListBook(),
            new SessionListBook(), new ConsultationListBook(), new GradedTestListBook());

    @Test
    public void execute_editComment_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Comment comment = new Comment(VALID_COMMENT);
        EditCommentCommand editCommentCommand = new EditCommentCommand(targetIndex, assignmentName, comment);
        Person editedPerson = editCommentCommand.createCommentedPerson(personToEdit);

        String expectedMessage = String.format(EditCommentCommand.MESSAGE_SUCCESS, VALID_ASSIGNMENT_NAME);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new TaskListBook(), new SessionListBook(),
                new ConsultationListBook(), new GradedTestListBook());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAssignment_failure() {
        AssignmentName assignmentName = new AssignmentName(INVALID_ASSIGNMENT_NAME);
        Comment comment = new Comment(VALID_COMMENT);
        EditCommentCommand editCommentCommand = new EditCommentCommand(INDEX_FIRST_PERSON, assignmentName, comment);

        assertCommandFailure(editCommentCommand, model, AssignmentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Comment comment = new Comment(VALID_COMMENT);
        EditCommentCommand editCommentCommand = new EditCommentCommand(outOfBoundIndex, assignmentName, comment);

        assertCommandFailure(editCommentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Comment comment = new Comment(VALID_COMMENT);

        EditCommentCommand editCommentCommand = new EditCommentCommand(outOfBoundIndex, assignmentName, comment);

        assertCommandFailure(editCommentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals_sameObject_success() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Comment comment = new Comment(VALID_COMMENT);
        EditCommentCommand editCommentCommand = new EditCommentCommand(INDEX_FIRST_PERSON, assignmentName, comment);

        assertEquals(editCommentCommand, editCommentCommand);
    }

    @Test
    public void equals_differentObjectType_failure() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Comment comment = new Comment(VALID_COMMENT);
        EditCommentCommand editCommentCommand = new EditCommentCommand(INDEX_FIRST_PERSON, assignmentName, comment);

        assertFalse(editCommentCommand.equals(5));
    }

    @Test
    public void equals_differentObjectSameContent_success() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Comment comment = new Comment(VALID_COMMENT);
        EditCommentCommand editCommentCommandFirst =
            new EditCommentCommand(INDEX_FIRST_PERSON, assignmentName, comment);
        EditCommentCommand editCommentCommandSecond =
                new EditCommentCommand(INDEX_FIRST_PERSON, assignmentName, comment);

        assertEquals(editCommentCommandFirst, editCommentCommandSecond);
    }

    @Test
    public void equals_differentObjectDifferentContent_failure() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Comment comment = new Comment(VALID_COMMENT);
        Comment otherComment = new Comment("hi");
        EditCommentCommand editCommentCommandFirst =
                new EditCommentCommand(INDEX_FIRST_PERSON, assignmentName, comment);
        EditCommentCommand editCommentCommandSecond =
                new EditCommentCommand(INDEX_FIRST_PERSON, assignmentName, otherComment);

        assertFalse(editCommentCommandFirst.equals(editCommentCommandSecond));
    }
}
