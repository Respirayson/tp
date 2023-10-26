package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.gradedtest.GradedTest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.session.Session;
import seedu.address.model.session.SessionNumber;
import seedu.address.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Session> PREDICATE_SHOW_ALL_SESSIONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<GradedTest> PREDICATE_SHOW_ALL_GRADED_TEST = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Consultation> PREDICATE_SHOW_ALL_CONSULTATIONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the user prefs' task list file path.
     */
    Path getTaskListFilePath();

    /**
     * Sets the user prefs' task list file path.
     */
    void setTaskListFilePath(Path taskListFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    //=========== SessionList =================================================================================
    Session findSessionBySessionNumber(SessionNumber sessionNumber);

    /**
     * Adds the given sesssion.
     */
    void addSession(Session session);

    /** Returns an unmodifiable view of the filtered session list */
    ObservableList<Session> getFilteredSessionList();

    /** Returns the SessionList */
    ReadOnlySessionList getSessionList();

    /**
     * Updates the filter of the filtered session list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSessionList(Predicate<Session> predicate);


    //=========== TaskList =================================================================================


    /**
     * Replaces task list data with the data in {@code taskList}.
     */
    void setTaskList(ReadOnlyTaskList taskList);

    /** Returns the TaskList */
    ReadOnlyTaskList getTaskList();

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task list.
     */
    boolean hasTask(Task task);

    /**
     * Deletes the given task.
     * The task must exist in the task list.
     */
    void deleteTask(Task target);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the task list.
     */
    void addTask(Task task);

    /**
     * Returns task at the given index.
     * {@code index} must be within the size of the task list.
     */
    Task getTask(int index);

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task list.
     */
    void setTask(Task target, Task editedTask);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Replaces task list data with the data in {@code taskList}.
     */
    void setGradedTestList(ReadOnlyGradedTestList gradedTestList);

    /** Returns the GradeTestList */
    ReadOnlyGradedTestList getGradedTestList();

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task list.
     */
    boolean hasGradedTest(GradedTest gradedTest);

    /**
     * Deletes the given gradedTest.
     * The gradedTest must exist in the gradedTest list.
     */
    void deleteGradedTest(GradedTest target);

    /**
     * Adds the given task.
     * {@code gradedTest} must not already exist in the gradedTest list.
     */
    void addGradedTest(GradedTest gradedTest);

    /**
     * Returns gradedTest at the given index.
     * {@code index} must be within the size of the gradedTest list.
     */
    GradedTest getGradedTest(int index);

    /**
     * Replaces the given task {@code target} with {@code editedGradedTest}.
     * {@code target} must exist in the address book.
     * The gradedTest identity of {@code editedGradedTest} must not be the same
     * as another existing gradedTest in the gradedTest List.
     */
    void setGradedTest(GradedTest target, GradedTest editedGradedTest);

    /**
     * Returns the user prefs' gradedTest list file path.
     */
    Path getGradedTestListFilePath();

    void setGradedTestListFilePath(Path gradedTestListFilePath);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if a consultation with the same identity as {@code task} exists in the consultation list.
     */
    boolean hasConsultation(Consultation consultation);

    /**
     * Adds the given consultation.
     */
    void addConsultation(Consultation consultation);

    /**
     * Deletes the given consultation.
     * The consultation must exist in the consultation list.
     */
    void deleteConsultation(Consultation consultationToDelete);

    /** Returns the ConsultationList */
    ReadOnlyConsultationList getConsultationList();

    /** Returns an unmodifiable view of the filtered consultation list */
    ObservableList<Consultation> getFilteredConsultationList();

    /**
     * Updates the filter of the filtered consultation list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredConsultationList(Predicate<Consultation> predicate);


    Person getMatchingStudentName(Name name);

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

}
