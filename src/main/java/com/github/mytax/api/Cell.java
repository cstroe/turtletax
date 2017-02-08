package com.github.mytax.api;

import java.util.List;
import java.util.Optional;

/**
 * A cell is a container for a piece of information in
 * a {@link Form}.
 *
 * Declaring a cell can impose a set of {@link Rule rules} impacting
 * the validity of the filled-in form.  The simplest example is
 * the requirement that a cell must be filled in, which
 * would introduce a {@link Mistake} if that cell is left blank.
 */
public interface Cell<V> {
    String getId();
    String getLabel();
    Line getLine();
    List<Rule> getRules();

    Optional<V> getValue();
    void setValue(V value);

    default boolean isFilledIn() {
        return getValue().isPresent();
    }
    default boolean isNotFilledIn() {
        return !isFilledIn();
    }
}
