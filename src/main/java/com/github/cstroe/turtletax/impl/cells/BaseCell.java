package com.github.cstroe.turtletax.impl.cells;

import com.github.cstroe.turtletax.api.Cell;
import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.api.Line;
import com.github.cstroe.turtletax.api.event.CellValueChangeSource;
import com.github.cstroe.turtletax.api.event.CellValueChangeListener;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseCell<V> implements Cell<V>, CellValueChangeSource, CellValueChangeListener<V> {
    @Getter @Setter private CellId id;
    @Getter @Setter private String label;
    @Getter @Setter private Line line;

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
    public int compareTo(Object o) {
        return ((Cell)o).getId().getId().compareTo(getId().getId());
    }
}
