package com.github.cstroe.turtletax.cli;

import com.github.cstroe.turtletax.dsl.DslParser;
import com.github.cstroe.turtletax.dsl.TaxReturnPrinter;
import com.github.cstroe.turtletax.impl.TaxReturn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
    }
}