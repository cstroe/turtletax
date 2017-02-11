package com.github.cstroe.turtletax.impl.cells;

import com.github.cstroe.turtletax.api.Cell;
import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.forms.y2016.Form1040;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.String.format;

public class CountFilledInCell extends BaseCell<Integer> {
    private Form1040 form;
    private CellId[] ids;

    public CountFilledInCell(Form1040 form, CellId... ids) {
        this.form = form;
        this.ids = ids;
    }

    @Override
    public Optional<Integer> getValue() throws NoSuchElementException {
        int filledIn = 0;
        for(CellId id : ids) {
            Cell cell = form.getCell(id);
            if(cell.getValue().isPresent()) {
                filledIn++;
            }
        }

        return Optional.of(filledIn);
    }

    @Override
    public void setValue(Integer value) {
        throw new UnsupportedOperationException(format("Cannot set the value of a count boxes cell with id '%s'.",getId()));
    }
}
