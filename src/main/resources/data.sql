DROP TABLE IF EXISTS Employee;

CREATE TABLE Employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    salary FLOAT(10)
);

INSERT INTO Employee (name, email, salary) VALUES
    ( 'Carl', 'carl@mail.com', 2455.23 ),
    ( 'Johny', 'johny@mail.com', 1400.53 ),
    ( 'Emily', 'emily@mail.com', 1540.13 ),
    ( 'Bob', 'bob@mail.com', 3450.77 ),
    ( 'Ann', 'ann@mail.com', 1899.77 ),
    ( 'Johny', 'jojo@mail.com', 2160.77 );