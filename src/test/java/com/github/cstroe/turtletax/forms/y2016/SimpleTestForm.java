package com.github.cstroe.turtletax.forms.y2016;

import com.github.cstroe.turtletax.api.BaseForm;

import static com.github.cstroe.turtletax.api.Line.line;

public class SimpleTestForm extends BaseForm {
    public SimpleTestForm(String name) {
        setName(name);
        StringCell("string", "Some string cell");
        MoneyCell("values.money", "Some money", line(1));
        BooleanCell("values.checkbox", "Some checkbox", line(2));
    }
}
