package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.assignment.AssignmentMap;
import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.model.person.assignment.Grade;

/**
 * Parses input arguments and creates a new EditGradeCommand object
 */
public class EditGradeCommandParser implements Parser<EditGradeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditGradeCommand
     * and returns an EditGradeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT, PREFIX_GRADE);

        if (!arePrefixesPresent(argMultimap, PREFIX_ASSIGNMENT, PREFIX_GRADE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGradeCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGradeCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT, PREFIX_GRADE);
        AssignmentName assignmentName = ParserUtil.parseAssignmentName(argMultimap.getValue(PREFIX_ASSIGNMENT).get());
        AssignmentMap assignmentMapReference = new AssignmentMap();
        String maxGrade = assignmentMapReference.get(assignmentName).maxGrade();
        Grade grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get(), maxGrade);
        return new EditGradeCommand(index, assignmentName, grade);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
