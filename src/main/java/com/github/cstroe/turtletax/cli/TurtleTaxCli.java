package com.github.cstroe.turtletax.cli;

import com.github.cstroe.turtletax.api.Mistake;
import com.github.cstroe.turtletax.dsl.DslParser;
import com.github.cstroe.turtletax.dsl.TaxReturnPrinter;
import com.github.cstroe.turtletax.impl.TaxReturn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static java.lang.String.format;

public class TurtleTaxCli {
    public static void main(final String[] args) throws FileNotFoundException {
        if(args.length == 0) {
            System.out.println("Not enough arguments.  Usage:\n run-java <tax return file>");
            return;
        } else if(args.length > 1) {
            System.out.println("Too many arguments.  Usage:\n run-java <tax return file>");
            return;
        }

        InputStream fs = new FileInputStream(new File(args[0]));

        TaxReturn taxReturn = new DslParser().parse(fs);

        TaxReturnPrinter printer = new TaxReturnPrinter(System.out);
        printer.print(taxReturn);

        List<Mistake> mistakes = taxReturn.validate();

        if(mistakes.isEmpty()) {
            System.out.println("Your tax return has no mistakes.");
        } else {
            System.out.println(format("Your tax return has %d mistakes.", mistakes.size()));
        }

        int i = 0;
        for(Mistake mistake : mistakes) {
            i++;
            System.out.println(format("%4d. Cell '%s', %s", i, mistake.getSource(), mistake.getExplanation()));
        }
    }
}