package be.vdab.luigi.domain;

import java.time.LocalDate;

public class Persoon {
    private final String firstName;
    private final String sirname;
    private final int numberOfKids;
    private final boolean married;
    private final LocalDate dateOfBirth;
    private final Address address;

    public Persoon(String firstName, String sirname, int numberOfKids, boolean married, LocalDate dateOfBirth, Address address) {
        this.firstName = firstName;
        this.sirname = sirname;
        this.numberOfKids = numberOfKids;
        this.married = married;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public String getName() {
        return firstName + " " + sirname;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getSirname() {
        return sirname;
    }

    public int getNumberOfKids() {
        return numberOfKids;
    }

    public boolean isMarried() {
        return married;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }
}
