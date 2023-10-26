package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.AssignmentMapBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalAssignments;

public class JsonSerializableAssignmentMapTest {

    private static final Path TEST_PERSON_DATA_FOLDER =
        Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_PERSON_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAssignmentMapTest");
    private static final Path INVALID_ASSIGNMENT_MAP_FILE = TEST_DATA_FOLDER.resolve("invalidAssignmentMap.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        Person samplePerson = addressBookFromFile.getPersonList().get(0);
        AssignmentMapBook assignmentMapBook = new AssignmentMapBook();
        assignmentMapBook.setAssignments(samplePerson.getAllAssignments().asUnmodifiableObservableMap());
        AssignmentMapBook typicalAssignmentMapBook = TypicalAssignments.getTypicalAssignmentMapBook();
        assertEquals(assignmentMapBook, typicalAssignmentMapBook);
    }

    @Test
    public void toModelType_invalidAssignmentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_ASSIGNMENT_MAP_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
