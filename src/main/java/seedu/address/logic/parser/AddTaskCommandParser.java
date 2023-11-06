package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PRIORITY;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_PAST_DATE;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TASK_DESCRIPTION,
                        PREFIX_TASK_PRIORITY, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TASK_NAME, PREFIX_TASK_DESCRIPTION,
                PREFIX_TASK_PRIORITY, PREFIX_DATE);
        TaskName name = ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_TASK_NAME).get());
        TaskDescription taskDescription = ParserUtil.parseTaskDescription(argMultimap
                .getValue(PREFIX_TASK_DESCRIPTION).orElse(""));
        TaskPriority priority = ParserUtil.parseTaskPriority(argMultimap
                .getValue(PREFIX_TASK_PRIORITY).orElse("LOW"));
        LocalDate date;

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

            if (LocalDate.now().until(date, ChronoUnit.DAYS) < 0L) {
                throw new ParseException(MESSAGE_PAST_DATE);
            }

        } else {
            date = null;
        }

        Task task = new Task(name, taskDescription, priority, date);

        return new AddTaskCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
