CREATE SEQUENCE IF NOT EXISTS user_id_seq START 1;

CREATE TABLE IF NOT EXISTS "users"
(
    "id"                    BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('user_id_seq'),
    "first_name"            varchar(255),
    "last_name"             varchar(255),
    "birth_date"            date,
    "sex"                   varchar(255),
    "created_at"            TIMESTAMP
) WITH (OIDS = FALSE);
