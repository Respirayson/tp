package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
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
 * Deletes the grade from a person's assignment.
 */
public class DeleteGradeCommand extends Command {

    public static final String COMMAND_WORD = "deletegrade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a grade from a person’s assignment identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT ";

    public static final String MESSAGE_SUCCESS = "Deleted grade from assignment: %1$s";

    public static final String MESSAGE_CONSTRAINT = "Cannot delete the grade from an ungraded assignment";

    private final AssignmentName assignmentName;
    private final Index index;

    /**
     * Creates a DeleteGradeCommand to delete the grade from a person's assignment.
     */
    public DeleteGradeCommand(Index index, AssignmentName assignmentName) {
        requireNonNull(index);
        requireNonNull(assignmentName);
        this.index = index;
        this.assignmentName = assignmentName;
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

        Person personToEdit = lastShownList.get(index.getZeroBased());

        AssignmentMap assignmentMapReference = personToEdit.getAllAssignments();
        boolean isGraded = assignmentMapReference.get(this.assignmentName).gradingStatus();
        if (!isGraded) {
            throw new CommandException(DeleteGradeCommand.MESSAGE_CONSTRAINT);
        }

        Person editedPerson = createUngradedPerson(personToEdit);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.assignmentName));
    }

    /**
     * Creates a new Person with the ungraded assignment.
     *
     * @param reference The person to be graded.
     * @return New person with a graded assignment.
     */
    public Person createUngradedPerson(Person reference) {
        Name name = reference.getName();
        Phone phone = reference.getPhone();
        Email email = reference.getEmail();
        Address address = reference.getAddress();
        Set<Tag> tags = reference.getTags();
        Set<GradedTest> gradedTest = reference.getGradedTest();
        Grade ungraded = reference.getAllAssignments().get(this.assignmentName).getGrade().ungrade();
        AssignmentMap updatedAssignmentMap =
                reference.getAllAssignments().createUpdatedMap(this.assignmentName, ungraded);
        return new Person(name, phone, email, address, tags, updatedAssignmentMap, gradedTest);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteGradeCommand)) {
            return false;
        }

        DeleteGradeCommand otherEditGradeCommand = (DeleteGradeCommand) other;

        boolean sameAssignmentName = this.assignmentName.equals(otherEditGradeCommand.assignmentName);
        boolean samePersonIndex = this.index.equals(otherEditGradeCommand.index);

        return sameAssignmentName && samePersonIndex;
    }
}
