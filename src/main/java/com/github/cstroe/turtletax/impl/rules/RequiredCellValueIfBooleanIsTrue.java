package com.github.cstroe.turtletax.impl.rules;

import com.github.cstroe.turtletax.api.Cell;
import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.api.Form;
import com.github.cstroe.turtletax.api.Mistake;
import com.github.cstroe.turtletax.api.Rule;
import com.github.cstroe.turtletax.impl.cells.BooleanCell;

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
            return aSimpleMistake(checkBox, format("You must fill in this cell.", checkBox.getId()));
        }

        Cell value = form.getCell(required);
        if(value.isNotFilledIn()) {
            return aSimpleMistake(required, format("You must fill in this cell.", required.getId()));
        }

        return noMistake();
    }
}
