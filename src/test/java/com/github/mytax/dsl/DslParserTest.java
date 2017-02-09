package com.github.mytax.dsl;

import com.github.mytax.api.Form;
import com.github.mytax.api.Mistake;
import com.github.mytax.impl.TaxReturn;
import com.github.mytax.impl.cells.MoneyCell;
import com.github.mytax.impl.cells.StringCell;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static com.github.mytax.api.Line.line;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("The DSL parser")
class DslParserTest {
    @Test
    @DisplayName("should create Form 1040")
    public void test01() {
        List<Form> forms = parse("/dsl01.taxret").getForms();
        assertEquals(1, forms.size());
        assertEquals("f1040", forms.get(0).getId());
    }

    @Test
    @DisplayName("should fill in a string value")
    public void test02() {
        Form form = parse("/dsl02.taxret").getForms().get(0);
        StringCell cell = form.getCellAsType("yourFirstName", StringCell.class);
        assertEquals("Cosmin", cell.getValue().get());
    }

    @Test
    @DisplayName("should ignore white space lines and comments")
    public void test03() {
        Form form = parse("/dsl03.taxret").getForms().get(0);
        StringCell cell = form.getCellAsType("yourFirstName", StringCell.class);
        assertEquals("Cosmin", cell.getValue().get());
    }

    @Test
    @DisplayName("should parse money value")
    public void test04() {
        TaxReturn taxReturn = parse("/dsl04.taxret");
        Form form1040 = taxReturn.getForm("f1040").get();
        MoneyCell cell = form1040.getCellAsType("agi.educatorExpenses", MoneyCell.class);

        assertTrue(cell.getValue().isPresent(), "Value should be filled in.");
        assertTrue(cell.getValue().get().compareTo(BigDecimal.valueOf(3_456.01)) == 0, "Value should be 3,456.01.");
    }

    @Test
    @DisplayName("should create form w2 and fill in values for it")
    public void test05() {
        TaxReturn taxReturn = parse("/dsl05.taxret");
        Form w2 = taxReturn.getForm("mycompany").get();

        MoneyCell wagesCell = w2.getCellAsType("wages", MoneyCell.class);
        assertTrue(wagesCell.getValue().isPresent(), "Wages should be filled in.");
        assertTrue(wagesCell.getValue().get().compareTo(BigDecimal.valueOf(1000)) == 0, "Wages should be 1,000.");

        MoneyCell nqpCell = w2.getCellAsType("nonQualifiedPlans", MoneyCell.class);
        assertTrue(nqpCell.getValue().isPresent(), "Nonqualified Plans should be filled in.");
        assertTrue(nqpCell.getValue().get().compareTo(BigDecimal.valueOf(0.01)) == 0, "Nonqualified Plans should be 0.01.");

    }

    @Test
    @DisplayName("should fill in values given the line number")
    public void test06() {
        TaxReturn taxReturn = parse("/dsl06.taxret");
        Form w2 = taxReturn.getForm("mycompany").get();

        MoneyCell wagesCell = w2.getCellAsType(line(1), MoneyCell.class);
        assertTrue(wagesCell.getValue().isPresent(), "Wages should be filled in.");
        assertTrue(wagesCell.getValue().get().compareTo(BigDecimal.valueOf(1000)) == 0, "Wages should be 1,000.");

        MoneyCell nqpCell = w2.getCellAsType(line(11), MoneyCell.class);
        assertTrue(nqpCell.getValue().isPresent(), "Nonqualified Plans should be filled in.");
        assertTrue(nqpCell.getValue().get().compareTo(BigDecimal.valueOf(0.01)) == 0, "Nonqualified Plans should be 0.01.");

        StringCell cell12a = w2.getCellAsType(line("12a_code"), StringCell.class);
        assertTrue(cell12a.getValue().isPresent(), "Cell 12a_code should be filled in.");
        assertTrue(cell12a.getValue().get().equals("W"));
    }

    private TaxReturn parse(String file) {
        InputStream is = getClass().getResourceAsStream(file);
        DslParser parser = new DslParser();
        return parser.parse(is);
    }

    @Test
    @Disabled
    public void test() throws FileNotFoundException {
        InputStream is = new FileInputStream("/home/marin/Zoo/07-income-tax-helper/2016.taxreturn");

        DslParser parser = new DslParser();
        TaxReturn taxReturn = parser.parse(is);

        TaxReturnPrinter printer = new TaxReturnPrinter(System.out);
        printer.print(taxReturn);

        List<Mistake> mistakes = taxReturn.validate();
        assertTrue(mistakes.size() > 0);
    }
}