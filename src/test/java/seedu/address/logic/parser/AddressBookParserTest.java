package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSION;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONSULTATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CreateConsultCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteConsultationCommand;
import seedu.address.logic.commands.DeleteGradeCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditGradeCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UpdateTaskProgressCommand;
import seedu.address.logic.commands.UpdateTaskProgressCommand.EditProgressDescriptor;
import seedu.address.logic.commands.session.CreateSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditProgressDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        descriptor.setGradedTest(null);
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));

        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_createConsultation() throws Exception {
        String inputCommandString = CreateConsultCommand.COMMAND_WORD + VALID_DATE_DESC + VALID_TIME_DESC
                + NAME_DESC_AMY;
        assertTrue(parser.parseCommand(inputCommandString) instanceof CreateConsultCommand);
    }

    @Test
    public void parseCommand_deleteConsultation() throws Exception {
        DeleteConsultationCommand command = (DeleteConsultationCommand) parser.parseCommand(
                DeleteConsultationCommand.COMMAND_WORD + " " + INDEX_FIRST_CONSULTATION.getOneBased());
        assertEquals(new DeleteConsultationCommand(INDEX_FIRST_CONSULTATION), command);
    }

    @Test
    public void parseCommand_addTask() throws Exception {
        Task task = new TaskBuilder().build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(TaskUtil.getAddCommand(task));
        assertEquals(new AddTaskCommand(task), command);
    }

    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_completeTask() throws Exception {
        Task task = new TaskBuilder().build();
        EditProgressDescriptor descriptor = new EditProgressDescriptorBuilder(task).build();
        UpdateTaskProgressCommand command = (UpdateTaskProgressCommand) parser
                .parseCommand(UpdateTaskProgressCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_TASK.getOneBased()
                        + " " + TaskUtil.getUpdateProgressDetails(descriptor));

        assertEquals(new UpdateTaskProgressCommand(INDEX_FIRST_TASK, descriptor), command);
    }

    @Test
    public void parseCommand_createSession() throws Exception {
        String sessionNumber = "1";
        String studentName = "Ding Han";
        String whiteSpace = " ";
        assertTrue(parser.parseCommand(CreateSessionCommand.COMMAND_WORD
                + whiteSpace + PREFIX_SESSION + sessionNumber
                + whiteSpace + PREFIX_NAME + studentName) instanceof CreateSessionCommand);
    }

    @Test
    public void parseCommand_editGrade() throws Exception {
        String personIndex = "1";
        String assignmentName = "Finding ELDRIC";
        String grade = "800";
        String whiteSpace = " ";
        assertTrue(parser.parseCommand(EditGradeCommand.COMMAND_WORD
                + whiteSpace + personIndex
                + whiteSpace + PREFIX_ASSIGNMENT + assignmentName
                + whiteSpace + PREFIX_GRADE + grade) instanceof EditGradeCommand);
    }

    @Test
    public void parseCommand_deleteGrade() throws Exception {
        String personIndex = "1";
        String assignmentName = "Finding ELDRIC";
        String whiteSpace = " ";
        assertTrue(parser.parseCommand(DeleteGradeCommand.COMMAND_WORD
                + whiteSpace + personIndex
                + whiteSpace + PREFIX_ASSIGNMENT + assignmentName) instanceof DeleteGradeCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
