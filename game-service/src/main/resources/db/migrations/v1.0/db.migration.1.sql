--liquibase formatted sql

--changeset toxa108:game_service_1
CREATE TABLE poker_tournament
(
     id BINARY(16) NOT NULL PRIMARY KEY
   , type TINYINT NOT NULL
   , createTime DATETIME NOT NULL
   , closeTime DATETIME NULL
)
-- WITH (SYS TEM_VERSIONING = ON)
;
--rollback drop table poker_tournament;