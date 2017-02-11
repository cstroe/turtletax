package com.github.cstroe.turtletax.dsl;

import com.github.cstroe.turtletax.api.Form;
import com.github.cstroe.turtletax.impl.TaxReturn;

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
