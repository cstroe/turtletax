package com.github.mytax.impl.cells;

import com.github.mytax.api.Cell;
import com.github.mytax.impl.cells.BaseCell;
import lombok.Setter;

import java.util.Optional;

public class StringCell extends BaseCell<String> {
    @Setter private String value;

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }
}
