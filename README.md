# DD2480group17
# Continuous Integration Server
Here is a continuous integration (CI) server that is triggered when a user pushes to the 'assessment' branch.  The CI
server improves software development processes by utilizing webhooks to trigger compilation, automated unit testing, and
notification of CI results.  These CI build results will allow teams to track the success or failure of each push. 

## Notification implementation
- gmail account created specifically for sending notifications
- sends mail to specified recipient through gmail servers
- tested by generating a random number, sending it to the
recipient and verifying that number has been recieved

# File Structure
Source code is located in LAB2group17/src/main/java
Test cases are located in LAB2group17/src/test/java

# Dependencies
* Java SDK version 11.0.2

## How to Build
```
git clone https://github.com/starkelove/LAB2group17.git
cd LAB2group17
gradle build
```

## How to Run
```
gradle run
```
In the second terminal
```
./runserver.sh [The port to be used] (e.g. ./runserver.sh 8080)
```

# How to Test
```
./gradlew test
```

# Contributions
* Anton
* Caroline
* Love
* Morgan
* Victor
