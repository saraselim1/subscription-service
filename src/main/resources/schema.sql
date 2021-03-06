CREATE TABLE CARD_INFO(
  ID BIGSERIAL PRIMARY KEY,
  CARD_NUMBER VARCHAR(24) NOT NULL UNIQUE,
  USER_NUMBER VARCHAR(24) NOT NULL,
  EXPR_DATE VARCHAR(5) NOT NULL
);

CREATE TABLE SUBSCRIPTION(
  ID BIGSERIAL PRIMARY KEY,
  USER_NUMBER VARCHAR(24) NOT NULL UNIQUE,
  RENEWAL_DATE DATE NOT NULL,
  PLAN_ID VARCHAR(4) NOT NULL,
  PAID BOOLEAN NOT NULL
);

CREATE TABLE SUBSCRIPTION_PLAN(
  ID BIGSERIAL PRIMARY KEY,
  PRICE NUMBER NOT NULL,
  DURATION VARCHAR(50) NOT NULL,
  SUBSCRIPTION_TYPE VARCHAR(50) NOT NULL
);
