package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;

/**
 * Deletes a consultation identified using it's displayed index from the consultation list book.
 */
public class DeleteConsultationCommand extends Command {
    public static final String COMMAND_WORD = "deleteconsult";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the consultation identified by the index number used in the displayed consultation list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + 1;

    public static final String MESSAGE_DELETE_CONSULTATION_SUCCESS = "Deleted Consultation: %1$s";

    private final Index targetIndex;

    public DeleteConsultationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Consultation> lastShownConsultation = model.getFilteredConsultationList();

        if (targetIndex.getZeroBased() >= lastShownConsultation.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX);
        }

        Consultation consultationToDelete = lastShownConsultation.get(targetIndex.getZeroBased());
        model.deleteConsultation(consultationToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CONSULTATION_SUCCESS,
                Messages.format(consultationToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteConsultationCommand)) {
            return false;
        }

        DeleteConsultationCommand otherDeleteConsultationCommand = (DeleteConsultationCommand) other;
        return targetIndex.equals(otherDeleteConsultationCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
