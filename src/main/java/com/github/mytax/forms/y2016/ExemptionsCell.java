package com.github.mytax.forms.y2016;

import com.github.mytax.api.Form;
import com.github.mytax.impl.BaseCell;
import com.github.mytax.impl.CountBoxesCell;
import com.github.mytax.impl.MoneyCell;

import java.math.BigDecimal;
import java.util.Optional;

import static com.github.mytax.api.Line.line;

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

            return 
        }
    }
}
