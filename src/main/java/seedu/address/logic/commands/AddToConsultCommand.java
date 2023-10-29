package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONSULTATIONS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds students to an existing consultation in the consultation list.
 */
public class AddToConsultCommand extends Command {
    public static final String COMMAND_WORD = "addtoconsult";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to a consultation at INDEX."
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe ";

    public static final String MESSAGE_SUCCESS = "New student(s) added to consultation at index %1$s: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "The student(s) added are already in the consultation";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No student matching given name(s)";
    public static final String MESSAGE_NOT_EDITED = "At least one student is to be added";
    private final Index index;
    private final AddToConsultationDescriptor addToConsultationDescriptor;

    /**
     * @param index of the consultation in the filtered consultation list to add students to
     * @param addToConsultationDescriptor students to add into the consultation
     */
    public AddToConsultCommand(Index index, AddToConsultationDescriptor addToConsultationDescriptor) {
        requireAllNonNull(index, addToConsultationDescriptor);

        this.index = index;
        this.addToConsultationDescriptor = addToConsultationDescriptor;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Consultation> lastShownConsultationList = model.getFilteredConsultationList();

        if (index.getZeroBased() >= lastShownConsultationList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX);
        }

        Consultation consultationToAddStudent = lastShownConsultationList.get(index.getZeroBased());
        Consultation updatedConsultation;

        try {
            updatedConsultation = createUpdatedConsultation(model, consultationToAddStudent,
                    addToConsultationDescriptor);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        if (consultationToAddStudent.isSameConsultation(updatedConsultation)
                || model.hasConsultation(updatedConsultation)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.setConsultation(consultationToAddStudent, updatedConsultation);
        model.updateFilteredConsultationList(PREDICATE_SHOW_ALL_CONSULTATIONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(updatedConsultation)));
    }

    /**
     * Creates and returns a {@code Consultation} with the details of {@code consultationToAddStudent}
     * edited with {@code addToConsultationDescriptor}.
     */
    public static Consultation createUpdatedConsultation(Model model, Consultation consultationToAddStudent,
                                                          AddToConsultationDescriptor addToConsultationDescriptor) {
        assert consultationToAddStudent != null;

        LocalDate date = consultationToAddStudent.getDate();
        LocalTime time = consultationToAddStudent.getTime();
        // Original target student list
        Set<Person> students = consultationToAddStudent.getStudents();
        // List with new students to be added
        Set<Person> newStudents = addToConsultationDescriptor.names.stream().map(model::getMatchingStudentName)
                .collect(Collectors.toSet());
        // Adding original students to new students list (replace)
        newStudents.addAll(students);

        return new Consultation(date, time, newStudents);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddToConsultCommand)) {
            return false;
        }

        AddToConsultCommand otherCommand = (AddToConsultCommand) other;
        return index.equals(otherCommand.index)
                && addToConsultationDescriptor.equals(otherCommand.addToConsultationDescriptor);
    }

    /**
     * Stores the details to edit the consultation with (students to add). Each non-empty field value will replace the
     * corresponding field value of the consultation.
     */
    public static class AddToConsultationDescriptor {
        private Set<Name> names;
        public AddToConsultationDescriptor() {

        }
        public AddToConsultationDescriptor(AddToConsultationDescriptor toCopy) {
            setNames(toCopy.names);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyStudentsAdded() {
            return CollectionUtil.isAnyNonNull(names);
        }

        /**
         * Sets {@code names} to this object's {@code names}.
         * A defensive copy of {@code names} is used internally.
         */
        public void setNames(Set<Name> names) {
            this.names = (names != null) ? new HashSet<>(names) : null;
        }

        /**
         * Returns an unmodifiable names set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code names} is null.
         */
        public Optional<Set<Name>> getNames() {
            return (names != null) ? Optional.of(Collections.unmodifiableSet(names)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddToConsultationDescriptor)) {
                return false;
            }

            AddToConsultationDescriptor otherAddToConsultationDescriptor = (AddToConsultationDescriptor) other;
            return Objects.equals(names, otherAddToConsultationDescriptor.names);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("names", names)
                    .toString();
        }
    }
}