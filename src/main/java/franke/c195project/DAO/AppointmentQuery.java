package franke.c195project.DAO;

import franke.c195project.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;


/**
 * DAO
 * @author
 * Abigail Franke
 */

public class AppointmentQuery {

    /**
     * Queries database for all appointments and places them in an observable list
     * @return observable list of all appointments
     * @throws SQLException throws SQL exception
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {

        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            int appId = rs.getInt("Appointment_ID");
            String appTitle = rs.getString("Title");
            String appDescription = rs.getString("Description");
            String appLocation = rs.getString("Location");
            String appContact = rs.getString("Contact_ID");
            String appType = rs.getString("Type");
            LocalDateTime appStart = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime appEnd = rs.getTimestamp("End").toLocalDateTime();
            int custId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appId, appTitle, appDescription, appLocation, appContact, appType,
                appStart, appEnd, custId, userId, contactId);
            appointmentObservableList.add(appointment);
        }

        return appointmentObservableList;
    }

    /**
     * Queries database for all appointments associated with a particular customer
     * @param custId the customer id to search
     * @return observable list of appointments associated to particular customer
     * @throws SQLException throws SQL exception
     */
    public static ObservableList<Appointment> addAppByID( int custId) throws SQLException {

        ObservableList<Appointment> addApp = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, custId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            int appId = rs.getInt("Appointment_ID");
            String appTitle = rs.getString("Title");
            String appDescription = rs.getString("Description");
            String appLocation = rs.getString("Location");
            String appContact = rs.getString("Contact_ID");
            String appType = rs.getString("Type");
            LocalDateTime appStart = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime appEnd = rs.getTimestamp("End").toLocalDateTime();
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appId, appTitle, appDescription, appLocation, appContact, appType,
                    appStart, appEnd, custId, userId, contactId);
            addApp.add(appointment);
        }
        return addApp;
    }

    /**
     * Queries database for all appointments associated to a particular customer,
     * excluding the appointment being edited
     * @param custId the customer id to search
     * @param appId the appointment id to exclude
     * @return observable list of appointments associated to particular customer excluding appointment being edited
     * @throws SQLException throws SQL exception
     */
    public static ObservableList<Appointment> editAppByID(int custId, int appId) throws SQLException {

            ObservableList<Appointment> editApp = FXCollections.observableArrayList();
            String sql = "SELECT * FROM appointments WHERE Customer_ID = ? AND Appointment_ID != ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, custId);
            ps.setInt(2, appId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appContact = rs.getString("Contact_ID");
                String appType = rs.getString("Type");
                LocalDateTime appStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEnd = rs.getTimestamp("End").toLocalDateTime();
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appId, appTitle, appDescription, appLocation, appContact, appType,
                        appStart, appEnd, custId, userId, contactId);
                editApp.add(appointment);
            }

            return editApp;
    }

    /**
     * Deletes appointment from database
     * @param customer the customer to delete the appointment from
     * @return returns result
     * @throws SQLException throws SQL exception
     */
    public static int deleteAppointment(int customer) throws SQLException {

        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, customer);
        int result = ps.executeUpdate();
        ps.close();
        return result;

    }

    /**
     * Adds appointment to database
     * @param appTitle the appointment title
     * @param appDesc the appointment description
     * @param appLocation the appointment location
     * @param appType the appointment type
     * @param appStart the appointment start
     * @param appEnd the appointment end
     * @param custId the customer ID tied to the appointment
     * @param userId the user ID tied to the appointment
     * @param contactId the contact ID tied to the appointment
     */
    public static void addAppointment(String appTitle, String appDesc, String appLocation, String appType, Timestamp appStart, Timestamp appEnd, int custId, int userId, int contactId) {

        try {
            String sql = "INSERT INTO appointments" +
            "(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)" +
            "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, appTitle);
            ps.setString(2, appDesc);
            ps.setString(3, appLocation);
            ps.setString(4, appType);
            ps.setTimestamp(5, Timestamp.valueOf(String.valueOf(appStart)));
            ps.setTimestamp(6, Timestamp.valueOf(String.valueOf(appEnd)));
            ps.setInt(7, custId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);

            ps.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits appointment in database
     * @param appId the appointment ID
     * @param appTitle the appointment title
     * @param appDesc the appointment description
     * @param appLocation the appointment location
     * @param appType the appointment type
     * @param appStart the appointment start
     * @param appEnd the appointment end
     * @param custId the customer ID tied to the appointment
     * @param userId the user ID tied to the appointment
     * @param contactId the contact ID tied to the appointment
     */
    public static void editAppointment(int appId, String appTitle, String appDesc, String appLocation, String appType, Timestamp appStart, Timestamp appEnd, int custId, int userId, int contactId) {
        try {
            String sql = "UPDATE appointments " +
                    "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                    "WHERE Appointment_ID = " + appId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, appTitle);
            ps.setString(2, appDesc);
            ps.setString(3, appLocation);
            ps.setString(4, appType);
            ps.setTimestamp(5, Timestamp.valueOf(String.valueOf(appStart)));
            ps.setTimestamp(6, Timestamp.valueOf(String.valueOf(appEnd)));
            ps.setInt(7, custId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);

            ps.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
