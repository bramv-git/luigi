package be.vdab.luigi.repositories;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.dto.NumberOfPizzasPerPrice;
import be.vdab.luigi.exceptions.PizzaNotFoundException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class JdbcPizzaRepository implements PizzaRepository{
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    private final RowMapper<Pizza> pizzaMapper = (result, rowNum) ->
            new Pizza(result.getLong("id"),
                    result.getString("name"),
                    result.getBigDecimal("price"),
                    result.getBoolean("spicy"));

    private final RowMapper<BigDecimal> priceMapper = (result, rowNum) ->
            result.getBigDecimal("price");

    public JdbcPizzaRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template)
                .withTableName("pizzas")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long create(Pizza pizza) {
        return insert.executeAndReturnKey(
                Map.of("name", pizza.getName(),
                        "price", pizza.getPrice(),
                        "spicy", pizza.isSpicy()))
                .longValue();
    }

    @Override
    public void update(Pizza pizza) {
        var sql = """
            UPDATE pizzas
            SET name = ?, price = ?, spicy = ?
            WHERE id = ?
            """;
        if(template.update(sql, pizza.getName(), pizza.getPrice(), pizza.isSpicy(), pizza.getId()) == 0){
        throw new PizzaNotFoundException();
        }
    }

    @Override
    public void delete(long id) {
        template.update("""
                DELETE FROM pizzas
                WHERE id = ?
                """, id);
    }

    @Override
    public List<Pizza> findAll() {
        return template.query("""
                SELECT id, name, price, spicy
                FROM pizzas
                ORDER BY id
                """, pizzaMapper);
    }

    @Override
    public Optional<Pizza> findById(long id) {
        try {
            var sql = """
                SELECT id, name, price, spicy
                FROM pizzas
                WHERE id = ?
                """;
        return Optional.of(template.queryForObject(sql, pizzaMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex){
            return Optional.empty();
        }

    }

    @Override
    public List<Pizza> findByPriceBetween(BigDecimal min, BigDecimal max) {
        return template.query("""
                SELECT id, name, price, spicy
                FROM pizzas
                WHERE price BETWEEN ? AND ?
                ORDER BY price
                """, pizzaMapper, min, max);
    }

    @Override
    public long findNumber() {
        return template.queryForObject("""
                SELECT COUNT(*) 
                FROM pizzas
                """, Long.class);
    }

    @Override
    public List<BigDecimal> findUniquePrices() {
        return template.query("""
                SELECT DISTINCT price
                FROM pizzas
                ORDER BY price
                """, priceMapper);
    }

    @Override
    public List<Pizza> findByPrice(BigDecimal price) {
        return template.query("""
                SELECT id, name, price, spicy
                FROM pizzas
                WHERE price = ?
                """, pizzaMapper, price);
    }

    @Override
    public List<Pizza> findByIds(Set<Long> ids) {
        if(ids.isEmpty()){
            return List.of();
        }
        return template.query("""
                SELECT id, name, price, spicy
                FROM pizzas
                WHERE id IN (""" + "?,".repeat(ids.size() - 1) + "?) ORDER BY id"
                , pizzaMapper, ids);
    }

    @Override
    public List<NumberOfPizzasPerPrice> findNumberOfPizzasPerPrice() {
        var sql = """
                SELECT price, count(*) AS number
                FROM pizzas
                GROUP BY price
                ORDER BY price
                """;
        RowMapper<NumberOfPizzasPerPrice> mapper = (result, rowNum) ->
                new NumberOfPizzasPerPrice(
                        result.getBigDecimal("price"),
                        result.getInt("number"));
        return template.query(sql, mapper);
    }
}
