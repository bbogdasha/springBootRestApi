# Spring REST API

REST is a style of software architecture for distributed systems. RESTful web services in Java use the Java State Preservation API to communicate with databases. 
This program implements standard CRUD commands and tests the service and controller for correct operation

### Tech
* JDK 1.8;
* Spring Boot;
* Spring MVC;
* Database H2;
* JSON.

### Functions 
* POST - create new employee;
* PUT - update exists employee;
* GET - get all employees;
* GET - get an existing employee by id;
* GET - get an existing employee by email;
* GET - get an existing employees by name;
* GET - get an existing top 3 employees by salary;
* DELETE - delete an esisting employee by id.

### Result
Starting the program sets up the initial employees in database.

![screenshot](https://github.com/bbogdasha/springBootRestApi/blob/main/screen/db.png)

---

**1. POST - create new employee**

POST JSON: 
```json
{
    "name": "Janny",
    "email": "janny@gmail.com",
    "salary": "1200.23"
}
```

Got a response status 201 and:
```json
{
    "id": 7,
    "name": "Janny",
    "email": "janny@gmail.com",
    "salary": 1200.23
}
```

---

**2. GET - get all employees**

Got a response status 200 and:
```json
[
    {
        "id": 1,
        "name": "Carl",
        "email": "carl@mail.com",
        "salary": 2455.22998046875
    },
    {
        "id": 2,
        "name": "Johny",
        "email": "johny@mail.com",
        "salary": 1400.530029296875
    },
    {
        "id": 3,
        "name": "Emily",
        "email": "emily@mail.com",
        "salary": 1540.1300048828125
    },
    {
        "id": 4,
        "name": "Bob",
        "email": "bob@mail.com",
        "salary": 3450.77001953125
    },
    {
        "id": 5,
        "name": "Ann",
        "email": "ann@mail.com",
        "salary": 1899.77001953125
    },
    {
        "id": 6,
        "name": "Johny",
        "email": "jojo@mail.com",
        "salary": 2160.77001953125
    },
    {
        "id": 7,
        "name": "Janny",
        "email": "janny@gmail.com",
        "salary": 1200.22998046875
    }
]
```

---

**3. GET - get an existing employee by id**

Got an employee by 6 id:
```json
{
    "id": 6,
    "name": "Johny",
    "email": "jojo@mail.com",
    "salary": 2160.77001953125
}
```

---

**4. GET - get an existing employee by email**

Got an employee by janny@gmail .com email:
```json
{
    "id": 7,
    "name": "Janny",
    "email": "janny@gmail.com",
    "salary": 1200.22998046875
}
```

---

**5. GET - get an existing employees by name**

Got employees by Johny name:
```json
[
    {
        "id": 2,
        "name": "Johny",
        "email": "johny@mail.com",
        "salary": 1400.530029296875
    },
    {
        "id": 6,
        "name": "Johny",
        "email": "jojo@mail.com",
        "salary": 2160.77001953125
    }
]
```

---

** 6. GET - get an existing top 3 employees by salary**

Got top 3 employees by salary:
```json
[
    {
        "id": 4,
        "name": "Bob",
        "email": "bob@mail.com",
        "salary": 3450.77001953125
    },
    {
        "id": 1,
        "name": "Carl",
        "email": "carl@mail.com",
        "salary": 2455.22998046875
    },
    {
        "id": 6,
        "name": "Johny",
        "email": "jojo@mail.com",
        "salary": 2160.77001953125
    }
]
```

---

7. DELETE - delete existing employee**

Deleted employee by 5 id and got status 200.

---

**Testing of service and controller levels**

<img src="https://github.com/bbogdasha/springBootRestApi/blob/main/screen/tests.png" width="500" />
