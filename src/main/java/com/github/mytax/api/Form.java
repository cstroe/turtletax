package com.github.mytax.api;

import com.github.mytax.impl.TaxReturn;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

public interface Form {
    String getId();
    List<Cell> getCells();
    Cell getCell(Line line);
    Cell getCell(String name);
    TaxReturn getTaxReturn();
    List<Mistake> validate();

    @SuppressWarnings("unchecked")
    default <T extends Cell> T getCellAsType(Line line, Class<T> clazz) {
        try {
            Cell cell = getCell(line);
            if (cell == null) {
                throw new NoSuchElementException(format("Cell on line '%s' was not found.", line.getLineNumber()));
            }
            return (T) cell;
        } catch (ClassCastException ex) {
            throw new IllegalStateException(format("Cell on line '%s' could not be cast to '%s'.",
                    line.getLineNumber(), clazz.getCanonicalName()));
        }
    }

    @SuppressWarnings("unchecked")
    default <T extends Cell> T getCellAsType(String id, Class<T> clazz) {
        try {
            Cell cell = getCell(id);
            if (cell == null) {
                throw new NoSuchElementException(format("Cell with id '%s' was not found.", id));
            }
            return (T) cell;
        } catch (ClassCastException ex) {
            throw new IllegalStateException(format("Cell with id '%s' could not be cast to '%s'.", id, clazz.getCanonicalName()));
        }
    }
}
