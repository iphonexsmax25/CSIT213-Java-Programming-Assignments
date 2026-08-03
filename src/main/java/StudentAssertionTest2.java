/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author limis
 */
public class StudentAssertionTest2 {
            public static void main(String[] args) {

        testAssertionEnabled();
        testPerson();
        testMember();
        testFitnessClass();
        testFitnessCentre();
        testFitnessCouncil_Positive();
        testFitnessCouncil_Negative();
		
        System.out.println("All fitness system tests passed successfully!");
    }

    private static boolean testAssertionEnabled() {
        boolean assertionsEnabled = false;
        assert assertionsEnabled = true; // Intentional side effect

        if (assertionsEnabled) {
            System.out.println("Assertions are enabled.");
            return true;
        } else {
            System.out.println("Assertions are NOT enabled. Use -ea to enable them.");
            return false;
        }
    }

    public static void testPerson() {
        String name = "Alice Tan";
        String nric = "S1234567A";
        String gender = "Female";
        String dob = "1999-05-12";

        Person p = new Person(name, nric, gender, dob);

        assert p.getName().equals(name) : "getName() mismatch";
        assert p.getNRIC().equals(nric) : "getNRIC() mismatch";
        assert p.getGender().equals(gender) : "getGender() mismatch";
        assert p.getDateOfBirth().equals(dob) : "getDateOfBirth() mismatch";

        String expected = name + " (" + nric + "), " + gender + ", DOB: " + dob;
        assert p.toString().equals(expected) : "toString() mismatch";

        System.out.println("All Person assertions passed.");
    }

    public static void testMember() {
        String name = "Alice Tan";
        String nric = "S1234567A";
        String gender = "Female";
        String dob = "1999-05-12";
        String tier = "Premium";
        String activity = "Yoga";

        Member m = new Member(name, nric, gender, dob, tier, activity);

        // Inherited getters from Person
        assert m.getName().equals(name) : "Member.getName() mismatch";
        assert m.getNRIC().equals(nric) : "Member.getNRIC() mismatch";
        assert m.getGender().equals(gender) : "Member.getGender() mismatch";
        assert m.getDateOfBirth().equals(dob) : "Member.getDateOfBirth() mismatch";

        // Member-specific getters
        assert m.getMembershipTier().equals(tier) : "getMembershipTier() mismatch";
        assert m.getPreferredActivity().equals(activity) : "getPreferredActivity() mismatch";

        // toString() must extend Person's toString()
        String expected = name + " (" + nric + "), " + gender + ", DOB: " + dob
                + ", Tier: " + tier + ", Prefers: " + activity;
        assert m.toString().equals(expected) : "Member.toString() mismatch";

        System.out.println("All Member assertions passed.");
    }

    public static void testFitnessClass() {
        Member m1 = new Member("Alice Tan", "S1234567A", "Female", "1999-05-12", "Premium", "Yoga");
        Member m2 = new Member("Bob Lim", "S7654321B", "Male", "1998-02-01", "Basic", "Spin");
        Member m3 = new Member("Carol Ong", "S1111222C", "Female", "1997-10-20", "VIP", "Yoga");

        // Constructor & getters
        FitnessClass cls = new FitnessClass("Sunrise Yoga", "Yoga", 60);
        assert cls.getClassName().equals("Sunrise Yoga") : "getClassName() mismatch";
        assert cls.getActivityType().equals("Yoga") : "getActivityType() mismatch";

        // Participants list must be non-null and initially empty
        assert cls.getParticipants() != null : "getParticipants() returned null";
        assert cls.getParticipants().isEmpty() : "New class should have 0 participants";

        // Fix capacity for the test
        FitnessClass.maxParticipants = 2;

        // Happy path enrolment
        assert cls.enrolMember(m1) : "Expected first enrolMember(m1) to succeed";
        assert cls.getParticipants().size() == 1 : "Participants should be 1 after first enrolment";

        assert cls.enrolMember(m2) : "Expected enrolMember(m2) to succeed";
        assert cls.getParticipants().size() == 2 : "Participants should be 2 after second enrolment";

        // Negative: null, duplicate NRIC, capacity
        assert !cls.enrolMember(null) : "enrolMember(null) must return false";
        assert !cls.enrolMember(m1) : "Duplicate member accepted";
        assert !cls.enrolMember(m3) : "Enrolment beyond capacity accepted";

        // Removal (case-insensitive) and not-found
        assert cls.removeMember("s1234567a") : "Expected case-insensitive removal to succeed";
        assert cls.getParticipants().size() == 1 : "Participants should be 1 after removal";
        assert !cls.removeMember("S9999999Z") : "Removed non-existent member";
        assert !cls.removeMember(null) : "removeMember(null) must return false";
        assert !cls.removeMember("") : "removeMember(\"\") must return false";

        // Capacity frees up after removal
        assert cls.enrolMember(m3) : "Expected enrolment to succeed after a slot freed up";

        System.out.println("All FitnessClass assertions passed.");
    }

