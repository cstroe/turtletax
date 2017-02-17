package com.github.cstroe.turtletax.dsl;

import com.github.cstroe.turtletax.api.Cell;
import com.github.cstroe.turtletax.api.Form;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

public class FormPrinter {
    private final PrintStream ps;
    private String prefix;

    public FormPrinter(PrintStream ps) {
        this.ps = ps;
    }

    public FormPrinter(String prefix, PrintStream ps) {
        this.ps = ps;
        this.prefix = prefix;
    }

    public void print(Form form) {
        List<Cell> cells = form.getCells();
        if(cells.isEmpty()) {
            return;
        }

        Collections.sort(cells);

        int maxLength = cells.stream().mapToInt(cell -> cell.getId().toString().length()).max().getAsInt();

        for(Cell cell : cells) {
            if(prefix != null) {
                ps.print(prefix);
            }

            int padding = maxLength - cell.getId().toString().length();

            for(int i = 0; i < padding; i++) {
                ps.print(" ");
            }

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
