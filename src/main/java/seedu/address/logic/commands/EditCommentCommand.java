package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
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
import seedu.address.model.person.assignment.Comment;
import seedu.address.model.tag.Tag;

/**
 * Edits the comment given to a person's assignment.
 */
public class EditCommentCommand extends Command {

    public static final String COMMAND_WORD = "editcomment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the comment given to a person's assignment, "
            + "identified by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT "
            + PREFIX_COMMENT + "COMMENT ";

    public static final String MESSAGE_SUCCESS = "Edited comment to assignment: %1$s";

    private final AssignmentName assignmentName;
    private final Comment comment;
    private final Index index;

    /**
     * Creates an EditCommentCommand to edit the specified comment to a person's assignment.
     */
    public EditCommentCommand(Index index, AssignmentName assignmentName, Comment comment) {
        requireAllNonNull(index, assignmentName, comment);
        this.index = index;
        this.assignmentName = assignmentName;
        this.comment = comment;
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
        Person editedPerson = createCommentedPerson(personToEdit);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.assignmentName));
    }

    /**
     * Creates a new Person with the newly commented on assignment.
     *
     * @param reference The person whose assignment is being commented on.
     * @return New person with a comment on their assignment.
     */
    public Person createCommentedPerson(Person reference) {
        Name name = reference.getName();
        Phone phone = reference.getPhone();
        Email email = reference.getEmail();
        Address address = reference.getAddress();
        Set<Tag> tags = reference.getTags();
        Set<GradedTest> gradedTest = reference.getGradedTest();
        AssignmentMap updatedAssignmentMap =
                reference.getAllAssignments().createUpdatedMap(this.assignmentName, this.comment);
        return new Person(name, phone, email, address, tags, updatedAssignmentMap, gradedTest);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommentCommand)) {
            return false;
        }

        EditCommentCommand otherEditCommentCommand = (EditCommentCommand) other;

        boolean isSameAssignmentName = this.assignmentName.equals(otherEditCommentCommand.assignmentName);
        boolean isSameComment = this.comment.equals(otherEditCommentCommand.comment);
        boolean isSamePersonIndex = this.index.equals(otherEditCommentCommand.index);

        return isSameAssignmentName && isSameComment && isSamePersonIndex;
    }
}
