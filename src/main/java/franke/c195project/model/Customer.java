package franke.c195project.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

import static franke.c195project.DAO.CustomerQuery.getAllCustomers;


/**
 * Customer model class
 * @author
 * Abigail Franke
 */
public class Customer {

    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private int custId;
    private String custName;
    private String custAddress;
    private String custCountry;
    private int custDivisId;
    private String custDivisName;
    private String custPostal;
    private String custPhone;

    public Customer(int custId, String custName, String custAddress, String custCountry, String custDivisName, int custDivisId, String custPostal, String custPhone) {

        this.custId = custId;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custCountry = custCountry;
        this.custDivisId = custDivisId;
        this.custDivisName = custDivisName;
        this.custPostal = custPostal;
        this.custPhone = custPhone;
    }

    /**
     * @return the first level division name
     */
    public String getCustDivisName() {
        return custDivisName;
    }

    /**
     * @param custDivisName the first level division name to set
     */
    public void setCustDivisName(String custDivisName) {
        this.custDivisName = custDivisName;
    }

    /**
     * @return the country
     */
    public String getCustCountry() {
        return custCountry;
    }

    /**
     * @param custCountry the country to set
     */
    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    /**
     * @return the customer ID
     */
    public int getCustId() {

        return custId;
    }

    /**
     * @param custId the customer ID to set
     */
    public void setCustId(int custId) {

        this.custId = custId;
    }

    /**
     * @return the customer name
     */
    public String getCustName() {

        return custName;
    }

    /**
     * @return the address
     */
    public String getCustAddress() {

        return custAddress;
    }

    /**
     * @return the postal code
     */
    public String getCustPostal() {

        return custPostal;
    }

    /**
     * @return the phone number
     */
    public String getCustPhone() {

        return custPhone;
    }

    /**
     * @return the division ID
     */
    public int getCustDivisId() {
        return custDivisId;
    }

    /**
     * Searches through customers by name
     * @param custName the customer name part of search
     * @return all customers containing search param
     * @throws SQLException throws SQL exception
     */
    public static ObservableList<Customer> lookUpCust(String custName) throws SQLException {
        ObservableList<Customer> customer = FXCollections.observableArrayList();
        for (Customer c : getAllCustomers()) {
            if (c.getCustName().contains(custName)) {
                customer.add(c);
            }
        }
        return customer;
    }

    /**
     * Searches through customers by ID
     * @param custId the customer ID part of search
     * @return all customers containing search param
     */
    public static Customer lookupCust(int custId) {

        for(int i = 0, customerSize = allCustomers.size(); i < customerSize; i++) {
            Customer cust = allCustomers.get(i);
            if (cust.getCustId() == custId) {
                return cust;
            }
        }
        return null;
    }

}
