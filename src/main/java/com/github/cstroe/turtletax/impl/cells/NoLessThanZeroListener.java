package com.github.cstroe.turtletax.impl.cells;

import com.github.cstroe.turtletax.api.Cell;
import com.github.cstroe.turtletax.api.event.CellValueChangeListener;

import java.math.BigDecimal;

public class NoLessThanZeroListener implements CellValueChangeListener<BigDecimal> {
    @Override
    public void valueChanged(Cell<BigDecimal> cell) {
        if(cell.isFilledIn()) {
            if(cell.getValue().get().compareTo(BigDecimal.ZERO) < 0) {
                cell.setValue(BigDecimal.ZERO);
            }
        }
    }
}
