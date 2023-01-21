package franke.c195project.DAO;

import franke.c195project.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DAO
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */

public class ContactQuery {

    /**
     * Queries database for all contacts and places them in an observable list
     * @return observable list of all contacts
     * @throws SQLException throws SQL exception
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            contactObservableList.add(contact);
        }
        return contactObservableList;
    }

    /**
     * Queries database for contact name based on contact ID
     * @param contactId the contact ID needed for name search
     * @return the contact
     * @throws SQLException throws SQL exception
     */
    public static Contact getContactName(int contactId) throws SQLException {
        String sql = "SELECT Contact_Name, Email FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactId, contactName, contactEmail);
            return contact;
        }

        return null;
    }

}
