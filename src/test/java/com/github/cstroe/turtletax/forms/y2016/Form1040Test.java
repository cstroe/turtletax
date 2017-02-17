package com.github.cstroe.turtletax.forms.y2016;

import com.github.cstroe.turtletax.api.CellRef;
import com.github.cstroe.turtletax.impl.TaxReturn;
import com.github.cstroe.turtletax.impl.cells.MoneyCell;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.github.cstroe.turtletax.api.CellRef.ref;
import static com.github.cstroe.turtletax.api.Line.line;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Form 1040 for year 2016")
public class Form1040Test {
    @Test
    @DisplayName("should instantiate without errors")
    public void canInstantiate() {
        Form1040 form = new Form1040("mytax");
        assertEquals("mytax", form.getName());
    }

    @Test
    @DisplayName("computes income from wages when w2 forms are present")
    public void test001() {
        TaxReturn taxReturn = new TaxReturn();
        taxReturn.addForm(new FormW2("w2_1"));
        taxReturn.addForm(new FormW2("w2_2"));
        taxReturn.addForm(new Form1040("my1040"));

        taxReturn.getRef(ref("w2_1/wages", MoneyCell.class)).get().setValue(BigDecimal.valueOf(434.03));
        taxReturn.getRef(ref("w2_2/wages", MoneyCell.class)).get().setValue(BigDecimal.valueOf(100.30));

        Optional<BigDecimal> wages = taxReturn.getRef(ref("my1040/income.w2", MoneyCell.class)).get().getValue();

        assertTrue(wages.isPresent(), "Line 7 should be computed.");
        assertTrue(wages.get().compareTo(BigDecimal.valueOf(534.33)) == 0);
    }
}