import java.util.ArrayList;
import java.util.HashMap;

// =====================================
// FitnessCouncil.java
// =====================================
public class FitnessCouncil {

    // Variables Declaration
    private ArrayList<FitnessCentre> centres;
    
    // Constructor: starts with no centre registered 
    public FitnessCouncil(){
        centres = new ArrayList<FitnessCentre>();
    }
    // Private Helper: case-insensitive lookup of a centre 
    //( kept private and reused by several public methods below - avoids duplicated logic ) 
    private FitnessCentre  findCentre(String centreName){
        if (centreName == null){
            return null;
        }
        for (FitnessCentre centre :centres){
            if (centre.getCentreName().equalsIgnoreCase(centreName)){
                
            }
        }
    }

    // =========================
    // Register Centre
    // =========================
    // Registers a new centre, rejecting nulls and duplicate centre names (case-insensitive)
    public boolean registerCentre(FitnessCentre c) {
        
    }

    // =========================
    // Get All Centres
    // =========================
    // Returns all registered centres
    public ArrayList<FitnessCentre> getCentres() {
        // empty list if none registered
    }

    // =========================
    // Get All Class Names (across all centres; duplicates kept)
    // =========================
    // Collects every class name across all centres, keeping duplicates
    public ArrayList<String> getAllClassNames() {
        
    }

    // =========================
    // Get Class Names by Centre (case-insensitive lookup)
    // =========================
    // Returns all class names for the centre matching the given name (case-insensitive)
    public ArrayList<String> getClassesByCentre(String centreName) {
        // centre not found -> empty list
    }

    // =========================
    // Get Activity Map by Centre
    // =========================
    // Returns the activity-to-classes map for the centre matching the given name
    public HashMap<String, ArrayList<FitnessClass>> getActivitiesByCentre(String centreName) {
        // centre not found -> empty list
    }

    // =========================
    // Get Class Names by Activity (across all centres; NO duplicates)
    // =========================
    // Collects unique class names for a given activity type across all centres
    public ArrayList<String> getClassesByActivity(String activityType) {
    }
}
