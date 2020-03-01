package us.hgmtrebing.swe699;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import us.hgmtrebing.swe699.model.Cuisine;
import us.hgmtrebing.swe699.search.SearchEngine;
import us.hgmtrebing.swe699.search.SearchRequest;
import us.hgmtrebing.swe699.search.SearchResults;

@Controller
public class Swe699RestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/test")
    public String test() {
        return "Goodbye peeps!";
    }

    @RequestMapping("/restaurant_search")
    public String restaurantSearch( @RequestParam("text-search") String textSearch,
                                    @RequestParam(value = "street-address", defaultValue="") String streetAddress,
                                    @RequestParam(value = "city", defaultValue="") String city,
                                    @RequestParam(value = "state", defaultValue="") String state,
                                    @RequestParam(value = "zipcode", defaultValue="22030") String zipCode,
                                    Model model) {

        log.info("Search request received with the following parameters: \n{} \n{} \n{} \n{} \n{}", textSearch, streetAddress, city, state, zipCode);

        SearchRequest request = new SearchRequest();
        request.setTextSearchInput(textSearch);
        request.setStreetAddress(streetAddress);
        request.setCity(city);
        request.setState(state);
        request.setZipCode(Integer.parseInt(zipCode));

        /*
        SearchEngine engine = new SearchEngine();
        SearchResults results = engine.getSearchResults(request);
        System.out.println(results.getResultsType());


        model.addAttribute("results", results);

         */
        return "search_results";
    }
}
