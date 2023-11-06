package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.TASK1;
import static seedu.address.testutil.TypicalTasks.TASK2;
import static seedu.address.testutil.TypicalTasks.TASK3;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.ConsultationListBook;
import seedu.address.model.GradedTestListBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SessionListBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.DateContainsKeywordsPredicate;
import seedu.address.model.task.TaskDescriptionContainsKeywordsPredicate;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;
import seedu.address.model.task.TaskPriorityContainsKeywordsPredicate;
import seedu.address.model.task.TaskProgressContainsKeywordsPredicate;


/**
 * Contains integration tests (interaction with the Model) for {@code ViewTasksCommand}.
 */
public class ViewTasksCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalTaskList(),
            new SessionListBook(), new ConsultationListBook(), new GradedTestListBook());
    private Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalTaskList(),
            new SessionListBook(), new ConsultationListBook(), new GradedTestListBook());

    @Test
    public void equals() {
        TaskNameContainsKeywordsPredicate firstPredicate =
                new TaskNameContainsKeywordsPredicate(Collections.singletonList("first"));
        TaskNameContainsKeywordsPredicate secondPredicate =
                new TaskNameContainsKeywordsPredicate(Collections.singletonList("second"));

        ViewTasksCommand firstCommand = new ViewTasksCommand(firstPredicate);
        ViewTasksCommand secondCommand = new ViewTasksCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        ViewTasksCommand firstCommandCopy = new ViewTasksCommand(firstPredicate);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different task -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_zeroKeywordsTaskName_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskNameContainsKeywordsPredicate predicate = prepareTaskNamePredicate(" ");
        ViewTasksCommand command = new ViewTasksCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_zeroKeywordsTaskDescription_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskDescriptionContainsKeywordsPredicate predicate = prepareTaskDescPredicate(" ");
        ViewTasksCommand command = new ViewTasksCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_zeroKeywordsTaskProgress_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskProgressContainsKeywordsPredicate predicate = prepareTaskProgressPredicate(" ");
        ViewTasksCommand command = new ViewTasksCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_zeroKeywordsTaskPriority_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskPriorityContainsKeywordsPredicate predicate = prepareTaskPriorityPredicate(" ");
        ViewTasksCommand command = new ViewTasksCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_zeroKeywordsDate_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        DateContainsKeywordsPredicate predicate = prepareDatePredicate(" ");
        ViewTasksCommand command = new ViewTasksCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywordsTaskName_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        TaskNameContainsKeywordsPredicate predicate = prepareTaskNamePredicate("cs2103t quant cs3233");
        ViewTasksCommand command = new ViewTasksCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TASK1, TASK2, TASK3), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywordsTaskDesc_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        TaskDescriptionContainsKeywordsPredicate predicate = prepareTaskDescPredicate("prs book");
        ViewTasksCommand command = new ViewTasksCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TASK1, TASK2, TASK3), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywordsTaskProgress_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 2);
        TaskProgressContainsKeywordsPredicate predicate = prepareTaskProgressPredicate("done not_started");
        ViewTasksCommand command = new ViewTasksCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TASK1, TASK2), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywordsTaskPriority_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        TaskPriorityContainsKeywordsPredicate predicate = prepareTaskPriorityPredicate("low medium");
        ViewTasksCommand command = new ViewTasksCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TASK1, TASK2, TASK3), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywordsDate_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 2);
        DateContainsKeywordsPredicate predicate = prepareDatePredicate("22/10/2024");
        ViewTasksCommand command = new ViewTasksCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TASK1, TASK3), model.getFilteredTaskList());
    }

    @Test
    public void getCOmmandType() {
        TaskNameContainsKeywordsPredicate firstPredicate =
                new TaskNameContainsKeywordsPredicate(Collections.singletonList("first"));
        ViewTasksCommand command = new ViewTasksCommand(firstPredicate);

        assertEquals(command.getCommandType(), CommandType.VIEW_TASKS);
    }

    @Test
    public void toStringMethod() {
        TaskNameContainsKeywordsPredicate predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("keyword"));
        ViewTasksCommand viewTasksCommand = new ViewTasksCommand(predicate);
        String expected = ViewTasksCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewTasksCommand.toString());

        TaskDescriptionContainsKeywordsPredicate descPredicate =
                new TaskDescriptionContainsKeywordsPredicate(Arrays.asList("keyword"));
        ViewTasksCommand viewTasksDescCommand = new ViewTasksCommand(descPredicate);
        String expectedDesc = ViewTasksCommand.class.getCanonicalName() + "{predicate=" + descPredicate + "}";
        assertEquals(expectedDesc, viewTasksDescCommand.toString());

        TaskProgressContainsKeywordsPredicate progressPredicate =
                new TaskProgressContainsKeywordsPredicate(Arrays.asList("keyword"));
        ViewTasksCommand viewTasksProgressCommand = new ViewTasksCommand(progressPredicate);
        String expectedProgress = ViewTasksCommand.class.getCanonicalName() + "{predicate=" + progressPredicate + "}";
        assertEquals(expectedProgress, viewTasksProgressCommand.toString());

        TaskPriorityContainsKeywordsPredicate priorityPredicate =
                new TaskPriorityContainsKeywordsPredicate(Arrays.asList("keyword"));
        ViewTasksCommand viewTasksPriorityCommand = new ViewTasksCommand(priorityPredicate);
        String expectedPriority = ViewTasksCommand.class.getCanonicalName() + "{predicate=" + priorityPredicate + "}";
        assertEquals(expectedPriority, viewTasksPriorityCommand.toString());

        DateContainsKeywordsPredicate datePredicate = new DateContainsKeywordsPredicate(Arrays.asList("keyword"));
        ViewTasksCommand viewTasksDateCommand = new ViewTasksCommand(datePredicate);
        String expectedDate = ViewTasksCommand.class.getCanonicalName() + "{predicate=" + datePredicate + "}";
        assertEquals(expectedDate, viewTasksDateCommand.toString());

    }

    /**
     * Parses {@code userInput} into a {@code TaskNameContainsKeywordsPredicate}.
     */
    private TaskNameContainsKeywordsPredicate prepareTaskNamePredicate(String userInput) {
        return new TaskNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TaskDescriptionContainsKeywordsPredicate}.
     */
    private TaskDescriptionContainsKeywordsPredicate prepareTaskDescPredicate(String userInput) {
        return new TaskDescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TaskProgressContainsKeywordsPredicate}.
     */
    private TaskProgressContainsKeywordsPredicate prepareTaskProgressPredicate(String userInput) {
        return new TaskProgressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TaskPriorityContainsKeywordsPredicate}.
     */
    private TaskPriorityContainsKeywordsPredicate prepareTaskPriorityPredicate(String userInput) {
        return new TaskPriorityContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code DateContainsKeywordsPredicate}.
     */
    private DateContainsKeywordsPredicate prepareDatePredicate(String userInput) {
        return new DateContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
