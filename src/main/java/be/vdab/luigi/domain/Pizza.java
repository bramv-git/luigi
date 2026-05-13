package be.vdab.luigi.domain;

import java.math.BigDecimal;

public class Pizza {
    private final long id;
    private final String name;
    private final BigDecimal price;
    private final boolean spicy;

    public Pizza(long id, String name, BigDecimal price, boolean spicy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.spicy = spicy;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isSpicy() {
        return spicy;
    }
}
