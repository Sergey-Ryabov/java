package com.testtask.haulmont.ui.dialogs;

import com.testtask.haulmont.model.Group;
import com.testtask.haulmont.utils.Constants;
import static com.testtask.haulmont.utils.Constants.*;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Сергей
 */
public class GroupAddEditDialog extends JDialog {

    private final JPanel panel;

    public GroupAddEditDialog(Window parentWindow, String mode, List<Group> groups, final Group group) {
        super(parentWindow, mode + " group", DEFAULT_MODALITY_TYPE);
        setMinimumSize(new Dimension(300, 150));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel numberLabel = new JLabel("Number:");
        JLabel facultyNameLabel = new JLabel("Faculty name:");

        final JTextField numberTextField = new JTextField();
        final JTextField facultyNameTextField = new JTextField();

        if (EDIT.equals(mode)) {
            numberTextField.setText(Integer.toString(group.getNumber()));
            facultyNameTextField.setText(group.getFacultyName());
        }

        final Window window = this.getOwner();
        Button okButton = new Button(OK);
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringUtils.isNotBlank(numberTextField.getText())
                        && StringUtils.isNotBlank(facultyNameTextField.getText())) {
                    try {
                        group.setNumber(Integer.parseInt(numberTextField.getText()));
                        group.setFacultyName(facultyNameTextField.getText());
                        dispose();
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(window, "Please, correctly enter number",
                                Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(window, PLEASE_FILL_ATTRIBUTES,
                            INFORMATION, JOptionPane.INFORMATION_MESSAGE);
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
                        .addComponent(numberLabel)
                        .addComponent(facultyNameLabel)
                        .addComponent(okButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(numberTextField)
                        .addComponent(facultyNameTextField)
                        .addComponent(cancelButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(numberLabel)
                        .addComponent(numberTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(facultyNameLabel)
                        .addComponent(facultyNameTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(okButton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)
                        .addComponent(cancelButton, COMPONENT_HEIGHT, COMPONENT_HEIGHT, COMPONENT_HEIGHT)));
        getContentPane().add(panel);
    }
}
