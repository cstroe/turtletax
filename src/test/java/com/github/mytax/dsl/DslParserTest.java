package com.github.mytax.dsl;

import com.github.mytax.api.Form;
import com.github.mytax.impl.TaxReturn;
import com.github.mytax.impl.cells.StringCell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DslParserTest {
    @Test
    public void test01() {
        InputStream is = getClass().getResourceAsStream("/dsl01.taxret");
        DslParser parser = new DslParser();
        TaxReturn taxReturn = parser.parse(is);

        List<Form> forms = taxReturn.getForms();
        assertEquals(1, forms.size());
        assertEquals("f1040", forms.get(0).getId());
    }

    @Test
    public void test02() {
        InputStream is = getClass().getResourceAsStream("/dsl02.taxret");
        DslParser parser = new DslParser();
        TaxReturn taxReturn = parser.parse(is);

        List<Form> forms = taxReturn.getForms();
        assertEquals(1, forms.size());
        assertEquals("f1040", forms.get(0).getId());

        Form form = forms.get(0);
        StringCell cell = form.getCellAsType("yourFirstName", StringCell.class);
        assertEquals("Cosmin", cell.getValue().get());
    }

    @Test
    @DisplayName("ignore white space lines and comments")
    public void test03() {
        InputStream is = getClass().getResourceAsStream("/dsl03.taxret");
        DslParser parser = new DslParser();
        TaxReturn taxReturn = parser.parse(is);

        List<Form> forms = taxReturn.getForms();
        assertEquals(1, forms.size());
        assertEquals("f1040", forms.get(0).getId());

        Form form = forms.get(0);
        StringCell cell = form.getCellAsType("yourFirstName", StringCell.class);
        assertEquals("Cosmin", cell.getValue().get());
    }

    @Test
    public void test() throws FileNotFoundException {
        InputStream is = new FileInputStream("/home/marin/Zoo/07-income-tax-helper/2016.taxreturn");

        DslParser parser = new DslParser();
        TaxReturn taxReturn = parser.parse(is);

        TaxReturnPrinter printer = new TaxReturnPrinter(System.out);
        printer.print(taxReturn);
    }
}