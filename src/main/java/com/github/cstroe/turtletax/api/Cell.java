package com.github.cstroe.turtletax.api;

import java.util.Optional;

/**
 * A cell is a container for a piece of information in
 * a {@link Form}.
 */
public interface Cell<V> extends Comparable {
    CellId getId();
    void setId(CellId cid);
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
