package service;

import model.IEntity;
import repository.RelationalDBImpl.RepositoryImpl;
import service.util.PropertyChangeObservable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public abstract class AbstractService<T extends IEntity> implements PropertyChangeObservable {
    protected final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("vacationbooking.mysql");

    /**
     * Specifies the event types that may occur in the observable Service and observers can
     * listen to.
     */
    public enum Events {
        /** Event fired when a new entity is saved. */
        NEW_ENTITY,
        /** Event fired when an entity is deleted. */
        REMOVED_ENTITY,
        /** Event fired when the data of an entity is deleted. */
        EDITED_ENTITY
    }


    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
