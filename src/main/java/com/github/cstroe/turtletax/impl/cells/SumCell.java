package com.github.cstroe.turtletax.impl.cells;

import com.github.cstroe.turtletax.api.Cell;
import com.github.cstroe.turtletax.api.Form;
import com.github.cstroe.turtletax.api.Line;

import java.math.BigDecimal;
import java.util.Optional;

public class SumCell extends MoneyCell {
    private final Form form;
    private final Line[] linesToAddUp;

    public SumCell(Form form, Line... linesToAddUp) {
        this.form = form;
        this.linesToAddUp = linesToAddUp;
    }

    @Override
    public void valueChanged(Cell<BigDecimal> cell) {}

    @Override
    public Optional<BigDecimal> getValue() {
        BigDecimal sum = BigDecimal.ZERO;
        for(Line line : linesToAddUp) {
            MoneyCell cell = form.getCellAsType(line, MoneyCell.class);
            if (cell.isNotFilledIn()) {
                return Optional.empty();
            }

            sum = sum.add(cell.getValue().get());
        }
        return Optional.of(sum);
    }
}