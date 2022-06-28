CREATE SCHEMA WALLET_CONTROL;

CREATE SEQUENCE WALLET_CONTROL.ID_WALLET_SEQ;

CREATE TABLE WALLET_CONTROL.WALLET (
ID_WALLET INTEGER NOT NULL DEFAULT NEXTVAL ('WALLET_CONTROL.ID_WALLET_SEQ'),
OWNER_NAME CHARACTER VARYING(50) NOT NULL,
WALLET_VALUE NUMERIC(10,2) NOT NULL,
LAST_PAYMENT TIMESTAMP NOT NULL,
DAYTIME_PAYMENT NUMERIC(10,2) NOT NULL,
NOCTURNAL_PAYMENT NUMERIC(10,2) NOT NULL,
WEEKEND_PAYMENT NUMERIC(10,2) NOT NULL,
PRIMARY KEY (ID_WALLET)
);