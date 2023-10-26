package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_TOO_LONG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommentCommand;
import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.model.person.assignment.Comment;

public class EditCommentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommentCommand.MESSAGE_USAGE);
    private EditCommentCommandParser parser = new EditCommentCommandParser();

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
        assertParseFailure(parser, "-5" + ASSIGNMENT_DESC + COMMENT_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ASSIGNMENT_DESC + COMMENT_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);

        // invalid order of prefix
        assertParseFailure(parser, "0" + COMMENT_DESC + ASSIGNMENT_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidAssignment_failure() {
        assertParseFailure(parser,
                "1" + INVALID_ASSIGNMENT_DESC + COMMENT_DESC, AssignmentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_commentTooLong_failure() {
        assertParseFailure(parser,
                "1" + ASSIGNMENT_DESC + COMMENT_DESC_TOO_LONG, Comment.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + ASSIGNMENT_DESC + COMMENT_DESC;
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_NAME);
        Comment comment = new Comment(VALID_COMMENT);

        assertParseSuccess(parser, userInput, new EditCommentCommand(targetIndex, assignmentName, comment));
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + ASSIGNMENT_DESC + ASSIGNMENT_DESC + COMMENT_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT));
    }
}
