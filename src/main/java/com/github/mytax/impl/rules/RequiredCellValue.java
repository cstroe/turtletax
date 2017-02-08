package com.github.mytax.impl.rules;

import com.github.mytax.api.Cell;
import com.github.mytax.api.Form;
import com.github.mytax.api.Mistake;
import com.github.mytax.api.Rule;

import java.util.List;

public class RequiredCellValue implements Rule {
    private final Form form;
    private final String cellId;

    public RequiredCellValue(Form form, String cellId) {
        this.form = form;
        this.cellId = cellId;
    }

    @Override
    public List<Mistake> validate() {
        Cell thecell = form.getCell(cellId);
        if(thecell.isNotFilledIn()) {
            return aSimpleMistake("You must fill in cell with id '%s'", thecell.getId());
        } else {
            return noMistake();
        }
    }
}
