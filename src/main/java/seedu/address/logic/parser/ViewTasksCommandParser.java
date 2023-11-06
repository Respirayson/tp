package seedu.address.logic.parser;

import static seedu.address.logic.commands.ViewTasksCommand.MESSAGE_MANY_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PROGRESS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.ViewTasksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.DateContainsKeywordsPredicate;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescriptionContainsKeywordsPredicate;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;
import seedu.address.model.task.TaskPriorityContainsKeywordsPredicate;
import seedu.address.model.task.TaskProgressContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ViewTasksCommandParser implements Parser<ViewTasksCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewTasksCommand
     * and returns a ViewTasksCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTasksCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME,
                        PREFIX_TASK_DESCRIPTION, PREFIX_TASK_PRIORITY, PREFIX_TASK_PROGRESS, PREFIX_DATE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TASK_NAME, PREFIX_TASK_DESCRIPTION,
                PREFIX_TASK_PRIORITY, PREFIX_TASK_PROGRESS, PREFIX_DATE);

        if (argMultimap.getCountOfPrefixes() > 2) {
            throw new ParseException(MESSAGE_MANY_PREFIXES);
        }

        Predicate<Task> predicate = PREDICATE_SHOW_ALL_TASKS;

        if (argMultimap.getValue(PREFIX_TASK_NAME).isPresent()) {
            String[] taskNameKeywords = ParserUtil.parseTaskName(
                    argMultimap.getValue(PREFIX_TASK_NAME).get()).taskName.split("\\s+");
            predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList(taskNameKeywords));
        }

        if (argMultimap.getValue(PREFIX_TASK_DESCRIPTION).isPresent()) {
            String[] taskDescKeywords = ParserUtil.parseTaskDescription(
                    argMultimap.getValue(PREFIX_TASK_DESCRIPTION).get()).description.split("\\s+");
            predicate = new TaskDescriptionContainsKeywordsPredicate(Arrays.asList(taskDescKeywords));
        }

        if (argMultimap.getValue(PREFIX_TASK_PRIORITY).isPresent()) {
            String[] taskPriorityKeywords = argMultimap.getValue(PREFIX_TASK_PRIORITY).get().split("\\s+");

            List<String> checkedTaskPriorityKeywords = new ArrayList<>();
            for (String str : taskPriorityKeywords) {
                checkedTaskPriorityKeywords.add(ParserUtil.parseTaskPriority(str).name());
            }

            predicate = new TaskPriorityContainsKeywordsPredicate(checkedTaskPriorityKeywords);
        }

        if (argMultimap.getValue(PREFIX_TASK_PROGRESS).isPresent()) {
            String[] taskProgressKeywords = argMultimap.getValue(PREFIX_TASK_PROGRESS).get().split("\\s+");

            List<String> checkedTaskProgressKeywords = new ArrayList<>();
            for (String str : taskProgressKeywords) {
                checkedTaskProgressKeywords.add(ParserUtil.parseTaskProgress(str).name());
            }

            predicate = new TaskProgressContainsKeywordsPredicate(checkedTaskProgressKeywords);
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            String[] dateKeywords = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get())
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).split("\\s+");
            predicate = new DateContainsKeywordsPredicate(Arrays.asList(dateKeywords));
        }

        return new ViewTasksCommand(predicate);
    }

}
