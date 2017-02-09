package com.testtask.haulmont.ui.dialogs;

import com.testtask.haulmont.model.Group;
import com.testtask.haulmont.model.Student;
import static com.testtask.haulmont.utils.Constants.*;
import java.awt.Button;
import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sery0814
 */
public class StudentAddEditDialog extends JDialog {

    private final JPanel panel;

    public StudentAddEditDialog(Window parentWindow, String mode, List<Student> students, final Student student, List<Group> groups) {
        super(parentWindow, mode + " student", DEFAULT_MODALITY_TYPE);
        setMinimumSize(new Dimension(400, 230));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel nameLabel = new JLabel("Name:");
        JLabel surnameLabel = new JLabel("Surname:");
        JLabel patronymicLabel = new JLabel("Patronymic:");
        JLabel birthdayLabel = new JLabel("Birthday:");
        JLabel groupLabel = new JLabel("Group:");

        final JTextField nameTextField = new JTextField();
        final JTextField surnameTextField = new JTextField();
        final JTextField patronymicTextField = new JTextField();
        final JTextField birthdayTextField = new JTextField();
        final JComboBox groupJComboBox = new JComboBox();

        for (Group group : groups) {
            groupJComboBox.addItem(group);
        }
        if (EDIT.equals(mode)) {
            nameTextField.setText(student.getName());
            surnameTextField.setText(student.getSurname());
            patronymicTextField.setText(student.getPatronymic());
            birthdayTextField.setText(student.getBirthday().toString());
        }

        final Window window = this.getOwner();
        Button okButton = new Button(OK);
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringUtils.isNotBlank(nameTextField.getText())
                        && StringUtils.isNotBlank(surnameTextField.getText())
                        && StringUtils.isNotBlank(patronymicTextField.getText())
                        && StringUtils.isNotBlank(birthdayTextField.getText())) {
                    try {
                        student.setName(nameTextField.getText());
                        student.setSurname(surnameTextField.getText());
                        student.setPatronymic(patronymicTextField.getText());
                        student.setBirthday(Date.valueOf(birthdayTextField.getText()));
                        student.setGroup((Group) groupJComboBox.getSelectedItem());
                        dispose();
                    } catch (IllegalArgumentException exception) {
                        JOptionPane.showMessageDialog(window, 
                                "Please, correctly enter date. Example: 2000-12-22", 
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(window, PLEASE_FILL_ATTRIBUTES, 
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        Button cancelButton = new Button(CANCEL);
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel = new JPanel();

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nameLabel)
                        .addComponent(surnameLabel)
                        .addComponent(patronymicLabel)
                        .addComponent(birthdayLabel)
                        .addComponent(groupLabel)
                        .addComponent(okButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nameTextField)
                        .addComponent(surnameTextField)
                        .addComponent(patronymicTextField)
                        .addComponent(birthdayTextField)
                        .addComponent(groupJComboBox)
                        .addComponent(cancelButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(surnameLabel)
                        .addComponent(surnameTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(patronymicLabel)
                        .addComponent(patronymicTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(birthdayLabel)
                        .addComponent(birthdayTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(groupLabel)
                        .addComponent(groupJComboBox))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(okButton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)
                        .addComponent(cancelButton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)));
        getContentPane().add(panel);
    }

}
