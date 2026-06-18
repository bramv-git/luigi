package be.vdab.luigi.services;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.dto.NumberOfPizzasPerPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PizzaService {
    long create(Pizza pizza);
    void update(Pizza pizza);
    void delete(long id);
    List<Pizza> findAll();
    Optional<Pizza> findById(long id);
    List<Pizza> findByPriceBetween(BigDecimal min, BigDecimal max);
    long findNumber();
    List<BigDecimal> findUniquePrices();
    List<Pizza> findByPrice(BigDecimal price);
    List<Pizza> findByIds(Set<Long> ids);
    List<NumberOfPizzasPerPrice> findNumberOfPizzasPerPrice();
}
