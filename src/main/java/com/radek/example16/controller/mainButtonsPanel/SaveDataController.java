package com.radek.example16.controller.mainButtonsPanel;

import com.radek.example16.view.FormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class SaveDataController implements ActionListener {

    private final FormView formView;
    private Statement statement;
    private Connection conn;

    public SaveDataController(FormView formView) {

        this.formView = formView;
        initDatabase();
        createDatabase();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            saveDataToDatabase();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void saveDataToDatabase() {

        String query = "INSERT INTO userFormExample16(Date) VALUES (?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            Timestamp timestamp = getTimeStampFromView();
            statement.setTimestamp(1, timestamp);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Timestamp getTimestampFromDatabase() {

        ResultSet rs = null;
        Timestamp timestamp = null;
        try {
            initDatabase();
            rs = statement.executeQuery("SELECT Date, Id FROM userFormExample16 ORDER BY Id DESC LIMIT 1");
            System.out.println("Contents of the table userFormExample16:");
            while (rs.next()) {
                timestamp = rs.getTimestamp("Date");
                System.out.print("Id: " + rs.getInt("Id"));
                System.out.print(" - Date: " + rs.getTimestamp("Date"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return timestamp;
    }

    private Timestamp getTimeStampFromView() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formView.getTextNewDate().getDate());
        long time = formView.getIntTime();
        int hours = (int) (time / (1000 * 60 * 60)) % 24;
        int minutes = (int) (time / (1000 * 60)) % 60;
        int seconds = (int) (time / 1000) % 60;
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);
        calendar.add(Calendar.SECOND, seconds);
        return new Timestamp(calendar.getTimeInMillis());
    }

    private void createDatabase() {

        try {
            String query = "CREATE TABLE userFormExample16(Id INT NOT NULL GENERATED ALWAYS AS IDENTITY, Date Timestamp)";
            System.out.println(statement.execute(query));
        } catch (SQLException e) {
            System.out.println("Database is already created");
        }
    }

    private void initDatabase() {

        try {
            conn = DriverManager.getConnection("jdbc:h2:~/test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
