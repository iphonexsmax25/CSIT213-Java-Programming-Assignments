import java.util.ArrayList;

// =====================================
// FitnessClass.java
// =====================================
public class FitnessClass {
   	// Variables Declaration
     private String className;
     private String activityType;
     private int durationsMinutes;
     private ArrayList<Member> participants;
     public static int maxParticipants = 2;
    private final int durationMinutes;
     
   	
    // Constructor: initialises a class with its name, activity type, and duration
    public FitnessClass(String className, String activityType, int durationMinutes) {
        this.className = className;
        this. activityType = activityType;
        this.durationMinutes = durationMinutes;
        this.participants = new ArrayList<Member>();
    }

    // Enrols a member into the class, rejecting nulls, duplicate NRICs, and enrolment past capacity
    public boolean enrolMember(Member m) {
        if (m == null){
            return false;
        }
        for (Member existing : participants){
            if (existing.getNRIC().equalsIgnoreCase(m.getNRIC())){
                return false; // duplicate NRIC already enrolled
            }
        }
        if(participants.size() >= maxParticipants){
            return false;
        }
        participants.add(m);
        return true;
        
   }

    // Removes a member from the class by NRIC (case-insensitive match)
    public boolean removeMember(String nric) {
        if (nric == null || nric.isEmpty()){
            return false;
        }
        for(int i = 0; i < participants.size(); i++){
           if (participants.get(i).getNRIC().equalsIgnoreCase(nric)){
               participants.remove(i);
               return true;
            }
        
        }
        return false;// no participant had that NRIC
    }
        

    // Returns the class name
    public String getClassName() {
        return className;
    }

    // Returns the activity type of the class
    public String getActivityType() {
        return activityType;
    }

    // Returns the list of enrolled participants
    public ArrayList<Member> getParticipants() {
        // never null; empty list when no one is enrolled
        return participants;
    }

    // Builds a summary string with name, activity, duration, and enrolment count
    @Override
    public String toString() {
        return className + " | " + activityType + " | " + durationMinutes + " mins | "
                + participants.size() + "/" + maxParticipants + " enrolled";
    }
}
