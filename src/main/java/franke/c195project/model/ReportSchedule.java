package franke.c195project.model;

import java.time.LocalDateTime;

/**
 * Reports Schedule model class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */
public class ReportSchedule {

    private int appId;
    private String appTitle;
    private String appType;
    private String appDesc;
    private LocalDateTime appStart;
    private LocalDateTime appEnd;
    private int custId;

    public ReportSchedule(int appId, String appTitle, String appType, String appDesc, LocalDateTime appStart, LocalDateTime appEnd, int custId) {
        this.appId = appId;
        this.appTitle = appTitle;
        this.appType = appType;
        this.appDesc = appDesc;
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.custId = custId;
    }

    /**
     * @return the appointment id
     */
    public int getAppId() {
        return appId;
    }

    /**
     * @param appId the appointment id to set
     */
    public void setAppId(int appId) {
        this.appId = appId;
    }

    /**
     * @return the appointment title
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     * @param appTitle the appointment title to set
     */
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
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
     * @return the appointment description
     */
    public String getAppDesc() {
        return appDesc;
    }

    /**
     * @param appDesc the appointment description to set
     */
    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    /**
     * @return the appointment start
     */
    public LocalDateTime getAppStart() {
        return appStart;
    }

    /**
     * @param appStart the appointment start to set
     */
    public void setAppStart(LocalDateTime appStart) {
        this.appStart = appStart;
    }

    /**
     * @return the appointment end
     */
    public LocalDateTime getAppEnd() {
        return appEnd;
    }

    /**
     * @param appEnd the appointment end to set
     */
    public void setAppEnd(LocalDateTime appEnd) {
        this.appEnd = appEnd;
    }

    /**
     * @return the customer id
     */
    public int getCustId() {
        return custId;
    }

    /**
     * @param custId the customer id to set
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }

}
