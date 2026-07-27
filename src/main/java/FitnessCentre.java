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
        this.classes = new HashMap<>();
    }

    // Adds a class to the centre, rejecting nulls and duplicate class names (case-insensitive)
    public boolean addClass(FitnessClass c) {
        if ( c == null) {
            return false;
        }
        String activityType = c.getActivityType();
        ArrayList<FitnessClass> list = classes.get(activityType);
        
        // Check for a duplicate className within the Same activity type
        if (list != null){
            for (FitnessClass existing : list){
                if(existing.getClassName().equalsIgnoreCase(c.getClassName()) ){
                    return false;
                }
            }
        } else{
            list = new ArrayList<>();
            classes.put(activityType, list);
        }
        list.add(c);
        return true;
    }

    // Removes a class by name (case-insensitive), searching across all activity types
    public boolean removeClass(String className) {
        if ( className == null || className.isEmpty() ){
            return false;
        }
        
        // Iterate through each activity type key to access its list of classes
        for (String activityType: classes.keySet()){
            ArrayList<FitnessClass> list = classes.get(activityType);
            
            for (int i = 0; i < list.size(); i++){
                if (list.get(i).getClassName().equalsIgnoreCase(className)){
                    list.remove(i);
                    // If this acivity type has no classes left, remove the key too
                    if (list.isEmpty()){
                        classes.remove(activityType);
                    }
                    return true;
                }
            }
            
        }
        return false;
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
