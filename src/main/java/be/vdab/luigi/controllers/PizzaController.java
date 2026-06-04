package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.services.EuroService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Arrays;

@Controller
@RequestMapping("pizzas")
public class PizzaController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Pizza[] pizzas = {
            new Pizza(1,"Prosciutto", BigDecimal.valueOf(4),true),
            new Pizza(2,"Margherita", BigDecimal.valueOf(5),false),
            new Pizza(3,"Calzone", BigDecimal.valueOf(4),false)
    };
    private final EuroService euroService;

    public PizzaController(EuroService euroService) {
        this.euroService = euroService;
    }

    @GetMapping
    public ModelAndView pizzas() {
        return new ModelAndView("pizzas", "pizzas", pizzas);
    }

    @GetMapping("{id}")
    public ModelAndView pizza(@PathVariable long id) {
        var modelAndView = new ModelAndView("pizza.html");
        Arrays.stream(pizzas)
                .filter(pizza -> pizza.getId() == id)
                .findFirst()
                .ifPresent(pizza -> {
                    modelAndView.addObject("pizza", pizza);
                    try {
                        modelAndView.addObject(
                                "inDollar", euroService.toDollar(pizza.getPrice()));
                    } catch (KoersClientException ex) {
                     logger.error("Kan dollar koers niet lezen.", ex);
                    }
                });
        return modelAndView;
    }
}