    public static void testFitnessCentre() {
        FitnessCentre centre = new FitnessCentre("IronWorks", "FC-2026-001");

        // Start clean
        assert centre.getClasses() != null : "getClasses() should not return null";
        assert centre.getClasses().isEmpty() : "New centre should have no activities initially";

        FitnessClass yoga1 = new FitnessClass("Sunrise Yoga", "Yoga", 60);
        FitnessClass yoga2 = new FitnessClass("Evening Flow", "Yoga", 45);
        FitnessClass spin1 = new FitnessClass("Spin Express", "Spin", 30);
        FitnessClass dupNameCased = new FitnessClass("sunrise yoga", "Yoga", 50);

        // addClass: null / valid
        assert !centre.addClass(null) : "addClass(null) must return false";
        assert centre.addClass(yoga1) : "First class (Yoga/yoga1) should be added";
        assert centre.getClasses().size() == 1 : "Map should contain 1 activity after first add";
        assert centre.getClassesByActivity("Yoga").size() == 1 : "Yoga list size should be 1";

        // addClass: duplicate by className (case-insensitive, same activity)
        assert !centre.addClass(dupNameCased)
                : "Duplicate className in same centre/activity must be rejected (case-insensitive)";
        assert centre.getClassesByActivity("Yoga").size() == 1
                : "Yoga list should remain size 1 after duplicate attempt";

        // addClass: another class in same activity (different name)
        assert centre.addClass(yoga2) : "Second distinct Yoga class should be added";
        assert centre.getClassesByActivity("Yoga").size() == 2 : "Yoga list should be size 2";

        // addClass: different activity
        assert centre.addClass(spin1) : "Spin class should be added";
        assert centre.getClasses().size() == 2 : "Map should contain 2 activities (Yoga, Spin)";
        assert centre.getClassesByActivity("Spin").size() == 1 : "Spin list should be size 1";

        // getClassesByActivity: unknown activity
        assert centre.getClassesByActivity("Pilates") != null
                : "Should return non-null list for unknown activity";
        assert centre.getClassesByActivity("Pilates").isEmpty()
                : "Unknown activity should give empty list";

        // removeClass: invalid inputs
        assert !centre.removeClass(null) : "removeClass(null) must return false";
        assert !centre.removeClass("") : "removeClass(\"\") must return false";
        assert !centre.removeClass("NO-SUCH-CLASS") : "Removing non-existent class should return false";

        // removeClass: success (case-insensitive) and key removal when last in activity
        // Current map: Yoga -> [yoga1, yoga2], Spin -> [spin1]
        assert centre.removeClass("sunrise yoga") : "Should remove yoga1 using case-insensitive name";
        assert centre.getClassesByActivity("Yoga").size() == 1 : "Yoga list should be size 1 after removal";

        assert centre.removeClass("Spin Express") : "Should remove Spin's only class spin1";
        assert !centre.getClasses().containsKey("Spin")
                : "Activity key 'Spin' should be removed when its list becomes empty";

        assert centre.removeClass("Evening Flow") : "Should remove last remaining Yoga class";
        assert !centre.getClasses().containsKey("Yoga")
                : "Activity key 'Yoga' should be removed when list becomes empty";

        // Final state
        assert centre.getClasses().isEmpty() : "All activities should be removed after removing all classes";

        System.out.println("All FitnessCentre assertions passed.");
    }

