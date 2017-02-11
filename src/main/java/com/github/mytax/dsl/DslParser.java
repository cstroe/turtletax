package com.github.mytax.dsl;

import com.github.mytax.api.Cell;
import com.github.mytax.api.CellId;
import com.github.mytax.api.Form;
import com.github.mytax.forms.y2016.Form1040;
import com.github.mytax.forms.y2016.FormW2;
import com.github.mytax.impl.TaxReturn;
import com.github.mytax.impl.cells.BooleanCell;
import com.github.mytax.impl.cells.MoneyCell;
import com.github.mytax.impl.cells.StateAbbreviationCell;
import com.github.mytax.impl.cells.StringCell;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import static com.github.mytax.api.Line.line;
import static java.lang.String.format;

public class DslParser {
    public TaxReturn parse(InputStream is) {
        final TaxReturn taxReturn = new TaxReturn();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        reader.lines().forEach(line -> {
            String[] parsedLine = line.split("\\s+");
            switch (parsedLine[0]) {
                case "Form1040":
                    createForm1040(taxReturn, parsedLine);
                    break;
                case "W2":
                    createW2(taxReturn, parsedLine);
                    break;
                case "enter":
                case "set":
                    enterOnTaxReturn(taxReturn, parsedLine);
                    break;
            }
        });
        return taxReturn;
    }

    public void createForm1040(TaxReturn taxReturn, String[] args) {
        Form1040 form1040 = new Form1040();
        form1040.setId(args[1]);
        taxReturn.addForm(form1040);
    }

    public void createW2(TaxReturn taxReturn, String[] args) {
        FormW2 form = new FormW2();
        form.setId(args[1]);
        taxReturn.addForm(form);
    }

    public void enterOnTaxReturn(TaxReturn taxReturn, String[] args) {
        Form form = taxReturn.getForm(args[1]).get();

        if("line".equalsIgnoreCase(args[2])) {
            Cell cell = form.getCell(line(args[3]));
            enterValueInCell(cell, args[4]);
        } else {
            CellId cellId = form.getCellId(args[2]).orElseThrow(() -> new IllegalArgumentException(format("Cannot find cell with id '%s'", args[2])));
            Cell cell = form.getCell(cellId);
            enterValueInCell(cell, args[3]);
        }
    }

    private void enterValueInCell(Cell cell, String newValue) {
        if(StringCell.class.isAssignableFrom(cell.getClass())) {
            ((StringCell) cell).setValue(newValue);
        } else if(MoneyCell.class.isAssignableFrom(cell.getClass())) {
            String cleanedUpDecimal = newValue.replaceAll(",", "");
            ((MoneyCell) cell).setValue(BigDecimal.valueOf(Double.parseDouble(cleanedUpDecimal)));
        } else if(BooleanCell.class.isAssignableFrom(cell.getClass())) {
            if ("_".equals(newValue) || "N".equalsIgnoreCase(newValue)) {
                ((BooleanCell) cell).setValue(false);
            } else if ("X".equalsIgnoreCase(newValue) || "Y".equalsIgnoreCase(newValue)) {
                ((BooleanCell) cell).setValue(true);
            } else {
                throw new IllegalArgumentException(format("Unknown value '%s' for boolean cell.", newValue));
            }
        } else if(StateAbbreviationCell.class.isAssignableFrom(cell.getClass())) {
            ((StateAbbreviationCell) cell).setValue(newValue);
        } else {
            throw new UnsupportedOperationException("Don't know how to fill in " + cell.getClass().getCanonicalName());
        }
    }
}
