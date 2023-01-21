package franke.c195project.model;

/**
 * Address model class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */
public class Address {

    private int addId;
    private String add;
    private String add2;
    private int cityId;
    private String postCode;
    private String phone;


    public Address(int addId, String add, String add2, int cityId, String postCode, String phone) {
        this.addId = addId;
        this.add = add;
        this.add2 = add2;
        this.cityId = cityId;
        this.postCode = postCode;
        this.phone = phone;

    }

    /**
     * @return the address
     */
    public String getAdd() {

        return add;
    }

    /**
     * @param add the address to set
     */
    public void setAdd(String add) {

        this.add = add;
    }


}
