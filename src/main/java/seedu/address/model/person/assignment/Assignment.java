package seedu.address.model.person.assignment;

/**
 * Represents an assignment.
 */
public class Assignment {

    // Identity fields
    private final AssignmentName assignmentName;
    private final Grade grade;
    private final Comment comment;

    /**
     * Creates an assignment without a comment.
     */
    public Assignment(AssignmentName name, Grade grade) {
        this.assignmentName = name;
        this.grade = grade;
        this.comment = new Comment();
    }

    /**
     * Creates an assignment with a comment.
     */
    public Assignment(AssignmentName name, Grade grade, Comment comment) {
        this.assignmentName = name;
        this.grade = grade;
        this.comment = comment;
    }

    public AssignmentName getName() {
        return this.assignmentName;
    }

    public Grade getGrade() {
        return this.grade;
    }

    public String maxGrade() {
        return this.grade.getMax();
    }

    public boolean gradingStatus() {
        return this.grade.getIsGraded();
    }

    public Comment getComment() {
        return this.comment;
    }

    public boolean commentStatus() {
        return this.comment.getIsCommented();
    }

    public Assignment copyAssignment() {
        return new Assignment(this.assignmentName, this.grade.copyGrade());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherAssignment = (Assignment) other;
        boolean isNameEqual = assignmentName.equals(otherAssignment.assignmentName);
        boolean isGradeEqual = grade.equals(otherAssignment.grade);
        boolean isCommentEqual = comment.equals(otherAssignment.comment);
        return isNameEqual && isGradeEqual && isCommentEqual;
    }
}
