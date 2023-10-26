package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT1;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT2;
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
import seedu.address.model.person.assignment.AssignmentMap;
import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.testutil.PersonBuilder;



public class DeleteGradeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new TaskListBook(),
            new SessionListBook(), new ConsultationListBook(), new GradedTestListBook());

    @Test
    public void execute_deleteGrade_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        AssignmentMap assignments =
            personToEdit.getAllAssignments().createUpdatedMap(ASSIGNMENT1.getName(), ASSIGNMENT1.getGrade());
        personToEdit = new PersonBuilder(personToEdit, assignments).build();
        model.setPerson(model.getFilteredPersonList().get(0), personToEdit);


        AssignmentName assignmentName = ASSIGNMENT1.getName();
        DeleteGradeCommand deleteGradeCommand = new DeleteGradeCommand(targetIndex, assignmentName);
        Person editedPerson = deleteGradeCommand.createUngradedPerson(personToEdit);

        String expectedMessage = String.format(DeleteGradeCommand.MESSAGE_SUCCESS, assignmentName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new TaskListBook(), new SessionListBook(),
                new ConsultationListBook(), new GradedTestListBook());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(deleteGradeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_ungradedAssignment_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        DeleteGradeCommand deleteGradeCommand = new DeleteGradeCommand(targetIndex, assignmentName);
        Person editedPerson = deleteGradeCommand.createUngradedPerson(personToEdit);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new TaskListBook(), new SessionListBook(),
                new ConsultationListBook(), new GradedTestListBook());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandFailure(deleteGradeCommand, model, DeleteGradeCommand.MESSAGE_CONSTRAINT);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        DeleteGradeCommand deleteGradeCommand = new DeleteGradeCommand(outOfBoundIndex, assignmentName);

        assertCommandFailure(deleteGradeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        DeleteGradeCommand deleteGradeCommand = new DeleteGradeCommand(outOfBoundIndex, assignmentName);

        assertCommandFailure(deleteGradeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equal_sameObject_success() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        DeleteGradeCommand deleteGradeCommand = new DeleteGradeCommand(INDEX_FIRST_PERSON, assignmentName);

        assertEquals(deleteGradeCommand, deleteGradeCommand);
    }

    @Test
    public void equals_differentObjectType_failure() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        DeleteGradeCommand deleteGradeCommand = new DeleteGradeCommand(INDEX_FIRST_PERSON, assignmentName);

        assertFalse(deleteGradeCommand.equals(assignmentName));
    }

    @Test
    public void equals_differentObjectSameContent_success() {
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        DeleteGradeCommand deleteGradeCommandFirst = new DeleteGradeCommand(INDEX_FIRST_PERSON, assignmentName);
        DeleteGradeCommand deleteGradeCommandSecond = new DeleteGradeCommand(INDEX_FIRST_PERSON, assignmentName);

        assertEquals(deleteGradeCommandFirst, deleteGradeCommandSecond);
    }

    @Test
    public void equals_differentObjectDifferentContent_failure() {
        AssignmentName assignmentNameFirst = new AssignmentName(ASSIGNMENT1.getName().toString());
        AssignmentName assignmentNameSecond = new AssignmentName(ASSIGNMENT2.getName().toString());
        DeleteGradeCommand deleteGradeCommandFirst = new DeleteGradeCommand(INDEX_FIRST_PERSON, assignmentNameFirst);
        DeleteGradeCommand deleteGradeCommandSecond = new DeleteGradeCommand(INDEX_FIRST_PERSON, assignmentNameSecond);

        assertFalse(deleteGradeCommandFirst.equals(deleteGradeCommandSecond));
    }
}
