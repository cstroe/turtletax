package com.github.cstroe.turtletax.impl.rules;

import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.api.Form;
import com.github.cstroe.turtletax.api.Mistake;
import com.github.cstroe.turtletax.impl.SimpleMistake;
import com.github.cstroe.turtletax.api.Rule;
import com.github.cstroe.turtletax.impl.cells.BooleanCell;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class CheckOneAndOnlyOne implements Rule {
    private final Form form;
    private final CellId[] cells;

    public CheckOneAndOnlyOne(Form form, CellId... cells) {
        this.form = form;
        this.cells = cells;
    }

    @Override
    public List<Mistake> validate() {
        try {
            List<Mistake> mistakes = new ArrayList<>();
            int numChecked = Stream.of(cells)
                    .map(name -> form.getCellAsType(name, BooleanCell.class))
                    .mapToInt(cell -> {
                        if (cell.getValue().get()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }).sum();

            if (numChecked == 0) {
                mistakes.add(SimpleMistake.of(null, "You must select your filing status."));
            } else if (numChecked > 1) {
                mistakes.add(SimpleMistake.of(null, "You must select only one filing status."));
            }

            return mistakes;
        } catch (NoSuchElementException ex) {
            return aSimpleMistake(null, "Exception while trying to ensure only one checkbox checked: %s", ex.getMessage());
        }
    }
}
