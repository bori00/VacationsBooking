package service.service_impl;

import model.IEntity;
import service.PropertyChangeObservable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractService<T extends IEntity> implements PropertyChangeObservable {
    protected final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("vacationbooking.mysql");


    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
