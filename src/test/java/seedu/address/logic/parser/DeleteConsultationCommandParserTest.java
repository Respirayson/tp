package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONSULTATION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteConsultationCommand;

/**
 * JUnit test class for the DeleteConsultationCommandParser.
 */
public class DeleteConsultationCommandParserTest {
    private DeleteConsultationCommandParser parser = new DeleteConsultationCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteConsultationCommand(INDEX_FIRST_CONSULTATION));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteConsultationCommand.MESSAGE_USAGE));
    }
}
