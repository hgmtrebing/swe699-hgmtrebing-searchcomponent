package us.hgmtrebing.swe699;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class SearchEngine {

    public static List<String> getSearchResults(String textSearch) {
        List<String> searchResults = new ArrayList<>();
        searchResults.add("Popeyes");
        searchResults.add("McDonalds");
        searchResults.add("Wendys");

        return searchResults;
    }
}
