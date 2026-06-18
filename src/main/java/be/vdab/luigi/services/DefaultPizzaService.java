package be.vdab.luigi.services;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.dto.NumberOfPizzasPerPrice;
import be.vdab.luigi.repositories.PizzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, timeout = 10)
public class DefaultPizzaService implements PizzaService{
    private final PizzaRepository repository;

    public DefaultPizzaService(PizzaRepository repository) {
        this.repository = repository;
    }

    @Override
    public long create(Pizza pizza) {
        return repository.create(pizza);
    }

    @Override
    public void update(Pizza pizza) {
        repository.update(pizza);
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pizza> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pizza> findById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pizza> findByPriceBetween(BigDecimal min, BigDecimal max) {
        return repository.findByPriceBetween(min, max);
    }

    @Override
    @Transactional(readOnly = true)
    public long findNumber() {
        return repository.findNumber();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BigDecimal> findUniquePrices() {
        return repository.findUniquePrices();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pizza> findByPrice(BigDecimal price) {
        return repository.findByPrice(price);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pizza> findByIds(Set<Long> ids) {
        return repository.findByIds(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NumberOfPizzasPerPrice> findNumberOfPizzasPerPrice() {
        return repository.findNumberOfPizzasPerPrice();
    }
}
