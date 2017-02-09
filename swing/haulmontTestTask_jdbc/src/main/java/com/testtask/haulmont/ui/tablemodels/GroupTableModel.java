package com.testtask.haulmont.ui.tablemodels;

import com.testtask.haulmont.model.Group;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Сергей
 */
public class GroupTableModel implements TableModel {

    private final Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private final List<Group> groups;

    public GroupTableModel(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public int getRowCount() {
        return groups.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Id";
            case 1:
                return "Number";
            case 2:
                return "Faculty name";
        }
        return "Column with this index not found.";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Group getGroup(int rowIndex) {
        if(rowIndex < groups.size()){
            return groups.get(rowIndex);
        }
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return groups.get(rowIndex).getId();
            case 1:
                return groups.get(rowIndex).getNumber();
            case 2:
                return groups.get(rowIndex).getFacultyName();
        }
        return "no value";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

}
