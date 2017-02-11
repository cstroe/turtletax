# TurtleTax

A personal tax helper for US Federal Income Tax.

This project is nowhere near ready to be used and development is ongoing.

## Usage

Currently, the way to interact with the program is to create a text file describing your tax return:

    W2 myjobW2
    enter myjobW2 wages        20,000.00
    enter myjobW2 taxWithheld   1,000.00
    
    Form1040 my1040
    enter my1040 yourFirstName John
    enter my1040 yourLastName  Smith
    enter my1040 income.taxableInterest  123.00

Then run it through the `DslParser` and have your `TaxReturn` parsed.  You can call `validate()` on the tax return to check it.  See `DslParserTest`.

## Other Tax-related Projects
* [Open Tax Solver](https://sourceforge.net/projects/opentaxsolver)
* [phptax](https://sourceforge.net/projects/phptax)
* [py1040](https://github.com/b-k/py1040)
* [1040js](https://github.com/b-k/1040.js)
