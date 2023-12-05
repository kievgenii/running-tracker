CREATE SEQUENCE IF NOT EXISTS run_id_seq START 1;

CREATE TABLE IF NOT EXISTS "runs"
(
    "id"                    BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('run_id_seq'),
    "user_id"               BIGINT constraint user_id_fkey references users,
    "start_latitude"        double precision,
    "finish_latitude"       double precision,
    "start_longitude"       double precision,
    "finish_longitude"      double precision,
    "distance"              double precision,
    "avg_speed"             double precision,
    "start_date_time"       timestamp,
    "finish_date_time"      timestamp
) WITH (OIDS = FALSE);


