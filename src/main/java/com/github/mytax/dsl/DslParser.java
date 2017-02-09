package com.github.mytax.dsl;

import com.github.mytax.api.Cell;
import com.github.mytax.api.Form;
import com.github.mytax.forms.y2016.Form1040;
import com.github.mytax.forms.y2016.FormW2;
import com.github.mytax.impl.TaxReturn;
import com.github.mytax.impl.cells.MoneyCell;
import com.github.mytax.impl.cells.StringCell;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

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
                    setOnTaxReturn(taxReturn, parsedLine);
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

    public void setOnTaxReturn(TaxReturn taxReturn, String[] args) {
        Form form = taxReturn.getForm(args[1]).get();

        Cell cell = form.getCell(args[2]);

        if(StringCell.class.isAssignableFrom(cell.getClass())) {
            ((StringCell) cell).setValue(args[3]);
        } else if(MoneyCell.class.isAssignableFrom(cell.getClass())) {
            String cleanedUpDecimal = args[3].replaceAll(",", "");
            ((MoneyCell) cell).setValue(BigDecimal.valueOf(Double.parseDouble(cleanedUpDecimal)));
        } else {
            throw new UnsupportedOperationException("Don't know how to fill in " + cell.getClass().getCanonicalName());
        }
    }
}
