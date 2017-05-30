# ExpenseTracker
## Steps 
1. Clone the project from github.
2. Import the project into IDE as a 'Existing Maven Project'. 
3. Build the project.
4. In the IDE select `Run As` -> `Run on server`. 
5. Create a database called 'expensetracker' in a local mysql database.
6. Run the `schema.sql`. 
  Ex:- `mysql -u username -p <db-name> < schema.sql`;
7. In `src/hibernate.cfg.xml` file, set `connection.username` and `connection.password` property to the local db username and password. 
8. Start the Tomcat server on localhost port 8080.
9. `mvn clean install` will build the project.
10. Enter `http://localhost:8080/ExpenseTracker/login.html` in a broswer. 
11. `Register` a user.
12. Enter the user credentials and `Login`.
13. Add, List, Delete or Update expenses belonging to the logged in user.

## Execution Flow Chart
  ![alt tag](https://github.com/rahulredd/ExpenseTracker/blob/master/flowchart.png)
  
## Things Incomplete
1. Admin user access.
2. Integration testing using automated tools. 

## Highlights
1. Completely abstracting out the underlying database using Hibernate.
2. Establishing OneToMany relationship between User table and Expense table.
3. Using in memory db (h2) for testing.
4. Strictly following MVC architecture. 

##  Tools and Technologies Used
1. Hibernate 5.0
2. Junit 4.8
3. MySQL 5.1.6
4. Jbcrypt 0.3m
5. Java 7
6. H2 database 1.4
7. Slf4j 1.6.6
8. Maven 3.3
