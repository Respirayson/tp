package seedu.address.model.person.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the Comment given to an assignment.
 */
public class Comment {

    public static final String MESSAGE_CONSTRAINTS = "Comment should be maximum 200 characters and cannot be empty.";

    public static final String VALIDATION_REGEX = "^.{1,200}$";

    private final String commentBody;
    private boolean isCommented;

    /**
     * Constructs a {@code Comment}.
     *
     * @param commentBody A valid comment.
     */
    public Comment(String commentBody) {
        checkArgument(isValidComment(commentBody), MESSAGE_CONSTRAINTS);
        this.commentBody = commentBody;
        this.isCommented = true;
    }

    /**
     * Constructs a {@code Comment} with no comment body.
     */
    public Comment() {
        this.isCommented = false;
        this.commentBody = "";
    }

    /**
     * Returns true if a given string is a valid comment body.
     */
    public static boolean isValidComment(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    public boolean getIsCommented() {
        return isCommented;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Comment)) {
            return false;
        }

        Comment otherComment = (Comment) other;
        if (isCommented) {
            return otherComment.isCommented && commentBody.equals(otherComment.commentBody);
        } else {
            return !otherComment.isCommented;
        }
    }

    @Override
    public String toString() {
        return commentBody;
    }
}
