package seedu.address.model.person.assignment;

/**
 * Represents the Grade given to an assignment.
 * Consists of the maximum grade as well as the actual grade.
 */
public class Grade {

    public static final String MESSAGE_CONSTRAINTS =
            "Grade must be a positive integer, less than or equal to (max grade + 75) and without leading 0's";

    public static final String VALIDATION_REGEX = "[0-9]+";

    private String actualGrade;
    private final String maxGrade;
    private boolean isGraded;

    /**
     * Creates a Grade for an assignment with only the maximum grade.
     *
     * @param maxGrade The maximum grade for the assignment.
     */
    public Grade(String maxGrade) {
        this.maxGrade = maxGrade;
        this.isGraded = false;
    }

    /**
     * Creates a Grade for an assignment with both the actual grade and the maximum grade.
     *
     * @param actualGrade The grade the person scores for the assignment.
     * @param maxGrade The maximum grade for the assignment.
     */
    public Grade(String actualGrade, String maxGrade) {
        this.actualGrade = actualGrade;
        this.maxGrade = maxGrade;
        this.isGraded = true;
    }

    public String getMax() {
        return this.maxGrade;
    }

    public boolean getIsGraded() {
        return this.isGraded;
    }

    /**
     * Creates a new copy of the Grade of an assignment.
     *
     * @return New copy of the Grade of an assignment.
     */
    public Grade copyGrade() {
        if (this.isGraded) {
            return new Grade(this.actualGrade, this.maxGrade);
        } else {
            return new Grade(this.maxGrade);
        }
    }

    public Grade ungrade() {
        return new Grade(maxGrade);
    }

    /**
     * Checks if the grade being added is valid.
     *
     * @param test Grade to be added.
     * @param maxGrade Maximum Grade.
     * @return Whether the grade being added is valid.
     */
    public static boolean isValidGrade(String test, String maxGrade) {
        int testInteger;
        int maxInteger;

        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        if (test.charAt(0) == '0' && test.length() > 1) {
            return false;
        }

        testInteger = Integer.parseInt(test);
        maxInteger = Integer.parseInt(maxGrade) + 75;

        return testInteger <= maxInteger;
    }

    /**
     * Checks if the grade is valid, including UNGRADED.
     *
     * @param test Grade to be added.
     * @param maxGrade Maximum Grade.
     * @return Whether the grade is valid.
     */
    public static boolean isValidIncludingUngraded(String test, String maxGrade) {
        if (test.equals("UNGRADED")) {
            return true;
        } else {
            return isValidGrade(test, maxGrade);
        }
    }

    @Override
    public String toString() {
        if (this.isGraded) {
            return this.actualGrade + "/" + this.maxGrade;
        } else {
            return "UNGRADED/" + this.maxGrade;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Grade)) {
            return false;
        }

        Grade otherGrade = (Grade) other;
        boolean sameMaxGrade = this.maxGrade.equals(otherGrade.maxGrade);
        if (this.isGraded) {
            boolean sameGrade = this.actualGrade.equals(otherGrade.actualGrade);
            return sameMaxGrade && sameGrade && otherGrade.isGraded;
        } else {
            return sameMaxGrade && !otherGrade.isGraded;
        }
    }
}
