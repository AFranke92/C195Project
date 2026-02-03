package franke.c195project.DAO;

import franke.c195project.model.ReportSchedule;
import franke.c195project.model.ReportTotal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


/**
 * DAO
 * @author
 * Abigail Franke
 */

public class ReportsQuery {

    /**
     * Pulls all appointments for specific contact
     * @param contact the contact to search
     * @return an observable list of appointments based on contact
     * @throws SQLException throws SQL exception
     */
    public static ObservableList<ReportSchedule> getAppByContact(int contact) throws SQLException {
        ObservableList<ReportSchedule> appsByContact = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = " + contact;
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appId = rs.getInt("Appointment_ID");
            String appTitle = rs.getString("Title");
            String appType = rs.getString("Type");
            String appDesc = rs.getString("Description");
            LocalDateTime appStart = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime appEnd = rs.getTimestamp("End").toLocalDateTime();
            int custId = rs.getInt("Customer_ID");

            ReportSchedule reportSchedule = new ReportSchedule(appId, appTitle, appType, appDesc, appStart, appEnd, custId);
            appsByContact.add(reportSchedule);

        }
        return appsByContact;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<ReportTotal> totalApps() throws SQLException {

        ObservableList<ReportTotal> totalApps = FXCollections.observableArrayList();
        String sql = "SELECT monthname(Start) AS Month, Type, count(Type) AS typeCount FROM appointments GROUP BY appointments.Type, monthname(Start)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            String appMonth = rs.getString("Month");
            String appType = rs.getString("Type");
            int typeCount = rs.getInt("typeCount");
            ReportTotal reportTotal = new ReportTotal(appMonth, appType, typeCount);
            totalApps.add(reportTotal);

        }
        return totalApps;
    }

    /**
     * Counts total customers in database
     * @return the amount of customers
     * @throws SQLException throws SQL exception
     */
    public static int totalCust() throws SQLException {

        String sql = "SELECT count(*) AS countRecords FROM customers";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        int count = 0;

        while (rs.next()) {
            count = rs.getInt("countRecords");
        }
        return count;

    }

}
