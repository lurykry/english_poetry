package edu.project.englishstoriesbot.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rounder {
    public static int round(double value) {

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(0, RoundingMode.CEILING);
        return bd.intValue();
    }
}
