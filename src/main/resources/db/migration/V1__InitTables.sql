CREATE TABLE groups (
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (id),
    CONSTRAINT groups_name_ukey UNIQUE (name)
);
INSERT INTO groups(id, name) VALUES (0, 'DEFAULT GROUP');

CREATE TABLE students (
    id SERIAL NOT NULL,
    group_id INT NOT NULL DEFAULT (0),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (id),
    CONSTRAINT students_group_fkey FOREIGN KEY (group_id) REFERENCES groups (id)
);

CREATE TABLE courses (
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (id),
    CONSTRAINT courses_name_ukey UNIQUE (name)
);