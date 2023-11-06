package seedu.address.testutil;

import static seedu.address.model.task.Task.FORMATTER;

import java.time.LocalDate;

import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskProgress;

/**
 * A utility class to help with building Person objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Borrow book";
    public static final String DEFAULT_DESCRIPTION = "At Central Library";
    public static final String DEFAULT_PRIORITY = "LOW";
    public static final String DEFAULT_DATE = "22/10/2024";

    private TaskName name;
    private TaskDescription description;
    private TaskPriority priority;
    private LocalDate date;
    private TaskProgress progress;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        name = new TaskName(DEFAULT_NAME);
        description = new TaskDescription(DEFAULT_DESCRIPTION);
        priority = TaskPriority.valueOf(DEFAULT_PRIORITY);
        date = LocalDate.parse(DEFAULT_DATE, FORMATTER);
        progress = TaskProgress.NOT_STARTED;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code task}.
     */
    public TaskBuilder(Task task) {
        name = task.getName();
        description = task.getDescription();
        priority = task.getPriority();
        date = task.getDate();
        progress = task.getProgress();
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new TaskName(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = new TaskDescription(description);
        return this;
    }

    /**
     * Sets the {@code priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String name) {
        this.priority = TaskPriority.valueOf(name.trim().toUpperCase());
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Task} that we are building.
     */
    public TaskBuilder withDate(String name) {
        this.date = name.equals("") ? null : LocalDate.parse(name, FORMATTER);
        return this;
    }

    /**
     * Sets the {@code progress} of the {@code Task} that we are building.
     */
    public TaskBuilder withProgress(String name) {
        this.progress = TaskProgress.valueOf(name.trim().toUpperCase());
        return this;
    }

    public Task build() {
        return new Task(name, description, priority, date, progress);
    }

}
