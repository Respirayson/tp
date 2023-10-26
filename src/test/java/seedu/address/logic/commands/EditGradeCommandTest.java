package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE;
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
import seedu.address.model.person.assignment.Grade;

public class EditGradeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new TaskListBook(),
        new SessionListBook(), new ConsultationListBook(), new GradedTestListBook());

    @Test
    public void execute_editGrade_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Grade actualGrade = new Grade(VALID_GRADE, personToEdit.getAssignment(assignmentName).maxGrade());
        EditGradeCommand editGradeCommand = new EditGradeCommand(targetIndex, assignmentName, actualGrade);
        Person editedPerson = editGradeCommand.createGradedPerson(personToEdit);

        String expectedMessage = String.format(EditGradeCommand.MESSAGE_SUCCESS, VALID_ASSIGNMENT_NAME);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new TaskListBook(), new SessionListBook(),
                new ConsultationListBook(), new GradedTestListBook());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editGradeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAssignment_failure() {
        AssignmentName assignmentName = new AssignmentName(INVALID_ASSIGNMENT_NAME);
        Grade validGrade = new Grade(VALID_GRADE, VALID_GRADE);
        EditGradeCommand editGradeCommand = new EditGradeCommand(INDEX_FIRST_PERSON, assignmentName, validGrade);

        assertCommandFailure(editGradeCommand, model, AssignmentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void execute_gradeNotInteger_failure() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Grade invalidGradeNotInteger = new Grade("hi", VALID_GRADE);
        EditGradeCommand editGradeCommand =
            new EditGradeCommand(INDEX_FIRST_PERSON, assignmentName, invalidGradeNotInteger);

        assertCommandFailure(editGradeCommand, model, Grade.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void execute_gradeLeadingZero_failure() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Grade invalidGradeLeadingZero = new Grade("0200", VALID_GRADE);
        EditGradeCommand editGradeCommand =
                new EditGradeCommand(INDEX_FIRST_PERSON, assignmentName, invalidGradeLeadingZero);

        assertCommandFailure(editGradeCommand, model, Grade.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Grade validGrade = new Grade(VALID_GRADE, VALID_GRADE);
        EditGradeCommand editGradeCommand = new EditGradeCommand(outOfBoundIndex, assignmentName, validGrade);

        assertCommandFailure(editGradeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        Grade validGrade = new Grade(VALID_GRADE, VALID_GRADE);

        EditGradeCommand editGradeCommand = new EditGradeCommand(outOfBoundIndex, assignmentName, validGrade);

        assertCommandFailure(editGradeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals_sameObject_success() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Grade validGrade = new Grade(VALID_GRADE, VALID_GRADE);
        EditGradeCommand editGradeCommand = new EditGradeCommand(INDEX_FIRST_PERSON, assignmentName, validGrade);

        assertEquals(editGradeCommand, editGradeCommand);
    }

    @Test
    public void equals_differentObjectType_failure() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Grade validGrade = new Grade(VALID_GRADE, VALID_GRADE);
        EditGradeCommand editGradeCommand = new EditGradeCommand(INDEX_FIRST_PERSON, assignmentName, validGrade);

        assertFalse(editGradeCommand.equals(assignmentName));
    }

    @Test
    public void equals_differentObjectSameContent_success() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Grade validGrade = new Grade(VALID_GRADE, VALID_GRADE);
        EditGradeCommand editGradeCommandFirst = new EditGradeCommand(INDEX_FIRST_PERSON, assignmentName, validGrade);
        EditGradeCommand editGradeCommandSecond = new EditGradeCommand(INDEX_FIRST_PERSON, assignmentName, validGrade);

        assertEquals(editGradeCommandFirst, editGradeCommandSecond);
    }

    @Test
    public void equals_differentObjectDifferentContent_failure() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Grade validGradeFirst = new Grade(VALID_GRADE, VALID_GRADE);
        Grade validGradeSecond = new Grade(VALID_GRADE);
        EditGradeCommand editGradeCommandFirst =
            new EditGradeCommand(INDEX_FIRST_PERSON, assignmentName, validGradeFirst);
        EditGradeCommand editGradeCommandSecond =
            new EditGradeCommand(INDEX_FIRST_PERSON, assignmentName, validGradeSecond);

        assertFalse(editGradeCommandFirst.equals(editGradeCommandSecond));
    }
}
