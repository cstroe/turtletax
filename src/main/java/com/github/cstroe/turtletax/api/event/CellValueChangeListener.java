package com.github.cstroe.turtletax.api.event;

import com.github.cstroe.turtletax.api.Cell;

import java.util.EventListener;

public interface CellValueChangeListener<V> extends EventListener {
    void valueChanged(Cell<V> cell);
}
