package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.ConsultationList;

/**
 * Represents a collection of consultations. This class is responsible for managing and manipulating
 * consultation data.
 */
public class ConsultationListBook implements ReadOnlyConsultationList {
    private final ConsultationList consultationList;

    /**
     * Initializes a new instance of ConsultationListBook.
     */
    public ConsultationListBook() {
        consultationList = new ConsultationList();
    }

    /**
     * Initializes a new instance of ConsultationListBook by copying data from an existing ReadOnlyConsultationList.
     *
     * @param toBeCopied The ReadOnlyConsultationList to copy data from.
     */
    public ConsultationListBook(ReadOnlyConsultationList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Sets the consultations list with the provided list of consultations.
     *
     * @param consultations The list of consultations to set.
     */
    public void setConsultationList(List<Consultation> consultations) {
        this.consultationList.setConsultationList(consultations);
    }

    /**
     * Resets the data in the ConsultationListBook by replacing it with the data from a ReadOnlyConsultationList.
     *
     * @param newData The ReadOnlyConsultationList to copy data from.
     */
    public void resetData(ReadOnlyConsultationList newData) {
        requireNonNull(newData);
        setConsultationList(newData.getConsultationList());
    }

    /**
     * Checks if the ConsultationListBook contains the provided consultation.
     *
     * @param consultation The consultation to check for existence.
     * @return True if the consultation exists in the list, false otherwise.
     */
    public boolean hasConsultation(Consultation consultation) {
        requireNonNull(consultation);
        return consultationList.contains(consultation);
    }

    /**
     * Adds a consultation to the ConsultationListBook.
     *
     * @param consultation The consultation to add.
     */
    public void addConsultation(Consultation consultation) {
        consultationList.addConsultation(consultation);
    }

    /**
     * Removes {@code key} from this {@code ConsultationListBook}.
     * {@code key} must exist in the ConsultationListBook.
     */
    public void removeConsultation(Consultation consultation) {
        consultationList.remove(consultation);
    }

    /**
     * Returns an observable list of consultations from the ConsultationListBook.
     *
     * @return An unmodifiable observable list of consultations.
     */
    public ObservableList<Consultation> getConsultationList() {
        return consultationList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConsultationListBook)) {
            return false;
        }

        ConsultationListBook otherConsultationList = (ConsultationListBook) other;
        return consultationList.equals(otherConsultationList.consultationList);
    }


}
