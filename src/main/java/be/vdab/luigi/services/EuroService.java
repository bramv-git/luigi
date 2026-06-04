package be.vdab.luigi.services;

import java.math.BigDecimal;

public interface EuroService {
    BigDecimal toDollar(BigDecimal euro);
}
