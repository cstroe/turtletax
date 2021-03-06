package com.github.cstroe.turtletax.impl.rules;

import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.api.Form;
import com.github.cstroe.turtletax.api.Mistake;
import com.github.cstroe.turtletax.api.Rule;

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
            return aSimpleMistake(required, format("You must fill in this cell because cell '%s' is filled in.", required, checkThis));
        }
        return Collections.emptyList();
   }
}
