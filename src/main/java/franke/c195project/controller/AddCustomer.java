package franke.c195project.controller;


import franke.c195project.DAO.CountryQuery;
import franke.c195project.DAO.CustomerQuery;
import franke.c195project.DAO.FirstLevelQuery;
import franke.c195project.model.Country;
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

public class AddCustomer implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private TextField addCustomerAddressTxt;

    @FXML
    private ComboBox<Country> addCustomerCountryComBx;

    @FXML
    private TextField addCustomerIDTxt;

    @FXML
    private TextField addCustomerNameTxt;

    @FXML
    private TextField addCustomerPhoneTxt;

    @FXML
    private TextField addCustomerPostTxt;

    @FXML
    private ComboBox<FirstLevel> addCustomerStProvComBx;

    /**
     * Saves new customer information
     * @param actionEvent on save button selection
     * @throws IOException throws I/O exception
     */
    public void onActionAddCustSave(ActionEvent actionEvent) throws IOException {

        if(!addCustomerNameTxt.getText().isEmpty() || !addCustomerAddressTxt.getText().isEmpty() || !addCustomerPostTxt.getText().isEmpty() || !addCustomerPhoneTxt.getText().isEmpty()) {

            String custName = addCustomerNameTxt.getText();
            String custAddress = addCustomerAddressTxt.getText();
            String custPost = addCustomerPostTxt.getText();
            String custPhone = addCustomerPhoneTxt.getText();
            int custDivis = addCustomerStProvComBx.getValue().getDivisId();

            CustomerQuery.addCust(custName, custAddress, custPost, custPhone, custDivis);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("CustomerTable.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }

    /**
     * Confirms and cancels changes made to add customer
     * @param actionEvent on cancel button selection
     * @throws IOException throws I/O exception
     */
    public void onActionAddCustCancel(ActionEvent actionEvent) throws IOException {

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
     * Populates state/province combo box
     * @param actionEvent on country selection
     */
    public void onActionAddCustCountryChange(ActionEvent actionEvent) {
        try {

            Country country = addCustomerCountryComBx.getValue();

            ObservableList<FirstLevel> firstLevelList = FirstLevelQuery.getCountryFirstLvl(country.getCountryId());
            addCustomerStProvComBx.setItems(firstLevelList);

            this.addCustomerStProvComBx.setValue(null);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes controller class and populates combo boxes
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<Country> country = CountryQuery.getAllCountries();
            addCustomerCountryComBx.setItems(country);

            ObservableList<FirstLevel> firstLevel = FirstLevelQuery.getAllFirstLevel();
            addCustomerStProvComBx.setItems(firstLevel);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
