CREATE TABLE students_courses (
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    CONSTRAINT students_courses_pkey PRIMARY KEY (student_id, course_id),
    CONSTRAINT students_courses_student_id_fkey FOREIGN KEY (student_id) REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT students_courses_course_id_fkey FOREIGN KEY (course_id) REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE
);