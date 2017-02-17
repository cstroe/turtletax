package com.github.cstroe.turtletax.api;

import lombok.Data;

/**
 * A reference to a cell in a TaxReturn.
 */
@Data
public class CellRef<T extends Cell> {
    private String ref;
    private Class<T> aClass;

    public static <T extends Cell> CellRef<T> ref(String refSpec, Class<T> aClass) {
        CellRef cellRef = new CellRef();
        cellRef.ref = refSpec;
        cellRef.aClass = aClass;
        return cellRef;
    }
}
