
DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups (
    id INTEGER auto_increment,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS students CASCADE;
CREATE TABLE students (
    id INTEGER,
    first_name VARCHAR(50) NOT NULL, 
    last_name VARCHAR(50) NOT NULL,
    group_id INTEGER DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE SET DEFAULT
);

DROP TABLE IF EXISTS courses CASCADE;
CREATE TABLE courses (
    id INTEGER auto_increment,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS students_courses;
create table students_courses (
    student_id INTEGER,
    course_id  INTEGER,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses (id) ON DELETE CASCADE
);

