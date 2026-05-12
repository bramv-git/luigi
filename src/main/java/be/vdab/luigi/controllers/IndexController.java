package be.vdab.luigi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;

@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public ModelAndView index() {
        var morningOrAfternoon = LocalTime.now().getHour() < 12 ? "morning" : "afternoon";
        return new ModelAndView("index", "time", morningOrAfternoon);
    }
}
