package be.vdab.luigi.repositories;

import be.vdab.luigi.domain.Pizza;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

@JdbcTest
@Import(JdbcPizzaRepository.class)
@Sql("/insertPizzas.sql")
public class JdbcPizzaRepositoryTest {
private static final String PIZZAS = "pizzas";
    private final JdbcPizzaRepository repository;
    private final JdbcTemplate template;

    public JdbcPizzaRepositoryTest(JdbcPizzaRepository repository, JdbcTemplate template) {
        this.repository = repository;
        this.template = template;
    }

    @Test
    void findNumber() {
        assertThat(repository.findNumber()).isEqualTo(countRowsInTable(template, PIZZAS));
    }

    @Test
    void findAllGivesAllPizzasSortedById() {
        assertThat(repository.findAll())
                .hasSize(countRowsInTable(template, PIZZAS))
                .extracting(Pizza::getId)
                .isSorted();
    }

    @Test
    void create(){
        var id = repository.create(new Pizza(0,"test2", BigDecimal.TEN, false));
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(template, PIZZAS, "id = " + id)).isOne();
    }

    @Test
    void numberOfPizzasPerPrice() {
        var numberOfPizzasPerPrice = repository.findNumberOfPizzasPerPrice();
        assertThat(numberOfPizzasPerPrice)
                .hasSize(template.queryForObject(
                        "SELECT COUNT(DISTINCT price) FROM pizzas", Integer.class));

        var row1 = numberOfPizzasPerPrice.get(0);
        assertThat(row1.number())
                .isEqualTo(countRowsInTableWhere(template, PIZZAS, "price = " + row1.price()));
    }
}
