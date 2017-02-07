package com.github.mytax.dsl;

import com.github.mytax.api.Form;
import com.github.mytax.impl.TaxReturn;

import java.io.PrintStream;

public class TaxReturnPrinter {
    private final PrintStream printStream;

    public TaxReturnPrinter(PrintStream stream) {
        this.printStream = stream;
    }

    public void print(TaxReturn taxReturn) {
        FormPrinter fp = new FormPrinter(printStream);
        for(Form f : taxReturn.getForms()) {
            printStream.print("Form: ");
            printStream.print(f.getId());
            printStream.print("\n");
            fp.print(f);
        }
    }
}
