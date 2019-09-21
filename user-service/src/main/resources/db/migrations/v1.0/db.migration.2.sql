--liquibase formatted sql

--changeset toxa108:2
CREATE TABLE poker_wallet
(
     id BINARY(16) NOT NULL PRIMARY KEY
   , amount DECIMAL(19,8) NOT NULL
)
;
--rollback drop table poker_wallet;