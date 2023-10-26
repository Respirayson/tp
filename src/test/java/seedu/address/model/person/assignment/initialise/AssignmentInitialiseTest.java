package seedu.address.model.person.assignment.initialise;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AssignmentInitialiseTest {

    @Test
    public void getInitialisationStatus_afterInitialisation_success() {
        AssignmentInitialise.init();
        assertTrue(AssignmentInitialise.getInitialisationStatus());
    }

    @Test
    public void init_noReinitialise_success() {
        AssignmentInitialise.init();
        Integer size = AssignmentInitialise.size();
        AssignmentInitialise.init();
        assertTrue(size.equals(AssignmentInitialise.size()));
    }
}
