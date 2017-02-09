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
        MoneyCell("wages", "Wages, tips, other comp.", line(1));
        MoneyCell("taxWithheld", "Federal income tax withheld", line(2));
        MoneyCell("socialSecurityWages", "Social security wages", line(3));
        MoneyCell("socialSecurityTaxWithheld", "Social security tax withheld", line(4));
        MoneyCell("medicareWages", "Medicare wages and tips", line(5));
        MoneyCell("medicateTaxWithheld", "Medicare tax withheld", line(6));
        MoneyCell("socialSecurityTips", "Social security tips", line(7));
        MoneyCell("allocatedTips", "Allocated tips", line(8));
        MoneyCell("dependentCareBenefits", "Dependent care benefits", line(10));
        MoneyCell("nonQualifiedPlans", "Nonqualified plans", line(11));
        Stream.of("a", "b", "c", "d").forEach(letter -> {
            String cellId = "12" + letter;
            StringCell("12" + letter + "_code", "Code for 12" + letter, line("12" + letter + "_code"));
            MoneyCell(cellId, cellId, line(cellId));
        });

        StateAbbreviationCell("15", "State", line(15));
        StringCell("employerStateId", "Employer’s state ID no");
        MoneyCell("stateWages", "State wages, tips, etc.", line(16));
        MoneyCell("stateIncomeTax", "State income tax", line(17));
        MoneyCell("localWages", "Local wages, tips, etc.", line(18));
        MoneyCell("localIncomeTax", "Local income tax", line(19));
        MoneyCell("localityName", "Locality name", line(20));
    }
}
