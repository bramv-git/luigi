package be.vdab.luigi.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record FromToPriceForm(@NotNull @PositiveOrZero BigDecimal from,
                              @NotNull @PositiveOrZero BigDecimal to) {
}
