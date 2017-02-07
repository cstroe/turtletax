package com.github.mytax.impl.cells;

import com.github.mytax.api.Form;
import com.github.mytax.api.Line;
import com.github.mytax.impl.cells.MoneyCell;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

public class SubtractCell extends MoneyCell {
    private final Form form;
    private final String subtractFromThisCell;
    private final String[] cellsToSubtract;

    public SubtractCell(Form form, String subtractFromThisCell, String... cellsToSubtract) {
        this.form = form;
        this.subtractFromThisCell = subtractFromThisCell;
        this.cellsToSubtract = cellsToSubtract;
    }

    @Override
    public Optional<BigDecimal> getValue() {
        Optional<BigDecimal> sumOfCellsToSubtract = Stream.of(cellsToSubtract)
                .map(Line::line)
                .map(line -> form.getCellAsType(line, MoneyCell.class))
                .map(MoneyCell::getValue)
                .reduce(Optional.of(BigDecimal.ZERO), (Optional<BigDecimal> c1, Optional<BigDecimal> c2) -> {
                    if(c1.isPresent() && c2.isPresent()) {
                        return Optional.of(c1.get().add(c1.get()));
                    } else {
                        return Optional.empty();
                    }
                });

        if(sumOfCellsToSubtract.isPresent()) {
            MoneyCell subtractFrom = form.getCellAsType(subtractFromThisCell, MoneyCell.class);
            if(subtractFrom.isFilledIn()) {
                return Optional.of(subtractFrom.getValue().get().subtract(sumOfCellsToSubtract.get()));
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
