# employee_db
This repo includes simple spring boot REST APIs to manage employee DB

Curl requests for the Rest APIs:

**create employees**

curl --location 'http://localhost:8080/employees' \
--header 'Content-Type: application/json' \
--data '{
    "firstName": "John",
    "lastName": "Nash",
    "dateOfBirth": "1991-01-01",
    "salary": "4050",
    "joinDate": "2016-03-18",
    "departement": "Business"
}
'

**get employee by id**

curl --location 'http://localhost:8080/employees/3' \
--header 'Content-Type: application/json' \
--data ''

**get employee by name**

curl --location 'http://localhost:8080/employees?name=moh' \
--header 'Content-Type: application/json' \
--data ''

**get employee by salary** 

curl --location 'http://localhost:8080/employees?fromSalary=3000&toSalary=9000' \
--header 'Content-Type: application/json' \
--data ''


The db data will be saved in the file **db_folder\employees_db**
