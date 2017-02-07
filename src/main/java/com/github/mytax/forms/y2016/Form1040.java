package com.github.mytax.forms.y2016;

import com.github.mytax.api.BaseForm;
import lombok.Getter;

import static com.github.mytax.api.Line.line;
import static com.github.mytax.impl.FormActions.subtract;

public class Form1040 extends BaseForm implements Form1040CellNames {
    @Getter final String id = "1040";

    public Form1040() {
        super();

        StringCell(YOUR_FIRST_NAME, "Your first name and initial");
        StringCell(YOUR_LAST_NAME, "Last name");
        StringCell(YOUR_SSN, "Your social security number");
        requiredValue(YOUR_FIRST_NAME);
        requiredValue(YOUR_LAST_NAME);
        requiredValue(YOUR_SSN);

        StringCell(SPOUSE_FIRST_NAME, "If a joint return, spouse's first name and initial");
        StringCell(SPOUSE_LAST_NAME, "Last name");
        StringCell(SPOUSE_SSN, "Spouse's social security number");

        StringCell("homeAddress", "Home address (number and street). If you have a P.O. box, see instructions.");
        StringCell("homeAptNo", "Apt. no.");

        StringCell("homeCityStateZip", "City, town or post office, state, and ZIP code. If you have a foreign address, also complete spaces below (see instructions).");
        StringCell("homeCountry", "Foreign country name");
        StringCell("homeProvinceStateCounty", "Foreign province/state/county");
        StringCell("homePostalCode", "Foreign postal code");

        BooleanCell("yourPecDonation", "Presidential Election Campaign: Check if you want $3 to go to this fund. Checking a box will not change your tax or refund. You:");
        BooleanCell("spousePecDonation", "Presidential Election Campaign: Check if you want $3 to go to this fund. Checking a box will not change your tax or refund. Spouse:");

        // filing status
        BooleanCell(FILING_STATUS_SINGLE, "Single", line(1));
        BooleanCell(FILING_STATUS_JOINTLY, "Married filing jointly (even if only one had income)", line(2));
        BooleanCell(FILING_STATUS_SEPARATELY, "Married filing separately.", line(3));
        StringCell(FILING_STATUS_SPOUSE_NAME, "Enter spouse's SSN above and full name here");
        BooleanCell(FILING_STATUS_HEAD_OF_HOUSEHOLD, "Head of household (with qualifying person). (See instructions.)", line(4));
        StringCell("filingStatus.nonDependenChild", "If the qualifying person is a child but not your dependent, enter this child’s name here.");
        BooleanCell(FILING_STATUS_WIDOWER, "Qualifying widow(er) with dependent child", line(5));
        CheckOneAndOnlyOne(FILING_STATUS_SINGLE, FILING_STATUS_JOINTLY, FILING_STATUS_SEPARATELY,
                FILING_STATUS_HEAD_OF_HOUSEHOLD, FILING_STATUS_WIDOWER);
        RequiredCellValueIfBooleanIsTrue(FILING_STATUS_JOINTLY, SPOUSE_FIRST_NAME);
        RequiredCellValueIfBooleanIsTrue(FILING_STATUS_JOINTLY, SPOUSE_LAST_NAME);
        RequiredCellValueIfBooleanIsTrue(FILING_STATUS_JOINTLY, SPOUSE_SSN);
        RequiredCellValueIfBooleanIsTrue(FILING_STATUS_SEPARATELY, FILING_STATUS_SPOUSE_NAME);
        RequiredCellValueIfBooleanIsTrue(FILING_STATUS_SEPARATELY, SPOUSE_SSN);

        // exemptions
        BooleanCell("exemptions.yourself", "Yourself. If someone can claim you as a dependent, do not check box 6a.", line("6a"));
        BooleanCell("exemptions.spouse", "Spouse.", line("6b"));

        // income
        MoneyCell("income.w2", "Wages, salaries, tips, etc. Attach Form(s) W-2", line(7));
        MoneyCell("income.taxableInterest", "Taxable interest. Attach Schedule B if required", line("8a"));
        MoneyCell("income.taxExemptInterest", "Tax-exempt interest. Do not include on line 8a", line("8b"));
        MoneyCell("income.ordinaryDividens", "Ordinary dividends. Attach Schedule B if required", line("9a"));
        MoneyCell("income.qualifiedDividens", "Qualified dividends", line("9b"));
        MoneyCell("income.stateRefunds", "Taxable refunds, credits, or offsets of state and local income taxes", line(10));
        MoneyCell("income.alimony", "Alimony received", line(11));
        MoneyCell("income.business", "Business income or (loss). Attach Schedule C or C-EZ", line(12));
        MoneyCell("income.capitalGain", "Capital gain or (loss). Attach Schedule D if required.", line(13));
        BooleanCell("income.capitalGainNoScheduleD", "If not required, check here");
        MoneyCell("income.iraTotal", "IRA distributions", line("15a"));
        MoneyCell("income.iraTaxable", "Taxable amount", line("15b"));
        MoneyCell("income.pensionsAndAnnuitiesTotal", "Pensions and annuities", line("16a"));
        MoneyCell("income.pensionsAndAnnuitiesTaxable", "Taxable amount", line("16b"));
        MoneyCell("income.scheduleE", "Rental real estate, royalties, partnerships, S corporations, trusts, etc. Attach Schedule E", line(17));
        MoneyCell("income.scheduleF", "Farm income or (loss). Attach Schedule F", line(18));
        MoneyCell("income.unemployment", "Unemployment compensation", line(19));
        MoneyCell("income.socialSecurityTotal", "Social security benefits", line("20a"));
        MoneyCell("income.socialSecurityTaxable", "Taxable amount", line("20b"));
        StringCell("income.otherIncomeType", "List type");
        MoneyCell("income.otherIncomeAmount", "Other income", line(21));
        SumCell("income.total", "Combine the amounts in the far right column for lines 7 through 21. This is your total income", line(22),
                line(7), line("8a"), line("9a"), line("10"), line("11"), line("12"), line("13"), line("14"),
                line("15b"), line("16b"), line("17"), line("18"), line("19"), line("20b"), line("21"), line("22"));

        // adjusted gross income
        MoneyCell("agi.educatorExpenses", "Educator expenses", line(23));
        MoneyCell("agi.govrelatedExpenses", "Certain business expenses of reservists, performing artists, and fee-basis government officials. Attach Form 2106 or 2106-EZ", line(24));
        MoneyCell("agi.hsaDeduction", "Health savings account deduction. Attach Form 8889", line(25));
        MoneyCell("agi.movingExpenses", "Moving expenses. Attach Form 3903", line(26));
        MoneyCell("agi.selfEmploymentTaxDeduction", "Deductible part of self-employment tax. Attach Schedule SE", line(27));
        MoneyCell("agi.selfEmployedPlan", "Self-employed SEP, SIMPLE, and qualified plans", line(28));
        MoneyCell("agi.selfEmployedHealthInsurance", "Self-employed health insurance deduction", line(29));
        MoneyCell("agi.earlyWithdrawlPenalty", "Penalty on early withdrawal of savings", line(30));
        MoneyCell("agi.alimonyPaid", "Alimony paid", line("31a"));
        SsnCell("agi.alimonyRecepientSSN", "Recipient's SSN", line("31b"));
        MoneyCell("agi.iraDeduction", "IRA deduction", line(32));
        MoneyCell("agi.studentLoanInterest", "Student loan interest deduction", line(33));
        MoneyCell("agi.tuitionAndFees", "Tuition and fees. Attach Form 8917", line(34));
        MoneyCell("agi.domesticProduction", "Domestic production activities deduction. Attach Form 8903", line(35));
        SumCell("agi.lines23to35", "Add lines 23 through 35", line(36),
                line(23), line(24), line(25), line(26), line(27), line("28"), line("29"),
                line("30"), line("31a"), line("32"), line("33"), line("34"), line("35"));
        SubtractCell("agi.value", "Subtract line 36 from line 22. This is your adjusted gross income", line(37),
                subtract("36").from("22"));

        MoneyCell("agi.valueCopy", "Amount from line 37 (adjusted gross income)", line(38), this, line(37));

        // tax and credits
        BooleanCell("taxAndCredits.youBefore1952", "You were born before January 2, 1952,");
        BooleanCell("taxAndCredits.youBlind", "Blind.");
        BooleanCell("taxAndCredits.spouseBefore1952", "Spouse was born before January 2, 1952,");
        BooleanCell("taxAndCredits.spouseBlind", "Blind.");
        CountBoxesCell("taxAndCredits.numChecked39a", "Total boxes checked", line("39a"),
                this, "taxAndCredits.youBefore1952", "taxAndCredits.youBlind", "taxAndCredits.spouseBefore1952", "taxAndCredits.spouseBlind");


        BooleanCell("taxAndCredits.spouseItemizesOrDualStatus",
                "If your spouse itemizes on a separate return or you were a dual-status alien, check here", line("39b"));
        MoneyCell("taxAndCredits.itemizedDeductionOrStandard",
                "Itemized deductions (from Schedule A) or your standard deduction (see left margin)", line(40));
        SubtractCell("taxAndCredits.subtract40from38", "Subtract line 40 from line 38", line(41),
                subtract("40").from("38"));


    }
}
