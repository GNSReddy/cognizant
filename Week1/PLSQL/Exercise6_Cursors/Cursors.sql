SET SERVEROUTPUT ON;

-- Scenario 1: Generate Monthly Statements

DECLARE

    CURSOR GenerateMonthlyStatements IS

        SELECT TransactionID,
               AccountID,
               TransactionDate,
               Amount,
               TransactionType
        FROM Transactions
        WHERE EXTRACT(MONTH FROM TransactionDate) = EXTRACT(MONTH FROM SYSDATE)
          AND EXTRACT(YEAR FROM TransactionDate) = EXTRACT(YEAR FROM SYSDATE);

BEGIN

    DBMS_OUTPUT.PUT_LINE('Monthly Transaction Statement');

    FOR rec IN GenerateMonthlyStatements LOOP

        DBMS_OUTPUT.PUT_LINE(
            'Transaction ID: ' || rec.TransactionID ||
            ', Account ID: ' || rec.AccountID ||
            ', Amount: ' || rec.Amount ||
            ', Type: ' || rec.TransactionType ||
            ', Date: ' || TO_CHAR(rec.TransactionDate,'DD-MON-YYYY')
        );

    END LOOP;

END;
/

-- Scenario 2: Apply Annual Maintenance Fee

DECLARE

    CURSOR ApplyAnnualFee IS

        SELECT AccountID
        FROM Accounts;

BEGIN

    FOR rec IN ApplyAnnualFee LOOP

        UPDATE Accounts
        SET Balance = Balance - 100
        WHERE AccountID = rec.AccountID;

    END LOOP;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Annual Maintenance Fee Applied');

END;
/

-- Scenario 3: Update Loan Interest Rates

DECLARE

    CURSOR UpdateLoanInterestRates IS

        SELECT LoanID,
               InterestRate
        FROM Loans;

BEGIN

    FOR rec IN UpdateLoanInterestRates LOOP

        UPDATE Loans
        SET InterestRate = rec.InterestRate + 0.5
        WHERE LoanID = rec.LoanID;

    END LOOP;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Loan Interest Rates Updated');

END;
/