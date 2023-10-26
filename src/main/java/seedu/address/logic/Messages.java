package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.gradedtest.GradedTest;
import seedu.address.model.person.Person;
import seedu.address.model.session.Session;
import seedu.address.model.task.Task;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_DATE_TIME = "The date or time provided is invalid";
    public static final String MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX =
            "The consultation index provided is invalid";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_ASSIGNMENT_GRADED = "The assignment has already been graded";
    public static final String MESSAGE_ASSIGNMENT_UNGRADED = "The assignment has not been graded";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        builder.append("; Graded Tests: ");
        person.getGradedTest().forEach(gradedTest -> builder.append(person.getGradedTest()).append(", "));
        return builder.toString();
    }

    /**
     * Formats the {@code consultation} for display to the user.
     */
    public static String format(Consultation consultation) {
        final StringBuilder builder = new StringBuilder();
        builder.append("; Date: ")
                .append(consultation.getDate())
                .append("; Time: ")
                .append(consultation.getTime())
                .append("; Students: ");
        consultation.getStudents().forEach(student -> builder.append(student.getName()));
        return builder.toString();
    }

    /**
     * Formats the {@code session} for display to the user.
     */
    public static String format(Session session) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Session: ")
                .append(session.getSessionNumber())
                .append(";");
        session.getStudents().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code task} for display to the user.
     */
    public static String format(Task task) {
        return task.getName()
                + "; Description: "
                + task.getDescription()
                + "; Priority: "
                + task.getPriority()
                + "; Date: "
                + task.getDate()
                + "; Progress: "
                + task.getProgress();
    }

    /**
     * Formats the {@code gradedTest} for display to the user.
     */
    public static String format(GradedTest gradedTest) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Graded Test:")
                .append("; Reading Assessment 1: ").append(gradedTest.getRA1())
                .append("; Reading Assessment 2: ").append(gradedTest.getRA2())
                .append("; MidTerms: ").append(gradedTest.getMidTerms())
                .append("; Final: ").append(gradedTest.getFinals())
                .append("; Practical Exam: ").append(gradedTest.getPracticalExam());
        return builder.toString();
    }
}
