package franke.c195project.model;

/**
 * Contact model class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */
public class Contact {

    public int contactId;
    public String contactName;
    public String contactEmail;

    public Contact(int contactId,String contactName, String contactEmail) {

        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;

    }

    /**
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the contact email address
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail the contact email to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String toString () {
        return contactName;
    }

}
