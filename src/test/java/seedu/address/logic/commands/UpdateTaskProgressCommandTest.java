package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROGRESS_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalConsultations.getTypicalConsultationListBook;
import static seedu.address.testutil.TypicalGradedTest.getTypicalGradedTestList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalSessions.getTypicalSessionList;
import static seedu.address.testutil.TypicalTasks.TASK1;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateTaskProgressCommand.EditProgressDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskListBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditProgressDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UpdateTaskProgressCommand}.
 */
public class UpdateTaskProgressCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
            getTypicalTaskList(), getTypicalSessionList(),
            getTypicalConsultationListBook(), getTypicalGradedTestList());

    @Test
    public void execute_unfilteredList_success() {
        Task editedTask = new TaskBuilder(TASK1).build();
        EditProgressDescriptor descriptor = new EditProgressDescriptorBuilder(editedTask).build();
        UpdateTaskProgressCommand editCommand = new UpdateTaskProgressCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(UpdateTaskProgressCommand.MESSAGE_MARK_TASK_SUCCESS,
                Messages.format(editedTask));

        Model expectedModel = new ModelManager(getTypicalAddressBook(),
                new UserPrefs(), new TaskListBook(model.getTaskList()), getTypicalSessionList(),
                getTypicalConsultationListBook(), getTypicalGradedTestList());

        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateTaskProgressCommand editCommand = new UpdateTaskProgressCommand(INDEX_FIRST_TASK,
                new EditProgressDescriptor());
        Task editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(UpdateTaskProgressCommand.MESSAGE_MARK_TASK_SUCCESS,
                Messages.format(editedTask));

        Model expectedModel = new ModelManager(getTypicalAddressBook(),
                new UserPrefs(), new TaskListBook(model.getTaskList()), getTypicalSessionList(),
                getTypicalConsultationListBook(), getTypicalGradedTestList());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        UpdateTaskProgressCommand updateTaskProgressCommand = new UpdateTaskProgressCommand(outOfBoundIndex,
                new EditProgressDescriptorBuilder().withProgress(VALID_PROGRESS_PENDING).build());

        assertCommandFailure(updateTaskProgressCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of task list
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of task list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskList().getTaskList().size());

        UpdateTaskProgressCommand editCommand = new UpdateTaskProgressCommand(outOfBoundIndex,
                new EditProgressDescriptorBuilder().withProgress(VALID_PROGRESS_PENDING).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UpdateTaskProgressCommand updateFirstCommand = new UpdateTaskProgressCommand(INDEX_FIRST_TASK, DESC_TASK);

        EditProgressDescriptor copyDescriptor = new EditProgressDescriptor(DESC_TASK);
        UpdateTaskProgressCommand commandWithSameValues =
                new UpdateTaskProgressCommand(INDEX_FIRST_TASK, copyDescriptor);

        // same object -> returns true
        assertTrue(updateFirstCommand.equals(updateFirstCommand));

        // same values -> returns true
        assertTrue(updateFirstCommand.equals(commandWithSameValues));

        // different types -> returns false
        assertFalse(updateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(updateFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(updateFirstCommand.equals(new UpdateTaskProgressCommand(INDEX_FIRST_TASK, DESC_TASK2)));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        EditProgressDescriptor descriptor = new EditProgressDescriptor();
        UpdateTaskProgressCommand updateTaskProgressCommand =
                new UpdateTaskProgressCommand(targetIndex, descriptor);
        String expected = UpdateTaskProgressCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", editProgressDescriptor="
                + descriptor + "}";
        assertEquals(expected, updateTaskProgressCommand.toString());
    }
}
