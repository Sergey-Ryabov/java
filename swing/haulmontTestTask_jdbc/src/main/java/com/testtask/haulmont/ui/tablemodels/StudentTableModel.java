package com.testtask.haulmont.ui.tablemodels;

import com.testtask.haulmont.model.Group;
import com.testtask.haulmont.model.Student;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Сергей
 */
public class StudentTableModel implements TableModel {

    private final Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private final List<Student> students;

    public StudentTableModel(List<Student> students) {
        this.students = students;
    }

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Id";
            case 1:
                return "Name";
            case 2:
                return "Surname";
            case 3:
                return "Patronymic";
            case 4:
                return "Birthday";
            case 5:
                return "Group";
        }
        return "Column with this index not found.";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Date.class;
            case 5:
                return Group.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Student getStudent(int rowIndex) {
        if (rowIndex < students.size()) {
            return students.get(rowIndex);
        }
        return null;

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return students.get(rowIndex).getId();
            case 1:
                return students.get(rowIndex).getName();
            case 2:
                return students.get(rowIndex).getSurname();
            case 3:
                return students.get(rowIndex).getPatronymic();
            case 4:
                return students.get(rowIndex).getBirthday();
            case 5:
                return students.get(rowIndex).getGroup();
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
