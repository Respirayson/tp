package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import javafx.collections.ObservableMap;
import seedu.address.model.person.assignment.Assignment;
import seedu.address.model.person.assignment.AssignmentMap;
import seedu.address.model.person.assignment.AssignmentName;

/**
 * Represents a collection of assignments. This class is responsible for managing and manipulating assignment data.
 */
public class AssignmentMapBook implements ReadOnlyAssignmentMap {

    private final AssignmentMap assignments;

    {
        assignments = new AssignmentMap();
    }

    public AssignmentMapBook() {}

    /**
     * Creates an AssignmentMapBook using the AssignmentMap in the {@code toBeCopied}
     */
    public AssignmentMapBook(ReadOnlyAssignmentMap toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the assignment map with {@code newAssignments}.
     */
    public void setAssignments(Map<AssignmentName, Assignment> newAssignments) {
        this.assignments.setAssignmentMap(newAssignments);
    }

    /**
     * Resets the existing data of this {@code AssignmentMapBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAssignmentMap newData) {
        requireNonNull(newData);

        setAssignments(newData.getAssignmentMap());
    }

    public Assignment getAssignment(AssignmentName assignmentName) {
        return assignments.get(assignmentName);
    }

    @Override
    public ObservableMap<AssignmentName, Assignment> getAssignmentMap() {
        return assignments.asUnmodifiableObservableMap();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignmentMapBook)) {
            return false;
        }

        AssignmentMapBook otherMap = (AssignmentMapBook) other;
        return this.assignments.equals(otherMap.assignments);
    }
}
