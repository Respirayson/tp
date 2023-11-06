package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.TASK_PRIORITY_TASK1;
import static seedu.address.logic.commands.ViewTasksCommand.MESSAGE_MANY_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PROGRESS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewTasksCommand;
import seedu.address.model.task.DateContainsKeywordsPredicate;
import seedu.address.model.task.TaskDescriptionContainsKeywordsPredicate;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;
import seedu.address.model.task.TaskPriorityContainsKeywordsPredicate;
import seedu.address.model.task.TaskProgressContainsKeywordsPredicate;

public class ViewTasksCommandParserTest {

    private ViewTasksCommandParser parser = new ViewTasksCommandParser();

    @Test
    public void parse_emptyArg_success() {
        ViewTasksCommand expectedCommand = new ViewTasksCommand(PREDICATE_SHOW_ALL_TASKS);
        assertParseSuccess(parser, "     ", expectedCommand);
    }

    @Test
    public void parse_validTaskNameArgs_returnsViewTasksCommand() {
        // no leading and trailing whitespaces
        ViewTasksCommand expectedCommand =
                new ViewTasksCommand(new TaskNameContainsKeywordsPredicate(Arrays.asList("cs2103t", "quant")));
        assertParseSuccess(parser, " " + PREFIX_TASK_NAME + "cs2103t quant", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_TASK_NAME + "cs2103t \n \t quant", expectedCommand);
    }

    @Test
    public void parse_validTaskDescArgs_returnsViewTasksCommand() {
        // no leading and trailing whitespaces
        ViewTasksCommand expectedCommand =
                new ViewTasksCommand(new TaskDescriptionContainsKeywordsPredicate(Arrays.asList("prs", "book")));
        assertParseSuccess(parser, " " + PREFIX_TASK_DESCRIPTION + "prs book", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_TASK_DESCRIPTION + "prs \t book", expectedCommand);
    }

    @Test
    public void parse_validTaskProgressArgs_returnsViewTasksCommand() {
        // no leading and trailing whitespaces
        ViewTasksCommand expectedCommand =
                new ViewTasksCommand(new TaskProgressContainsKeywordsPredicate(Arrays.asList("DONE", "PENDING")));
        assertParseSuccess(parser, " " + PREFIX_TASK_PROGRESS + "done pending", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_TASK_PROGRESS + "done \n \t pending", expectedCommand);
    }

    @Test
    public void parse_validTaskPriorityArgs_returnsViewTasksCommand() {
        // no leading and trailing whitespaces
        ViewTasksCommand expectedCommand =
                new ViewTasksCommand(new TaskPriorityContainsKeywordsPredicate(Arrays.asList("LOW", "HIGH")));
        assertParseSuccess(parser, " " + PREFIX_TASK_PRIORITY + "low high", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_TASK_PRIORITY + "low \n \t high", expectedCommand);
    }

    @Test
    public void parse_validDateArgs_returnsViewTasksCommand() {
        // no leading and trailing whitespaces
        ViewTasksCommand expectedCommand =
                new ViewTasksCommand(new DateContainsKeywordsPredicate(Arrays.asList("22/10/2023")));
        assertParseSuccess(parser, " " + PREFIX_DATE + "22/10/2023", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_DATE + "22/10/2023 \n \t", expectedCommand);
    }

    @Test
    public void parse_manyPrefixes_throwsParseException() {
        assertParseFailure(parser, TASK_DESCRIPTION_TASK2 + TASK_PRIORITY_TASK1, MESSAGE_MANY_PREFIXES);
    }

}
