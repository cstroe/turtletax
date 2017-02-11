package com.github.cstroe.turtletax.dsl;

import com.github.cstroe.turtletax.api.Cell;
import com.github.cstroe.turtletax.api.Form;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

public class FormPrinter {
    private final PrintStream ps;

    public FormPrinter(PrintStream ps) {
        this.ps = ps;
    }

    public void print(Form form) {
        List<Cell> cells = form.getCells();
        Collections.sort(cells);
        for(Cell cell : cells) {
            ps.print(cell.getId());
            ps.print(": ");
            if(cell.getValue().isPresent()) {
                ps.print(cell.getValue().get());
            } else {
                ps.print("undefined");
            }
            ps.print("\n");
        }
    }
}
