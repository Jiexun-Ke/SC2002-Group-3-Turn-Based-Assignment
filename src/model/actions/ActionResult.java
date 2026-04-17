package model.actions;

public class ActionResult {
    private final int damageDealt;
    private final boolean prevented;
    private final String reason;

    public ActionResult(int damageDealt, boolean prevented, String reason) {
        this.damageDealt = damageDealt;
        this.prevented = prevented;
        this.reason = reason;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public boolean isPrevented() {
        return prevented;
    }

    public String getReason() {
        return reason;
    }
}
