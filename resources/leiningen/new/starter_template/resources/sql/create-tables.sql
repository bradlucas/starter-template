-- ----------------------------------------------------------------------------------------------------
-- Account

DROP TABLE IF EXISTS account;

CREATE TABLE account (
       id serial not null,
       email varchar(100) not null,
       password varchar(100) not null,
       timestamp timestamp default current_timestamp,
       UNIQUE(email)
);

