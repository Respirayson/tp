package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalConsultations.getTypicalConsultationListBook;
import static seedu.address.testutil.TypicalGradedTest.getTypicalGradedTestList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONSULTATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONSULTATION;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalSessions.getTypicalSessionList;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.consultation.Consultation;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteConsultationCommand}.
 */
public class DeleteConsultationCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTaskList(),
            getTypicalSessionList(), getTypicalConsultationListBook(), getTypicalGradedTestList());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Consultation consultationToDelete = model.getFilteredConsultationList()
                .get(INDEX_FIRST_CONSULTATION.getZeroBased());
        DeleteConsultationCommand deleteConsultationCommand = new DeleteConsultationCommand(INDEX_FIRST_CONSULTATION);

        String expectedMessage = String.format(DeleteConsultationCommand.MESSAGE_DELETE_CONSULTATION_SUCCESS,
                Messages.format(consultationToDelete));

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTaskList(),
                getTypicalSessionList(), getTypicalConsultationListBook(), getTypicalGradedTestList());

        expectedModel.deleteConsultation(consultationToDelete);

        assertCommandSuccess(deleteConsultationCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredConsultationList().size() + 1);
        DeleteConsultationCommand deleteConsultationCommand = new DeleteConsultationCommand(outOfBoundIndex);

        assertCommandFailure(deleteConsultationCommand, model, Messages.MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteConsultationCommand deleteFirstCommand = new DeleteConsultationCommand(INDEX_FIRST_CONSULTATION);
        DeleteConsultationCommand deleteSecondCommand = new DeleteConsultationCommand(INDEX_SECOND_CONSULTATION);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteConsultationCommand deleteFirstCommandCopy = new DeleteConsultationCommand(INDEX_FIRST_CONSULTATION);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different person -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteConsultationCommand deleteConsultationCommand = new DeleteConsultationCommand(targetIndex);
        String expected = DeleteConsultationCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteConsultationCommand.toString());
    }
}
