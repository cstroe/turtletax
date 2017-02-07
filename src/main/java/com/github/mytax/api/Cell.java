package com.github.mytax.api;

import java.util.Optional;

public interface Cell<V> {
    String getId();
    String getLabel();
    Line getLine();

    Optional<V> getValue();
    void setValue(V value);

    default boolean isFilledIn() {
        return getValue().isPresent();
    }

    default boolean isNotFilledIn() {
        return !isFilledIn();
    }
}
