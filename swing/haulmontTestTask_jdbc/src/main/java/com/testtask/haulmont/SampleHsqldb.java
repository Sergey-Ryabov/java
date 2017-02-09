package com.testtask.haulmont;

import com.testtask.haulmont.ui.MainFrame;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Сергей
 */
public class SampleHsqldb {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainFrame("TestTask").setVisible(true);
                } catch (HeadlessException ex) {
                    Logger.getLogger(SampleHsqldb.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(SampleHsqldb.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
}
