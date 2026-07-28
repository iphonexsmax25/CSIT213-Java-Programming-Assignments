import java.util.*;

// =====================================
// FitnessCentre.java
// =====================================
public class FitnessCentre {
    //Variables declaration
    
    // Constructor: initialises the centre with its name and registration number
    public FitnessCentre(String centreName, String registrationNumber) {
    }

    // Adds a class to the centre, rejecting nulls and duplicate class names (case-insensitive)
    public boolean addClass(FitnessClass c) {
    }

    // Removes a class by name (case-insensitive), searching across all activity types
    public boolean removeClass(String className) {
    }

    // Returns the full map of classes, grouped by activity type
    public HashMap<String, ArrayList<FitnessClass>> getClasses() {
        // never null; empty map when there are no classes
    }

    // Returns the classes for a given activity type
    public ArrayList<FitnessClass> getClassesByActivity(String activityType) {
    }

    // Returns the centre's name
    public String getCentreName() {
    }

    // Builds a summary string with the centre name and registration number
    @Override
    public String toString() {
        return centreName + " (Reg: " + registrationNumber + ")";
    }
}
