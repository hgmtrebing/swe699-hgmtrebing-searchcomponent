package us.hgmtrebing.swe699.search;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Results implements Iterable<IndividualRestaurantSearchResult>{

    private List<IndividualRestaurantSearchResult> results;

    @Getter @Setter
    private ResultsType resultsType;

    @Getter @Setter
    private SearchRequest request;

    public Results() {
        this.results = new ArrayList<>();
    }



    public void sortAndFilter() {
        if (request == null) {
            return;
        }

        if (this.getNumberOfSearchResults() <= 0) {
            return;
        }


    }
    public void addSearchResult(IndividualRestaurantSearchResult result) {
        this.results.add(result);
    }

    public void removeSearchResultAt(int index) {
       if (index < this.results.size()) {
           this.results.remove(index);
       }
    }

    public void switchResults(int index1, int index2) {
        if (index1 < this.results.size() && index2 < this.results.size()) {
            IndividualRestaurantSearchResult a = this.results.get(index1);
            this.results.set(index1, this.results.get(index2));
            this.results.set(index2, a);
        }
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