    public static void testFitnessCouncil_Positive() {
        FitnessCouncil council = new FitnessCouncil();

        FitnessCentre c1 = new FitnessCentre("IronWorks", "FC-2026-001");
        FitnessCentre c2 = new FitnessCentre("ZenFlow Studio", "FC-2026-002");

        // C1 classes
        FitnessClass yogaC1 = new FitnessClass("Sunrise Yoga", "Yoga", 60);
        FitnessClass spinC1 = new FitnessClass("Spin Express", "Spin", 30);
        assert c1.addClass(yogaC1) : "C1 add Sunrise Yoga should succeed";
        assert c1.addClass(spinC1) : "C1 add Spin Express should succeed";

        // C2 classes (duplicate name across centres is allowed)
        FitnessClass yogaC2 = new FitnessClass("Sunrise Yoga", "Yoga", 45);
        FitnessClass yogaC2b = new FitnessClass("Moonlight Yoga", "Yoga", 75);
        assert c2.addClass(yogaC2) : "C2 add Sunrise Yoga should succeed";
        assert c2.addClass(yogaC2b) : "C2 add Moonlight Yoga should succeed";

        // Register centres
        assert council.registerCentre(c1) : "registerCentre(c1) should succeed";
        assert council.registerCentre(c2) : "registerCentre(c2) should succeed";

        // getCentres()
        assert council.getCentres() != null : "getCentres() must not return null";
        assert council.getCentres().size() == 2 : "Council should contain 2 centres";

        // getAllClassNames(): duplicates across centres are kept
        java.util.ArrayList<String> allNames = council.getAllClassNames();
        assert allNames != null : "getAllClassNames() must not return null";
        assert allNames.size() == 4 : "Expected 4 class names across centres";
        int sunriseCount = 0;
        for (String n : allNames)
            if ("Sunrise Yoga".equals(n))
                sunriseCount++;
        assert sunriseCount == 2 : "Expected Sunrise Yoga to appear twice in all-names list";

        // getClassesByCentre(String)
        java.util.ArrayList<String> c1Names = council.getClassesByCentre("IronWorks");
        java.util.ArrayList<String> c2Names = council.getClassesByCentre("ZenFlow Studio");
        java.util.ArrayList<String> c2NamesCase = council.getClassesByCentre("zenflow studio");
        assert c1Names.size() == 2 : "C1 should list 2 classes";
        assert c1Names.contains("Sunrise Yoga") && c1Names.contains("Spin Express")
                : "C1 class names mismatch";
        assert c2Names.size() == 2 : "C2 should list 2 classes";
        assert c2Names.contains("Sunrise Yoga") && c2Names.contains("Moonlight Yoga")
                : "C2 class names mismatch";
        assert c2Names.equals(c2NamesCase) : "Centre lookup must be case-insensitive";

        // getActivitiesByCentre(String)
        java.util.HashMap<String, java.util.ArrayList<FitnessClass>> c1Map =
                council.getActivitiesByCentre("IronWorks");
        assert c1Map != null : "getActivitiesByCentre should not return null";
        assert c1Map.size() == 2 : "C1 should have 2 activity keys";
        assert c1Map.containsKey("Yoga") && c1Map.containsKey("Spin") : "C1 activity keys mismatch";
        assert c1Map.get("Yoga").size() == 1 && c1Map.get("Spin").size() == 1
                : "C1 activity list sizes mismatch";

        // getClassesByActivity(String): NO duplicates across centres
        java.util.ArrayList<String> yogaNames = council.getClassesByActivity("Yoga");
        java.util.ArrayList<String> spinNames = council.getClassesByActivity("Spin");
        assert yogaNames.size() == 2 : "Yoga list should de-duplicate to 2 names";
        assert yogaNames.contains("Sunrise Yoga") && yogaNames.contains("Moonlight Yoga")
                : "Yoga unique names mismatch";
        assert spinNames.size() == 1 && spinNames.contains("Spin Express") : "Spin names mismatch";

        System.out.println("All FitnessCouncil POSITIVE assertions passed.");
    }

