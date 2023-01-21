package franke.c195project.model;

/**
 * Country model class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */
public class Country {

    private String countryName;
    private int countryId;

    public Country (String countryName, int countryId) {
        this.countryName = countryName;
        this.countryId = countryId;
    }

    /**
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @return returns country name as string
     */
    public String toString() {
        return countryName;
    }
}
