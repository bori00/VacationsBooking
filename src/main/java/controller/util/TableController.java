package controller.util;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.PropertyChangeObservable;
import service.view_models.ViewField;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.List;

public abstract class TableController<T> implements PropertyChangeListener {
    private final Class<T> type;
    private final TableView<T> tableView;

    public TableController(Class<T> type,
                           PropertyChangeObservable service,
                           TableView<T> tableView) {
        this.type = type;
        service.addPropertyChangeListener(this);
        this.tableView = tableView;
        setupColumns();
        reFillTable();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Some event");
        if (evt.getPropertyName().equals(PropertyChangeObservable.Events.NEW_ENTITY.toString())) {
            System.out.println("Add Event");
            tableView.getItems().add((T) evt.getNewValue());
        } else if (evt.getPropertyName().equals(PropertyChangeObservable.Events.REMOVED_ENTITY.toString())) {
            reFillTable();
        } else if (evt.getPropertyName().equals(PropertyChangeObservable.Events.EDITED_ENTITY.toString())) {
            reFillTable();
        }
    }

    private void setupColumns() {
        Field[] entityFields = type.getDeclaredFields();

        for (Field field : entityFields) {
            // check if the field is to be displayed
            if (field.isAnnotationPresent(ViewField.class)) {
                if (!field.getAnnotation(ViewField.class).displayed()) {
                    // current field is not suppoosed to be displayed
                    continue;
                }
            }

            // find name of the field to be displayed
            String fieldName;
            if (field.isAnnotationPresent(ViewField.class)) {
                fieldName = field.getAnnotation(ViewField.class).name();
            } else {
                fieldName = field.getName();
            }

            // add field as a column to the table
            TableColumn<T, String> column =
                    new TableColumn<>(fieldName);
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tableView.getColumns().add(column);
        }
    }

    public void reFillTable() {
        tableView.getItems().clear();
        List<T> rows = null;
        rows = getUpdatedEntries();
        for (T row : rows) {
            tableView.getItems().add(row);
        }
    }

    protected abstract List<T> getUpdatedEntries();
}
