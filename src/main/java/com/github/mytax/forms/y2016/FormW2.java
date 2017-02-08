package com.github.mytax.forms.y2016;

import com.github.mytax.api.BaseForm;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Stream;

import static com.github.mytax.api.Line.line;

public class FormW2 extends BaseForm {
    @Getter @Setter private String id;

    public FormW2() {
        StringCell("c", "Employer’s name, address, and ZIP code", line("c"));
        MoneyCell("1", "Wages, tips, other comp.", line(1));
        MoneyCell("2", "Federal income tax withheld", line(2));
        MoneyCell("3", "Social security wages", line(3));
        MoneyCell("4", "Social security tax withheld", line(4));
        MoneyCell("5", "Medicare wages and tips", line(5));
        MoneyCell("6", "Medicare tax withheld", line(6));
        MoneyCell("7", "Social security tips", line(7));
        MoneyCell("8", "Allocated tips", line(8));
        MoneyCell("10", "Dependent care benefits", line(10));
        MoneyCell("11", "Nonqualified plans", line(11));
        Stream.of("a", "b", "c", "d").forEach(letter -> {
            String cellId = "12" + letter;
            StringCell("12" + letter + "_code", "Code for 12" + letter, line("12" + letter + "_code"));
            MoneyCell(cellId, cellId, line(cellId));
        });

        StateAbbreviationCell("15", "State", line(15));
        StringCell("employerStateId", "Employer’s state ID no");
        MoneyCell("16", "State wages, tips, etc.", line(16));
        MoneyCell("17", "State income tax", line(17));
        MoneyCell("18", "Local wages, tips, etc.", line(18));
        MoneyCell("19", "Local income tax", line(19));
        MoneyCell("20", "Locality name", line(20));
    }
}
