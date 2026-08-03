import java.util.ArrayList;
import java.util.HashMap;

// =====================================
// FitnessCouncil.java
// =====================================
public class FitnessCouncil {

    // Variables Declaration
    private ArrayList<FitnessCentre> centres = new ArrayList<>();
    
     // ---- Private helper: shared by several methods above ----
    // Not part of the spec's public API, but avoids repeating the same
    // case-insensitive search loop three times.
    private FitnessCentre findCentre(String centreName) {
        if (centreName == null) {
            return null;
        }
        for (FitnessCentre centre : centres) {
            if (centre.getCentreName().equalsIgnoreCase(centreName)) {
                return centre;
            }
        }
        return null;
    }
    
    
    // =========================
    // Register Centre
    // =========================
    // Registers a new centre, rejecting nulls and duplicate centre names (case-insensitive)
    public boolean registerCentre(FitnessCentre c) {
        if (c == null){
            return false;
        }
        for (FitnessCentre existing : centres){
            if(existing.getCentreName().equalsIgnoreCase(c.getCentreName())){
                return false; 
            }
            
        }
        centres.add(c);
        return true;
    }

    // =========================
    // Get All Centres
    // =========================
    // Returns all registered centres
    public ArrayList<FitnessCentre> getCentres() {
        // empty list if none registered
        return centres;
    }

    // =========================
    // Get All Class Names (across all centres; duplicates kept)
    // =========================
    // Collects every class name across all centres, keeping duplicates
    public ArrayList<String> getAllClassNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (FitnessCentre centre : centres){
            HashMap<String, ArrayList<FitnessClass>> activityMap = centre.getClasses();
            for(ArrayList<FitnessClass> list : activityMap.values()){
                for (FitnessClass fc : list){
                    names.add(fc.getClassName());
                }
            }
        }
         return names;
           
    }

    // =========================
    // Get Class Names by Centre (case-insensitive lookup)
    // =========================
    // Returns all class names for the centre matching the given name (case-insensitive)
    public ArrayList<String> getClassesByCentre(String centreName) {
        FitnessCentre found = findCentre(centreName);
        ArrayList<String> names = new ArrayList<>();
        if (found == null) {
            return names; // centre not found -> empty list
        }

        for (ArrayList<FitnessClass> list : found.getClasses().values()) {
            for (FitnessClass fc : list) {
                names.add(fc.getClassName());
            }
        }
        return names;
    }

    // =========================
    // Get Activity Map by Centre
    // =========================
    // Returns the activity-to-classes map for the centre matching the given name
    public HashMap<String, ArrayList<FitnessClass>> getActivitiesByCentre(String centreName) {
        FitnessCentre found = findCentre(centreName);
        if (found == null) {
            return new HashMap<>(); // centre not found -> empty map
        }
        return found.getClasses();
    }

    // =========================
    // Get Class Names by Activity (across all centres; NO duplicates)
    // =========================
    // Collects unique class names for a given activity type across all centres
    public ArrayList<String> getClassesByActivity(String activityType) {
        ArrayList<String> uniqueNames = new ArrayList<>();
        for (FitnessCentre centre : centres) {
            ArrayList<FitnessClass> list = centre.getClassesByActivity(activityType);
            for (FitnessClass fc : list) {
                String name = fc.getClassName();
                // Only add if we haven't already recorded this class name.
                if (!uniqueNames.contains(name)) {
                    uniqueNames.add(name);
                }
            }
        }
        return uniqueNames;
    }

   
}
