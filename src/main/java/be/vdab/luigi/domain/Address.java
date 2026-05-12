package be.vdab.luigi.domain;

public class Address {
    private final String street;
    private final String houseNumber;
    private final int postalCode;
    private final String City;

    public Address(String street, String houseNumber, int postalCode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        City = city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return City;
    }
}
