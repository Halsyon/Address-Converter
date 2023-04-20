insert into person (person_id, username, password)
values (1, 'admin', '$2a$10$BSmGhQ2XVFOD7YWDOjBTyOHVoOzhA1yw69Pp2fEYXqP73.YQgGiwu'),
       (2, 'admin1', '$2a$10$LAmXtybMFiDujHkTXdN5.e3CpZDiPVV/egC.L4/NpWyu8Hhnksj32');


insert into url (encode_url, address_url, total, person_id)
values ('21ec382d', 'https://www.baeldung.com/hibernate-identifiers', 1,
        (select person.person_id from person where person_id = 1)),
       ('4b7a2a11', 'https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html#put-K-V-', 0,
        (select person.person_id from person where person_id = 1));