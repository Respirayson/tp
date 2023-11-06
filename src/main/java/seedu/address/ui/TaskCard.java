package seedu.address.ui;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskProgress;

/**
 * A UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Text name;
    @FXML
    private Label id;
    @FXML
    private Text description;
    @FXML
    private Text priority;
    @FXML
    private Text date;
    @FXML
    private FlowPane progress;
    @FXML
    private Label dueDate;

    /**
     * Creates a {@code TaskCard} with the given {@code Task} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().taskName);
        description.setText(task.getDescription().description);
        priority.setText(task.getPriority().name());
        date.setText(task.getDate() != null ? task.getDate().toString() : "");
        Label progressLabel = new Label(task.getProgress().name().toLowerCase());
        progress.getChildren().add(progressLabel);

        if (task.getDate() != null) {
            String deadline = LocalDate.now().until(task.getDate(), DAYS) + " Day(s)";
            dueDate.setText(deadline);
            if (LocalDate.now().until(task.getDate(), DAYS) < 0L) {
                dueDate.setStyle("-fx-background-color: red;");
            }
        } else {
            dueDate.setText("No Due Date");
        }

        boolean isDone = task.getProgress().equals(TaskProgress.DONE);

        switch(task.getProgress()) {
        case NOT_STARTED:
            progressLabel.setStyle("-fx-background-color: red;");
            break;
        case DONE:
            progressLabel.setStyle("-fx-background-color: green;");
            break;
        default:
            progressLabel.setStyle("-fx-background-color: #3e7b91;");
        }

        switch(task.getPriority()) {
        case LOW:
            priority.setStyle("-fx-fill: green");
            break;
        case MEDIUM:
            priority.setStyle("-fx-fill: blue");
            break;
        case HIGH:
            priority.setStyle("-fx-fill: red");
            break;
        default:
            priority.setStyle("-fx-fill: black");
        }

        name.setStrikethrough(isDone);
        description.setStrikethrough(isDone);
        priority.setStrikethrough(isDone);
        date.setStrikethrough(isDone);

    }
}
