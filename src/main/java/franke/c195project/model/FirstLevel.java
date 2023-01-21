package franke.c195project.model;

/**
 * First Level Division model class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */
public class FirstLevel {

    private int divisId;
    private String divisName;
    public int countryId;

    public FirstLevel(int divisId, String divisName, int countryId) {

        this.divisId = divisId;
        this.divisName = divisName;
        this.countryId = countryId;

    }

    /**
     * @return the division ID
     */
    public int getDivisId() {
        return divisId;
    }

    /**
     * @return
     */
    public String toString() {
        return divisName;
    }

}
