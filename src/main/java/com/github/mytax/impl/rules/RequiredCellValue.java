package com.github.mytax.impl.rules;

import com.github.mytax.api.Cell;
import com.github.mytax.api.Form;
import com.github.mytax.api.Mistake;
import com.github.mytax.api.Rule;

import java.util.List;

public class RequiredCellValue implements Rule {
    private final Form form;
    private final String cell;

    public RequiredCellValue(Form form, String cell) {
        this.form = form;
        this.cell = cell;
    }

    @Override
    public List<Mistake> validate() {
        Cell thecell = form.getCell(cell);
        if(thecell.isNotFilledIn()) {
            return aSimpleMistake("You must fill in cell %s", thecell.getId());
        } else {
            return noMistake();
        }
    }
}
