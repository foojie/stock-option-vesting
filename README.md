# stock-option-vesting
Stock Option Vesting

To build the project and run tests:

$ gradle build

To run the program:

$ java -jar build/libs/sovesting-1.0.jar < /path/to/input.def

----------

Outline of model:

SOVInputReader
-reads raw input from stdin

DataParser
-parses records into Transaction and Employee data objects (stored in Database model)

Database
-stores a list of Transactions and a list of Employees for retrieval by other areas of the project

Transaction
-represents a VEST, PERF, SALE with employeeId, date, units, price, multiplier, etc

Employee
-stores identifier, totalGain and totalSales for output

GainCalculator
-performs calculations and logic

----------

Outline of Strategy:

-The list of transactions is sorted by date, then by type (order: VEST, PERF, SALE)
-First pass: Iterate sorted list of transactions and modify unit counts based on PERFs and SALEs
-Second pass: iterate again and count up gains from VESTs with modified units

Notes and Considerations:
-Price is stored in BigDecimal to preserve precision
-BigDecimals are always constructed with a string
-I realize there are inefficiencies when calling functions like getEmployeeVestsToDate() multiple times during inner loops

Holdings Idea:
-If I could change things, there is the idea of a Holdings model
-Holdings would be like an intermediate step between your list of Transactions and calculating gains/sales
-Instead of modifying past units, you could preserve history
-You could iterate the list of sorted transactions once, and create a data structure of holdings based on PERFs and SALEs
-Then you could calculate holdings by date, then calculate gains/sales from holdings
-On a given day you could get the employee's assets
-You could even calculate a weighted average price from holdings
