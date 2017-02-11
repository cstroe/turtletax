package com.github.mytax.impl.rules;

import com.github.mytax.api.CellId;
import com.github.mytax.api.Form;
import com.github.mytax.api.Mistake;
import com.github.mytax.api.Rule;

import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

/**
 * A rule to require that a cell is filled when another one is filled in.
 */
public class RequiredCellIfValueIsFilledIn implements Rule {
    private final Form form;
    private final CellId required;
    private final CellId checkThis;

    public RequiredCellIfValueIsFilledIn(Form form, CellId checkThis, CellId required) {
        this.form = form;
        this.required = required;
        this.checkThis = checkThis;
    }

    @Override
    public List<Mistake> validate() {
        if(checkThis.on(form).isFilledIn() && required.on(form).isNotFilledIn()) {
            return aSimpleMistake(required, format("Cell '%s' must be filled in because cell '%s' is filled in.", required, checkThis));
        }
        return Collections.emptyList();
   }
}
