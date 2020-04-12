package us.hgmtrebing.swe699.model;

import lombok.Getter;

public enum Pricing {
    ONE_STAR (-1, 10.5, "One Star", "$"),
    TWO_STAR (10.5, 15, "Two Stars", "$$"),
    THREE_STAR(15, 25, "Three Stars", "$$$"),
    FOUR_STAR (25, 40, "Four Stars", "$$$$"),
    FIVE_STAR (40, -1, "Five Stars", "$$$$$");

    @Getter private double lowBounds;
    @Getter private double highBounds;
    @Getter private String formalName;
    @Getter private String simpleName;

    Pricing(double lowBounds, double highBounds, String formalName, String simpleName) {
        this.lowBounds = lowBounds;
        this.highBounds = highBounds;
        this.formalName = formalName;
        this.simpleName = simpleName;
    }

    public static Pricing getPriceRankByPrice (double price) {
        for (Pricing pricing : Pricing.values()) {
            if (    (pricing.lowBounds == -1 && price < pricing.highBounds) ||
                    (pricing.highBounds == -1 && price > pricing.lowBounds) ||
                    (price > pricing.lowBounds && price < pricing.highBounds) ){

                return pricing;
            }
        }
        return null;
    }

    public static Pricing parseFromString (String string) {
        for (Pricing pricing : Pricing.values()) {
            if (pricing.getFormalName().equals(string) || pricing.getSimpleName().equals(string) ||
            pricing.name().equals(string)) {
                return pricing;
            }
        }
        return null;
    }
}
