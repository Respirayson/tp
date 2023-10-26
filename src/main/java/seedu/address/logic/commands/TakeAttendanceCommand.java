package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE_PRESENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSION;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendancePresence;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.session.Session;
import seedu.address.model.session.SessionNumber;
import seedu.address.model.session.SessionStudents;

/**
 * Represents a command for taking attendance of one or more students in a session.
 * The attendance status of each student is updated based on the input provided.
 */
public class TakeAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "takeattendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Takes the attendance of student(s). "
            + "Parameters: "
            + PREFIX_SESSION + "SESSION_NUMBER "
            + "[" + PREFIX_NAME + "NAME]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SESSION + "3 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NAME + "Foo Bar "
            + PREFIX_ATTENDANCE_PRESENCE + "present";
    public static final String MESSAGE_SUCCESS = "Attendance taken";

    private SessionNumber sessionNumber;
    private Name name;
    private Set<Name> names;
    private SessionStudents students;
    private Session session;
    private AttendancePresence attendancePresence;

    /**
     * Creates a `TakeAttendanceCommand` to take attendance for a single student.
     *
     * @param sessionNumber The session number to take attendance in.
     * @param name The name of the student.
     * @param attendancePresence The attendance status of the student (present/absent).
     */
    public TakeAttendanceCommand(SessionNumber sessionNumber, Name name, AttendancePresence attendancePresence) {
        requireAllNonNull(sessionNumber, name, attendancePresence);

        this.sessionNumber = sessionNumber;
        this.name = name;
        this.attendancePresence = attendancePresence;
    }

    /**
     * Creates a `TakeAttendanceCommand` to take attendance for multiple students.
     *
     * @param sessionNumber The session number to take attendance in.
     * @param names The names of the students.
     * @param attendancePresence The attendance status of the students (present/absent).
     */
    public TakeAttendanceCommand(SessionNumber sessionNumber, Set<Name> names, AttendancePresence attendancePresence) {
        requireAllNonNull(sessionNumber, names);

        this.sessionNumber = sessionNumber;
        this.names = names;
        this.attendancePresence = attendancePresence;
    }

    /**
     * Executes the `TakeAttendanceCommand` to take the attendance of students in the model.
     *
     * @param model The model to execute the command on.
     * @return A `CommandResult` containing the success message.
     * @throws CommandException If an error occurs during execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        this.session = model.findSessionBySessionNumber(sessionNumber);
        this.students = new SessionStudents();

        if (name != null) {
            // Get the student to add to the session
            Person student = model.getMatchingStudentName(name);
            this.students.add(student);
            if (attendancePresence.equals(AttendancePresence.PRESENT)) {
                student.attendSession(this.session);
            } else {
                student.missSession(this.session);
            }
        }

        if (names != null && !names.isEmpty()) {
            for (Name name : names) {
                Person student = model.getMatchingStudentName(name);
                this.students.add(student);
                if (attendancePresence.equals(AttendancePresence.PRESENT)) {
                    student.attendSession(this.session);
                } else {
                    student.missSession(this.session);
                }
            }
        }

        // Return a success message
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(this.session)));
    }

    /**
     * Checks if this `TakeAttendanceCommand` is equal to another object.
     *
     * @param other The object to compare with.
     * @return `true` if the objects are equal, `false` otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TakeAttendanceCommand)) {
            return false;
        }

        TakeAttendanceCommand otherTakeAttendanceCommand = (TakeAttendanceCommand) other;
        if (!attendancePresence.equals(otherTakeAttendanceCommand.attendancePresence)) {
            return false;
        }
        if (names != null && otherTakeAttendanceCommand.names != null) {
            // Compare when both 'names' are not null
            return this.names.equals(otherTakeAttendanceCommand.names)
                    && this.sessionNumber.equals(otherTakeAttendanceCommand.sessionNumber);
        } else if (names == null && otherTakeAttendanceCommand.names == null) {
            // Compare when both 'names' are null
            return this.name == null ? otherTakeAttendanceCommand.name == null
                    : this.name.equals(otherTakeAttendanceCommand.name)
                    && this.sessionNumber.equals(otherTakeAttendanceCommand.sessionNumber);
        } else {
            // 'names' is null in one of the objects
            return false;
        }
    }

    /**
     * Returns a string representation of the `TakeAttendanceCommand`.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("takeAttendance", sessionNumber)
                .add("students", students)
                .toString();
    }
}