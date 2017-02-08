package com.github.mytax.impl.cells;

import com.github.mytax.api.Cell;
import com.github.mytax.api.Form;
import com.github.mytax.api.Line;
import com.github.mytax.api.Rule;
import com.github.mytax.api.event.CellValueChangeListener;
import com.github.mytax.api.event.CellValueChangeSource;
import com.github.mytax.impl.rules.RequiredCellValue;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseCell<V> implements Cell<V>, CellValueChangeSource, CellValueChangeListener<V> {
    @Getter @Setter private String id;
    @Getter @Setter private String label;
    @Getter @Setter private Line line;
    @Getter @Setter private boolean isRequired = false;
    @Getter @Setter private Form form;

    private List<CellValueChangeListener> listeners = new ArrayList<>();

    @Override
    public void feeds(CellValueChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public List<CellValueChangeListener> getCellValueChangeListeners() {
        return Collections.unmodifiableList(listeners);
    }

    @Override
    public void valueChanged(Cell<V> cell) {
        Optional<V> newValue = cell.getValue();
        if(newValue.isPresent()) {
            setValue(newValue.get());
        } else {
            setValue(null);
        }
    }

    @Override
    public List<Rule> getRules() {
        if(form == null) {
            throw new NullPointerException("No form set.");
        }
        if(isRequired) {
            return Collections.singletonList(new RequiredCellValue(form, id));
        }
        return Collections.emptyList();
    }
}
