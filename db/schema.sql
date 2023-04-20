DROP TABLE IF EXISTS research.shortcut.person CASCADE;
DROP TABLE IF EXISTS research.shortcut.url CASCADE;

CREATE TABLE shortcut.person
(
    person_id SERIAL PRIMARY KEY,
    username  varchar(255),
    password  varchar(255),
    status    BOOLEAN DEFAULT FALSE
);

CREATE TABLE shortcut.url
(
    encode_url  varchar(255) PRIMARY KEY,
    address_url varchar(255),
    total       int4,
    person_id   BIGINT,
    CONSTRAINT url_person_id_fk FOREIGN KEY (person_id) REFERENCES research.shortcut.person (person_id)
);

