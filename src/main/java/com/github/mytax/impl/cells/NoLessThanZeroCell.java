package com.github.mytax.impl.cells;

import java.math.BigDecimal;
import java.util.Optional;

public class NoLessThanZeroCell extends MoneyCell {
    private final MoneyCell sourceCell;

    public NoLessThanZeroCell(MoneyCell cell) {
        this.sourceCell = cell;
    }

    @Override
    public Optional<BigDecimal> getValue() {
        if(sourceCell.isNotFilledIn()) {
            return Optional.empty();
        }

        BigDecimal value = sourceCell.getValue().get();

        if(value.compareTo(BigDecimal.ZERO) < 0) {
            return Optional.of(BigDecimal.ZERO);
        } else {
            return Optional.of(value);
        }
    }
}
