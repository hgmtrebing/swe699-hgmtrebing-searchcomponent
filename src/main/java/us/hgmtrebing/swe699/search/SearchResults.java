package us.hgmtrebing.swe699.search;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchResults implements Iterable<IndividualRestaurantSearchResult>{

    @Getter @Setter
    private SearchRequest request;
    private List<IndividualRestaurantSearchResult> results;

    public SearchResults() {
        this.results = new ArrayList<>();
    }

    public void addSearchResult(IndividualRestaurantSearchResult result) {
        this.results.add(result);
    }

    public IndividualRestaurantSearchResult getResultAt(int index) {
        if(index < this.results.size()) {
            return results.get(index);
        } else {
            return null;
        }
    }

    public int getNumberOfSearchResults() {
        return this.results.size();
    }

    @Override
    public Iterator<IndividualRestaurantSearchResult> iterator() {
        return new Iterator<IndividualRestaurantSearchResult>() {
            private int location = 0;
            private int size = results.size();
            private boolean stateViolated = false;

            private void checkState() {
                if (size != results.size()) {
                    stateViolated = true;
                }

                if (this.stateViolated) {
                    throw new IllegalStateException("The state of this Iterator has been violated ( by adding or removing elements)!");
                }
            }

            @Override
            public boolean hasNext() {
                this.checkState();
                return this.location < this.size;
            }

            @Override
            public IndividualRestaurantSearchResult next() {
                checkState();
                if (this.hasNext()) {
                   return results.get(this.location++);
                }
                throw new IndexOutOfBoundsException("No More elements in this iterator!");
            }
        };
    }
}
