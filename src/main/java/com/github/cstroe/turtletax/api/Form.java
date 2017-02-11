package com.github.cstroe.turtletax.api;

import com.github.cstroe.turtletax.impl.TaxReturn;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

public interface Form {
    String getId();
    List<Cell> getCells();
    Optional<CellId> getCellId(String id);
    Cell getCell(Line line);
    Cell getCell(CellId id);
    Cell getCellById(String id);
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
            throw new IllegalStateException(format("Cell on line '%s' could not be cast ifFilled '%s'.",
                    line.getLineNumber(), clazz.getCanonicalName()));
        }
    }

    // TODO: Remove class parameter.
    @SuppressWarnings("unchecked")
    default <T extends Cell> T getCellAsType(CellId id, Class<T> clazz) {
        try {
            Cell cell = getCell(id);
            if (cell == null) {
                throw new NoSuchElementException(format("Cell with id '%s' was not found.", id));
            }
            return (T) cell;
        } catch (ClassCastException ex) {
            throw new IllegalStateException(format("Cell with id '%s' could not be cast ifFilled '%s'.", id, clazz.getCanonicalName()));
        }
    }

    // TODO: Remove class parameter.
    @SuppressWarnings("unchecked")
    default <T extends Cell> T getCellAsType(String id, Class<T> clazz) {
        try {
            CellId cid = getCellId(id).orElseThrow(() -> new IllegalArgumentException());
            Cell cell = getCell(cid);
            if (cell == null) {
                throw new NoSuchElementException(format("Cell with id '%s' was not found.", id));
            }
            return (T) cell;
        } catch (ClassCastException ex) {
            throw new IllegalStateException(format("Cell with id '%s' could not be cast ifFilled '%s'.", id, clazz.getCanonicalName()));
        }
    }

    default CellId[] getCellIdsFromLines(String... lines) {
        return Arrays.stream(lines)
                .map(Line::line)
                .map(this::getCell)
                .map(Cell::getId)
                .collect(Collectors.toList()).toArray(new CellId[0]);
    }
}
