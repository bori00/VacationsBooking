package service;

import java.beans.PropertyChangeListener;

/**
 * Defines a generic interface for Observable objects (as specified in the Observer pattern.
 */
public interface PropertyChangeObservable {

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Specifies the event types that may occur in the observable Service and observers can
     * listen to.
     */
    enum Events {
        /**
         * Event fired when a new entity is saved.
         */
        NEW_ENTITY,
        /**
         * Event fired when an entity is deleted.
         */
        REMOVED_ENTITY,
        /**
         * Event fired when the data of an entity is deleted.
         */
        EDITED_ENTITY
    }
}
