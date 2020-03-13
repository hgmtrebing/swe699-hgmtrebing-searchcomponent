package us.hgmtrebing.swe699.search;

import lombok.Getter;
import lombok.Setter;
import us.hgmtrebing.swe699.model.Restaurant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RestaurantSearchResults implements Iterable<Restaurant>{

    private List<Restaurant> results;

    @Getter @Setter
    private RestaurantSearchResultsType restaurantSearchResultsType;

    @Getter @Setter
    private RestaurantSearchRequest request;

    public RestaurantSearchResults() {
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
    public void addSearchResult(Restaurant result) {
        this.results.add(result);
    }

    public void removeSearchResultAt(int index) {
       if (index < this.results.size()) {
           this.results.remove(index);
       }
    }

    public void switchResults(int index1, int index2) {
        if (index1 < this.results.size() && index2 < this.results.size()) {
            Restaurant a = this.results.get(index1);
            this.results.set(index1, this.results.get(index2));
            this.results.set(index2, a);
        }
    }

    public Restaurant getResultAt(int index) {
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
    public Iterator<Restaurant> iterator() {
        return new Iterator<Restaurant>() {
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
            public Restaurant next() {
                checkState();
                if (this.hasNext()) {
                   return results.get(this.location++);
                }
                throw new IndexOutOfBoundsException("No More elements in this iterator!");
            }
        };
    }
}
