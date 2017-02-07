package com.github.mytax.impl;

import lombok.Getter;
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
