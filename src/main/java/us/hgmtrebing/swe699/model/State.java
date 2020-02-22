package us.hgmtrebing.swe699.model;

import lombok.Getter;
import lombok.Setter;

public enum State {
    NONE ("NONE", "N/A"),

    ALASKA ("ALASKA", "AK"),

    VIRGINIA("VIRGINIA", "VA"),
    ;

    @Getter @Setter
    private String longName;

    @Getter @Setter
    private String abbreviation;

    State (String longName, String abbreviation) {
        this.longName = longName;
        this.abbreviation = abbreviation;
    }

    public static State parseFromString(String state) {
        for (State newState: State.values()) {
            if (newState.getLongName().equalsIgnoreCase(state)) {
                return newState;
            }
            if (newState.getAbbreviation().equalsIgnoreCase(state)) {
                return newState;
            }
        }
        return State.NONE;
    }
}