    public static void testFitnessCouncil_Negative() {
       //Think of the negative cases and write the test case here to
       //stress test your codes.
       // Use a brand new council so this test is independent of testFitnessCouncil_Positive()
         FitnessCouncil council = new FitnessCouncil();

        // ---- Brand-new council: every accessor must be safe, never null ----
        assert council.getCentres() != null : "getCentres() must not return null";
        assert council.getCentres().isEmpty() : "New council should start with 0 centres";

        assert council.getAllClassNames() != null : "getAllClassNames() must not return null";
        assert council.getAllClassNames().isEmpty()
                : "getAllClassNames() should be empty before any centre is registered";

        assert council.getClassesByCentre("Nowhere Gym") != null
                : "getClassesByCentre() must not return null for an unknown centre";
        assert council.getClassesByCentre("Nowhere Gym").isEmpty()
                : "Unknown centre name should give an empty class list";

        assert council.getActivitiesByCentre("Nowhere Gym") != null
                : "getActivitiesByCentre() must not return null for an unknown centre";
        assert council.getActivitiesByCentre("Nowhere Gym").isEmpty()
                : "Unknown centre name should give an empty activity map";

        assert council.getClassesByActivity("Pilates") != null
                : "getClassesByActivity() must not return null for an unknown activity";
        assert council.getClassesByActivity("Pilates").isEmpty()
                : "Unknown activity type should give an empty list";

        // ---- registerCentre: reject null ----
        assert !council.registerCentre(null) : "registerCentre(null) must return false";
        assert council.getCentres().isEmpty() : "Council should still have 0 centres after rejecting null";

        // ---- registerCentre: reject duplicate name, case-insensitive ----
        FitnessCentre original = new FitnessCentre("PowerHouse Gym", "FC-2026-010");
        FitnessCentre duplicateCased = new FitnessCentre("powerhouse gym", "FC-2026-999");

        assert council.registerCentre(original) : "First registration of PowerHouse Gym should succeed";
        assert !council.registerCentre(duplicateCased)
                : "Duplicate centre name (case-insensitive) must be rejected";
        assert council.getCentres().size() == 1
                : "Council should still only have 1 centre after the duplicate was rejected";

        // ---- A registered centre with no classes yet must still behave safely ----
        assert council.getClassesByCentre("PowerHouse Gym").isEmpty()
                : "Registered centre with no classes should give an empty class list";
        assert council.getActivitiesByCentre("powerhouse gym").isEmpty()
                : "Registered centre with no classes should give an empty activity map (case-insensitive lookup)";

        // ---- Populate one centre, then re-check that unrelated lookups stay empty ----
        FitnessClass hiit = new FitnessClass("Blast HIIT", "HIIT", 40);
        assert original.addClass(hiit) : "Adding a HIIT class to PowerHouse Gym should succeed";

        assert council.getClassesByCentre("Some Other Gym").isEmpty()
                : "An unregistered centre name should still give an empty list, even once other centres have classes";
        assert council.getClassesByActivity("Zumba").isEmpty()
                : "An activity type nobody offers should give an empty (non-null) list";

        // ---- getAllClassNames() must reflect exactly what's registered so far ----
        assert council.getAllClassNames().size() == 1
                : "Only 1 class exists across the whole council at this point";
        assert council.getAllClassNames().contains("Blast HIIT")
                : "getAllClassNames() should include the one class that was added";

        System.out.println("All FitnessCouncil NEGATIVE assertions passed.");
    }
}
