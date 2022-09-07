package tnb.vndac.data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import java.awt.Color;

@SuppressWarnings("unchecked")
public class NcEngineering extends BaseHullMod {

    // Accelerated Shield Emitter
    public static final float SHIELD_BONUS_TURN = 60f;
    public static final float SHIELD_BONUS_UNFOLD = 60f;

    // Blast Doors
    public static final float HULL_BONUS = 15f;
    public static final float CASUALTY_REDUCTION = 30f;

    // Hardened Subsystems
    public static final float PEAK_BONUS_PERCENT = 50f;
    public static final float DEGRADE_REDUCTION_PERCENT = 25f;

    // Extended Shield Emitter
    public static final float SHIELD_ARC_BONUS = 30f;

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        // Accelerated Shield Emitter
        stats.getShieldTurnRateMult().modifyPercent(id, SHIELD_BONUS_TURN);
        stats.getShieldUnfoldRateMult().modifyPercent(id, SHIELD_BONUS_UNFOLD);

        // Blast Doors
        stats.getHullBonus().modifyPercent(id, HULL_BONUS);
        stats.getCrewLossMult().modifyMult(id, 1f - CASUALTY_REDUCTION * 0.01f);

        // Hardened Subsystems
        stats.getPeakCRDuration().modifyPercent(id, PEAK_BONUS_PERCENT);
        stats.getCRLossPerSecondPercent().modifyMult(id, 1f - DEGRADE_REDUCTION_PERCENT / 100f);

        // Extended Shield Emitter
        stats.getShieldArcBonus().modifyFlat(id, SHIELD_ARC_BONUS);
    }


    public String getDescriptionParam(int index, HullSize hullSize) {
        // Accelerated Shield Emitter
        if (index == 0) return "" + (int) SHIELD_BONUS_TURN + "%";
        if (index == 1) return "" + (int) SHIELD_BONUS_UNFOLD + "%";

        // Blast Doors
        if (index == 2) return "" + (int) HULL_BONUS + "%";
        if (index == 3) return "" + (int) CASUALTY_REDUCTION + "%";

        // Hardened Subsystems
        if (index == 4) return "" + (int) PEAK_BONUS_PERCENT + "%";
        if (index == 5) return "" + (int) DEGRADE_REDUCTION_PERCENT + "%";

        // Extended Shield Emitter
        if (index == 6) return "" + (int) SHIELD_ARC_BONUS;

        return null;
    }

    public boolean isApplicableToShip(ShipAPI ship) {
        return ship != null && ship.getShield() != null; // && (ship.getHullSpec().getNoCRLossTime() < 10000 || ship.getHullSpec().getCRLossPerSecond() > 0)
    }
    public String getUnapplicableReason(ShipAPI ship) {
        return "Ship has no shields";
    }

    @Override
    public boolean affectsOPCosts() {
        return false;
    }

    @Override
    public Color getBorderColor() {
        return new Color(115, 10, 8, 0);
    }

    @Override
    public Color getNameColor() {
        return new Color(146, 0, 0,255);
    }

}