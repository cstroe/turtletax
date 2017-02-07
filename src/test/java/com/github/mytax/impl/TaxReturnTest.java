package com.github.mytax.impl;


import com.github.mytax.api.Form;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("A TaxReturn")
public class TaxReturnTest {
    @Test
    @DisplayName("should return a form")
    public void gettingAForm() {
        Form myForm = mock(Form.class);
        when(myForm.getId()).thenReturn("formId");
        TaxReturn taxReturn = new TaxReturn();
        taxReturn.addForm(myForm);
        Optional<Form> aForm = taxReturn.getForm("formId");
        assertTrue(aForm.isPresent());
    }
}