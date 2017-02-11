package com.github.mytax.impl.rules;

import com.github.mytax.api.Cell;
import com.github.mytax.api.CellId;
import com.github.mytax.api.Form;
import com.github.mytax.api.Mistake;
import com.github.mytax.api.Rule;
import com.github.mytax.impl.cells.BooleanCell;

import java.util.List;

import static java.lang.String.format;

public class RequiredCellValueIfBooleanIsTrue implements Rule {
    private final Form form;
    private final CellId required;
    private final CellId checkBox;

    public RequiredCellValueIfBooleanIsTrue(Form form, CellId checkBox, CellId required) {
        this.form = form;
        this.required = required;
        this.checkBox = checkBox;
    }

    @Override
    public List<Mistake> validate() {
        BooleanCell checkbox = form.getCellAsType(checkBox, BooleanCell.class);
        if(checkbox.isNotFilledIn()) {
            return aSimpleMistake(checkBox, format("Cell '%s' must be filled in.", checkBox.getId()));
        }

        Cell value = form.getCell(required);
        if(value.isNotFilledIn()) {
            return aSimpleMistake(required, format("Cell '%s' must be filled in.", required.getId()));
        }

        return noMistake();
    }
}
