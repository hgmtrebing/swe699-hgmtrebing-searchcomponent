package us.hgmtrebing.swe699.search;

import lombok.Data;
import us.hgmtrebing.swe699.model.State;

@Data
public class SearchRequest {

    private String textSearchInput;
    private String streetAddress;
    private String city;
    private State state;
    private int zipCode;

    public SearchRequest () { }

    public void setState(String state) {
        this.state = State.parseFromString(state);
    }
}
