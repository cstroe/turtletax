package com.github.cstroe.turtletax.api;

import lombok.Data;

@Data
public class CellId {
    String id;

    CellId(String id) {
        this.id = id;
    }

    public Cell on(Form form) {
        return form.getCell(this);
    }

    @Override
    public String toString() {
        return id;
    }
}
