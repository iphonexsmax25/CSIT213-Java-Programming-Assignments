// =====================================
// Person.java
// =====================================
public class Person {
    private String name;
    private String nric;
    private String gender;
    private String dateOfBirth;

    // Constructor: initialises the basic identity fields for a person
    public Person(String name, String nric, String gender, String dateOfBirth) {
        this.name = name;
        this.nric = nric;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    // Returns the person's name
    public String getName() {
        return name;
    }

    // Returns the person's NRIC (unique identifier)
    public String getNRIC() {
        return nric;
    }

    // Returns the person's gender
    public String getGender() {
        return gender;
    }

    // Returns the person's date of birth
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    // Builds a human-readable summary of the person's details
    @Override
    public String toString() {
        return name + " (" + nric + "), " + gender + ", DOB: " + dateOfBirth;
    }
}
