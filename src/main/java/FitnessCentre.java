import java.util.ArrayList;
import java.util.HashMap;

// =====================================
// FitnessCentre.java
// =====================================
public class FitnessCentre {
    //Variables declaration
    private String centreName;
    private String registrationNumber;
    private HashMap<String, ArrayList<FitnessClass>> classes;
    
    
    // Constructor: initialises the centre with its name and registration number
    public FitnessCentre(String centreName, String registrationNumber) {
        this.centreName = centreName;
        this.registrationNumber = registrationNumber;
        this.classes = new HashMap<String, ArrayList<FitnessClass>>();
    }

    // Adds a class to the centre, rejecting nulls and duplicate class names (case-insensitive)
    public boolean addClass(FitnessClass c) {
        if (c == null ){
            return false;
        }
        String activityType =c.getActivityType();
        ArrayList<FitnessClass> list = classes.get(activityType);
        
        if (list == null){
            // first class of this activity type at this centre
            list = new ArrayList<FitnessClass>();
            classes.put(activityType, list);
        } else{
            for (FitnessClass existing : list) {
                if (existing.getClassName().equalsIgnoreCase(c.getClassName())) {
                    return false;
                }
            }
        }
        list.add(c);
        return true;
    }

    // Removes a class by name (case-insensitive), searching across all activity types
    public boolean removeClass(String className) {
        if (className == null || className.isEmpty() ){
            return false;
        }
        for (String activityType : classes.keySet()){
            ArrayList<FitnessClass> list = classes.get(activityType);
        }
                
                
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
