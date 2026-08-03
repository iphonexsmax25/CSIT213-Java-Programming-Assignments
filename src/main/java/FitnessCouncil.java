import java.util.ArrayList;
import java.util.HashMap;

// =====================================
// FitnessCouncil.java
// =====================================
public class FitnessCouncil {

    // Variables Declaration
    private ArrayList<FitnessCentre> centres = new ArrayList<>();
    
    // Private Helper: case-insensitive lookup of a centre 
    //( kept private and reused by several public methods below - avoids duplicated logic ) 
    private FitnessCentre  findCentre(String centreName){
        if (centreName == null){
            return null;
        }
        for (FitnessCentre centre :centres){
            if (centre.getCentreName().equalsIgnoreCase(centreName)){
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
        if(findCentre(c.getCentreName()) != null){
            return false; // 
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
        for (FitnessCentre centre :centres){
            for (ArrayList<FitnessClass> list : centre.getClasses().values()){
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
        // centre not found -> empty list
        ArrayList<String> names = new ArrayList<String>();
        FitnessCentre centre = findCentre(centreName);
        if (centre == null){
            return names;
        }
        for (ArrayList<FitnessClass> list : centre.getClasses().values()){
            for (FitnessClass fc : list ){
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
        // centre not found -> empty list
        FitnessCentre centre = findCentre(centreName);
        if (centre == null){
            return new HashMap<String, ArrayList<FitnessClass>>();
        }
        return centre.getClasses();
    }

    // =========================
    // Get Class Names by Activity (across all centres; NO duplicates)
    // =========================
    // Collects unique class names for a given activity type across all centres
    public ArrayList<String> getClassesByActivity(String activityType) {
        ArrayList<String> names = new ArrayList<String>();
        for (FitnessCentre centre : centres){
            ArrayList<FitnessClass> list = centre.getClassesByActivity(activityType);
            for(FitnessClass fc: list){
                if(!names.contains(fc.getClassName())){
                    names.add(fc.getClassName());
                }
            }
        }
        return names;
    }
}
