package franke.c195project.model;

/**
 * Reports Total model class
 * @author
 * Abigail Franke
 */
public class ReportTotal {

    private String appMonth;
    private String appType;
    private int typeCount;

    public ReportTotal(String appMonth, String appType, int typeCount) {
        this.appMonth = appMonth;
        this.appType = appType;
        this.typeCount = typeCount;
    }

    /**
     * @return the appointment month
     */
    public String getAppMonth() {
        return appMonth;
    }

    /**
     * @param appMonth the appointment month to set
     */
    public void setAppMonth(String appMonth) {
        this.appMonth = appMonth;
    }

    /**
     * @return the appointment type
     */
    public String getAppType() {
        return appType;
    }

    /**
     * @param appType the appointment type to set
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     * @return the number of appointments
     */
    public int getTypeCount() {
        return typeCount;
    }

    /**
     * @param typeCount the number of appointments to set
     */
    public void setTypeCount(int typeCount) {
        this.typeCount = typeCount;
    }

}
