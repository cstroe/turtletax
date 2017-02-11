package com.github.cstroe.turtletax.api.event;

import java.util.List;

public interface CellValueChangeSource {
    void feeds(CellValueChangeListener listener);
    List<CellValueChangeListener> getCellValueChangeListeners();
}
