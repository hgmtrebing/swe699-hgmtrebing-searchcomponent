package us.hgmtrebing.swe699;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Swe699RestController {

    // hgt - removing so that index.html can be the entry point
    /*
    @RequestMapping("/")
    public String index() {
        return "Hello World Buddies!";
    }

     */

    @RequestMapping("/test")
    public String test() {
        return "Goodbye peeps!";
    }

    @RequestMapping("/restaurant_search")
    public String restaurantSearch(@RequestParam("text-search") String textSearch, Model model) {
        model.addAttribute("textSearch", textSearch);
        model.addAttribute("results", SearchEngine.getSearchResults(textSearch));
        return "search_results";
    }

}
