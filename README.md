# SchoolApp

### A few words about the project

SchooleApp is a study project for working with Java **JDBC** and **H2** database and also manipulation data in tables

### Tools and technologies
- Java 11
- H2 database
- JUnit 5
- Maven

### Setup

1. get clone: https://github.com/DennisYudin/SchoolApp.git
2. build project in working dir: mvn clean install
3. open terminal
4. go to the dir with jar file and run command: java -jar SchoolApp-jar-with-dependencies.jar
5. after that you should see welcome window like: ![image](https://github.com/DennisYudin/SchoolApp/assets/79792162/f2e30808-7c0c-49a6-9f79-cd44c5c50a39)



_*Note that In project **in-memory database** was used and you dont need to do anything else_
_.All database's tables will be created and populated with init data automatically_

### Database structure 
![image](https://github.com/DennisYudin/SchoolApp/assets/79792162/51afa3d3-7fda-4870-93be-9b51308a192b)

### Init Data 
What we have:
  - 200 unique students
  - 10 unique groups
  - 10 unique courses
