import java.util.*;

// =====================================
// FitnessClass.java
// =====================================
public class FitnessClass {
   	// Variables Declaration
   	
    // Constructor: initialises a class with its name, activity type, and duration
    public FitnessClass(String className, String activityType, int durationMinutes) {
    }

    // Enrols a member into the class, rejecting nulls, duplicate NRICs, and enrolment past capacity
    public boolean enrolMember(Member m) {
        }

    // Removes a member from the class by NRIC (case-insensitive match)
    public boolean removeMember(String nric) {
        }

    // Returns the class name
    public String getClassName() {
    }

    // Returns the activity type of the class
    public String getActivityType() {
    }

    // Returns the list of enrolled participants
    public ArrayList<Member> getParticipants() {
        // never null; empty list when no one is enrolled
    }

    // Builds a summary string with name, activity, duration, and enrolment count
    @Override
    public String toString() {
        return className + " | " + activityType + " | " + durationMinutes + " mins | "
                + participants.size() + "/" + maxParticipants + " enrolled";
    }
}
