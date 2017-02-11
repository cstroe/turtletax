package com.github.cstroe.turtletax.impl.rules;

import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.api.Form;
import com.github.cstroe.turtletax.api.Cell;
import com.github.cstroe.turtletax.api.Mistake;
import com.github.cstroe.turtletax.api.Rule;

import java.util.List;

public class RequiredCellValue implements Rule {
    private final Form form;
    private final CellId required;

    public RequiredCellValue(Form form, CellId required) {
        this.form = form;
        this.required = required;
    }

    @Override
    public List<Mistake> validate() {
        Cell thecell = form.getCell(required);
        if(thecell.isNotFilledIn()) {
            return aSimpleMistake(required, "You must fill in cell with id '%s'", thecell.getId());
        } else {
            return noMistake();
        }
    }
}
