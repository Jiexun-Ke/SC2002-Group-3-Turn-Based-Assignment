package model.actions;

import java.util.ArrayList;
import java.util.List;

public class ActionResult {
    private final String actionName;
    private final int damageDealt;
    private final int healingDone;
    private final boolean appliedStatusEffect;
    private final String statusEffectName;
    private final List<String> targetSummaries;
    private final boolean prevented;
    private final String reason;

    public ActionResult(String actionName, int damageDealt, int healingDone,
                        boolean appliedStatusEffect, String statusEffectName,
                        List<String> targetSummaries, boolean prevented, String reason) {
        this.actionName = actionName;
        this.damageDealt = damageDealt;
        this.healingDone = healingDone;
        this.appliedStatusEffect = appliedStatusEffect;
        this.statusEffectName = statusEffectName;
        this.targetSummaries = targetSummaries != null ? targetSummaries : new ArrayList<>();
        this.prevented = prevented;
        this.reason = reason;
    }

    public String getActionName() {
        return actionName;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public int getHealingDone() {
        return healingDone;
    }

    public boolean isAppliedStatusEffect() {
        return appliedStatusEffect;
    }

    public String getStatusEffectName() {
        return statusEffectName;
    }

    public List<String> getTargetSummaries() {
        return targetSummaries;
    }

    public boolean isPrevented() {
        return prevented;
    }

    public String getReason() {
        return reason;
    }


}