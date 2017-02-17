![GNU Affero GPL v3](https://img.shields.io/badge/license-Affero%20GPL%20v3-blue.svg)

# TurtleTax

A personal tax helper for US Federal Income Tax.

This project is nowhere near ready to be used and development is ongoing.

## Creating a tax return

Currently, the way to interact with the program is to create a text file describing your tax return:

    W2 myjobW2
    enter myjobW2 wages        20,000.00
    enter myjobW2 taxWithheld   1,000.00
    
    Form1040 my1040
    enter my1040 yourFirstName John
    enter my1040 yourLastName  Smith
    enter my1040 income.taxableInterest  123.00

## Parsing your tax return

Using maven:

    ./mvnw clean compile
    ./bin/run-java <path to tax return file>

The program will parse your tax return and output it.

## Other Tax-related Projects
* [Open Tax Solver](https://sourceforge.net/projects/opentaxsolver)
* [phptax](https://sourceforge.net/projects/phptax)
* [py1040](https://github.com/b-k/py1040)
* [1040js](https://github.com/b-k/1040.js)

## Disclaimer

Section 15 of the license:

> THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY
> APPLICABLE LAW. EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT
> HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM "AS IS" WITHOUT
> WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT
> LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
> A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND
> PERFORMANCE OF THE PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE
> DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY SERVICING, REPAIR OR
> CORRECTION.

We leave it up to anyone using this software to check the applicability or validity of any output of this program.  There is no warranty of any kind, for any purpose.

