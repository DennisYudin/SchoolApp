DROP SEQUENCE IF EXISTS groups_group_id_sequence CASCADE;
CREATE SEQUENCE groups_group_id_sequence
START 100
INCREMENT 1;

DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups (
    group_id INT DEFAULT nextval('groups_group_id_sequence'), 
    group_name VARCHAR(50) NOT NULL,
    PRIMARY KEY(group_id)
);

DROP SEQUENCE IF EXISTS students_student_id_sequence CASCADE;
CREATE SEQUENCE students_student_id_sequence
START 200
INCREMENT 1;

DROP TABLE IF EXISTS students CASCADE;
CREATE TABLE students (
    student_id INT DEFAULT nextval('students_student_id_sequence'), 
    first_name VARCHAR(50) NOT NULL, 
    last_name VARCHAR(50) NOT NULL,
    group_id INT DEFAULT NULL,
    PRIMARY KEY (student_id),
    FOREIGN KEY (group_id) REFERENCES groups (group_id) ON DELETE SET DEFAULT
);

DROP SEQUENCE IF EXISTS courses_course_id_sequence CASCADE;
CREATE SEQUENCE courses_course_id_sequence
START 600
INCREMENT 1;

DROP TABLE IF EXISTS courses CASCADE;
CREATE TABLE courses (
    course_id INT DEFAULT nextval('courses_course_id_sequence'), 
    course_name VARCHAR(50) NOT NULL, 
    course_description VARCHAR(50) NOT NULL,
    PRIMARY KEY(course_id)
);

DROP TABLE IF EXISTS students_courses;
create table students_courses (
    student_id INT,
    course_id  INT,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students (student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses (course_id) ON DELETE CASCADE
);

