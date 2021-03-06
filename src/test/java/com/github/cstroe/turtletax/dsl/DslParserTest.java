package com.github.cstroe.turtletax.dsl;

import com.github.cstroe.turtletax.api.Form;
import com.github.cstroe.turtletax.api.Mistake;
import com.github.cstroe.turtletax.impl.TaxReturn;
import com.github.cstroe.turtletax.impl.cells.BooleanCell;
import com.github.cstroe.turtletax.impl.cells.MoneyCell;
import com.github.cstroe.turtletax.impl.cells.StringCell;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static com.github.cstroe.turtletax.api.Line.line;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The DSL parser")
class DslParserTest {
    @Test
    @DisplayName("should create Form 1040")
    public void test01() {
        List<Form> forms = parse("Form1040 f1040").getForms();
        assertEquals(1, forms.size());
        assertEquals("f1040", forms.get(0).getName());
    }

    @Test
    @DisplayName("should fill in a string value")
    public void test02() {
        Form form = parse("Form1040 f1040\nset f1040 yourFirstName Bob").getForms().get(0);
        StringCell cell = form.getCellAsType("yourFirstName", StringCell.class);
        assertEquals("Bob", cell.getValue().get());
    }

    @Test
    @DisplayName("should ignore white space lines and comments")
    public void test03() {
        Form form = parse("\n# This is a comment\nForm1040 f1040\nset f1040 yourFirstName Bob")
                .getForms().get(0);
        StringCell cell = form.getCellAsType("yourFirstName", StringCell.class);
        assertEquals("Bob", cell.getValue().get());
    }

    @Test
    @DisplayName("should parse money value")
    public void test04() {
        TaxReturn taxReturn = parse("Form1040 f1040\nenter f1040 agi.educatorExpenses 3,456.01");
        Form form1040 = taxReturn.getForm("f1040").get();
        MoneyCell cell = form1040.getCellAsType("agi.educatorExpenses", MoneyCell.class);

        assertTrue(cell.getValue().isPresent(), "Value should be filled in.");
        assertTrue(cell.getValue().get().compareTo(BigDecimal.valueOf(3_456.01)) == 0, "Value should be 3,456.01.");
    }

    @Test
    @DisplayName("should create form w2 and fill in values for it")
    public void test05() {
        TaxReturn taxReturn = parseFromFile("/dsl05.taxret");
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
        TaxReturn taxReturn = parseFromFile("/dsl06.taxret");
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

    @Test
    @DisplayName("should fill in boolean values")
    public void test07() {
        TaxReturn taxReturn = parseFromFile("/dsl07.taxret");
        Form f1040 = taxReturn.getForm("f1040").get();

        BooleanCell c1 = f1040.getCellAsType(line(1), BooleanCell.class);
        assertTrue(c1.getValue().isPresent(), "Cell 'filingStatus.single' should be filled in.");
        assertTrue(c1.getValue().get());

        BooleanCell c2 = f1040.getCellAsType("taxAndCredits.youBefore1952", BooleanCell.class);
        assertTrue(c2.getValue().isPresent(), "Cell 'taxAndCredits.youBefore1952' should be filled in.");
        assertTrue(c2.getValue().get());

        BooleanCell c3 = f1040.getCellAsType("taxAndCredits.form8814", BooleanCell.class);
        assertTrue(c3.getValue().isPresent(), "Cell 'taxAndCredits.form8814' should be filled in.");
        assertFalse(c3.getValue().get());

        BooleanCell c4 = f1040.getCellAsType(line("44b"), BooleanCell.class);
        assertTrue(c4.getValue().isPresent(), "Cell '44b' should be filled in.");
        assertFalse(c4.getValue().get());
    }

    @Test
    @DisplayName("should fail on unknown boolean values")
    public void test08() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DslParser().parse(new ByteArrayInputStream("Form1040 f1040\nenter f1040 line 1 Z".getBytes()));
        });
    }

    @Test
    @DisplayName("should parse state abbreviation cells")
    public void test09() {
        val taxReturnString = "W2 w2\nenter w2 line 15 IL";
        val baos = new ByteArrayInputStream(taxReturnString.getBytes());
        val taxReturn = new DslParser().parse(baos);

        val form = taxReturn.getForm("w2").get();
        val cell = form.getCell(line(15));

        assertTrue(cell.getValue().isPresent());
        assertEquals("IL", cell.getValue().get());
    }

    @Test
    @DisplayName("should set the tax return when creating Form1040")
    public void test10() {
        val taxReturn = parse("Form1040 f1040");
        val form = taxReturn.getForm("f1040")
                .orElseThrow(() -> new AssertionError("Could not find form 'f1040'"));
        assertNotNull(form.getTaxReturn());
    }

    @Test
    @DisplayName("should set the tax return when creating a W2 form")
    public void test11() {
        val taxReturn = parse("W2 formW2");
        val form = taxReturn.getForm("formW2")
                .orElseThrow(() -> new AssertionError("Could not find form 'formW2'"));
        assertNotNull(form.getTaxReturn());
    }


    private TaxReturn parse(String taxReturn) {
        return new DslParser()
                .parse(new ByteArrayInputStream(taxReturn.getBytes()));
    }

    private TaxReturn parseFromFile(String file) {
        InputStream is = getClass().getResourceAsStream(file);
        DslParser parser = new DslParser();
        return parser.parse(is);
    }
}