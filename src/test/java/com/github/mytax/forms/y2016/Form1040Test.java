package com.github.mytax.forms.y2016;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Form 1040 for year 2016")
public class Form1040Test {
    @Test
    @DisplayName("should instantiate without errors")
    public void canInstantiate() {
        Form1040 form = new Form1040();
    }
}