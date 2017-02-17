package com.github.cstroe.turtletax.forms.y2016;

import com.github.cstroe.turtletax.api.BaseForm;
import com.github.cstroe.turtletax.api.Line;
import com.github.cstroe.turtletax.impl.TaxReturn;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Stream;

import static com.github.cstroe.turtletax.api.Line.line;

public class FormW2 extends BaseForm {
    public FormW2(String id) {
        this();
        setName(id);
    }

    public FormW2() {
        StringCell("c", "Employer’s name, address, and ZIP code", Line.line("c"));
        MoneyCell("wages", "Wages, tips, other comp.", Line.line(1));
        MoneyCell("taxWithheld", "Federal income tax withheld", Line.line(2));
        MoneyCell("socialSecurityWages", "Social security wages", Line.line(3));
        MoneyCell("socialSecurityTaxWithheld", "Social security tax withheld", Line.line(4));
        MoneyCell("medicareWages", "Medicare wages and tips", Line.line(5));
        MoneyCell("medicateTaxWithheld", "Medicare tax withheld", Line.line(6));
        MoneyCell("socialSecurityTips", "Social security tips", Line.line(7));
        MoneyCell("allocatedTips", "Allocated tips", Line.line(8));
        MoneyCell("dependentCareBenefits", "Dependent care benefits", Line.line(10));
        MoneyCell("nonQualifiedPlans", "Nonqualified plans", Line.line(11));
        Stream.of("a", "b", "c", "d").forEach(letter -> {
            String cellId = "12" + letter;
            StringCell("12" + letter + "_code", "Code for 12" + letter, Line.line("12" + letter + "_code"));
            MoneyCell(cellId, cellId, Line.line(cellId));
        });

        BooleanCell("statEmp", "Stat. Emp.");
        BooleanCell("retPlan", "Ret. plan");
        BooleanCell("tpSickPay", "3rd party sick pay");

        StateAbbreviationCell("15", "State", Line.line(15));
        StringCell("employerStateId", "Employer’s state ID no");
        MoneyCell("stateWages", "State wages, tips, etc.", Line.line(16));
        MoneyCell("stateIncomeTax", "State income tax", Line.line(17));
        MoneyCell("localWages", "Local wages, tips, etc.", Line.line(18));
        MoneyCell("localIncomeTax", "Local income tax", Line.line(19));
        MoneyCell("localityName", "Locality name", Line.line(20));
    }
}
