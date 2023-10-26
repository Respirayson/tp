package seedu.address.model.person.assignment;

import static javafx.collections.FXCollections.observableHashMap;
import static javafx.collections.FXCollections.unmodifiableObservableMap;

import java.util.Map;

import javafx.collections.ObservableMap;
import seedu.address.model.person.assignment.initialise.AssignmentInitialise;

/**
 * Stores the list of assignments a person has.
 */
public class AssignmentMap {
    private final ObservableMap<AssignmentName, Assignment> assignments = observableHashMap();
    private final ObservableMap<AssignmentName, Assignment> unmodifiableAssignments =
            unmodifiableObservableMap(assignments);

    /**
     * Creates an AssignmentMap to store all assignments a person has.
     */
    public AssignmentMap() {
        if (!AssignmentInitialise.getInitialisationStatus()) {
            AssignmentInitialise.init();
        }
        for (int i = 0; i < AssignmentInitialise.size(); i++) {
            AssignmentName assignmentName = AssignmentInitialise.getAssignmentName(i);
            Grade assignmentMaxGrade = AssignmentInitialise.getAssignmentMaxGrade(i);
            assignments.put(assignmentName, new Assignment(assignmentName, assignmentMaxGrade));
        }
    }

    public void setAssignmentMap(Map<AssignmentName, Assignment> assignments) {
        for (int i = 0; i < AssignmentInitialise.size(); i++) {
            AssignmentName assignmentName = AssignmentInitialise.getAssignmentName(i);
            Assignment replaceAssignment = assignments.get(assignmentName);
            this.assignments.replace(assignmentName, replaceAssignment.copyAssignment());
        }
    }

    /**
     * Creates a new AssignmentMap with the updated grade being given to an assignment.
     *
     * @param toBeGraded Name of assignment being graded.
     * @param newGrade Grade being given to the assignment.
     * @return New AssignmentMap with the updated grades.
     */
    public AssignmentMap createUpdatedMap(AssignmentName toBeGraded, Grade newGrade) {
        AssignmentMap updateTo = new AssignmentMap();
        for (int i = 0; i < AssignmentInitialise.size(); i++) {
            AssignmentName assignmentName = AssignmentInitialise.getAssignmentName(i);
            if (assignmentName.equals(toBeGraded)) {
                Assignment gradedAssignment = new Assignment(toBeGraded, newGrade);
                updateTo.assignments.replace(assignmentName, gradedAssignment);
            } else {
                Assignment originalAssignment = this.assignments.get(assignmentName);
                updateTo.assignments.replace(assignmentName, originalAssignment.copyAssignment());
            }
        }
        return updateTo;
    }

    /**
     * Creates a new AssignmentMap with the updated comment being given to an assignment.
     *
     * @param toBeCommented Name of assignment being graded.
     * @param newComment Grade being given to the assignment.
     * @return New AssignmentMap with the updated grades.
     */
    public AssignmentMap createUpdatedMap(AssignmentName toBeCommented, Comment newComment) {
        AssignmentMap updateTo = new AssignmentMap();
        for (int i = 0; i < AssignmentInitialise.size(); i++) {
            AssignmentName assignmentName = AssignmentInitialise.getAssignmentName(i);
            if (assignmentName.equals(toBeCommented)) {
                Grade grade = assignments.get(assignmentName).getGrade();
                Assignment commentedAssignment = new Assignment(toBeCommented, grade, newComment);
                updateTo.assignments.replace(assignmentName, commentedAssignment);
            } else {
                Assignment originalAssignment = this.assignments.get(assignmentName);
                updateTo.assignments.replace(assignmentName, originalAssignment.copyAssignment());
            }
        }
        return updateTo;
    }

    public boolean contains(AssignmentName key) {
        return assignments.containsKey(key);
    }

    public Assignment get(AssignmentName key) {
        return assignments.get(key);
    }

    public ObservableMap<AssignmentName, Assignment> asUnmodifiableObservableMap() {
        return unmodifiableAssignments;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignmentMap)) {
            return false;
        }

        AssignmentMap otherMap = (AssignmentMap) other;
        return this.assignments.equals(otherMap.assignments);
    }
}
