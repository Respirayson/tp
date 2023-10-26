package seedu.address.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyConsultationList;
import seedu.address.model.ReadOnlyGradedTestList;
import seedu.address.model.ReadOnlySessionList;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.gradedtest.GradedTest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.session.Session;
import seedu.address.model.session.SessionNumber;
import seedu.address.model.task.Task;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getTaskListFilePath() {
        throw new AssertionError("This method should not be called.");

    }

    @Override
    public void setTaskListFilePath(Path taskListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTaskList(ReadOnlyTaskList taskList) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Task getTask(int index) {
        throw new AssertionError("This method should not be called.");
    }
    @Override
    public void deleteTask(Task target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasConsultation(Consultation consultation) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addConsultation(Consultation consultation) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteConsultation(Consultation consultationToDelete) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyConsultationList getConsultationList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Consultation> getFilteredConsultationList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredConsultationList(Predicate<Consultation> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Person getMatchingStudentName(Name name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addSession(Session session) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Session> getFilteredSessionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlySessionList getSessionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredSessionList(Predicate<Session> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Session findSessionBySessionNumber(SessionNumber sessionNumber) {
        throw new AssertionError("This method should not be called.");
    }
    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getGradedTestListFilePath() {
        throw new AssertionError("This method should not be called.");

    }

    @Override
    public void setGradedTestListFilePath(Path gradedTestListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGradedTestList(ReadOnlyGradedTestList gradedTestList) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyGradedTestList getGradedTestList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasGradedTest(GradedTest gradedTest) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteGradedTest(GradedTest target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GradedTest getGradedTest(int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addGradedTest(GradedTest gradedTest) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGradedTest(GradedTest target, GradedTest editedGradedTest) {
        throw new AssertionError("This method should not be called.");
    }
}
