package franke.c195project.model;

/**
 * User model class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */
public class User {

    public static int userId;
    public static String userName;
    public static String userPass;

    public User(int userId, String userName, String userPass) {
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
    }

    /**
     * @return the user id
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * @param userId the user id to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the username
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the user password
     */
    public static String getUserPass() {
        return userPass;
    }

    /**
     * @param userPass the user password to set
     */
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

}
