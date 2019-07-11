# RestService

### Reference Documentation
For further reference, please consider the following sections:
Requirements for the project are at the very bottom

####Security Details
Username: user

Password: password

###Usage: 
#####move to parent folder
cd restservice

#####build with maven
mvn clean install

#####move to target folder
cd target

#####execute the program
java -jar lendingbackend-1.0.jar

###Sending Messages

Make a post call to localhost:8080

example with curl (in windows please use escape characters as shown as {"} symbol causes issues, text form of this file has the windows version)

curl -u user:password --header "Content-Type: application/json" --request POST --data "{\"amount\":\"65344363\",\"term\":36}" http://localhost:8080 

or with https://www.getpostman.com/

if using postman remember to enter username and password (user:password) and select JSON(Application/json as message body), escape characters are not required in postman

{"amount":65344363, "term":36}

###Getting History

curl -u user:password --header "Content-Type: application/json" --request GET http://localhost:8080/history

with postman just make a get request at http://localhost:8080/history
(you will still need username and password (user:password))

###Database
H2 database is available at address below, you will be asked to enter username and password if you use a browser to access it (user:password)

localhost:8080/console/

for h2 database
username: sa, there is no password

##Application of Requirements

###Business requirements

● User can authenticate and enter amount and duration to get a loan

● Request is denied if it is between 0-6 am or 3 requests from same ip has been made

● Result is returned either as a reference or an error message

● User can get a history of past transactions

###Technical requirements
● Must be created with java 8, java based config and jpa, using restful api, embedded database

● Has unit and integration tests