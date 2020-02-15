package us.hgmtrebing.swe699;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Swe699RestController {

    @RequestMapping("/")
    public String index() {
        return "Hello World Buddies!";
    }

    @RequestMapping("/test")
    public String test() {
        return "Goodbye peeps!";
    }
}
