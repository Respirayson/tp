package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.gradedtest.GradedTest;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.assignment.Assignment;
import seedu.address.model.person.assignment.AssignmentMap;
import seedu.address.model.person.assignment.AssignmentName;
import seedu.address.model.person.assignment.initialise.AssignmentInitialise;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedGradedTest> gradedTests = new ArrayList<>();

    private final JsonSerializableAssignmentMap assignmentMap;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("assignmentMap") JsonSerializableAssignmentMap assignmentMap,
                             @JsonProperty("gradedTests") List<JsonAdaptedGradedTest> gradedTests) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (gradedTests != null) {
            this.gradedTests.addAll(gradedTests);
        }
        this.assignmentMap = assignmentMap;
    }

    public JsonAdaptedPerson(String name, String phone, String email, String address, List<JsonAdaptedTag> tags,
                             List<JsonAdaptedGradedTest> gradedTests) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
        HashMap<String, JsonAdaptedAssignment> jsonMap = new HashMap<>();
        for (int i = 0; i < AssignmentInitialise.size(); i++) {
            String assignmentName = AssignmentInitialise.getAssignmentName(i).toString();
            String assignmentGrade = AssignmentInitialise.getAssignmentMaxGrade(i).toString();
            jsonMap.put(assignmentName, new JsonAdaptedAssignment(assignmentName, assignmentGrade));
        }
        this.assignmentMap = new JsonSerializableAssignmentMap(jsonMap);
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        HashMap<String, JsonAdaptedAssignment> jsonMap = new HashMap<>();
        for (int i = 0; i < AssignmentInitialise.size(); i++) {
            AssignmentName assignmentName = AssignmentInitialise.getAssignmentName(i);
            Assignment assignment = source.getAllAssignments().get(assignmentName);
            jsonMap.put(assignmentName.toString(),
                    new JsonAdaptedAssignment(assignment));
        }
        this.assignmentMap = new JsonSerializableAssignmentMap(jsonMap);
        gradedTests.addAll(source.getGradedTest().stream()
                .map(JsonAdaptedGradedTest::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<GradedTest> personGradedTests = new ArrayList<>();

        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        for (JsonAdaptedGradedTest gradedTest : gradedTests) {
            personGradedTests.add(gradedTest.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Set<GradedTest> modelGradedTests = new HashSet<>(personGradedTests);

        final AssignmentMap assignments = new AssignmentMap();
        HashMap<String, JsonAdaptedAssignment> jsonMap = assignmentMap.getAssignments();
        HashMap<AssignmentName, Assignment> actualMap = new HashMap<>();
        for (int i = 0; i < AssignmentInitialise.size(); i++) {
            AssignmentName assignmentName = AssignmentInitialise.getAssignmentName(i);
            if (!jsonMap.containsKey(assignmentName.toString())) {
                throw new IllegalValueException(AssignmentName.MESSAGE_CONSTRAINTS);
            }
            Assignment assignment = jsonMap.get(assignmentName.toString()).toModelType();
            actualMap.put(assignmentName, assignment);
        }
        assignments.setAssignmentMap(actualMap);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, assignments, modelGradedTests);
    }

}
