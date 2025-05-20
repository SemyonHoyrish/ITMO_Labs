--- DROP SECTION ---
--- APPLICATION ---
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS ownership CASCADE;
--- DOMAINS ---
DROP TABLE IF EXISTS study_group CASCADE;
DROP TABLE IF EXISTS person CASCADE;
DROP TABLE IF EXISTS coordinates CASCADE;

DROP TYPE IF EXISTS semester CASCADE;
DROP TYPE IF EXISTS form_of_education CASCADE;
DROP TYPE IF EXISTS country CASCADE;
DROP TYPE IF EXISTS color CASCADE;
--- SECTION END ---


--- DOMAIN SECTION ---
CREATE TYPE semester as ENUM ('FIRST', 'SECOND', 'FOURTH', 'SIXTH', 'SEVENTH');
CREATE TYPE form_of_education AS ENUM ('DISTANCE_EDUCATION', 'FULL_TIME_EDUCATION', 'EVENING_CLASSES');
CREATE TYPE country as ENUM ('INDIA', 'RUSSIA', 'VATICAN');
CREATE TYPE color as ENUM ('GREEN', 'RED', 'YELLOW', 'BROWN', 'BLACK');


CREATE TABLE coordinates (
    id serial PRIMARY KEY,
    x float,
    y double precision
);

CREATE TABLE person (
    id serial PRIMARY KEY,
    name varchar,
    weight double precision,
    eye_color color,
    hair_color color,
    nationality country
);

CREATE TABLE study_group (
    id serial PRIMARY KEY,
    name varchar,
    coordinates_id integer REFERENCES coordinates ON DELETE CASCADE,
    creation_date timestamp,
    students_count bigint,
    should_be_expelled bigint,
    form_of_education form_of_education,
    semester semester,
    group_admin_id integer REFERENCES person ON DELETE CASCADE
);
--- SECTION END ---

--- APPLICATION SECTION ---
CREATE TABLE account (
    id serial PRIMARY KEY,
    login varchar UNIQUE NOT NULL,
    password_hash bytea,
    salt varchar
);

CREATE TABLE ownership (
    account_id integer REFERENCES account,
    object_id integer UNIQUE REFERENCES study_group ON DELETE CASCADE,
    PRIMARY KEY (account_id, object_id)
)
--- SECTION END ---

