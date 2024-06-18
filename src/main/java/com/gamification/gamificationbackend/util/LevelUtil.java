package com.gamification.gamificationbackend.util;

public final class LevelUtil {
    private LevelUtil() {}

    public static int calculateLevel(int points, int levelFormulaPower, int levelFormulaConstant) {
        return Math.toIntExact((long) Math.floor(Math.pow(points, (double) 1 / levelFormulaPower) / levelFormulaConstant));
    }

    public static int calculateLevelPoints(int level, int levelFormulaPower, int levelFormulaConstant) {
        return Math.toIntExact(Math.round(Math.pow(level * levelFormulaConstant, levelFormulaPower)));
    }
}
