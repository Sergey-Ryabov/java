package com.testtask.haulmont.ui;

import com.testtask.haulmont.ui.dialogs.StudentAddEditDialog;
import com.testtask.haulmont.ui.dialogs.GroupAddEditDialog;
import com.testtask.haulmont.dao.GroupDao;
import com.testtask.haulmont.dao.StudentDao;
import com.testtask.haulmont.dao.hsqldb.HsqlDaoFactory;
import com.testtask.haulmont.model.Group;
import com.testtask.haulmont.model.Student;
import com.testtask.haulmont.ui.tablemodels.GroupTableModel;
import com.testtask.haulmont.ui.tablemodels.StudentTableModel;
import com.testtask.haulmont.utils.Constants;
import static com.testtask.haulmont.utils.Constants.*;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.*;
import javax.swing.table.TableRowSorter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Сергей
 */
public class MainFrame extends JFrame {

    HsqlDaoFactory daoFactory = new HsqlDaoFactory();
    Connection connection = daoFactory.getConnection();
    Object[] options = {OK, CANCEL};
    Group emptyGroup = new Group();

    public MainFrame(String title) throws SQLException {
        super(title);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel groupsPanel = new JPanel();

        tabbedPane.addTab("Groups", groupsPanel);

        daoFactory.createDb(connection);
        final GroupDao groupDao = daoFactory.getGroupDao(connection);
        final List<Group> groups = groupDao.getAll();

        GroupTableModel groupTableModel = new GroupTableModel(groups);
        final JTable groupsTable = new JTable(groupTableModel);
        final JScrollPane groupsScroller = new JScrollPane(groupsTable);

        final Button addGroupBotton = new Button("addGroupBotton");
        addGroupBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Group group = new Group();
                Window parentWindow = SwingUtilities.windowForComponent(addGroupBotton);
                GroupAddEditDialog dialog = new GroupAddEditDialog(parentWindow, ADD, groups, group);
                dialog.setLocationRelativeTo(addGroupBotton);
                dialog.pack();
                dialog.setVisible(true);

                try {
                    if (StringUtils.isNotBlank(group.getFacultyName())) {
                        groups.add(groupDao.createGroup(group.getNumber(), group.getFacultyName()));
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(parentWindow, FAILED_ADD + GROUP,
                            Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                }
                groupsTable.revalidate();
                groupsScroller.repaint();
            }
        });
        Button editGroupBotton = new Button("editGroupBotton");
        editGroupBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window parentWindow = SwingUtilities.windowForComponent(addGroupBotton);
                if (groupsTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(parentWindow, PLEASE_SELECT_ROW,
                            INFORMATION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Group group = groups.get(groupsTable.getSelectedRow());
                    GroupAddEditDialog dialog = new GroupAddEditDialog(parentWindow, EDIT, groups, group);
                    dialog.setLocationRelativeTo(addGroupBotton);
                    dialog.pack();
                    dialog.setVisible(true);
                    try {
                        groupDao.update(group);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(parentWindow, FAILED_UPDATE + GROUP,
                                Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                    }
                    groupsTable.revalidate();
                    groupsScroller.repaint();
                }

            }
        });
        final Button deleteGroupBotton = new Button("deleteGroupBotton");
        deleteGroupBotton.setSize(WIDTH, COMPONENT_HEIGHT);
        deleteGroupBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window parentWindow = SwingUtilities.windowForComponent(deleteGroupBotton);
                if (groupsTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(parentWindow, PLEASE_SELECT_ROW,
                            INFORMATION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Group group = groups.get(groupsTable.getSelectedRow());
                    int n = JOptionPane.showOptionDialog(parentWindow,
                            DELETE_QUESTION_PART + group,
                            CONFIRM_DELETE + GROUP,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    if (n == JOptionPane.YES_OPTION) {
                        try {
                            groupDao.delete(group);
                            groups.remove(groupsTable.getSelectedRow());
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(parentWindow, FAILED_DELETE + GROUP,
                                    Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                        }
                        groupsTable.revalidate();
                    }
                }

            }
        });

        GroupLayout groupLayout = new GroupLayout(groupsPanel);
        groupsPanel.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                .addComponent(groupsScroller)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(addGroupBotton)
                        .addComponent(editGroupBotton)
                        .addComponent(deleteGroupBotton)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addComponent(groupsScroller)
                .addGroup(groupLayout.createParallelGroup(BASELINE)
                        .addComponent(addGroupBotton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)
                        .addComponent(editGroupBotton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)
                        .addComponent(deleteGroupBotton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)));

        JComponent studentsPanel = new JPanel();
        tabbedPane.addTab("Students", studentsPanel);
        add(tabbedPane);

        final StudentDao studentDao = daoFactory.getStudentDao(connection);
        final List<Student> students = studentDao.getAll();

        StudentTableModel studentTableModel = new StudentTableModel(students);
        final JTable studentsTable = new JTable(studentTableModel);
        final JScrollPane studentsScroller = new JScrollPane(studentsTable);

        JLabel surnameFilterLabel = new JLabel("Enter student's surname: ");
        JLabel groupFilterLabel = new JLabel("Enter student's group number: ");
        final JTextField surnameFilterTextField = new JTextField();
        final JTextField groupFilterTextField = new JTextField();
        Button filterButton = new Button(OK);

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentsTable.getRowSorter().allRowsChanged();
                studentsTable.revalidate();
                studentsScroller.repaint();
            }
        });

        Object o = new Object();
        //
        RowFilter<StudentTableModel, Integer> filter = new RowFilter<StudentTableModel, Integer>() {
            @Override
            public boolean include(RowFilter.Entry<? extends StudentTableModel, ? extends Integer> entry) {
                boolean isNeedShowBySurname = false;
                boolean isNeedShowByGroup = false;
                Student studentModel = entry.getModel().getStudent(entry.getIdentifier());
                if (StringUtils.isNotBlank(surnameFilterTextField.getText())) {
                    if (surnameFilterTextField.getText().equalsIgnoreCase(studentModel.getSurname())) {
                        isNeedShowBySurname = true;
                    }
                } else {
                    isNeedShowBySurname = true;
                }
                if (StringUtils.isNotBlank(groupFilterTextField.getText())) {
                    try {
                        if (Integer.parseInt(groupFilterTextField.getText()) == studentModel.getGroup().getNumber()) {
                            isNeedShowByGroup = true;
                        }
                    } catch (NumberFormatException exception) {
                    }
                } else {
                    isNeedShowByGroup = true;
                }
                return isNeedShowBySurname && isNeedShowByGroup;
            }
        };

        TableRowSorter<StudentTableModel> sorter = new TableRowSorter<>(studentTableModel);
        sorter.setRowFilter(filter);
        studentsTable.setRowSorter(sorter);

        //
        final Button addStudentBotton = new Button("addStudentBotton");
        addStudentBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student student = new Student();
                Window parentWindow = SwingUtilities.windowForComponent(addStudentBotton);
                StudentAddEditDialog dialog = new StudentAddEditDialog(parentWindow, ADD,
                        students, student, groups);
                dialog.setLocationRelativeTo(addStudentBotton);
                dialog.pack();
                dialog.setVisible(true);

                try {
                    if (student.getBirthday() != null && !emptyGroup.equals(student.getGroup())) {
                        students.add(studentDao.createStudent(student.getName(), student.getSurname(), student.getPatronymic(), student.getBirthday(), student.getGroup()));
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(parentWindow, FAILED_ADD + STUDENT,
                            Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                }
                studentsTable.getRowSorter().allRowsChanged();
                studentsTable.revalidate();
                studentsScroller.repaint();
            }
        });
        final Button editStudentBotton = new Button("editStudentBotton");
        editStudentBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window parentWindow = SwingUtilities.windowForComponent(editStudentBotton);
                if (studentsTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(parentWindow, PLEASE_SELECT_ROW,
                            INFORMATION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Student student = students.get(studentsTable.getSelectedRow());
                    StudentAddEditDialog dialog = new StudentAddEditDialog(parentWindow, EDIT, students, student, groups);
                    dialog.setLocationRelativeTo(editStudentBotton);
                    dialog.pack();
                    dialog.setVisible(true);

                    try {
                        studentDao.update(student);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(parentWindow, FAILED_UPDATE + STUDENT,
                                Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                    }
                    studentsTable.getRowSorter().allRowsChanged();
                    studentsTable.revalidate();
                    studentsScroller.repaint();
                }

            }
        });
        final Button deleteStudentBotton = new Button("deleteStudentBotton");
        deleteStudentBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window parentWindow = SwingUtilities.windowForComponent(deleteStudentBotton);
                if (studentsTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(parentWindow, PLEASE_SELECT_ROW,
                            INFORMATION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Student student = students.get(studentsTable.getSelectedRow());
                    int n = JOptionPane.showOptionDialog(parentWindow,
                            DELETE_QUESTION_PART + student,
                            CONFIRM_DELETE + STUDENT,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    if (n == JOptionPane.YES_OPTION) {
                        try {
                            studentDao.delete(student);
                            students.remove(studentsTable.getSelectedRow());
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(parentWindow, FAILED_DELETE + STUDENT,
                                    Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                        }
                        studentsTable.getRowSorter().allRowsChanged();
                        studentsTable.revalidate();
                        studentsScroller.repaint();
                    }
                }
            }
        });

        GroupLayout studentsLayout = new GroupLayout(studentsPanel);
        studentsPanel.setLayout(studentsLayout);
        studentsLayout.setAutoCreateGaps(true);
        studentsLayout.setAutoCreateContainerGaps(true);

        studentsLayout.setHorizontalGroup(studentsLayout.createParallelGroup()
                .addGroup(studentsLayout.createSequentialGroup()
                        .addComponent(surnameFilterLabel)
                        .addComponent(surnameFilterTextField)
                        .addComponent(groupFilterLabel)
                        .addComponent(groupFilterTextField)
                        .addComponent(filterButton))
                .addComponent(studentsScroller)
                .addGroup(studentsLayout.createSequentialGroup()
                        .addComponent(addStudentBotton)
                        .addComponent(editStudentBotton)
                        .addComponent(deleteStudentBotton)));

        studentsLayout.setVerticalGroup(studentsLayout.createSequentialGroup()
                .addGroup(studentsLayout.createParallelGroup(BASELINE)
                        .addComponent(surnameFilterLabel, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)
                        .addComponent(surnameFilterTextField, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)
                        .addComponent(groupFilterLabel, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)
                        .addComponent(groupFilterTextField, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)
                        .addComponent(filterButton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT))
                .addComponent(studentsScroller)
                .addGroup(studentsLayout.createParallelGroup(BASELINE)
                        .addComponent(addStudentBotton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)
                        .addComponent(editStudentBotton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)
                        .addComponent(deleteStudentBotton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)));
        pack();
    }

}
