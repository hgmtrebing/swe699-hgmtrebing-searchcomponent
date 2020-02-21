package us.hgmtrebing.swe699;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

}
