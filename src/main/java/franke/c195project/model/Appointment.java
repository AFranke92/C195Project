package franke.c195project.model;

import java.time.LocalDateTime;


/**
 * Appointment model class
 * @author
 * Abigail Franke
 */
public class Appointment {

    private int appId;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appContact;
    private String appType;
    private LocalDateTime appStart;
    private LocalDateTime appEnd;
    private int custId;
    private int userId;
    private int contactId;

    public Appointment(int appId, String appTitle, String appDescription, String appLocation, String appContact, String appType,
                       LocalDateTime appStart, LocalDateTime appEnd, int custId, int userId, int contactId) {
        this.appId = appId;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appContact = appContact;
        this.appType = appType;
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.custId = custId;
        this.userId = userId;
        this.contactId = contactId;

    }

    /**
     * @return the appointment contact
     */
    public String getAppContact() {
        return appContact;
    }

    /**
     * @param appContact the appointment contact to set
     */
    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    /**
     * @return the appointment id
     */
    public int getAppId() {

        return appId;
    }

    /**
     * @return the appointment title
     */
    public String getAppTitle() {

        return appTitle;
    }

    /**
     * @return the appointment description
     */
    public String getAppDescription() {

        return appDescription;
    }

    /**
     * @return the appointment location
     */
    public String getAppLocation() {

        return appLocation;
    }

    /**
     * @return the appointment type
     */
    public String getAppType() {
        return appType;
    }

    /**
     * @return the appointment start date and time
     */
    public LocalDateTime getAppStart() {
        return appStart;
    }

    /**
     * @return the appointment end date and time
     */
    public LocalDateTime getAppEnd() {
        return appEnd;
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
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }


}

