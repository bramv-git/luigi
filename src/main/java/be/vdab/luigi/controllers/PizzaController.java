package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.forms.FromToPriceForm;
import be.vdab.luigi.services.EuroService;

import be.vdab.luigi.services.PizzaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("pizzas")
public class PizzaController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PizzaService pizzaService;
    private final EuroService euroService;

    public PizzaController(PizzaService pizzaService, EuroService euroService) {
        this.pizzaService = pizzaService;
        this.euroService = euroService;
    }

    @GetMapping
    public ModelAndView pizzas() {
        return new ModelAndView("pizzas", "pizzas", pizzaService.findAll());
    }

    @GetMapping("{id}")
    public ModelAndView pizza(@PathVariable long id) {
        var modelAndView = new ModelAndView("pizza.html");
        pizzaService.findById(id).ifPresent(pizza -> {
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

    @GetMapping("prices")
    public ModelAndView prices(){
        return new ModelAndView("prices", "prices", pizzaService.findUniquePrices());
    }

    @GetMapping("prices/{price}")
    public ModelAndView pizzasWithAPrice(@PathVariable BigDecimal price){
        return new ModelAndView(
                "prices", "pizzas", pizzaService.findByPrice(price))
                .addObject("prices", pizzaService.findUniquePrices());
    }

    @GetMapping("numberofpizzasperprice")
    public ModelAndView numberOfPizzasPerPrice(){
        return new ModelAndView("numberofpizzasperprice",
                "numberOfPizzasPerPrice", pizzaService.findNumberOfPizzasPerPrice());
    }

    @GetMapping("fromtoprice")
    public ModelAndView fromToPrice(@Valid FromToPriceForm form, Errors errors){
        var modelAndView = new ModelAndView("fromtoprice");
        if(errors.hasErrors()){
            return modelAndView;
        }
        return modelAndView.addObject("pizzas",
                pizzaService.findByPriceBetween(form.from(), form.to()));
    }

    @GetMapping("/fromtoprice/form")
    public ModelAndView fromToPriceForm(){
        return new ModelAndView("fromtoprice")
                .addObject(new FromToPriceForm(BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @GetMapping("/add/form")
    public ModelAndView addForm(){
        return new ModelAndView("add")
                .addObject(new Pizza(0, "", null, false));
    }

    @PostMapping
    public String add(@Valid Pizza pizza, Errors errors, RedirectAttributes redirect){
        if(errors.hasErrors()){
            return "add";
        }
        redirect.addAttribute("idNewPizza", pizzaService.create(pizza));
        return "redirect:/pizzas";
    }
}
