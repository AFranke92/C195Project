package franke.c195project.controller;


import franke.c195project.DAO.CustomerQuery;
import franke.c195project.model.Customer;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static franke.c195project.DAO.CustomerQuery.getAllCustomers;
import static franke.c195project.DAO.DBConnection.*;
import static franke.c195project.model.Customer.lookUpCust;
import static franke.c195project.model.Customer.lookupCust;


/**
 * FXML controller class
 * @author
 * Abigail Franke
 */

public class CustomerTable implements Initializable {

    Parent scene;
    Stage stage;
    Customer foundCust;

    @FXML
    private TableColumn<Customer, String> customerTableAddressCol;

    @FXML
    private TableColumn<Customer, String> customerTableCountryCol;

    @FXML
    private TableColumn<Customer, Integer> customerTableIDCol;

    @FXML
    private TableColumn<Customer, String> customerTableNameCol;

    @FXML
    private TableColumn<Customer, Integer> customerTablePhoneCol;

    @FXML
    private TableColumn<Customer, Integer> customerTablePostCol;

    @FXML
    private TextField customerTableSearchTxt;

    @FXML
    private TableColumn<Customer, String> customerTableStProvCol;

    @FXML
    private TableView<Customer> customerTblview;

    /**
     * CLoses DB connection and application
     * @param actionEvent on logout button selection
     */
    public void onActionCustLogOut(ActionEvent actionEvent) {

        closeConnection();
        System.exit(0);

    }

    /**
     * Used as an ObservableList in customer search
     * @param name of searched customer
     * @return the customer(s) with matching name(s)
     * @throws SQLException throws SQL exception
     */
    public ObservableList<Customer> filterCustomers(String name) throws SQLException {
        ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = getAllCustomers();

        for (Customer customer : allCustomers) {
            if (customer.getCustName().contains(name)) {
                filteredCustomers.add(customer);
            }
        }
        return filteredCustomers;
    }

    /**
     * Checks search field for an integer or string
     * Searches the customers tableview for matching parameters
     * @param event enter key pressed
     * @throws SQLException throws SQL exception
     */
    @FXML
    void onActionCustSearch(ActionEvent event) throws SQLException {

        String searchCust = customerTableSearchTxt.getText();

        ObservableList<Customer> customers = filterCustomers(searchCust);

        if (searchCust.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Customer Search Warning");
            alert.setHeaderText("There were no customers found.");
            alert.setContentText("No customer ID or name was entered");
            alert.showAndWait();
            customerTblview.setItems(getAllCustomers());
        }
        else {
            boolean search = false;

            try {
                foundCust = lookupCust(Integer.parseInt(searchCust));

                if (foundCust != null) {
                    ObservableList<Customer> customers1 = FXCollections.observableArrayList();
                    customers1.add(foundCust);
                    customerTblview.setItems(customers1);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Customer Search Warning");
                    alert.setHeaderText("There were no customers found.");
                    alert.setHeaderText("Search term entered does not match any customer ID.");
                    alert.showAndWait();
                    customerTblview.setItems(getAllCustomers());
                }
            }
            catch (NumberFormatException e) {

                ObservableList<Customer> allCusts = getAllCustomers();

                if (allCusts.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Customer Search Warning");
                    alert.setHeaderText("There were no customers found.");
                    alert.setContentText("There are no customers in customer list.\nPlease add customers to list first.");
                    alert.showAndWait();
                    customerTblview.setItems(getAllCustomers());
                }
                else {
                    for (Customer c : allCusts) {
                        if (c.getCustName().contains(searchCust)) {
                            search = true;
                            customers = lookUpCust(searchCust);
                            customerTblview.setItems(customers);
                        }
                    }
                    if (!search) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Customer Search Warning");
                        alert.setHeaderText("There were no customers found.");
                        alert.setContentText("The searched term does not match any customer name.");
                        alert.showAndWait();
                        customerTblview.setItems(getAllCustomers());
                    }
                }
            }
        }
        customerTableSearchTxt.setText("");
    }

    /**
     * Opens Appointment tableview
     * @param actionEvent Appointments button selection
     * @throws IOException throws I/O exception
     */
    public void onActionAppointments(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentTable.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Opens add customer scene
     * @param actionEvent add customer button selection
     * @throws IOException throws I/O exception
     */
    public void onActionCustAdd(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Takes selected customer and sends customer information to
     * edit customer scene
     * @param actionEvent edit customer button selection
     * @throws IOException throws I/O exception
     */
    public void onActionCustEdit(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("EditCustomer.fxml"));
        loader1.load();

        EditCustomer editCustomer = loader1.getController();
        editCustomer.sendCustomer(customerTblview.getSelectionModel().getSelectedItem());

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
        customerTblview.setItems(CustomerQuery.getAllCustomers());
    }

    /**
     * Checks is a customer is selected.
     * If customer is selected, checks and confirms customer delete
     * @param actionEvent delete customer button selection
     */
    public void onActionCustDelete(ActionEvent actionEvent) {

        try {

            int deleteCustId = customerTblview.getSelectionModel().getSelectedItem().getCustId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected customer with customer ID: " + deleteCustId + "?\nThis will also delete this customers appointments.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                CustomerQuery.deleteCust(deleteCustId);
                resetTable();

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens reports scene
     * @param event reports button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionCustReports(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Initializes controller class and populates customer tableview
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<Customer> allCustomers = getAllCustomers();
            customerTblview.setItems(getAllCustomers());
            customerTableIDCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
            customerTableNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
            customerTableAddressCol.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
            customerTableCountryCol.setCellValueFactory(new PropertyValueFactory<>("custCountry"));
            customerTableStProvCol.setCellValueFactory(new PropertyValueFactory<>("custDivisName"));
            customerTablePostCol.setCellValueFactory(new PropertyValueFactory<>("custPostal"));
            customerTablePhoneCol.setCellValueFactory(new PropertyValueFactory<>("custPhone"));

        }

        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
