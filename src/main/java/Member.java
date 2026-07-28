// =====================================
// Member.java
// =====================================
public class Member extends Person {
    private String membershipTier;
    private String preferredActivity;

    // Constructor: initialises a member using the Person fields plus tier and preferred activity
    public Member(String name, String nric, String gender, String dateOfBirth,
                  String membershipTier, String preferredActivity) {
        super(name, nric, gender, dateOfBirth);
        this.membershipTier = membershipTier;
        this.preferredActivity = preferredActivity;
    }

    // Returns the member's membership tier
    public String getMembershipTier() {
        return membershipTier;
    }

    // Returns the member's preferred activity type
    public String getPreferredActivity() {
        return preferredActivity;
    }

    // Extends the Person summary with membership tier and preferred activity
    @Override
    public String toString() {
        // Extend the superclass string rather than rebuilding it
        return super.toString() + ", Tier: " + membershipTier + ", Prefers: " + preferredActivity;
    }
}
