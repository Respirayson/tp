package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINALS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADED_TEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIDTERMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRACTICAL_EXAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_READING_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PROGRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditProgressDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_GT_RA1 = "1.0";
    public static final String VALID_GT_RA2 = "2";
    public static final String VALID_GT_MIDTERMS = "3";
    public static final String VALID_GT_FINALS = "4";
    public static final String VALID_GT_PE = "5";
    public static final String VALID_GRADED_TEST_1 =
            "Reading Assessment 1:- | Reading Assessment 2:- | MidTerms:3 | Finals:4 | Practical Exam:5";
    public static final String VALID_GRADED_TEST_2 =
            "Reading Assessment 1:100 | Reading Assessment 2:100 | MidTerms:100 | Finals:100 | Practical Exam:100";
    public static final String VALID_TASK_NAME = "Do cs2103t";
    public static final String VALID_TASK_DESCRIPTION = "Complete PRS";
    public static final String VALID_ASSIGNMENT_NAME = "Finding ELDRIC";
    public static final String INVALID_ASSIGNMENT_NAME = "Finding BOYD";
    public static final String VALID_PROGRESS_PENDING = "PENDING";
    public static final String VALID_PROGRESS_DONE = "DONE";
    public static final String VALID_GRADE = "1200";
    public static final String GRADE_TOO_HIGH = "3000000";
    public static final String GRADE_NOT_INT = "haha";
    public static final String GRADE_400 = "400";
    public static final String VALID_COMMENT = "Great job!";
    public static final String COMMENT_LENGTH_30 = "dddddddddddddddddddddddddddddd";
    public static final String INVALID_COMMENT = COMMENT_LENGTH_30 + COMMENT_LENGTH_30 + COMMENT_LENGTH_30
            + COMMENT_LENGTH_30 + COMMENT_LENGTH_30 + COMMENT_LENGTH_30 + COMMENT_LENGTH_30;
    public static final String VALID_DATE = "11/11/2023";
    public static final String VALID_TIME = "11:11";

    public static final LocalDate VALID_DATE_OBJ = LocalDate.parse(VALID_DATE,
            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    public static final LocalTime VALID_TIME_OBJ = LocalTime.parse(VALID_TIME,
            DateTimeFormatter.ofPattern("HH:mm"));

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String GT_DESC_DEFAULT = " " + PREFIX_GRADED_TEST + VALID_GRADED_TEST_1;
    public static final String GT_DESC_100 = " " + PREFIX_GRADED_TEST + VALID_GRADED_TEST_2;
    public static final String GT_DESC_RA1 = " " + PREFIX_READING_ASSESSMENT + VALID_GT_RA1;
    public static final String GT_DESC_RA2 = " " + PREFIX_READING_ASSESSMENT + VALID_GT_RA2;
    public static final String GT_DESC_MIDTERMS = " " + PREFIX_MIDTERMS + VALID_GT_MIDTERMS;
    public static final String GT_DESC_FINALS = " " + PREFIX_FINALS + VALID_GT_FINALS;
    public static final String GT_DESC_PE = " " + PREFIX_PRACTICAL_EXAM + VALID_GT_PE;
    public static final String ASSIGNMENT_DESC = " " + PREFIX_ASSIGNMENT + "Finding ELDRIC";
    public static final String GRADE_DESC_400 = " " + PREFIX_GRADE + GRADE_400;
    public static final String GRADE_DESC_NOT_INT = " " + PREFIX_GRADE + GRADE_NOT_INT;
    public static final String GRADE_DESC_TOO_HIGH = " " + PREFIX_GRADE + GRADE_TOO_HIGH;
    public static final String COMMENT_DESC = " " + PREFIX_COMMENT + VALID_COMMENT;
    public static final String COMMENT_DESC_TOO_LONG = " " + PREFIX_COMMENT + INVALID_COMMENT;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_GT_RA_DESC = "-1";
    public static final String INVALID_GT_MIDTERMS_DESC = "wergwrg"; // only numerics
    public static final String INVALID_GT_FINALS_DESC = "-43"; // no negative numbers
    public static final String INVALID_GT_PE_DESC = "%#&@%$^@#"; // no special symbols
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_ASSIGNMENT_DESC = " " + PREFIX_ASSIGNMENT + "Finding BOYD";

    public static final String INVALID_GRADED_TEST_DESC_1 = "Reading Assessment 1:-1 | Reading Assessment 2:0 "
            + "| MidTerms:0 | Finals:-1 | Practical Exam:0"; // No negative scores
    public static final String INVALID_GRADED_TEST_DESC_2 = "Reading Assessment 1:0 | Reading Assessment 2:0 "
            + "| MidTerms:0 | Finals:0 | Practical Exam:*"; // No special char * allowed
    public static final String TASK_NAME_TASK1 = " " + PREFIX_TASK_NAME + VALID_TASK_NAME;
    public static final String TASK_DESCRIPTION_TASK1 = " " + PREFIX_TASK_DESCRIPTION + VALID_TASK_DESCRIPTION;
    public static final String TASK_NAME_TASK2 = " " + PREFIX_TASK_NAME + "Read quant guide";
    public static final String TASK_DESCRIPTION_TASK2 = " " + PREFIX_TASK_DESCRIPTION + "The green book";
    public static final String TASK_PROGRESS_TASK1 = " " + PREFIX_TASK_PROGRESS + VALID_PROGRESS_PENDING;
    public static final String DATE_TASK = " " + PREFIX_DATE + "22/10/2023";
    public static final String TASK_PRIORITY_TASK1 = " " + PREFIX_TASK_PRIORITY + "low";
    public static final String TASK_PRIORITY_TASK2 = " " + PREFIX_TASK_PRIORITY + "high";
    public static final String INVALID_TASK_NAME = " " + PREFIX_TASK_NAME + "@@@@HER"; // '@' not allowed in name

    public static final String GRADED_TEST_1 = " " + PREFIX_GRADED_TEST + VALID_GRADED_TEST_1;
    public static final String GRADED_TEST_2 = " " + PREFIX_GRADED_TEST + VALID_GRADED_TEST_2;
    public static final String INVALID_GRADED_TEST_1 = " " + PREFIX_GRADED_TEST + INVALID_GRADED_TEST_DESC_1;
    public static final String INVALID_GRADED_TEST_2 = " " + PREFIX_GRADED_TEST + INVALID_GRADED_TEST_DESC_2;
    public static final String INVALID_RA_TEST = " " + PREFIX_READING_ASSESSMENT + INVALID_GT_RA_DESC;
    public static final String INVALID_MIDTERMS_TEST = " " + PREFIX_MIDTERMS + INVALID_GT_MIDTERMS_DESC;
    public static final String INVALID_FINALS_TEST = " " + PREFIX_FINALS + INVALID_GT_FINALS_DESC;
    public static final String INVALID_PE_TEST = " " + PREFIX_PRACTICAL_EXAM + INVALID_GT_PE_DESC;

    public static final String INVALID_TASK_DESCRIPTION = " "
            + PREFIX_TASK_DESCRIPTION + "ssssssssssssssssssssssssssssssssssssssssssssssssssssss"
            + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssss"; // more than 100 chars not allowed
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "1/1/2002";
    public static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "0:30";

    public static final String VALID_DATE_DESC = " " + PREFIX_DATE + VALID_DATE;
    public static final String VALID_TIME_DESC = " " + PREFIX_TIME + VALID_TIME;
    public static final String SESSION_NUMBER_SESSION1 = " " + PREFIX_SESSION + "1";
    public static final String INVALID_SESSION_NUMBER = " " + PREFIX_SESSION + "abc";
    public static final String SESSION_STUDENTS_STUDENTS1 = " " + PREFIX_NAME + "Bob";
    public static final String INVALID_SESSION_STUDENTS = " " + PREFIX_NAME + "Charlie123@abc";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).withGradedTest(VALID_GRADED_TEST_1).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withGradedTest(VALID_GRADED_TEST_2).build();
    }
    public static final UpdateTaskProgressCommand.EditProgressDescriptor DESC_TASK;
    public static final UpdateTaskProgressCommand.EditProgressDescriptor DESC_TASK2;

    static {
        DESC_TASK = new EditProgressDescriptorBuilder().withProgress(VALID_PROGRESS_PENDING).build();
        DESC_TASK2 = new EditProgressDescriptorBuilder().withProgress(VALID_PROGRESS_DONE).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s task list.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().taskName.split("\\s+");
        model.updateFilteredTaskList(new TaskNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }
}
