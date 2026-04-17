package model.combat;

public class DamageResult {
    private final int damage;
    private final boolean prevented;
    private final String reason;

    public DamageResult(int damage, boolean prevented, String reason) {
        this.damage = damage;
        this.prevented = prevented;
        this.reason = reason;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isPrevented() {
        return prevented;
    }

    public String getReason() {
        return reason;
    }
}