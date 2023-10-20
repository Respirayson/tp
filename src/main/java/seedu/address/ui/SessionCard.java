package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.session.Session;

/**
 * Represents a UI component that displays information about a session in the user interface.
 */
public class SessionCard extends UiPart<Region> {
    private static final String FXML = "SessionListCard.fxml";

    /** The session associated with this card. */
    public final Session session;

    @FXML
    private HBox cardPane;
    @FXML
    private Label sessionNumberLabel;
    @FXML
    private Label studentListLabel;

    /**
     * Creates a `SessionCard` object to display information about a session.
     *
     * @param session The session to display information about.
     */
    public SessionCard(Session session) {
        super(FXML);
        this.session = session;
        sessionNumberLabel.setText(session.getSessionNumber() + ". ");
        studentListLabel.setText(session.getStudents().toStudentNames());
    }
}