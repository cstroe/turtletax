package com.github.mytax.impl;

import lombok.Setter;

import java.util.Optional;

public class StringCell extends BaseCell<String> {
    @Setter private String value;

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }
}
