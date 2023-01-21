package franke.c195project.controller;


import franke.c195project.DAO.ContactQuery;
import franke.c195project.DAO.ReportsQuery;
import franke.c195project.model.Contact;
import franke.c195project.model.ReportSchedule;
import franke.c195project.model.ReportTotal;
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
import java.util.ResourceBundle;


/**
 * FXML controller class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */

public class Reports implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private ComboBox<Contact> reportSchdComBx;

    @FXML
    private TableColumn<ReportSchedule, Integer> reportsSchdAppIDCol;

    @FXML
    private TableColumn<ReportSchedule, Integer> reportsSchdCustIDCol;

    @FXML
    private TableColumn<ReportSchedule, String> reportsSchdDescCol;

    @FXML
    private TableColumn<ReportSchedule, LocalDateTime> reportsSchdEndCol;

    @FXML
    private TableColumn<ReportSchedule, LocalDateTime> reportsSchdStartCol;

    @FXML
    private TableView<ReportSchedule> reportsSchdTblview;

    @FXML
    private TableColumn<ReportSchedule, String> reportsSchdTitleCol;

    @FXML
    private TableColumn<ReportSchedule, String> reportsSchdTypeCol;

    @FXML
    private TableColumn<ReportTotal, Integer> reportsTotAppCountCol;

    @FXML
    private TableColumn<ReportTotal, String> reportsTotAppMonthCol;

    @FXML
    private TableView<ReportTotal> reportsTotAppTblview;

    @FXML
    private TableColumn<ReportTotal, String> reportsTotAppTypeCol;

    @FXML
    private TextField reportsTotCust;

    /**
     * Returns user to Customer tableview
     * @param actionEvent on back button selection
     * @throws IOException throws I/O exception
     */
    public void onActionReportsBack(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("CustomerTable.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Populates schedule tableview
     * @param actionEvent on contact selection
     * @throws SQLException throws SQL exception
     */
    public void onActionReportsContactCmBx(ActionEvent actionEvent) throws SQLException {

        ObservableList<ReportSchedule> appByContact = ReportsQuery.getAppByContact(reportSchdComBx.getValue().getContactId());
        reportsSchdTblview.setItems(appByContact);
        reportsSchdAppIDCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        reportsSchdTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        reportsSchdTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        reportsSchdDescCol.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
        reportsSchdStartCol.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        reportsSchdEndCol.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        reportsSchdCustIDCol.setCellValueFactory(new PropertyValueFactory<>("custId"));

    }

    /**
     * Initializes controller class and populates tableview and combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<ReportTotal> totalA = ReportsQuery.totalApps();
            reportsTotAppTblview.setItems(totalA);
            reportsTotAppMonthCol.setCellValueFactory(new PropertyValueFactory<>("appMonth"));
            reportsTotAppTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
            reportsTotAppCountCol.setCellValueFactory(new PropertyValueFactory<>("typeCount"));

            ObservableList<Contact> contacts = ContactQuery.getAllContacts();
            reportSchdComBx.setItems(contacts);

            reportsTotCust.setText(String.valueOf(ReportsQuery.totalCust()));

        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
