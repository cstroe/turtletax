package com.github.cstroe.turtletax.impl.cells;

import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.forms.y2016.Form1040;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.String.format;

public class CountBoxesCell extends BaseCell<Integer> {
    private Form1040 form;
    private CellId[] ids;

    public CountBoxesCell(Form1040 form, CellId... ids) {
        this.form = form;
        this.ids = ids;
    }

    @Override
    public Optional<Integer> getValue() throws NoSuchElementException {
        int checked = 0;
        for(CellId id : ids) {
            BooleanCell cell = form.getCellAsType(id, BooleanCell.class);

            if(!cell.getValue().isPresent()) {
                return Optional.empty();
            }

            if(cell.getValue().get()) {
                checked++;
            }
        }

        return Optional.of(checked);
    }

    @Override
    public void setValue(Integer value) {
        throw new UnsupportedOperationException(format("Cannot set the value of a count boxes cell with id '%s'.",getId()));
    }
}
