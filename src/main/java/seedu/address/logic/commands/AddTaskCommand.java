package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

/**
 * Adds a task to the address book.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "addTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a new task with the specified description.\n"
            + "Parameters: "
            + PREFIX_TASK_NAME + "NAME "
            + PREFIX_TASK_DESCRIPTION + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_NAME + "Do laundry "
            + PREFIX_TASK_DESCRIPTION + "Wash the clothes and pants in basket";

    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, Description: %2$s";

    private final String name;
    private final String description;

    /**
     * @param name of the task to be added
     * @param description of the task to be added
     */
    public AddTaskCommand(String name, String description) {
        requireAllNonNull(name, description);

        this.name = name;
        this.description = description;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, name, description));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTaskCommand)) {
            return false;
        }

        AddTaskCommand e = (AddTaskCommand) other;
        return name.equals(e.name)
                && description.equals(e.description);
    }


}
