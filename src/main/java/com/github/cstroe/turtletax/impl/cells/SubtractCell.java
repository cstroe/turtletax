package com.github.cstroe.turtletax.impl.cells;

import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.api.Form;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

public class SubtractCell extends MoneyCell {
    private final Form form;
    private final CellId subtractFromThisCell;
    private final CellId[] cellsToSubtract;

    public SubtractCell(Form form, CellId subtractFromThisCell, CellId... cellsToSubtract) {
        this.form = form;
        this.subtractFromThisCell = subtractFromThisCell;
        this.cellsToSubtract = cellsToSubtract;
    }

    @Override
    public Optional<BigDecimal> getValue() {
        Optional<BigDecimal> sumOfCellsToSubtract = Stream.of(cellsToSubtract)
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
