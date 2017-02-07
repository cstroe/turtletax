package com.github.mytax.api.event;

import com.github.mytax.api.Cell;

import java.util.EventListener;

public interface CellValueChangeListener<V> extends EventListener {
    void valueChanged(Cell<V> cell);
}
