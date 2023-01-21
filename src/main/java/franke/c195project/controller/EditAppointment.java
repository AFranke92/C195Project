package franke.c195project.controller;


import franke.c195project.DAO.AppointmentQuery;
import franke.c195project.DAO.ContactQuery;
import franke.c195project.model.Appointment;
import franke.c195project.model.Contact;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


/**
 * FXML controller class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */

public class EditAppointment implements Initializable {

    Parent scene;
    Stage stage;

    Appointment appToEdit;

    @FXML
    private TextField editAppointAppIDTxt;

    @FXML
    private ComboBox<Contact> editAppointContactComBx;

    @FXML
    private TextField editAppointCustIDTxt;

    @FXML
    private TextField editAppointDescTxt;

    @FXML
    private TextField editAppointLocationTxt;

    @FXML
    private TextField editAppointTitleTxt;

    @FXML
    private TextField editAppointTypeTxt;

    @FXML
    private TextField editAppointUserIDTxt;

    @FXML
    private DatePicker editAppointEndDyDatePick;

    @FXML
    private ComboBox<LocalTime> editAppointEndTmCmBx;

    @FXML
    private DatePicker editAppointStDyDatePick;

    @FXML
    private ComboBox<LocalTime> editAppointStTmCmBx;

    /**
     * Checks if edited appointment overlaps any appointment associated to customer. Updates edited appointment in database
     * @param actionEvent save button selection
     */
    public void onActionEditAppSave(ActionEvent actionEvent) {

        try {

            ObservableList<Appointment> appointments = AppointmentQuery.editAppByID(Integer.parseInt(editAppointCustIDTxt.getText()), Integer.parseInt(editAppointAppIDTxt.getText()));

            LocalDateTime bStart = LocalDateTime.of(editAppointStDyDatePick.getValue(), editAppointStTmCmBx.getValue());
            LocalDateTime bEnd = LocalDateTime.of(editAppointEndDyDatePick.getValue(), editAppointEndTmCmBx.getValue());

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

                }
                else if (bEnd.isAfter(aStart) && (bEnd.isBefore(aEnd) || bEnd.isEqual(aEnd))) {

                    conflict = true;

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlapping Appointments");
                    alert.setHeaderText("Ending Appointment Time Is Invalid");
                    alert.setContentText("The appointment you are trying to save will overlap with another appointment held by this customer.");
                    alert.showAndWait();

                }
                else if ((bStart.isBefore(aStart) || bStart.isEqual(aStart)) && (bEnd.isAfter(aEnd) || bEnd.isEqual(aEnd))) {

                    conflict = true;

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Overlapping Appointments");
                    alert.setHeaderText("Appointment Encompasses Existing Appointment");
                    alert.setContentText("The appointment you are trying to save will overlap with another appointment held by this customer.");
                    alert.showAndWait();

                }
            }

            if (conflict == false) {

                if (bStart.isBefore(bEnd)) {

                    timeMax = false;

                    int appId = Integer.parseInt(editAppointAppIDTxt.getText());
                    String appTitle = editAppointTitleTxt.getText();
                    String appDesc = editAppointDescTxt.getText();
                    String appLocation = editAppointLocationTxt.getText();
                    String appType = editAppointTypeTxt.getText();
                    Timestamp appStart = Timestamp.valueOf(editAppointStDyDatePick.getValue() + " " + editAppointStTmCmBx.getValue() + ":00");
                    Timestamp appEnd = Timestamp.valueOf(editAppointEndDyDatePick.getValue() + " " + editAppointEndTmCmBx.getValue() + ":00");
                    int custId = Integer.parseInt(editAppointCustIDTxt.getText());
                    int userId = Integer.parseInt(editAppointUserIDTxt.getText());
                    int contactId = editAppointContactComBx.getValue().getContactId();

                    AppointmentQuery.editAppointment(appId, appTitle, appDesc, appLocation, appType, appStart, appEnd, custId, userId, contactId);

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
     * Cancels editing appointment and sends user back to appointment tableview
     * @param event cancel button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionEditAppCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentTable.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Populates selected appointment values in correlating text/combo boxes
     * to be edited
     * @param appointment the selected appointment to edit
     */
    public void sendApp (Appointment appointment) {

        try {
            appToEdit = appointment;

            if (appointment != null) {

                editAppointAppIDTxt.setText(String.valueOf(appToEdit.getAppId()));
                editAppointTitleTxt.setText(String.valueOf(appToEdit.getAppTitle()));
                editAppointDescTxt.setText(String.valueOf(appToEdit.getAppDescription()));
                editAppointLocationTxt.setText(String.valueOf(appToEdit.getAppLocation()));
                editAppointTypeTxt.setText(String.valueOf(appToEdit.getAppType()));
                editAppointStDyDatePick.setValue(LocalDate.parse(String.valueOf(appToEdit.getAppStart().toLocalDate())));
                editAppointStTmCmBx.setValue(appToEdit.getAppStart().toLocalTime());
                editAppointEndDyDatePick.setValue(LocalDate.parse(String.valueOf(appToEdit.getAppEnd().toLocalDate())));
                editAppointEndTmCmBx.setValue(appToEdit.getAppEnd().toLocalTime());
                editAppointCustIDTxt.setText(String.valueOf(appToEdit.getCustId()));
                editAppointUserIDTxt.setText(String.valueOf(appToEdit.getUserId()));

                Contact contact = ContactQuery.getContactName(appToEdit.getContactId());
                editAppointContactComBx.setValue(contact);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes controller class and populates combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<Contact> contact = ContactQuery.getAllContacts();
            editAppointContactComBx.setItems(contact);

            editAppointStTmCmBx.setItems(TimeUtility.getStartTimes());

            editAppointEndTmCmBx.setItems(TimeUtility.getEndTimes());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
