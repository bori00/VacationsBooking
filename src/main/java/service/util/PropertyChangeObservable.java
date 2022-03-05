package service.util;

import java.beans.PropertyChangeListener;

/**
 * Defines a generic interface for Observable objects (as specified in the Observer pattern.
 */
public interface PropertyChangeObservable {

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
