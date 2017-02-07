package com.github.mytax.impl.cells;

import lombok.Setter;

import java.util.Optional;

public class BooleanCell extends BaseCell<Boolean> {
    @Setter private Boolean value;

    @Override
    public Optional<Boolean> getValue() {
        return Optional.ofNullable(value);
    }
}
