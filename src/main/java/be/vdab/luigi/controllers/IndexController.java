package be.vdab.luigi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public String index() {
        var morningOrAfternoon = LocalTime.now().getHour() < 12 ? "morning" : "afternoon";
        return "<!doctype html><html><title>Hallo</title><body>Good "+morningOrAfternoon+"</body></html>";
    }
}
