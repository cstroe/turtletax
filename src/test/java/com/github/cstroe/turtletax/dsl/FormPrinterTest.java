package com.github.cstroe.turtletax.dsl;

import com.github.cstroe.turtletax.forms.y2016.SimpleTestForm;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("The form printer")
class FormPrinterTest {
    @Test
    @DisplayName("should print out the cells of a form in sorted order")
    public void test001() {
        SimpleTestForm testForm = new SimpleTestForm("aform");
        val baos = new ByteArrayOutputStream();
        FormPrinter printer = new FormPrinter(new PrintStream(baos));
        printer.print(testForm);
        assertEquals("         string: undefined\nvalues.checkbox: undefined\n   values.money: undefined\n", baos.toString());
    }

    @Test
    @DisplayName("should print out the cells of a form with a prefix")
    public void test002() {
        SimpleTestForm testForm = new SimpleTestForm("aform");
        val baos = new ByteArrayOutputStream();
        FormPrinter printer = new FormPrinter("    ", new PrintStream(baos));
        printer.print(testForm);
        assertEquals("             string: undefined\n    values.checkbox: undefined\n       values.money: undefined\n", baos.toString());
    }
}