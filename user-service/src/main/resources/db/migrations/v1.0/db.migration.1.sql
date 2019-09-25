--liquibase formatted sql

--changeset toxa108:user_service_1
CREATE TABLE poker_user
(
     id BINARY(16) NOT NULL PRIMARY KEY
   , email varchar(100) NOT NULL
   , login varchar(30) NOT NULL
   , password varchar(50) NOT NULL
   , walletId BINARY(16) NOT NULL
)
-- WITH (SYS TEM_VERSIONING = ON)
;
--rollback drop table poker_user;