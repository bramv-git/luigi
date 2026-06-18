package be.vdab.luigi.dto;

import java.math.BigDecimal;

public record NumberOfPizzasPerPrice(BigDecimal price, int number) {
}
