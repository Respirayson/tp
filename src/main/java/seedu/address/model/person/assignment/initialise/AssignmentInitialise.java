package seedu.address.model.person.assignment.initialise;

import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.model.person.assignment.Grade;

/**
 * Represents an initializer to create assignments for each person.
 */
public class AssignmentInitialise {
    private static boolean isInitialised = false;
    private static AssignmentNameInitialise nameInitialise;
    private static AssignmentMaxGradeInitialise maxGradeInitialise;

    private AssignmentInitialise() {} // prevents instantiation

    /**
     * Initializes all assignment names and maximum grades if they have yet to be initialized.
     */
    public static void init() {
        if (!isInitialised) {
            AssignmentInitialise.nameInitialise = new AssignmentNameInitialise();
            AssignmentInitialise.nameInitialise.initMissions();
            AssignmentInitialise.nameInitialise.initQuests();

            AssignmentInitialise.maxGradeInitialise = new AssignmentMaxGradeInitialise();
            AssignmentInitialise.maxGradeInitialise.missionGrades();
            AssignmentInitialise.maxGradeInitialise.questGrades();

            isInitialised = true;
        }
    }

    public static AssignmentName getAssignmentName(int index) {
        return AssignmentInitialise.nameInitialise.getName(index);
    }

    public static Grade getAssignmentMaxGrade(int index) {
        return AssignmentInitialise.maxGradeInitialise.getGrade(index);
    }

    public static boolean getInitialisationStatus() {
        return isInitialised;
    }

    public static int size() {
        return AssignmentInitialise.nameInitialise.size();
    }
}
