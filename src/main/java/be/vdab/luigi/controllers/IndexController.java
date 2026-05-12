package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Address;
import be.vdab.luigi.domain.Persoon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public ModelAndView index() {
        var morningOrAfternoon = LocalTime.now().getHour() < 12 ? "morning" : "afternoon";
        return new ModelAndView("index", "time", morningOrAfternoon)
                .addObject("owner",
                        new Persoon("Luigi","Peperone",7, true,
                                LocalDate.of(1966,1,31),
                        new Address("Grote markt", "3", 9700, "Oudenaarde")));
    }
}
