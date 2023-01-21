package franke.c195project.DAO;

import franke.c195project.model.User;
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

public class UserQuery {

    /**
     * Validates user during login
     * @param userName the username to validate
     * @param userPass the password to validate
     * @return the user ID if valid
     */
    public static int validUser(String userName, String userPass) {

        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, userPass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                    return rs.getInt("User_ID");

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
