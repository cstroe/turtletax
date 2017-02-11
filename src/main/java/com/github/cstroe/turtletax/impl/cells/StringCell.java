package com.github.cstroe.turtletax.impl.cells;

import lombok.Setter;

import java.util.Optional;

public class StringCell extends BaseCell<String> {
    @Setter private String value;

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }
}
