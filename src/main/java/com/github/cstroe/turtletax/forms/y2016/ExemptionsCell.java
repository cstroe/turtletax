package com.github.cstroe.turtletax.forms.y2016;

import com.github.cstroe.turtletax.api.Form;
import com.github.cstroe.turtletax.impl.cells.CountBoxesCell;
import com.github.cstroe.turtletax.impl.cells.MoneyCell;

import java.math.BigDecimal;
import java.util.Optional;

import static com.github.cstroe.turtletax.api.Line.line;

public class ExemptionsCell extends MoneyCell {
    private final BigDecimal cutoff = BigDecimal.valueOf(155_650);
    private final BigDecimal creditAmount = BigDecimal.valueOf(4_050);
    private Form form;

    public ExemptionsCell(Form form) {
        this.form = form;
    }

    @Override
    public Optional<BigDecimal> getValue() {
        MoneyCell line38 = form.getCellAsType(line(38), MoneyCell.class);

        if(line38.isNotFilledIn()) {
            return Optional.empty();
        }

        if(line38.getValue().get().compareTo(cutoff) < 1) {
            CountBoxesCell count = form.getCellAsType(line("6d"), CountBoxesCell.class);

            if(count.isNotFilledIn()) {
                return Optional.empty();
            }

            return Optional.of(creditAmount.multiply(BigDecimal.valueOf(count.getValue().get())));
        }

        return Optional.empty();
    }
}
