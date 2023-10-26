package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_400;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_400;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_NOT_INT;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_TOO_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditGradeCommand;
import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.model.person.assignment.Grade;

public class EditGradeCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGradeCommand.MESSAGE_USAGE);
    private EditGradeCommandParser parser = new EditGradeCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ASSIGNMENT_DESC + GRADE_DESC_400, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ASSIGNMENT_DESC + GRADE_DESC_400, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);

        // invalid order of prefix
        assertParseFailure(parser, "0" + GRADE_DESC_400 + ASSIGNMENT_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidAssignment_failure() {
        assertParseFailure(parser,
            "1" + INVALID_ASSIGNMENT_DESC + GRADE_DESC_400, AssignmentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_gradeOutOfRange_failure() {
        assertParseFailure(parser,
            "1" + ASSIGNMENT_DESC + GRADE_DESC_TOO_HIGH, Grade.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_gradeNotInt_failure() {
        assertParseFailure(parser,
            "1" + ASSIGNMENT_DESC + GRADE_DESC_NOT_INT, Grade.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + ASSIGNMENT_DESC + GRADE_DESC_400;
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Grade grade = new Grade(GRADE_400, VALID_GRADE);

        assertParseSuccess(parser, userInput, new EditGradeCommand(targetIndex, assignmentName, grade));
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + ASSIGNMENT_DESC + ASSIGNMENT_DESC + GRADE_DESC_400;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT));
    }
}
