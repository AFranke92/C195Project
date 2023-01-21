package franke.c195project.DAO;

import franke.c195project.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static franke.c195project.model.Customer.allCustomers;

/**
 * DAO
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */

public class CustomerQuery {

    /**
     * ObservableList of all customers
     * @return all customers
     * @throws SQLException throws SQL exception
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        allCustomers.clear();
        try {
            String sql =  "SELECT tableA.Customer_ID, tableA.Customer_Name, tableA.Address, tableA.Postal_Code, tableA.Phone, tableA.Division_ID, tableB.Division, tableC.Country "
            + "FROM customers tableA, first_level_divisions tableB, countries tableC "
            + "WHERE tableA.Division_ID = tableB.Division_ID "
            + "AND tableB.Country_ID = tableC.Country_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

            while (rs.next()) {
                int custId = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAddress = rs.getString("Address");
                String custCountry = rs.getString("Country");
                String custDivisName = rs.getString("Division");
                String custPostal = rs.getString("Postal_Code");
                String custPhone = rs.getString("Phone");
                int divisId = rs.getInt("Division_ID");

                Customer customer = new Customer(custId, custName,custAddress,custCountry, custDivisName,divisId,custPostal,custPhone);
                allCustomers.add(customer);
            }
            return allCustomers;

        }
        catch (SQLException e) {

            e.printStackTrace();
            return null;
        }

    }

    /**
     * Updates customer information in database
     * @param custId the customers ID
     * @param custName the customers name
     * @param custAddress the customers address
     * @param custPost the customers postal code
     * @param custPhone the customers phone number
     * @param custDivis the customers first level division ID
     */
    public static void updateCust(int custId, String custName, String custAddress, String custPost, String custPhone, int custDivis) {

        try {

            String sql = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = " + custId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, custId);
            ps.setString(2, custName);
            ps.setString(3, custAddress);
            ps.setString(4, custPost);
            ps.setString(5, custPhone);
            ps.setInt(6, custDivis);

            ps.executeUpdate();
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

    }

    /**
     * Deletes customer in database
     * @param customer the customer to delete
     * @throws SQLException throws SQL exception
     */
    public static void deleteCust(int customer) throws SQLException {

        String sql1 = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps1 = DBConnection.getConnection().prepareStatement(sql1);
        ps1.setInt(1, customer);
        ps1.execute();


        String sql2 = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps2 = DBConnection.getConnection().prepareStatement(sql2);
        ps2.setInt(1, customer);
        ps2.execute();

    }


    /**
     * Adds customer to database
     * @param custName the customers name
     * @param custAddress the customers address
     * @param custPost the customers postal code
     * @param custPhone the customers phone number
     * @param firstDivisId the customers first level division ID
     */
    public static void addCust(String custName, String custAddress, String custPost,  String custPhone,int firstDivisId) {

        try {

            String sql = "INSERT INTO customers " +
                    "(Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, custName);
            ps.setString(2, custAddress);
            ps.setString(3, custPost);
            ps.setString(4, custPhone);
            ps.setInt(5, firstDivisId);

            ps.execute();
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

    }

}
