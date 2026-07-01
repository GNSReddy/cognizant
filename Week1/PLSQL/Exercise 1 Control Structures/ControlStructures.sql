SET SERVEROUTPUT ON;


-- Scenario 1: Apply 1% Discount for Customers Above 60


BEGIN
    FOR rec IN (
        SELECT l.LoanID
        FROM Loans l
        JOIN Customers c
            ON l.CustomerID = c.CustomerID
        WHERE FLOOR(MONTHS_BETWEEN(SYSDATE, c.DOB) / 12) > 60
    )
    LOOP
        UPDATE Loans
        SET InterestRate = InterestRate - 1
        WHERE LoanID = rec.LoanID;

        DBMS_OUTPUT.PUT_LINE('Discount applied to Loan ID: ' || rec.LoanID);
    END LOOP;

    COMMIT;
END;
/


-- Scenario 2: Promote Customers to VIP

ALTER TABLE Customers
ADD IsVIP VARCHAR2(5);

BEGIN
    FOR rec IN (
        SELECT CustomerID, Balance
        FROM Customers
    )
    LOOP
        IF rec.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'TRUE'
            WHERE CustomerID = rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE(
                'Customer ID ' || rec.CustomerID || ' promoted to VIP'
            );
        END IF;
    END LOOP;

    COMMIT;
END;
/

-- Scenario 3: Loan Due Reminder


BEGIN
    FOR rec IN (
        SELECT c.Name,
               l.LoanID,
               l.EndDate
        FROM Loans l
        JOIN Customers c
            ON l.CustomerID = c.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
    )
    LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Customer ' || rec.Name ||
            ', Loan ID ' || rec.LoanID ||
            ' is due on ' || TO_CHAR(rec.EndDate, 'DD-MON-YYYY')
        );
    END LOOP;
END;
/