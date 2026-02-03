package franke.c195project.controller;


import franke.c195project.DAO.AppointmentQuery;
import franke.c195project.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * FXML controller class
 * @author
 * Abigail Franke
 */

public class AppointmentTable implements Initializable {

    Parent scene;
    Stage stage;


    @FXML
    private TableColumn<Appointment, String> appointmentContactCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustIDCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDescCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentStartCol;

    @FXML
    private RadioButton appointmentTableAllRdBtn;

    @FXML
    private RadioButton appointmentTableMonthRdBtn;

    @FXML
    private RadioButton appointmentTableWeekRdBtn;

    @FXML
    private TableView<Appointment> appointmentTblview;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentUserIDCol;

    @FXML
    private ToggleGroup sourceTg;


    /**
     * Fills appointment table with all appointments
     * @param event all radio button selection
     * @throws SQLException SQL exception
     */
    @FXML
    void onActionAllApps(ActionEvent event) throws SQLException {
        try {
            ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
            if (allAppointments != null) {
                for (Appointment appointment : allAppointments) {
                    appointmentTblview.setItems(allAppointments);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filters appointment table by current week
     * Lambda #3 Filters appointments within a one week period, shortens code.
     * @param event week radio button selection
     * @throws SQLException throws SQL exception
     */
    @FXML
    void onActionWeekApps(ActionEvent event) throws SQLException {
        try {

            ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
            ObservableList<Appointment> weekApps = FXCollections.observableArrayList();
            LocalDateTime startWeek = LocalDateTime.now().minusMinutes(1);
            LocalDateTime endWeek = LocalDateTime.now().plusDays(7);

            if (allAppointments != null) {
                allAppointments.forEach(appointment -> {
                    if (appointment.getAppEnd().isAfter(startWeek) && appointment.getAppEnd().isBefore(endWeek)) {
                        weekApps.add(appointment);
                    }
                    appointmentTblview.setItems(weekApps);
                });
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filters appointment table by current month
     * Lambda #2 Filters appointments within a one month period, shortens code.
     * @param event month radio button selection
     */
    @FXML
    void onActionMonthApps(ActionEvent event) {
        try {

            ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
            ObservableList<Appointment> monthApps = FXCollections.observableArrayList();
            LocalDateTime currentStart = LocalDateTime.now().minusMonths(1);
            LocalDateTime currentEnd = LocalDateTime.now().plusMonths(1);

            if (allAppointments != null) {
                allAppointments.forEach(appointment -> {
                    if (appointment.getAppEnd().isAfter(currentStart) && appointment.getAppEnd().isBefore(currentEnd)) {
                        monthApps.add(appointment);
                    }
                    appointmentTblview.setItems(monthApps);
                });
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Opens Add Appointment scene
     * @param actionEvent add button selection
     * @throws IOException throws I/O exception
     */
    public void onActionAppAdd(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Takes selected appointment and sends appointment information to
     * edit appointment scene
     * @param actionEvent edit appointment button selection
     * @throws IOException throws I/O exception
     */
    public void onActionAppEdit(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("EditAppointment.fxml"));
        loader1.load();

        EditAppointment editAppointment = loader1.getController();
        editAppointment.sendApp(appointmentTblview.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader1.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Resets tableview
     * @throws SQLException throws SQL exception
     */
    private void resetTable() throws SQLException {

        appointmentTblview.setItems(AppointmentQuery.getAllAppointments());
    }

    /**
     * Deletes selected appointment
     * @param actionEvent delete button selection
     */
    public void onActionAppDelete(ActionEvent actionEvent) {
        try {

            int deleteAppId = appointmentTblview.getSelectionModel().getSelectedItem().getAppId();
            String deleteAppType = appointmentTblview.getSelectionModel().getSelectedItem().getAppType();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected appointment?\nAppointment ID: " + deleteAppId + "\nAppointment Type: " + deleteAppType);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                AppointmentQuery.deleteAppointment(deleteAppId);
                resetTable();

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Exits appointment tableview scene. Opens Customer tableview scene.
     * @param event back button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionBackBtn(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("CustomerTable.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Initializes controller class and populates tableview
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{

            ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
            appointmentTblview.setItems(AppointmentQuery.getAllAppointments());

            appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
            appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
            appointmentDescCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
            appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
            appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("appContact"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
            appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("appStart"));
            appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
            appointmentCustIDCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
            appointmentUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
