package com.github.cstroe.turtletax.forms.y2016;

import com.github.cstroe.turtletax.api.Form;
import com.github.cstroe.turtletax.impl.cells.MoneyCell;
import lombok.val;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.cstroe.turtletax.api.Line.line;
import static com.github.cstroe.turtletax.impl.ReflectionUtils.match;

public class W2IncomeCell extends MoneyCell {
    @Override
    public Optional<BigDecimal> getValue() {
        val w2forms = getForm().getTaxReturn().getForms().stream()
                .filter(form -> match(form, FormW2.class))
                .collect(Collectors.toList());
        BigDecimal sum = null;
        for(Form w2 : w2forms) {
            val wages = w2.getCellAsType(line(1), MoneyCell.class).getValue();
            if(!wages.isPresent()) {
                return Optional.empty();
            }

            if(sum == null) {
                sum = wages.get();
            } else {
                sum = sum.add(wages.get());
            }
        }

        return Optional.ofNullable(sum);
    }
}
