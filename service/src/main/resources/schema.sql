DROP TABLE IF EXISTS compilation_events CASCADE;

DROP TABLE IF EXISTS compilations CASCADE;

DROP TABLE IF EXISTS participation_requests CASCADE;

DROP TABLE IF EXISTS events CASCADE;

DROP TABLE IF EXISTS locations CASCADE;

DROP TABLE IF EXISTS categories CASCADE;



CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255)                            NOT NULL,
    email VARCHAR(512)                            NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS locations
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat FLOAT                                   NOT NULL,
    lon FLOAT                                   NOT NULL,
    CONSTRAINT pk_locations PRIMARY KEY (id),
    CONSTRAINT UQ_LOCATION_LAT_LON UNIQUE (lat, lon)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000)                           NOT NULL,
    category_id        BIGINT                                  NOT NULL,
    confirmed_requests INTEGER                                 NULL,
    created_on         TIMESTAMP WITH TIME ZONE                NOT NULL,
    description        VARCHAR(7000)                           NOT NULL,
    event_date         TIMESTAMP WITH TIME ZONE                NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    location_id        BIGINT                                  NOT NULL,
    paid               BOOLEAN                                 NOT NULL,
    participant_limit  INT                                     NOT NULL,
    published_on       TIMESTAMP WITH TIME ZONE                NULL,
    request_moderation BOOLEAN                                 NOT NULL,
    state              VARCHAR(255)                            NOT NULL,
    title              VARCHAR(120)                            NOT NULL,
    views              INTEGER                                 NULL,
    CONSTRAINT pk_events PRIMARY KEY (id),
    CONSTRAINT EVENTS_TO_USERS_FK FOREIGN KEY (initiator_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT EVENTS_TO_LOCATIONS_FK FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE,
    CONSTRAINT EVENTS_TO_CATEGORIES_FK FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS participation_requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    status       VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_participation_requests PRIMARY KEY (id),
    CONSTRAINT PARTICIPATION_REQUEST_TO_EVENTS_FK FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT PARTICIPATION_REQUEST_TO_USERS_FK FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT UQ_PARTICIPATION_REQUEST_EVENT_REQUESTER UNIQUE (event_id, requester_id)

);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN                                 NOT NULL,
    title  VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_compilations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilation_events
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id       BIGINT                                  NOT NULL,
    compilation_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_compilation_events PRIMARY KEY (id),
    CONSTRAINT COMPILATION_EVENTS_TO_EVENTS_FK FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT COMPILATION_EVENTS_TO_COMPILATIONS_FK FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE
);
