package com.github.mytax.impl.rules;

import com.github.mytax.api.Cell;
import com.github.mytax.api.Form;
import com.github.mytax.api.Mistake;
import com.github.mytax.api.Rule;
import com.github.mytax.impl.cells.BooleanCell;

import java.util.List;

import static java.lang.String.format;

public class RequiredCellValueIfBooleanIsTrue implements Rule {
    private final Form form;
    private final String requiredId;
    private final String booleanId;

    public RequiredCellValueIfBooleanIsTrue(Form form, String booleanId, String requiredId) {
        this.form = form;
        this.requiredId = requiredId;
        this.booleanId = booleanId;
    }

    @Override
    public List<Mistake> validate() {
        BooleanCell checkbox = form.getCellAsType(booleanId, BooleanCell.class);
        if(checkbox.isNotFilledIn()) {
            return aSimpleMistake(format("Cell '%s' must be filled in.", booleanId));
        }

        Cell value = form.getCell(requiredId);
        if(value.isNotFilledIn()) {
            return aSimpleMistake(format("Cell '%s' must be filled in.", requiredId));
        }

        return noMistake();
    }
}
