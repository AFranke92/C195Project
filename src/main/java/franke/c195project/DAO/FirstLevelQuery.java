package franke.c195project.DAO;

import franke.c195project.model.FirstLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DAO
 * @author
 * Abigail Franke
 */

public class FirstLevelQuery {

    /**
     * Queries database for all first level divisions and places them in an observable list
     * @return observable list of all first level divisions
     * @throws SQLException throws SQL exception
     */
    public static ObservableList<FirstLevel> getAllFirstLevel() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<FirstLevel> firstLevelObservableList = FXCollections.observableArrayList();

        while (rs.next()) {
            int divisId = rs.getInt("Division_ID");
            String divisName = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevel firstLevel = new FirstLevel(divisId, divisName, countryId);
            firstLevelObservableList.add(firstLevel);
        }
        return firstLevelObservableList;

    }

    /**
     * Pulls all first level divisions based on country
     * @param countryId the country ID to search
     * @return observable list of first level divisions
     * @throws SQLException throws SQL exception
     */
    public static ObservableList<FirstLevel> getCountryFirstLvl(int countryId) throws SQLException {
        ObservableList<FirstLevel> firstLevelObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int divisId = rs.getInt("Division_ID");
            String divisName = rs.getString("Division");
            FirstLevel firstLevel = new FirstLevel(divisId, divisName, countryId);
            firstLevelObservableList.add(firstLevel);
        }
        return firstLevelObservableList;

    }

    /**
     * Pulls all first level divisions based on first level division ID
     * @param divisId the division ID to search
     * @return the first level division
     * @throws SQLException throws SQL exception
     */
    public static FirstLevel getFirstLvl(int divisId) throws SQLException {

        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, divisId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            String divisName = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevel firstLevel = new FirstLevel(divisId, divisName, countryId);
            return firstLevel;
        }
        return null;

    }

}
