package com.github.cstroe.turtletax.impl.cells;

import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

public class MoneyCell extends BaseCell<BigDecimal> {
    @Setter private BigDecimal value;

    @Override
    public Optional<BigDecimal> getValue() {
        return Optional.ofNullable(value);
    }
}
