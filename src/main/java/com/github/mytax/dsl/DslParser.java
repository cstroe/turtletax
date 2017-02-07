package com.github.mytax.dsl;

import com.github.mytax.api.Cell;
import com.github.mytax.api.Form;
import com.github.mytax.forms.y2016.Form1040;
import com.github.mytax.impl.TaxReturn;
import com.github.mytax.impl.cells.StringCell;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DslParser {
    public TaxReturn parse(InputStream is) {
        final TaxReturn taxReturn = new TaxReturn();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        reader.lines().forEach(line -> {
            String[] parsedLine = line.split(" ");
            switch (parsedLine[0]) {
                case "Form1040":
                    createForm1040(taxReturn, parsedLine);
                    break;
                case "set":
                    set(taxReturn, parsedLine);
            }
        });
        return taxReturn;
    }

    public void createForm1040(TaxReturn taxReturn, String[] args) {
        Form1040 form1040 = new Form1040();
        form1040.setId(args[1]);
        taxReturn.addForm(form1040);
    }

    public void set(TaxReturn taxReturn, String[] args) {
        String[] field = args[1].split("\\.");
        Form form = taxReturn.getForm(field[0]).get();

        Cell cell = form.getCell(field[1]);

        if(StringCell.class.isAssignableFrom(cell.getClass())) {
            ((StringCell) cell).setValue(args[2]);
        }
    }
}
