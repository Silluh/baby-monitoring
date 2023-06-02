package com.radek.example16.view;

import com.radek.example16.controller.mainButtonsPanel.SaveDataController;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class FormView extends JFrame implements ActionListener {

    private JXDatePicker textNewDate;
    private JSpinner textNewTime;
    private final Container container = getContentPane();

    public FormView() {

        setTitle("Form");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }

    public void initComponents() {

        JPanel mainPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // TOP - LEFT - BOTTOM - RIGHT
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(getDatePanel(), gbc);

        gbc.insets = new Insets(10, 10, 10, 10); // TOP - LEFT - BOTTOM - RIGHT
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(getButtonPanel(), gbc);

        container.add(mainPanel);
        pack();
    }

    private JPanel getDatePanel() {

        final JPanel panel = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // TOP - LEFT - BOTTOM - RIGHT
        gbc.gridx = 0;
        gbc.gridy = 0;
        final JLabel descriptionOldValue = new JLabel("Current value: ");
        panel.add(descriptionOldValue, gbc);

        Timestamp oldTimestamp = new SaveDataController(this).getTimestampFromDatabase();
        gbc.insets = new Insets(10, 10, 10, 10); // TOP - LEFT - BOTTOM - RIGHT
        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField displayValue;
        try {
            displayValue = new JTextField(oldTimestamp.toString(), 10);
        } catch (NullPointerException e) {
            displayValue = new JTextField("", 10);
        }
        panel.add(displayValue, gbc);

        gbc.insets = new Insets(10, 10, 10, 10); // TOP - LEFT - BOTTOM - RIGHT
        gbc.gridx = 0;
        gbc.gridy = 1;
        final JLabel descriptionNewValue = new JLabel("New value: ");
        panel.add(descriptionNewValue, gbc);

        gbc.insets = new Insets(10, 10, 10, 10); // TOP - LEFT - BOTTOM - RIGHT
        gbc.gridx = 1;
        gbc.gridy = 1;
        textNewDate = new JXDatePicker();
        panel.add(textNewDate, gbc);

        SpinnerDateModel model = new SpinnerDateModel(new Date(0), null, null, Calendar.HOUR_OF_DAY);
        textNewTime = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(textNewTime, "HH:mm:ss");
        textNewTime.setEditor(editor);
        gbc.insets = new Insets(10, 10, 10, 10); // TOP - LEFT - BOTTOM - RIGHT
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(textNewTime, gbc);

        return panel;
    }

    public long getIntTime() {

        return ((Date) textNewTime.getValue()).getTime();
    }

    public JXDatePicker getTextNewDate() {

        return textNewDate;
    }

    public JSpinner getTextNewTime() {

        return textNewTime;
    }

    public void setTextNewTime(JSpinner textNewTime) {

        this.textNewTime = textNewTime;
    }

    public void setTextNewDate(JXDatePicker textNewDate) {

        this.textNewDate = textNewDate;
    }

    private JPanel getButtonPanel() {

        final JPanel panel = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // TOP - LEFT - BOTTOM - RIGHT
        gbc.gridx = 0;
        gbc.gridy = 0;
        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveDataController(this));
        panel.add(saveButton, gbc);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
