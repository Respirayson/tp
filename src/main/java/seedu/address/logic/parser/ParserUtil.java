package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.gradedtest.GradedTest.DEFAULT_VALUE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.AttendancePresence;
import seedu.address.model.gradedtest.Finals;
import seedu.address.model.gradedtest.GradedTest;
import seedu.address.model.gradedtest.MidTerms;
import seedu.address.model.gradedtest.PracticalExam;
import seedu.address.model.gradedtest.ReadingAssessment1;
import seedu.address.model.gradedtest.ReadingAssessment2;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.model.person.assignment.Comment;
import seedu.address.model.person.assignment.Grade;
import seedu.address.model.session.SessionNumber;
import seedu.address.model.session.SessionRemark;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskProgress;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DATE = "Date is needs to be in the format dd/MM/yyyy, "
            + "or date provided does not exist.";
    public static final String MESSAGE_INVALID_TIME = "Time is needs to be in the format HH:mm.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses {@code Collection<String> names} into a {@code Set<Name>}.
     */
    public static Set<Name> parseNames(Collection<String> names) throws ParseException {
        requireNonNull(names);
        final Set<Name> nameSet = new HashSet<>();
        for (String studentName : names) {
            if (!Name.isValidName(studentName)) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }
            nameSet.add(parseName(studentName));
        }
        return nameSet;
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }


    /**
     * Parses a {@code String telegramHandle} into an {@code TelegramHandle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code telegramHandle} is invalid.
     */
    public static TelegramHandle parseTelegramHandle(String telegramHandle) throws ParseException {
        requireNonNull(telegramHandle);
        String trimmedTelegramHandle = telegramHandle.trim();
        if (!TelegramHandle.isValidTelegramHandle(trimmedTelegramHandle)) {
            throw new ParseException(TelegramHandle.MESSAGE_CONSTRAINTS);
        }
        return new TelegramHandle(trimmedTelegramHandle);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String date} into an {@code LocalDate} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            return LocalDate.parse(trimmedDate, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Parses {@code String time} into an {@code LocalTime} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(trimmedTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_TIME);
        }
    }

    /**
     * Parses the input session number as a string and returns it as a SessionNumber.
     *
     * @param sessionNumber The session number to be parsed.
     * @return The parsed session number as a SessionNumber.
     * @throws ParseException If the session number is not a valid number.
     */
    public static SessionNumber parseSessionNumber(String sessionNumber) throws ParseException {
        requireNonNull(sessionNumber);
        String trimmedSessionNumber = sessionNumber.trim();
        if (!SessionNumber.isValidSessionNumber(trimmedSessionNumber)) {
            throw new ParseException(SessionNumber.MESSAGE_CONSTRAINTS);
        }
        return new SessionNumber(trimmedSessionNumber);
    }

    /**
     * Parses the input session remark as a string and returns it as a SessionRemark.
     *
     * @param sessionRemark The session remark to be parsed.
     * @return The parsed session remark as a SessionRemark.
     * @throws ParseException If the session remark is not a valid remark.
     */
    public static SessionRemark parseSessionRemark(String sessionRemark) throws ParseException {
        requireNonNull(sessionRemark);
        String trimmedSessionRemark = sessionRemark.trim();
        if (!SessionRemark.isValidSessionRemark(trimmedSessionRemark)) {
            throw new ParseException(SessionRemark.MESSAGE_CONSTRAINTS);
        }
        return new SessionRemark(trimmedSessionRemark);
    }

    /**
     * Parses the input attendance presence as a string and returns it as an AttendancePresence.
     *
     * @param attendancePresence The attendance presence to be parsed.
     * @return The parsed attendance presence as an AttendancePresence.
     * @throws ParseException If the attendance presence is invalid.
     */
    public static AttendancePresence parseAttendancePresence(String attendancePresence) throws ParseException {
        requireNonNull(attendancePresence);
        String trimmedPresence = attendancePresence.trim().toUpperCase();
        for (AttendancePresence presence : AttendancePresence.values()) {
            if (trimmedPresence.equals(presence.name())) {
                return AttendancePresence.valueOf(trimmedPresence);
            }
        }
        throw new ParseException(AttendancePresence.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses a {@code String taskName} into a {@code TaskName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static TaskName parseTaskName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!TaskName.isValidName(trimmedName)) {
            throw new ParseException(TaskName.MESSAGE_CONSTRAINTS);
        }
        return new TaskName(trimmedName);
    }

    /**
     * Parses a {@code String taskDescription} into a {@code TaskDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static TaskDescription parseTaskDescription(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!TaskDescription.isValidDescription(trimmedName)) {
            throw new ParseException(TaskDescription.MESSAGE_CONSTRAINTS);
        }
        return new TaskDescription(trimmedName);
    }

    /**
     * Parses a {@code String gradedTest} into a {@code GradedTest}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gradedTest} is invalid.
     */
    public static GradedTest parseGradedTest(String gradedTest) throws ParseException {
        requireNonNull(gradedTest);
        String trimmedGradedTest = gradedTest.trim();
        if ("default".equalsIgnoreCase(gradedTest)) {
            ReadingAssessment1 readingAssessment1 = new ReadingAssessment1(DEFAULT_VALUE);
            ReadingAssessment2 readingAssessment2 = new ReadingAssessment2(DEFAULT_VALUE);
            MidTerms midTerms = new MidTerms(DEFAULT_VALUE);
            Finals finals = new Finals(DEFAULT_VALUE);
            PracticalExam practicalExam = new PracticalExam(DEFAULT_VALUE);

            return new GradedTest(readingAssessment1, readingAssessment2, midTerms, finals, practicalExam);
        } else {
            try {
                ReadingAssessment1 readingAssessment1 = new ReadingAssessment1(gradedTestParser(trimmedGradedTest)[0]);
                ReadingAssessment2 readingAssessment2 = new ReadingAssessment2(gradedTestParser(trimmedGradedTest)[1]);
                MidTerms midTerms = new MidTerms(gradedTestParser(trimmedGradedTest)[2]);
                Finals finals = new Finals(gradedTestParser(trimmedGradedTest)[3]);
                PracticalExam practicalExam = new PracticalExam(gradedTestParser(trimmedGradedTest)[4]);

                return new GradedTest(readingAssessment1, readingAssessment2, midTerms, finals, practicalExam);
            } catch (ParseException e) {
                if (!GradedTest.isValidGradeTestName(trimmedGradedTest)) {
                    throw new ParseException(GradedTest.MESSAGE_CONSTRAINTS);
                }
                return new GradedTest(trimmedGradedTest);
            }
        }
    }

    /**
     * Parses a string representing a GradedTest into its component scores.
     *
     * @param gradedTestsIndv The input string to be parsed.
     * @return An array of five strings, each representing the score for a specific component.
     * @throws ParseException If the input string is not in the expected format or if any of the scores are invalid.
     */
    public static String[] gradedTestParser(String gradedTestsIndv) throws ParseException {
        String[] components = gradedTestsIndv.split("\\|");

        if (components.length != 5) {
            throw new ParseException("Invalid GradedTest format. Expected 5 components.");
        }

        String[] scores = new String[5];

        for (int i = 0; i < components.length; i++) {
            String[] fieldParts = components[i].split(":");
            if (fieldParts.length != 2) {
                throw new ParseException("Invalid GradedTest format for field " + i);
            }
            String fieldName = fieldParts[0].trim();
            String fieldValue = fieldParts[1].trim();
            validateGradedTestField(fieldName, fieldValue);
            scores[i] = fieldValue;
        }

        return scores;
    }

    /**
     * Validates a field within a GradedTest by checking if it conforms to specific criteria.
     *
     * @param fieldName The name of the GradedTest component, e.g., "RA1", "RA2", "MidTerms", "Finals", or "PE".
     * @param fieldValue The value of the field to be validated.
     * @throws ParseException If the field does not meet the validation criteria.
     */
    public static void validateGradedTestField(String fieldName, String fieldValue) throws ParseException {
        switch (fieldName) {
        case "RA1":
            if (!fieldValue.matches(ReadingAssessment1.VALIDATION_REGEX)) {
                throw new ParseException(ReadingAssessment1.MESSAGE_CONSTRAINTS);
            }
            break;
        case "RA2":
            if (!fieldValue.matches(ReadingAssessment2.VALIDATION_REGEX)) {
                throw new ParseException(ReadingAssessment2.MESSAGE_CONSTRAINTS);
            }
            break;
        case "MidTerms":
            if (!fieldValue.matches(MidTerms.VALIDATION_REGEX)) {
                throw new ParseException(MidTerms.MESSAGE_CONSTRAINTS);
            }
            break;
        case "Finals":
            if (!fieldValue.matches(Finals.VALIDATION_REGEX)) {
                throw new ParseException(Finals.MESSAGE_CONSTRAINTS);
            }
            break;
        case "PE":
            if (!fieldValue.matches(PracticalExam.VALIDATION_REGEX)) {
                throw new ParseException(PracticalExam.MESSAGE_CONSTRAINTS);
            }
            break;
        default:
            throw new ParseException("Invalid field name: " + fieldName);
        }
    }

    /**
     * Parses {@code Collection<String> gradedTest} into a {@code Set<GradedTest>}.
     */
    public static Set<GradedTest> parseGradedTests(Collection<String> gradedTests) throws ParseException {
        requireNonNull(gradedTests);
        final Set<GradedTest> gradedTestSet = new HashSet<>();
        for (String gradedTestName : gradedTests) {
            gradedTestSet.add(parseGradedTest(gradedTestName));
        }
        return gradedTestSet;
    }

    /**
     * Parses a {@code String readingAssessment} into a {@code readingAssessment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ReadingAssessment1 parseReadingAssessment1(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!ReadingAssessment1.isValidRaResult(trimmedName)) {
            throw new ParseException(ReadingAssessment1.MESSAGE_CONSTRAINTS);
        }
        return new ReadingAssessment1(trimmedName);
    }

    /**
     * Parses a {@code String readingAssessment} into a {@code readingAssessment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ReadingAssessment2 parseReadingAssessment2(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!ReadingAssessment1.isValidRaResult(trimmedName)) {
            throw new ParseException(ReadingAssessment1.MESSAGE_CONSTRAINTS);
        }
        return new ReadingAssessment2(trimmedName);
    }

    /**
     * Parses a {@code String readingAssessment} into a {@code readingAssessment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static MidTerms parseMidTerms(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!MidTerms.isValidMidTermResult(trimmedName)) {
            throw new ParseException(MidTerms.MESSAGE_CONSTRAINTS);
        }
        return new MidTerms(trimmedName);
    }

    /**
     * Parses a {@code String finals} into a {@code finals}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Finals parseFinals(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Finals.isValidFinalsResult(trimmedName)) {
            throw new ParseException(Finals.MESSAGE_CONSTRAINTS);
        }
        return new Finals(trimmedName);
    }

    /**
     * Parses a {@code String practicalExam} into a {@code practicalExam}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static PracticalExam parsePracticalExam(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!PracticalExam.isValidPeResult(trimmedName)) {
            throw new ParseException(PracticalExam.MESSAGE_CONSTRAINTS);
        }
        return new PracticalExam(trimmedName);
    }

    /**
     * Parses a {@code String priority} into a {@code TaskPriority}.
     * Leading and trailing whitespaces will be trimmed.
     * String will be uppercase.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static TaskPriority parseTaskPriority(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim().toUpperCase();
        for (TaskPriority priority : TaskPriority.values()) {
            if (trimmedName.equals(priority.name())) {
                return TaskPriority.valueOf(trimmedName);
            }
        }
        throw new ParseException(TaskPriority.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses a {@code String progress} into a {@code TaskProgress}.
     * Leading and trailing whitespaces will be trimmed.
     * String will be uppercase.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static TaskProgress parseTaskProgress(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim().toUpperCase();
        for (TaskProgress progress : TaskProgress.values()) {
            if (trimmedName.equals(progress.name())) {
                return TaskProgress.valueOf(trimmedName);
            }
        }
        throw new ParseException(TaskProgress.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses a {@code String assignmentName} into a {@code AssignmentName}.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static AssignmentName parseAssignmentName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!AssignmentName.isValidName(trimmedName)) {
            throw new ParseException(AssignmentName.MESSAGE_CONSTRAINTS);
        }
        return new AssignmentName(trimmedName);
    }

    /**
     * Parses a {@code String grade} into a {@code Grade}.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String grade, String maxGrade) throws ParseException {
        requireNonNull(grade);
        String trimmedGrade = grade.trim();
        if (!Grade.isValidGrade(trimmedGrade, maxGrade)) {
            throw new ParseException(Grade.MESSAGE_CONSTRAINTS);
        }
        return new Grade(grade, maxGrade);
    }

    /**
     * Parses a {@code String comment} into a {@code Comment}.
     *
     * @throws ParseException if the given {@code comment} is invalid.
     */
    public static Comment parseComment(String comment) throws ParseException {
        requireNonNull(comment);
        String trimmedComment = comment.trim();
        if (!Comment.isValidComment(trimmedComment)) {
            throw new ParseException(Comment.MESSAGE_CONSTRAINTS);
        }
        return new Comment(trimmedComment);
    }
}
