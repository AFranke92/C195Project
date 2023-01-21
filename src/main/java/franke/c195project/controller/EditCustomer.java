package franke.c195project.controller;


import franke.c195project.DAO.CountryQuery;
import franke.c195project.DAO.CustomerQuery;
import franke.c195project.DAO.FirstLevelQuery;
import franke.c195project.model.Country;
import franke.c195project.model.Customer;
import franke.c195project.model.FirstLevel;
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
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * FXML controller class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */

public class EditCustomer implements Initializable {

    Parent scene;
    Stage stage;
    Customer customerToEdit;

    @FXML
    private TextField editCustomerAddressTxt;

    @FXML
    private ComboBox<Country> editCustomerCountryComBx;

    @FXML
    private TextField editCustomerIDTxt;

    @FXML
    private TextField editCustomerNameTxt;

    @FXML
    private TextField editCustomerPhoneTxt;

    @FXML
    private TextField editCustomerPostTxt;

    @FXML
    private ComboBox<FirstLevel> editCustomerStProvComBx;

    /**
     * Saves edited customer information
     * @param actionEvent on save button selection
     */
    public void onActionEditCustSave(ActionEvent actionEvent) {

        try {

            if(!editCustomerNameTxt.getText().isEmpty() || !editCustomerAddressTxt.getText().isEmpty() || !editCustomerPostTxt.getText().isEmpty() || !editCustomerPhoneTxt.getText().isEmpty()) {

                int customerId = Integer.parseInt(editCustomerIDTxt.getText());
                String custName = editCustomerNameTxt.getText();
                String custAddress = editCustomerAddressTxt.getText();
                String custPost = editCustomerPostTxt.getText();
                String custPhone = editCustomerPhoneTxt.getText();
                int custDivis = editCustomerStProvComBx.getValue().getDivisId();

                CustomerQuery.updateCust(customerId, custName, custAddress, custPost, custPhone, custDivis);

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("CustomerTable.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Confirms and cancels changes made to customer being edited
     * @param actionEvent cancel button selection
     * @throws IOException throws I/O exception
     */
    public void onActionEditCustCancel(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all values. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("CustomerTable.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Populates selected customer values in correlating text/combo boxes
     * to be edited
     * @param customer the selected customer to edit
     */
    public void sendCustomer (Customer customer) {

        try {
            customerToEdit = customer;

            if (customer != null) {

                this.editCustomerIDTxt.setText(String.valueOf(customerToEdit.getCustId()));
                this.editCustomerNameTxt.setText(customerToEdit.getCustName());
                this.editCustomerAddressTxt.setText(customerToEdit.getCustAddress());
                this.editCustomerPostTxt.setText(customerToEdit.getCustPostal());
                this.editCustomerPhoneTxt.setText(customerToEdit.getCustPhone());

                Country country = CountryQuery.getDivisionCountry(customerToEdit.getCustDivisId());
                this.editCustomerCountryComBx.setValue(country);

                ObservableList<FirstLevel> firstLevelList = FirstLevelQuery.getCountryFirstLvl(country.getCountryId());
                editCustomerStProvComBx.setItems(firstLevelList);

                FirstLevel firstLevel = FirstLevelQuery.getFirstLvl(customerToEdit.getCustDivisId());
                this.editCustomerStProvComBx.setValue(firstLevel);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates state/province combo box
     * @param actionEvent on country selection
     */
    @FXML
    private void onActionCountryChange(ActionEvent actionEvent) {
        try {

            Country country = editCustomerCountryComBx.getValue();

            ObservableList<FirstLevel> firstLevelList = FirstLevelQuery.getCountryFirstLvl(country.getCountryId());
            editCustomerStProvComBx.setItems(firstLevelList);

            this.editCustomerStProvComBx.setValue(null);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes controller class and populates combo box
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<Country> country = CountryQuery.getAllCountries();
            editCustomerCountryComBx.setItems(country);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
