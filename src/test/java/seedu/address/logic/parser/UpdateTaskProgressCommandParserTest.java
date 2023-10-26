package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_PROGRESS_TASK1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROGRESS_PENDING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateTaskProgressCommand;
import seedu.address.logic.commands.UpdateTaskProgressCommand.EditProgressDescriptor;
import seedu.address.testutil.EditProgressDescriptorBuilder;

/**
 * JUnit test class for the UpdateTaskProgressCommandParser.
 */
public class UpdateTaskProgressCommandParserTest {

    private UpdateTaskProgressCommandParser parser = new UpdateTaskProgressCommandParser();

    @Test
    public void parse_validArgs_returnsCompleteTaskCommand() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + TASK_PROGRESS_TASK1;
        EditProgressDescriptor descriptor =
                new EditProgressDescriptorBuilder().withProgress(VALID_PROGRESS_PENDING).build();
        UpdateTaskProgressCommand expectedCommand = new UpdateTaskProgressCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateTaskProgressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPreamble_throwsParseException() {
        assertParseFailure(parser, TASK_PROGRESS_TASK1, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateTaskProgressCommand.MESSAGE_USAGE));
    }

}
