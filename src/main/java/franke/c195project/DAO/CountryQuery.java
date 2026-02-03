package franke.c195project.DAO;

import franke.c195project.model.Country;
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

public class CountryQuery {

    /**
     * Queries database for country based on first level division ID
     * @param divId the first level division ID
     * @return the country
     * @throws SQLException throws SQL exception
     */
    public static Country getDivisionCountry(int divId) throws SQLException{
        String sql = "SELECT c.Country_ID, c.Country FROM countries AS c INNER JOIN First_Level_Divisions AS f ON c.Country_ID = f.Country_ID AND f.Division_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, divId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String countryName = rs.getString("Country");
            int countryId = rs.getInt("Country_ID");
            Country country = new Country(countryName, countryId);
            return country;
        }
        return null;
    }

    /**
     * Queries database for all countries and places them in an observable list
     * @return an observable list of all countries
     * @throws SQLException throws SQL exception
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> countryObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country FROM countries";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String countryName = rs.getString("Country");
            int countryId = rs.getInt("Country_ID");
            Country country = new Country(countryName, countryId);
            countryObservableList.add(country);
        }
        return countryObservableList;
    }

}
