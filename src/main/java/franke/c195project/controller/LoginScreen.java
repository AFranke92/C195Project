package franke.c195project.controller;


import franke.c195project.DAO.DBConnection;
import franke.c195project.DAO.UserQuery;
import franke.c195project.model.Appointment;
import franke.c195project.model.User;
import franke.c195project.utilities.TimeUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * FXML controller class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */

public class LoginScreen  implements Initializable {

    public Label loginLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    Stage stage;
    Parent scene;
    ObservableList<Appointment> appointmentRemind = FXCollections.observableArrayList();
    private DateTimeFormatter dateTimeForm = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private ZoneId localZone = ZoneId.systemDefault();

    @FXML
    private TextField logInPasswordTxt;

    @FXML
    private TextField logInUserIDTxt;

    @FXML
    private TextField logInUserLocationTxt;

    @FXML
    private Button loginCancelBtn;

    @FXML
    private Button loginSubmitBtn;

    /**
     * Initializes controller class and accommodates language change
     * @param url url
     * @param resourceBundle resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            Locale locale = Locale.getDefault();
            //Locale locale = new Locale("fr");
            Locale.setDefault(locale);
            ZoneId zone = ZoneId.systemDefault();
            logInUserLocationTxt.setText(String.valueOf(zone));

            resourceBundle = ResourceBundle.getBundle("franke.c195project/Login", locale);

            loginSubmitBtn.setText(resourceBundle.getString("Submit"));
            loginCancelBtn.setText(resourceBundle.getString("Cancel"));
            loginLabel.setText(resourceBundle.getString("Login"));
            usernameLabel.setText(resourceBundle.getString("Username"));
            passwordLabel.setText(resourceBundle.getString("Password"));


        }
        catch (MissingResourceException mre) {
            System.out.println("Missing file:" + mre);
            mre.printStackTrace();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects user and appointment information and adds appointments to a reminder list.
     * @param userId the userID logging in
     */
    private void reminderList(int userId) {
        try {
            String sql = "SELECT appointments.Customer_ID, appointments.Appointment_ID, appointments.User_ID, appointments.Location, appointments.Title, appointments.Description, " +
                    "appointments.Start, appointments.End, appointments.Type, appointments.Contact_ID, contacts.Contact_ID, contacts.Contact_Name " +
                    "FROM appointments, users, contacts " +
                    "WHERE appointments.Contact_ID = contacts.Contact_ID AND appointments.User_ID = ? " +
                    "ORDER BY Start";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int appId = rs.getInt("Appointment_ID");
                String appType = rs.getString("Type");
                String appTitle = rs.getString("Title");
                String appDesc = rs.getString("Description");
                int custId = rs.getInt("Customer_ID");
                String appLoc = rs.getString("Location");
                String appContact = rs.getString("Contact_Name");
                int contactId = rs.getInt("Contact_ID");
                LocalDateTime appStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEnd = rs.getTimestamp("End").toLocalDateTime();

                appointmentRemind.add(new Appointment(appId, appTitle, appDesc, appLoc, appContact, appType, appStart, appEnd, custId, userId, contactId));

            }
        }
        catch (SQLException sql) {
            System.out.println("There is an error in the SQL statement");
            sql.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("There is an error outside of the SQL statement.");
            e.printStackTrace();
        }
    }

    /**
     * Alerts user if there is an upcoming appointment in 15 minutes or not
     * Lambda #1 Filters upcoming appointments to find appointment within 15 minutes, shortens code.
     */
    private void appAlert() {

        LocalDateTime current = LocalDateTime.now();
        LocalDateTime currentAdd = current.plusMinutes(15);

        FilteredList<Appointment> filtered = new FilteredList<>(appointmentRemind);

        filtered.setPredicate(line -> {
            LocalDateTime lineDt = LocalDateTime.parse(line.getAppStart().toString().substring(0,16), dateTimeForm);
            return lineDt.isAfter(current.minusMinutes(1)) && lineDt.isBefore(currentAdd);
        });

        if (filtered.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Upcoming Appointments");
            alert.setHeaderText("No Upcoming Appointments");
            alert.setContentText("You have no upcoming appointment within the next fifteen minutes");
            alert.showAndWait();

        }
        else {

            String appType = filtered.get(0).getAppType();
            String appDesc = filtered.get(0).getAppDescription();
            int custId = filtered.get(0).getCustId();
            String start = filtered.get(0).getAppStart().toString().substring(0, 16);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointment");
            alert.setHeaderText("Reminder! You have an upcoming appointment within the next fifteen minutes");
            alert.setContentText("Upcoming Appointment Details:\nCustomer: " + custId + "\nStarting: " + start + "\nAppointment Type: " + appType + "\nAppointment Description: " + appDesc);
            alert.showAndWait();

        }

    }


    /**
     * Closes the application
     * @param actionEvent cancel button selection
     */
    public void onActionLogInCancel(ActionEvent actionEvent) {

        DBConnection.closeConnection();
        System.exit(0);
    }

    /**
     * Verifies user is using a valid password.
     * @param userId the userId to check
     * @param password the password to check
     * @return True if password is correct. False if password is incorrect.
     * @throws SQLException throws SQL exception
     */
    private boolean isValidPass(int userId, String password) throws SQLException {
        Statement st = DBConnection.connection.createStatement();
        String sql = "SELECT Password FROM users WHERE User_ID ='" + userId + "'";
        ResultSet rst = st.executeQuery(sql);

        while (rst.next()) {
            if (rst.getString("Password").equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies login credentials and opens up application
     * Throws alert, in both English or French depending on system default,
     * if login credentials are incorrect
     * @param actionEvent submit button selection
     */
    public void onActionLogInSubmit(ActionEvent actionEvent) throws IOException, SQLException {

        Locale locale = Locale.getDefault();
        //Locale locale = new Locale("fr");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("franke.c195project/Login", locale);

        String userName = logInUserIDTxt.getText();
        String userPass = logInPasswordTxt.getText();
        int userId = UserQuery.validUser(userName, userPass);
        User user = new User(userId, userName, userPass);

        if (isValidPass(userId, userName)) {
            user.setUserId(userId);
            user.setUserName(userName);

            logInLogsPass(User.getUserName());
            reminderList(userId);
            appAlert();

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("CustomerTable.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        else {

            logInLogsFail(User.getUserName());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("Invalid"));
            alert.setHeaderText(resourceBundle.getString("Header"));
            alert.setContentText(resourceBundle.getString("Error"));
            Optional<ButtonType> rst = alert.showAndWait();
        }

    }

    /**
     * Logs successful login attempts
     * @param user the username attempting login
     */
    public void logInLogsPass(String user) {
        try {

            String flName = "login_activity.txt";
            BufferedWriter wrt = new BufferedWriter(new FileWriter(flName, true));
            wrt.append(TimeUtility.getTimeStamp() + " " + user + " Successful\n");
            System.out.println("New login recorded");
            wrt.flush();
            wrt.close();

        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Logs failed login attempts
     * @param user the username attempting login
     */
    public void logInLogsFail(String user) {
        try {

            String flName = "login_activity.txt";
            BufferedWriter wrt = new BufferedWriter(new FileWriter(flName, true));
            wrt.append(TimeUtility.getTimeStamp() + " " + user + " Failed\n");
            System.out.println("New login recorded");
            wrt.flush();
            wrt.close();

        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

}