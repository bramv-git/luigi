package be.vdab.luigi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
@RequestMapping("os")
public class OSController {
    private static final String[] OSS = {"Windows", "Macintosh", "Linux","Android"};
    @GetMapping
    public ModelAndView os(@RequestHeader("User-Agent") String userAgent){
        var modalAndView = new ModelAndView("os");
        Arrays.stream(OSS)
                .filter(userAgent::contains)
                .findFirst()
                .ifPresent(os -> modalAndView.addObject("os", os));
        return modalAndView;
    }
}
