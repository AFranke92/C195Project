package franke.c195project.utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.*;

public class TimeUtility {

    /**
     * Retrieves local timestamp
     * @return local timestamp
     */
    public static java.sql.Timestamp getTimeStamp() {
        ZoneId zid = ZoneId.of("UTC");
        LocalDateTime ldt = LocalDateTime.now(zid);
        java.sql.Timestamp ts = Timestamp.valueOf(ldt);
        return ts;
    }

    private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();

    /**
     * Loads local times, within business hours, to observable lists
     */
    private static void loadLocalTimes() {

        ZonedDateTime startBHET = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        LocalDateTime startLocal = startBHET.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endLocal = startLocal.plusHours(14);

        while (startLocal.isBefore(endLocal)) {

            startTimes.add(startLocal.toLocalTime());
            startLocal = startLocal.plusMinutes(15);
            endTimes.add(startLocal.toLocalTime());

        }
    }

    /**
     * Loads local times to start time combo boxes
     * @return observable list of local times within business hours
     */
    public static ObservableList<LocalTime> getStartTimes() {

        if(startTimes.isEmpty()) {
            loadLocalTimes();
        }
        return startTimes;
    }

    /**
     * Loads local times to end time combo boxes
     * @return observable list of local times within business hours
     */
    public static ObservableList<LocalTime> getEndTimes() {

        if(endTimes.isEmpty()) {
            loadLocalTimes();
        }
        return endTimes;
    }
}
