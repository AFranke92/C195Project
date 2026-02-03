package franke.c195project.controller;


import franke.c195project.DAO.AppointmentQuery;
import franke.c195project.DAO.ContactQuery;
import franke.c195project.model.*;
import franke.c195project.utilities.TimeUtility;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;



/**
 * FXML controller class
 * @author
 * Abigail Franke
 */

public class AddAppointment implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private TextField addAppointAppIDTxt;

    @FXML
    private ComboBox<Contact> addAppointContactComBx;

    @FXML
    private TextField addAppointCustIDTxt;

    @FXML
    private TextField addAppointDescTxt;

    @FXML
    private TextField addAppointEndTxt;

    @FXML
    private TextField addAppointLocationTxt;

    @FXML
    private TextField addAppointStartTxt;

    @FXML
    private TextField addAppointTitleTxt;

    @FXML
    private TextField addAppointTypeTxt;

    @FXML
    private TextField addAppointUserIDTxt;

    @FXML
    private DatePicker appAppointEndDyDatePick;

    @FXML
    private ComboBox<LocalTime> addAppointEndTmCmBx;

    @FXML
    private DatePicker addAppointStDyDatePick;

    @FXML
    private ComboBox<LocalTime> addAppointStTmCmBx;

    /**
     * Checks if new appointment overlaps any appointment associated to customer. Adds appointment to database.
     * @param actionEvent save button selection
     */
    public void onActionAddAppSave(ActionEvent actionEvent) {
        try {

            ObservableList<Appointment> appointments = AppointmentQuery.addAppByID(Integer.parseInt(addAppointCustIDTxt.getText()));

            LocalDateTime bStart = LocalDateTime.of(addAppointStDyDatePick.getValue(), addAppointStTmCmBx.getValue());
            LocalDateTime bEnd = LocalDateTime.of(appAppointEndDyDatePick.getValue(), addAppointEndTmCmBx.getValue());

            boolean conflict = false;
            boolean timeMax = false;

            for (Appointment a : appointments) {

                LocalDateTime aStart = a.getAppStart();
                LocalDateTime aEnd = a.getAppEnd();

                if ((bStart.isAfter(aStart) || bStart.isEqual(aStart)) && bStart.isBefore(aEnd)) {

                    conflict = true;

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlapping Appointments");
                    alert.setHeaderText("Starting Appointment Time Is Invalid");
                    alert.setContentText("The appointment you are trying to save will overlap with another appointment held by this customer.");
                    alert.showAndWait();

                } else if (bEnd.isAfter(aStart) && (bEnd.isBefore(aEnd) || bEnd.isEqual(aEnd))) {

                    conflict = true;

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlapping Appointments");
                    alert.setHeaderText("Ending Appointment Time Is Invalid");
                    alert.setContentText("The appointment you are trying to save will overlap with another appointment held by this customer.");
                    alert.showAndWait();

                } else if ((bStart.isBefore(aStart) || bStart.isEqual(aStart)) && (bEnd.isAfter(aEnd) || bEnd.isEqual(aEnd))) {

                    conflict = true;

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Overlapping Appointments");
                    alert.setHeaderText("Appointment Encompasses Existing Appointment");
                    alert.setContentText("The appointment you are trying to save will overlap with another appointment held by this customer.");
                    alert.showAndWait();

                }
            }

            if (conflict == false) {

                if (bStart.isBefore(bEnd)) {

                    timeMax = false;

                    String appTitle = addAppointTitleTxt.getText();
                    String appDesc = addAppointDescTxt.getText();
                    String appLocation = addAppointLocationTxt.getText();
                    String appType = addAppointTypeTxt.getText();
                    Timestamp appStart = Timestamp.valueOf(addAppointStDyDatePick.getValue() + " " + addAppointStTmCmBx.getValue() + ":00");
                    Timestamp appEnd = Timestamp.valueOf(appAppointEndDyDatePick.getValue() + " " + addAppointEndTmCmBx.getValue() + ":00");
                    int custId = Integer.parseInt(addAppointCustIDTxt.getText());
                    int userId = Integer.parseInt(addAppointUserIDTxt.getText());
                    int contactId = addAppointContactComBx.getValue().getContactId();

                    AppointmentQuery.addAppointment(appTitle, appDesc, appLocation, appType, appStart, appEnd, custId, userId, contactId);

                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("AppointmentTable.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else {

                    timeMax = true;

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Inappropriate Appointment Times");
                    alert.setHeaderText("Appointment Times Need To Change");
                    alert.setContentText("Appointment start time is after appointment end time.");
                    alert.showAndWait();

                }
            }

        }
        catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * Cancels adding appointment and sends user back to appointment tableview
     * @param actionEvent cancel button selection
     * @throws IOException throws I/O exception
     */
    public void onActionAddAppCancel(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentTable.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Initilizes controller class and populates combo boxes
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<Contact> contact = ContactQuery.getAllContacts();
            addAppointContactComBx.setItems(contact);

            addAppointStTmCmBx.setItems(TimeUtility.getStartTimes());

            addAppointEndTmCmBx.setItems(TimeUtility.getEndTimes());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
