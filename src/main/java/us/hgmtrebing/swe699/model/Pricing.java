package us.hgmtrebing.swe699.model;

public enum Pricing {
    ANY(0, -1, -1),
    ONE_STAR (1, 0, 8.5),
    TWO_STAR (2, 8.51, 15.0),
    THREE_STAR (3, 15.01, 20.0),
    FOUR_STAR (4, 20.01, 35.00),
    FIVE_STAR(5, 35.01, 1_000_000)
    ;

    private int numStars;
    private double startingPrice;
    private double endingPrice;

    Pricing(int numStars, double startingPrice, double endingPrice) {
        this.numStars = numStars;
        this.startingPrice = startingPrice;
        this.endingPrice = endingPrice;
    }
}
