DROP TABLE IF EXISTS endpoint_hits;

CREATE TABLE IF NOT EXISTS endpoint_hits
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    app       VARCHAR(255)                            NOT NULL,
    uri       VARCHAR(512)                            NOT NULL,
    ip        VARCHAR(255)                            NOT NULL,
    timestamp TIMESTAMP WITH TIME ZONE                NOT NULL,
    CONSTRAINT pk_endpoint_hits PRIMARY KEY (id)
);