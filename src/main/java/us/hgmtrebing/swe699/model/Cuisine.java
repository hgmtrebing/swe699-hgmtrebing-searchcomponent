package us.hgmtrebing.swe699.model;

public enum Cuisine {
    ANY,

    SPANISH,
    FRENCH,
    IRISH,
    BRITISH,
    GERMAN,
    GREEK,
    ITALIAN,
    RUSSIAN,
    EAST_EUROPEAN,
    MEDITERREAN,

    ASIAN,
    CHINESE,
    JAPANESE,
    KOREAN,
    VIETNAMESE,
    THAI,

    INDIAN,
    PERSIAN,
    AFGHAN,
    TURKISH,
    LEBANESE,
    ARAB,

    LATIN_AMERICAN,
    MEXICAN,
    CENTRAL_AMERICAN,

    BARBAQUE,
    CLASSIC_AMERICAN,
    GENERAL_AMERICAN,
    TEX_MEX,
    HAPPY_HOUR_FOOD,
    SOUL_FOOD,
    CAJUN,
    BAR_FOOD,

    BUFFET,
    SUSHI,
    HIBACHI,
    PHO,
    FUSION,
    VEGAN,
    VEGETARIAN,

    FAST_FOOD,
    CASUAL,
    SIT_DOWN,
    FORMAL
    ;

    public static Cuisine parseFromString(String input) {
        for (Cuisine cuisine : Cuisine.values()) {
            if (input.equalsIgnoreCase(cuisine.name())) {
                return cuisine;
            }
        }
        return null;
    }
}
