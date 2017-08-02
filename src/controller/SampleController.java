package controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Fresher on 27/07/2017.
 */


@EnableAutoConfiguration
@RestController
public class SampleController {
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

}
