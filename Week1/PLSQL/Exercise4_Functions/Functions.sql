SET SERVEROUTPUT ON;

-- Scenario 1: Calculate Customer Age

CREATE OR REPLACE FUNCTION CalculateAge(

    p_dob DATE

)

RETURN NUMBER

IS

    v_age NUMBER;

BEGIN

    v_age := FLOOR(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);

    RETURN v_age;

END;
/

DECLARE

    v_age NUMBER;

BEGIN

    SELECT CalculateAge(DOB)

    INTO v_age

    FROM Customers

    WHERE CustomerID = 1;

    DBMS_OUTPUT.PUT_LINE('Customer Age : ' || v_age);

END;
/

-- Scenario 2: Calculate Monthly Installment

CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment(

    p_loanAmount NUMBER,
    p_interestRate NUMBER,
    p_years NUMBER

)

RETURN NUMBER

IS

    v_monthlyInstallment NUMBER;

BEGIN

    v_monthlyInstallment :=
        (p_loanAmount +
        (p_loanAmount * p_interestRate / 100))
        / (p_years * 12);

    RETURN ROUND(v_monthlyInstallment,2);

END;
/

DECLARE

    v_installment NUMBER;

BEGIN

    v_installment := CalculateMonthlyInstallment(

        500000,
        10,
        5

    );

    DBMS_OUTPUT.PUT_LINE(
        'Monthly Installment : ' ||
        v_installment
    );

END;
/

-- Scenario 3: Check Sufficient Balance

CREATE OR REPLACE FUNCTION HasSufficientBalance(

    p_accountId NUMBER,
    p_amount NUMBER

)

RETURN BOOLEAN

IS

    v_balance NUMBER;

BEGIN

    SELECT Balance

    INTO v_balance

    FROM Accounts

    WHERE AccountID = p_accountId;

    RETURN v_balance >= p_amount;

END;
/

DECLARE

    v_result BOOLEAN;

BEGIN

    v_result := HasSufficientBalance(1,500);

    IF v_result THEN

        DBMS_OUTPUT.PUT_LINE(
            'Sufficient Balance'
        );

    ELSE

        DBMS_OUTPUT.PUT_LINE(
            'Insufficient Balance'
        );

    END IF;

END;
/