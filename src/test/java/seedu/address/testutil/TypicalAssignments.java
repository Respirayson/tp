package seedu.address.testutil;

import java.util.HashMap;
import java.util.Map;

import seedu.address.model.AssignmentMapBook;
import seedu.address.model.person.assignment.Assignment;
import seedu.address.model.person.assignment.AssignmentMap;
import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.model.person.assignment.Grade;
import seedu.address.model.person.assignment.initialise.AssignmentInitialise;

/**
 * A utility class containing a map of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {
    public static final Assignment ASSIGNMENT1 = new AssignmentBuilder()
            .withName("Beyond the First Dimension")
            .withGrade("800", "800")
            .build();

    public static final Assignment ASSIGNMENT2 = new AssignmentBuilder()
            .withName("Colorful Carpets")
            .withGrade("400", "600")
            .build();

    public static final Assignment ASSIGNMENT3 = new AssignmentBuilder()
            .withName("Premorseal Communications")
            .withGrade("900", "950")
            .build();

    public static final Assignment ASSIGNMENT4 = new AssignmentBuilder()
            .withName("The Magical Tone Matrix")
            .withGrade("400", "800")
            .build();

    public static final Assignment ASSIGNMENT_UNGRADED = new AssignmentBuilder()
            .withName("Finding ELDRIC")
            .withGrade("UNGRADED", "1200")
            .build();

    private TypicalAssignments() {} // prevents instantiation

    /**
     * Returns an {@code AssignmentMapBook} with all the typical assignments.
     */
    public static AssignmentMapBook getTypicalAssignmentMapBook() {
        Map<AssignmentName, Assignment> newAssignments = new HashMap<>();
        AssignmentInitialise.init();
        for (int i = 0; i < AssignmentInitialise.size(); i++) {
            AssignmentName assignmentName = AssignmentInitialise.getAssignmentName(i);
            Grade grade = AssignmentInitialise.getAssignmentMaxGrade(i);
            Assignment assignment = new Assignment(assignmentName, grade);
            newAssignments.put(assignmentName, assignment);
        }
        AssignmentMapBook assignmentMapBook = new AssignmentMapBook();
        assignmentMapBook.setAssignments(newAssignments);
        return assignmentMapBook;
    }

    /**
     * Returns an {@code AssignmentMapBook} with all the sample assignments.
     */
    public static AssignmentMapBook getSampleAssignmentMapBook() {
        Map<AssignmentName, Assignment> newAssignments = new HashMap<>();
        AssignmentInitialise.init();
        for (int i = 0; i < AssignmentInitialise.size(); i++) {
            AssignmentName assignmentName = AssignmentInitialise.getAssignmentName(i);
            Grade grade = AssignmentInitialise.getAssignmentMaxGrade(i);
            Assignment assignment = new Assignment(assignmentName, grade);
            newAssignments.put(assignmentName, assignment);
        }
        newAssignments.replace(ASSIGNMENT1.getName(), ASSIGNMENT1);
        newAssignments.replace(ASSIGNMENT2.getName(), ASSIGNMENT2);
        newAssignments.replace(ASSIGNMENT3.getName(), ASSIGNMENT3);
        newAssignments.replace(ASSIGNMENT4.getName(), ASSIGNMENT4);
        AssignmentMapBook assignmentMapBook = new AssignmentMapBook();
        assignmentMapBook.setAssignments(newAssignments);
        return assignmentMapBook;
    }

    /**
     * Returns an {@code AssignmentMap} with all the sample assignments.
     */
    public static AssignmentMap getSampleAssignmentMap() {
        Map<AssignmentName, Assignment> newAssignments = new HashMap<>();
        AssignmentInitialise.init();
        for (int i = 0; i < AssignmentInitialise.size(); i++) {
            AssignmentName assignmentName = AssignmentInitialise.getAssignmentName(i);
            Grade grade = AssignmentInitialise.getAssignmentMaxGrade(i);
            Assignment assignment = new Assignment(assignmentName, grade);
            newAssignments.put(assignmentName, assignment);
        }
        newAssignments.replace(ASSIGNMENT1.getName(), ASSIGNMENT1);
        newAssignments.replace(ASSIGNMENT2.getName(), ASSIGNMENT2);
        newAssignments.replace(ASSIGNMENT3.getName(), ASSIGNMENT3);
        newAssignments.replace(ASSIGNMENT4.getName(), ASSIGNMENT4);
        AssignmentMap assignmentMap = new AssignmentMap();
        assignmentMap.setAssignmentMap(newAssignments);
        return assignmentMap;
    }
}
