package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gradedtest.GradedTest;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.assignment.AssignmentMap;
import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.model.person.assignment.Grade;
import seedu.address.model.tag.Tag;


/**
 * Edits a grade to a person's assignment.
 */
public class EditGradeCommand extends Command {

    public static final String COMMAND_WORD = "editgrade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a grade to a person’s assignment identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT "
            + PREFIX_GRADE + "GRADE ";

    public static final String MESSAGE_SUCCESS = "Edited grade to assignment: %1$s";

    private final AssignmentName assignmentName;
    private final Grade grade;
    private final Index index;

    /**
     * Creates an EditGradeCommand to edit the specified grade to a person's assignment.
     */
    public EditGradeCommand(Index index, AssignmentName assignmentName, Grade grade) {
        requireAllNonNull(index, assignmentName, grade);
        this.index = index;
        this.assignmentName = assignmentName;
        this.grade = grade;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!AssignmentName.isValidName(this.assignmentName.toString())) {
            throw new CommandException(AssignmentName.MESSAGE_CONSTRAINTS);
        }

        String[] gradeArray = this.grade.toString().split("/");
        String actualGrade = gradeArray[0];

        if (!Grade.isValidGrade(actualGrade, this.grade.getMax())) {
            throw new CommandException(Grade.MESSAGE_CONSTRAINTS);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createGradedPerson(personToEdit);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.assignmentName));
    }

    /**
     * Creates a new Person with the newly graded assignment.
     *
     * @param reference The person to be graded.
     * @return New person with a graded assignment.
     */
    public Person createGradedPerson(Person reference) {
        Name name = reference.getName();
        Phone phone = reference.getPhone();
        Email email = reference.getEmail();
        Address address = reference.getAddress();
        Set<Tag> tags = reference.getTags();
        Set<GradedTest> gradedTest = reference.getGradedTest();
        AssignmentMap updatedAssignmentMap =
            reference.getAllAssignments().createUpdatedMap(this.assignmentName, this.grade);
        return new Person(name, phone, email, address, tags, updatedAssignmentMap, gradedTest);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditGradeCommand)) {
            return false;
        }

        EditGradeCommand otherEditGradeCommand = (EditGradeCommand) other;

        boolean isSameAssignmentName = this.assignmentName.equals(otherEditGradeCommand.assignmentName);
        boolean isSameGrade = this.grade.equals(otherEditGradeCommand.grade);
        boolean isSamePersonIndex = this.index.equals(otherEditGradeCommand.index);

        return isSameAssignmentName && isSameGrade && isSamePersonIndex;
    }
}
