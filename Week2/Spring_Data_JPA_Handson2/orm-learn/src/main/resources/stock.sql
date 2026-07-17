-- ============================================================
-- stock.sql
-- Hands-on 2: Stock table with FB, GOOGL, NFLX data
-- Handbook: Spring Data JPA Hands-on 2
-- Run in MySQL BEFORE starting the Spring Boot application.
-- ============================================================

USE ormlearn;

-- Create stock table
-- id        : Auto-increment primary key
-- st_code   : Stock ticker symbol (FB, GOOGL, NFLX)
-- st_name   : Company full name
-- st_date   : Trading date
-- st_open   : Opening price for the day
-- st_close  : Closing price for the day
-- st_volume : Number of shares traded
CREATE TABLE IF NOT EXISTS stock (
    id        INT            NOT NULL AUTO_INCREMENT,
    st_code   VARCHAR(10)    NOT NULL,
    st_name   VARCHAR(50)    NOT NULL,
    st_date   DATE           NOT NULL,
    st_open   DECIMAL(10,2)  NOT NULL,
    st_close  DECIMAL(10,2)  NOT NULL,
    st_volume BIGINT         NOT NULL,
    PRIMARY KEY (id)
);

-- Clear existing data
DELETE FROM stock;

-- ── Facebook (FB) Stock Data ──────────────────────────────────────────────
INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('FB', 'Facebook', '2018-01-02', 177.09, 181.42, 18151900);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('FB', 'Facebook', '2018-01-03', 181.88, 184.67, 16886600);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('FB', 'Facebook', '2018-01-04', 184.10, 184.33, 13880900);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('FB', 'Facebook', '2018-01-05', 185.61, 186.85, 13574600);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('FB', 'Facebook', '2018-01-08', 187.20, 188.28, 17994700);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('FB', 'Facebook', '2018-01-09', 188.29, 188.00, 12393400);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('FB', 'Facebook', '2018-01-10', 188.67, 190.16, 10529400);

-- ── Google / Alphabet (GOOGL) Stock Data ─────────────────────────────────
INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('GOOGL', 'Alphabet Inc', '2018-01-02', 1048.34, 1065.00, 1296900);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('GOOGL', 'Alphabet Inc', '2018-01-03', 1070.00, 1082.48, 1414500);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('GOOGL', 'Alphabet Inc', '2018-01-04', 1101.62, 1102.23, 1345200);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('GOOGL', 'Alphabet Inc', '2018-01-05', 1108.29, 1102.61, 1234100);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('GOOGL', 'Alphabet Inc', '2018-01-08', 1106.30, 1106.94, 1234400);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('GOOGL', 'Alphabet Inc', '2018-01-09', 1109.40, 1106.26, 1109700);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('GOOGL', 'Alphabet Inc', '2018-01-10', 1108.29, 1102.61, 1220500);

-- ── Netflix (NFLX) Stock Data ─────────────────────────────────────────────
INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('NFLX', 'Netflix', '2018-01-02', 196.10, 201.07, 7562100);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('NFLX', 'Netflix', '2018-01-03', 202.00, 205.05, 5765500);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('NFLX', 'Netflix', '2018-01-04', 206.50, 217.96, 7885700);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('NFLX', 'Netflix', '2018-01-05', 222.00, 223.00, 6716100);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('NFLX', 'Netflix', '2018-01-08', 225.00, 226.19, 5765400);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('NFLX', 'Netflix', '2018-01-09', 227.00, 226.51, 5512800);

INSERT INTO stock (st_code, st_name, st_date, st_open, st_close, st_volume)
VALUES ('NFLX', 'Netflix', '2018-01-10', 228.32, 230.85, 5264000);

-- Verify
SELECT st_code, COUNT(*) AS entries FROM stock GROUP BY st_code;
