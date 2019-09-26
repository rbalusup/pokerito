--liquibase formatted sql

--changeset toxa108:table_service_1
CREATE TABLE poker_table
(
     id BINARY(16) NOT NULL PRIMARY KEY
   , gameId BINARY(16) NOT NULL
   , createTime DATETIME NOT NULL
   , closeTime DATETIME NULL
   , players TINYINT NOT NULL
)
-- WITH (SYS TEM_VERSIONING = ON)
;
--rollback drop table poker_table;